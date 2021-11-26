package com.smeekens.fitback.fitback.fitback.controllers;

import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.security.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<Object> saveFeedback(@RequestBody Feedback feedback) {
        Long newFeedback = feedbackService.saveFeedback(feedback);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newFeedback).toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getAllFeedback() {
        return ResponseEntity.ok().body(feedbackService.getAllFeedback());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFeedback(@PathVariable("id") Long id, @RequestBody Feedback feedback) {
        feedbackService.updateFeedback(id, feedback);
        return ResponseEntity.ok("Feedback updated");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable("id") Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Feedback deleted");
    }

}
