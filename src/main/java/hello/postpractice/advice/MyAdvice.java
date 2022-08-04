package hello.postpractice.advice;

import hello.postpractice.advice.exception.EmailExsistFailedCException;
import hello.postpractice.advice.exception.EmailNotFailedCException;
import hello.postpractice.advice.exception.PasswordFailCException;
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
    @ExceptionHandler(EmailNotFailedCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedCException(HttpServletRequest request, EmailNotFailedCException e) {
        return responseService.getFailResult();
    }

    @ExceptionHandler(PasswordFailCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult passwordFailException(HttpServletRequest request, PasswordFailCException e) {
        return responseService.getFailResult();
    }

    @ExceptionHandler(EmailExsistFailedCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailExsistFailedCException(HttpServletRequest request, EmailExsistFailedCException e) {
        return responseService.getFailResult();
    }
}