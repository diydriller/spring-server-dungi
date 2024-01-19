package com.dungi.file.infrastructure.file;

import com.dungi.common.exception.BaseException;
import com.dungi.common.response.BaseResponseStatus;
import com.dungi.core.domain.file.FileUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


@Profile("local")
@Component
public class FileLocalUploaderImpl implements FileUploader {

    @Value("${file.upload.path}")
    private String fileUploadPath;
    @Value("${file.down.path}")
    private String fileDownPath;

    public String imageUpload(byte[] imageContent, String imageName) {

        String current_date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        File folder = new File(fileUploadPath);
        folder.mkdirs();
        String basePath = folder.getAbsolutePath();

        String[] imageFileFlags = imageName.split("\\.");
        String imageExt = imageFileFlags[imageFileFlags.length-1];

        String imagePath = basePath + "/image" + current_date + "." + imageExt;
        String imageDownUrl = fileDownPath + "image" + current_date + "." + imageExt;

        File file = new File(imagePath);

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(imageContent);
            fos.close();
        } catch (Exception ex){
            throw new BaseException(BaseResponseStatus.FILE_UPLOAD_ERROR);
        }

        return imageDownUrl;
    }
}
