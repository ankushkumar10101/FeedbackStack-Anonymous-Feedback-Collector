package com.feedbackstack.backend.controller;

import com.feedbackstack.backend.model.Feedback;
import com.feedbackstack.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/") // Context path is /api, so this handles /api/
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("feedback/")
    public org.springframework.http.ResponseEntity<?> submitFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackService.saveFeedback(feedback);
            return org.springframework.http.ResponseEntity.ok(savedFeedback);
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.internalServerError()
                    .body("Error saving feedback: " + e.getMessage());
        }
    }

    @GetMapping("analytics/")
    public Map<String, Object> getAnalytics() {
        return feedbackService.getAnalytics();
    }

    @GetMapping("test-db")
    public String testDbConnection() {
        try {
            long count = feedbackService.countFeedback();
            return "Database Connection OK! Feedback count: " + count;
        } catch (Exception e) {
            return "Database Connection FAILED: " + e.getMessage();
        }
    }
}
