package hello.postpractice.advice;

import hello.postpractice.advice.exception.EmailLoginFailedCException;
import hello.postpractice.advice.exception.UserNotFoundCException;
import hello.postpractice.model.response.CommonResult;
import hello.postpractice.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class MyAdvice {
    @Autowired
    ResponseService responseService;
    /***
     * 유저를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(UserNotFoundCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundCException e) {
        return responseService.getFailResult();
    }
    /*
    이메일 찾지못했을때 발생시키는 예외
     */
    @ExceptionHandler(EmailLoginFailedCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedCException(HttpServletRequest request, EmailLoginFailedCException e) {
        return responseService.getFailResult();
    }
}