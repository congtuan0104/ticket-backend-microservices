package com.ticket.uploadservice.services;

import com.google.auth.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.storage.v2.StorageClient;
import com.ticket.uploadservice.dto.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class UploadService {
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        String bucketName = "ticket-d39f7.appspot.com";
        BlobId blobId = BlobId.of(bucketName , fileName); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = UploadService.class.getClassLoader().getResourceAsStream("firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/ticket-d39f7.appspot.com/o/%s?alt=media";
        String downloadUrl = String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        
        return downloadUrl;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public ResponseDto upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);

            String bucketName = "ticket-d39f7.appspot.com";
            BlobId blobId = BlobId.of(bucketName, fileName); // Replace with your bucker name
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
            InputStream inputStream = UploadService.class.getClassLoader().getResourceAsStream("firebase.json");
            Credentials credentials = GoogleCredentials.fromStream(inputStream);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));

            //get file information
            Blob blob = storage.get(blobId);

            String name = blob.getName();
            long size = blob.getSize();
            String contentType = blob.getContentType();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createdAt = blob.getCreateTimeOffsetDateTime().format(formatter);

            // to convert multipartFile to File
            String fileUrl = this.uploadFile(file, fileName);
            ResponseDto dto = new ResponseDto(name, size, contentType, createdAt, fileUrl);
            file.delete();
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
