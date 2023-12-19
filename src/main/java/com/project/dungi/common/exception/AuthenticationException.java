package com.project.dungi.common.exception;

import com.project.dungi.common.response.BaseResponseStatus;
import lombok.Getter;

@Getter
public class AuthenticationException extends BaseException{
    public AuthenticationException(BaseResponseStatus status) {
        super(status);
    }
}
