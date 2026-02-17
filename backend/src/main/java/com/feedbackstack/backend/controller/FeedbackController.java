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
    public Feedback submitFeedback(@RequestBody Feedback feedback) {
        return feedbackService.saveFeedback(feedback);
    }

    @GetMapping("analytics/")
    public Map<String, Object> getAnalytics() {
        return feedbackService.getAnalytics();
    }
}
