package com.project.dungi.domain.common;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    String imageUpload(MultipartFile file) throws Exception;
}
