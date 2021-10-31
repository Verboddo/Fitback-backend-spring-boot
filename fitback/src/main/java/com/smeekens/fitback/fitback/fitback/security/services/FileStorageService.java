package com.smeekens.fitback.fitback.fitback.security.services;

import com.smeekens.fitback.fitback.fitback.models.FileDB;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.repository.FileDBRepository;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    @Autowired
    private UserRepository userRepository;

    public FileDB uploadFile(MultipartFile multipartFile, Long id) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        User user = userRepository.findById(id).get();

        FileDB fileDB = new FileDB(fileName, multipartFile.getContentType(), multipartFile.getBytes());
        fileDB.setUser(user);
        user.addFileDB(fileDB);
        userRepository.save(user);
        return fileDBRepository.save(fileDB);
    }

    public FileDB getFileById(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public void deleteFile(Long id) {
        fileDBRepository.deleteById(id);
    }

    public Optional<FileDB> getFileDBById(Long id) {
        return fileDBRepository.findById(id);
    }

}
