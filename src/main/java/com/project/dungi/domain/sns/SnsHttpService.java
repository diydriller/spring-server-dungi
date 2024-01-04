package com.project.dungi.domain.sns;

import com.project.dungi.application.user.dto.SnsTokenDto;

public interface SnsHttpService {
    String getSnsInfo(String token) throws Exception;
    SnsTokenDto getSnsToken(String code) throws Exception;
}
