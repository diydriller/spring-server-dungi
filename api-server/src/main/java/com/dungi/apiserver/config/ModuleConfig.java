package com.dungi.apiserver.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dungi.file.infrastructure.FileUploader;
import com.dungi.file.infrastructure.aws.FileAwsUploaderImpl;
import com.dungi.file.infrastructure.local.FileLocalUploaderImpl;
import com.dungi.sms.infrastructure.SmsSender;
import com.dungi.sms.infrastructure.twilio.TwilioSmsSender;
import com.dungi.sns.infrastructure.SnsHttpService;
import com.dungi.sns.infrastructure.kakao.KakaoHttpServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@RequiredArgsConstructor
@Configuration
public class ModuleConfig {
    private final AmazonS3Client amazonS3Client;

    @Profile("prod")
    @Bean
    public FileUploader fileAwsUploaderImpl() {
        return new FileAwsUploaderImpl(amazonS3Client);
    }

    @Profile("local")
    @Bean
    public FileUploader fileLocalUploaderImpl() {
        return new FileLocalUploaderImpl();
    }

    @Bean
    public SnsHttpService snsHttpService() {
        return new KakaoHttpServiceImpl();
    }

    @Bean
    public SmsSender smsSender() {
        return new TwilioSmsSender();
    }
}
