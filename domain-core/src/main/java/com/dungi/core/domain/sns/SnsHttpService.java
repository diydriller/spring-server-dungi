package com.dungi.core.domain.sns;

import com.dungi.core.domain.user.dto.SnsTokenDto;

public interface SnsHttpService {
    String getSnsInfo(String token) throws Exception;
    SnsTokenDto getSnsToken(String code) throws Exception;
}
