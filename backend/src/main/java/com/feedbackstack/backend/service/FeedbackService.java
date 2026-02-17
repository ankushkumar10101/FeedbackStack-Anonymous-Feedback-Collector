package com.feedbackstack.backend.service;

import com.feedbackstack.backend.model.Feedback;
import com.feedbackstack.backend.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public long countFeedback() {
        return feedbackRepository.count();
    }

    public Map<String, Object> getAnalytics() {
        long totalFeedback = feedbackRepository.count();
        Double averageRating = getAverageRating();
        List<CategoryStats> categoryBreakdown = getCategoryBreakdown();

        return Map.of(
                "total_feedback", totalFeedback,
                "average_rating", averageRating != null ? Math.round(averageRating * 10.0) / 10.0 : 0.0,
                "category_breakdown", categoryBreakdown);
    }

    private Double getAverageRating() {
        Aggregation aggregation = newAggregation(
                group().avg("rating").as("avgRating"));
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "feedback", Map.class);
        Map<String, Object> result = results.getUniqueMappedResult();
        return result != null ? (Double) result.get("avgRating") : 0.0;
    }

    private List<CategoryStats> getCategoryBreakdown() {
        List<Feedback> allFeedback = feedbackRepository.findAll();

        Map<String, List<Feedback>> byCategory = allFeedback.stream()
                .collect(Collectors.groupingBy(Feedback::getCategory));

        return byCategory.entrySet().stream().map(entry -> {
            String category = entry.getKey();
            List<Feedback> list = entry.getValue();

            CategoryStats stats = new CategoryStats();
            stats.setCategory(category);
            stats.setFeedback_count(list.size());
            stats.setAverage_rating(
                    Math.round(list.stream().mapToInt(Feedback::getRating).average().orElse(0.0) * 10.0) / 10.0);
            stats.setComments(list.stream().map(Feedback::getMessage).collect(Collectors.toList()));
            stats.setSuggestions(list.stream()
                    .map(Feedback::getSuggestion)
                    .filter(s -> s != null && !s.isEmpty())
                    .collect(Collectors.toList()));
            return stats;
        }).collect(Collectors.toList());
    }

    public static class CategoryStats {
        private String category;
        private int feedback_count;
        private double average_rating;
        private List<String> comments;
        private List<String> suggestions;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getFeedback_count() {
            return feedback_count;
        }

        public void setFeedback_count(int feedback_count) {
            this.feedback_count = feedback_count;
        }

        public double getAverage_rating() {
            return average_rating;
        }

        public void setAverage_rating(double average_rating) {
            this.average_rating = average_rating;
        }

        public List<String> getComments() {
            return comments;
        }

        public void setComments(List<String> comments) {
            this.comments = comments;
        }

        public List<String> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<String> suggestions) {
            this.suggestions = suggestions;
        }
    }
}
