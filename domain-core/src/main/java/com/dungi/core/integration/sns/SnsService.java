package com.dungi.core.integration.sns;

import com.dungi.core.integration.sns.dto.SnsTokenDto;
import org.springframework.stereotype.Component;

@Component
public interface SnsService {
    String getSnsInfo(String token) throws Exception;

    SnsTokenDto getSnsToken(String code) throws Exception;
}
