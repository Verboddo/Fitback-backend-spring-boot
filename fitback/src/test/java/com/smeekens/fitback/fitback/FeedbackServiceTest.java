package com.smeekens.fitback.fitback;

import com.smeekens.fitback.fitback.fitback.exceptions.RecordNotFoundException;
import com.smeekens.fitback.fitback.fitback.exceptions.UserNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.repository.FeedbackRepository;
import com.smeekens.fitback.fitback.fitback.security.services.FeedbackService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@ContextConfiguration(classes = {FitbackApplication.class})
public class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    Feedback feedback;

    @Test
    public void getAllFeedback() {
        List<Feedback> testFeedbackList = new ArrayList<>();
        Feedback feedback1 = new Feedback();
        feedback1.setId(1L);
        feedback1.setFeedback("dit is feedback test 1");
        Feedback feedback2 = new Feedback();
        feedback2.setId(2L);
        feedback2.setFeedback("dit is feedback test 2");
        Feedback feedback3 = new Feedback();
        feedback3.setId(3L);
        feedback3.setFeedback("dit is feedback test 3");

        testFeedbackList.add(feedback1);
        testFeedbackList.add(feedback2);
        testFeedbackList.add(feedback3);

        Mockito
                .when(feedbackRepository.findAll())
                .thenReturn(testFeedbackList);

        feedbackService.getAllFeedback();

        verify(feedbackRepository, times(1)).findAll();

        assertThat(testFeedbackList.size()).isEqualTo(3);
    }

    @Test
    public void saveFeedback() {
        feedback = new Feedback("Test Feedback");
        feedback.setId(24L);

        Mockito
                .when(feedbackRepository.save(feedback))
                .thenReturn(feedback);

        assertEquals(24L, feedbackService.saveFeedback(feedback));
        verify(feedbackRepository, Mockito.times(1)).save(feedback);
    }

    @Test
    public void deleteFeedback() {
        feedback = new Feedback();
        feedback.setId(1L);

        Mockito
                .doThrow(new RecordNotFoundException())
                .when(feedbackRepository)
                .delete(feedback);

        feedbackService.deleteFeedback(1L);

        verify(feedbackRepository, times(1)).deleteById(feedback.getId());
    }

    @Test
    public void updateFeedback() {
        feedback = new Feedback();
        feedback.setId(1L);
        feedback.setFeedback("this is test feedback");

        Mockito
                .when(feedbackRepository.findById(1L))
                .thenReturn(Optional.of(feedback));

        feedback.setFeedback("this is new test feedback");
        feedbackService.updateFeedback(1L, feedback);

        verify(feedbackRepository).save(feedback);
    }

    @Test
    public void getUpdateFeedbackException() {
        assertThrows(RecordNotFoundException.class, () -> feedbackService.updateFeedback(null, null));
    }

}