package com.dungi.file.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.dungi.core.infrastructure.file.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class FileAwsUploaderImpl implements FileUploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String imageUpload(MultipartFile file) throws Exception {
        String current_date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String originalFilename = "dungi/image" + current_date + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3Client.putObject(bucket, originalFilename, file.getInputStream(), metadata);
        return amazonS3Client.getUrl(bucket, originalFilename).toString();
    }
}
