package com.smeekens.fitback.fitback.fitback.controllers;


import com.smeekens.fitback.fitback.fitback.message.ResponseFile;
import com.smeekens.fitback.fitback.fitback.models.FileDB;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.repository.FileDBRepository;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import com.smeekens.fitback.fitback.fitback.security.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/file")
public class FilesController {

    private final FileStorageService fileStorageService;

    private final UserRepository userRepository;

    @Autowired
    private FileDBRepository fileDBRepository;

    @Autowired
    public FilesController(FileStorageService fileStorageService, UserRepository userRepository) {
        this.fileStorageService = fileStorageService;
        this.userRepository = userRepository;
    }

    // upload a file
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(authentication.getName()).get();
        FileDB fileDB = fileStorageService.uploadFile(multipartFile, user.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(fileDB).toUri();
        return ResponseEntity.created(location).build();
    }

    // get all files, only for admin
    @GetMapping("/files")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponseFile>> getAllFiles() {
        List<ResponseFile> files = fileStorageService.getAllFiles().map(fileDB -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/file/")
                    .path(String.valueOf(fileDB.getId()))
                    .toUriString();

            return new ResponseFile(
                    fileDB.getUser(),
                    fileDB.getId(),
                    fileDB.getName(),
                    fileDownloadUri,
                    fileDB.getType(),
                    fileDB.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

    // Get file by id, only for admin
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> getFileById(@PathVariable("id") Long id) {
        FileDB fileDB = fileStorageService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    // Delete file by id, only for admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteFileById(@PathVariable("id") Long id) {
        fileStorageService.deleteFile(id);
        return ResponseEntity.ok().build();
    }

}
