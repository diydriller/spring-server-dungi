package com.project.dungi.infrastructure.file;

import com.project.dungi.common.exception.BaseException;
import com.project.dungi.common.response.BaseResponseStatus;
import com.project.dungi.domain.file.FileUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.project.dungi.common.response.BaseResponseStatus.FILE_NAME_NOT_ALLOWED;

@Profile("local")
@Component
public class FileLocalUploaderImpl implements FileUploader {

    @Value("${file.upload.path}")
    private String fileUploadPath;
    @Value("${file.down.path}")
    private String fileDownPath;

    public String imageUpload(MultipartFile file) {

        String current_date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        File folder = new File(fileUploadPath);
        folder.mkdir();
        String basePath = folder.getAbsolutePath();

        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.isEmpty()){
            throw new BaseException(FILE_NAME_NOT_ALLOWED);
        }
        String[] imageFileFlags = fileName.split("\\.");
        String imageExt = imageFileFlags[imageFileFlags.length-1];

        String imagePath = basePath + "/image" + current_date + "." + imageExt;
        String imageDownUrl = fileDownPath + "image" + current_date + "." + imageExt;

        File dest = new File(imagePath);

        try {
            file.transferTo(dest);
        } catch (Exception ex){
            throw new BaseException(BaseResponseStatus.FILE_UPLOAD_ERROR);
        }

        return imageDownUrl;
    }
}
