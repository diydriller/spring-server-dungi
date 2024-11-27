package com.dungi.core.integration.sns;

import org.springframework.stereotype.Component;

@Component
public interface SnsService {
    String getSnsInfo(String token) throws Exception;

    String getSnsToken(String code) throws Exception;
}
