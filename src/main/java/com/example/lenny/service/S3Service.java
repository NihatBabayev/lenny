package com.example.lenny.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;

@Service
public interface S3Service {
    void uploadPhoto(String photoName, MultipartFile file) throws IOException;

    String getObjectFromS3AsBase64(String fullPath);
}
