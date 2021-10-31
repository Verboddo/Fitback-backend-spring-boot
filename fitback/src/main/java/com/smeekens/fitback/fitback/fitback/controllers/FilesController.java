package com.smeekens.fitback.fitback.fitback.controllers;


import com.smeekens.fitback.fitback.fitback.message.ResponseFile;
import com.smeekens.fitback.fitback.fitback.models.FileDB;
import com.smeekens.fitback.fitback.fitback.models.User;
import com.smeekens.fitback.fitback.fitback.repository.UserRepository;
import com.smeekens.fitback.fitback.fitback.security.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/file")
public class FilesController {

    private final FileStorageService fileStorageService;

    private final UserRepository userRepository;

    @Autowired
    public FilesController(FileStorageService fileStorageService, UserRepository userRepository) {
        this.fileStorageService = fileStorageService;
        this.userRepository = userRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(authentication.getName()).get();
        FileDB fileDB = fileStorageService.uploadFile(multipartFile, user.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(fileDB).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getAllFiles() {
        List<ResponseFile> files = fileStorageService.getAllFiles().map(fileDB -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files")
                    .path(String.valueOf(fileDB.getId()))
                    .toUriString();

            return new ResponseFile(
                    fileDB.getName(),
                    fileDownloadUri,
                    fileDB.getType(),
                    fileDB.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable("id") Long id) {
        FileDB fileDB = fileStorageService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFileById(@PathVariable("id") Long id){
        fileStorageService.deleteFile(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFileDBById(@PathVariable("id") Long id) {
        // moet data hieronder uit krijgen
       return ResponseEntity.ok().body(fileStorageService.getFileDBById(id));
    }

}
