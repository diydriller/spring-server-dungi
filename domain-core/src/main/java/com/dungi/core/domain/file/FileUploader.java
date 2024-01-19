package com.dungi.core.domain.file;

public interface FileUploader {
    String imageUpload(byte[] imageContent, String imageName) throws Exception;
}