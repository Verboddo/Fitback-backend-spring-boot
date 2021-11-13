package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.exceptions.RecordNotFoundException;
import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.models.FileDB;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.repository.FileDBRepository;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class FileStorageService {

    private FileDBRepository fileDBRepository;
    private UserRepository userRepository;

    @Autowired
    public FileStorageService(FileDBRepository fileDBRepository, UserRepository userRepository) {
        this.fileDBRepository = fileDBRepository;
        this.userRepository = userRepository;
    }

    public FileDB uploadFile(MultipartFile multipartFile, Long id) throws IOException {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            User user = userRepository.findById(id).get();

            FileDB fileDB = new FileDB(fileName, multipartFile.getContentType(), multipartFile.getBytes());
            fileDB.setUser(user);
            userRepository.save(user);
            return fileDBRepository.save(fileDB);
    }

    public FileDB getFileById(Long id) {
        if (fileDBRepository.findById(id).isPresent()) {
            return fileDBRepository.findById(id).get();
        } else {
            throw new RecordNotFoundException(id);
        }
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public void deleteFile(Long id) {
        if (fileDBRepository.existsById(id)) {
            fileDBRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(id);
        }
    }

    public List<Feedback> getFilesFeedback(Long id) {
        Optional<FileDB> fileDB = fileDBRepository.findById(id);
        return fileDB.get().getFeedback();
    }


}
