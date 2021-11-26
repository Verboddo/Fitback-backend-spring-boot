package com.smeekens.fitback.fitback.controllerTest;

import com.smeekens.fitback.fitback.fitback.controllers.FeedbackController;
import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.repository.FeedbackRepository;
import com.smeekens.fitback.fitback.fitback.repository.FileDBRepository;
import com.smeekens.fitback.fitback.fitback.security.services.FeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(FeedbackController.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value = FeedbackController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @Configuration
    @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
    static class ContextConfiguration {
    }

    @Test
    public void getAllFeedbackTest() throws Exception {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback("test"));

        Mockito
                .when(feedbackService.getAllFeedback())
                .thenReturn(feedbacks);

        mvc.perform(get("/api/feedback")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].feedback")
                    .value("test"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void deleteFeedbackTest() throws Exception {
        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setFeedback("test");

        Mockito
                .when(feedbackRepository.existsById(1L))
                        .thenReturn(true);

        mvc.perform(delete("/api/feedback/1"))
            .andExpect(status().isOk());
    }
}
