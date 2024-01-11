package com.project.dungi.web;


import com.project.dungi.common.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.project.dungi.common.response.BaseResponseStatus.AUTHENTICATION_ERROR;
import static com.project.dungi.common.util.StringUtil.ACCESS_TOKEN;

@RequiredArgsConstructor
@Component
public class Interceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(ACCESS_TOKEN);

        if(StringUtils.isEmpty(token)){
            throw new AuthenticationException(AUTHENTICATION_ERROR);
        }
        try{
            tokenProvider.verifyToken(token);
        }
        catch (Exception e){
            throw new AuthenticationException(AUTHENTICATION_ERROR);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView
    ) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
