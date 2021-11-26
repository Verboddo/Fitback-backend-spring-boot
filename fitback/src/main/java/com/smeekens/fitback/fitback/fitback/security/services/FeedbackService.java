package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.exceptions.RecordNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.repository.FeedbackRepository;
import com.smeekens.fitback.fitback.fitback.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    FileDBRepository fileDBRepository;

    public Long saveFeedback(Feedback feedback) {
        Feedback newFeedback = feedbackRepository.save(feedback);
        return newFeedback.getId();
    }

    public Collection<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public void updateFeedback(Long id, Feedback newFeedback) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()) {
            Feedback feedback = feedbackRepository.findById(id).get();
            feedback.setFeedback(newFeedback.getFeedback());
            feedbackRepository.save(feedback);
        } else {
            throw new RecordNotFoundException();
        }
    }

    public void deleteFeedback(Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException();
        }

    }

}
