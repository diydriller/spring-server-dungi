package com.dungi.file.infrastructure;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    String imageUpload(MultipartFile file) throws Exception;
}