package com.ticket.uploadservice.controllers;

import com.ticket.uploadservice.dto.ResponseDto;
import com.ticket.uploadservice.services.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final UploadService uploadService;

    @Autowired
    public UploadController(final UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto> upload(@RequestBody() MultipartFile file) {
        try {
            return new ResponseEntity<>(uploadService.upload(file), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
