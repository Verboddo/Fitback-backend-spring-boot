package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.models.FileDB;
import com.smeekens.fitback.fitback.fitback.repository.FeedbackRepository;
import com.smeekens.fitback.fitback.fitback.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FileDBRepository fileDBRepository;

    public Long createFeedback(Feedback feedback) {
        Feedback newFeedback = feedbackRepository.save(feedback);

        return newFeedback.getId();
    }

}
