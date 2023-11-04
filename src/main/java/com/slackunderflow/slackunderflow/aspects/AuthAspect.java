package com.slackunderflow.slackunderflow.aspects;

import com.slackunderflow.slackunderflow.dtos.AuthDto;
import com.slackunderflow.slackunderflow.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {
    private final UserEntityService userEntityService;

    @Before("@annotation(AuthBefore)")
    public void beforeAuth(JoinPoint joinPoint) {
        AuthDto authDto = (AuthDto) joinPoint.getArgs()[0];
        userEntityService.login(authDto.getUserLoginDto());
    }
}
