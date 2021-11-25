package com.smeekens.fitback.fitback.servicesTest;

import com.smeekens.fitback.fitback.FitbackApplication;
import com.smeekens.fitback.fitback.fitback.exceptions.RecordNotFoundException;
import com.smeekens.fitback.fitback.fitback.exceptions.UserNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.models.FileDB;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.repository.FileDBRepository;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import com.smeekens.fitback.fitback.fitback.security.services.FileStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = {FitbackApplication.class})
public class FileStorageServiceTest {

    @Mock
    private FileDBRepository fileDBRepository;

    @InjectMocks
    private FileStorageService fileStorageService;

    @Mock
    FileDB fileDB;

    @Mock
    Feedback feedback;

    @Test
    public void getFileByIdTest() {
      fileDB = new FileDB();
      fileDB.setId(1L);

      Mockito
              .when(fileDBRepository.findById(1L))
              .thenReturn(Optional.of(fileDB));

      FileDB found = fileStorageService.getFileById(1L);

      assertEquals(1L, found.getId());
    }

    @Test
    public void getFileByIdExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> fileStorageService.getFileById(null));
    }

    @Test
    public void deleteFileTest() {
        fileDB = new FileDB();
        fileDB.setId(1L);

        Mockito
                .doThrow(new RecordNotFoundException())
                .when(fileDBRepository)
                .delete(fileDB);

        fileStorageService.deleteFile(1L);

        verify(fileDBRepository, times(1)).deleteById(fileDB.getId());

        assertThat(fileDB.getId()).isEqualTo(1L);
    }

    @Test
    public void getFilesFeedbackTest() {
        fileDB = new FileDB();
        fileDB.setId(1L);

        List<Feedback> feedbackList = new ArrayList<>();

        feedback = new Feedback();
        feedback.setId(1L);
        feedback.setFeedback("test feedback");

        feedbackList.add(feedback);

        fileDB.setFeedback(feedbackList);

        Mockito
                .when(fileDBRepository.findById(1L))
                .thenReturn(Optional.of(fileDB));

        assertThat(fileDB.getFeedback()).isEqualTo(feedbackList);
    }

    @Test
    public void getFilesFeedbackException() {
        assertThrows(RecordNotFoundException.class, () -> fileStorageService.getFilesFeedback(null));
    }
}
