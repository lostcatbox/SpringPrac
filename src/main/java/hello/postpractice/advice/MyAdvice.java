package hello.postpractice.advice;

import hello.postpractice.advice.exception.*;
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
        System.out.println(e.getMessage());
        return responseService.getFailResult();
    }

    /*
    이메일 찾지못했을때 발생시키는 예외
     */
    @ExceptionHandler(EmailNotFailedCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedCException(HttpServletRequest request, EmailNotFailedCException e) {
        System.out.println(e.getMessage());
        return responseService.getFailResult();
    }

    @ExceptionHandler(PasswordFailCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult passwordFailException(HttpServletRequest request, PasswordFailCException e) {
        System.out.println(e.getMessage());
        return responseService.getFailResult();
    }

    @ExceptionHandler(EmailExsistFailedCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailExsistFailedCException(HttpServletRequest request, EmailExsistFailedCException e) {
        System.out.println(e.getMessage());
        return responseService.getFailResult();
    }

    @ExceptionHandler(UnMatchedUserCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult unMathedUserException(HttpServletRequest request, UnMatchedUserCException e) {
        System.out.println(e.getMessage());
        return responseService.getFailResult();
    }
    @ExceptionHandler(PostNotFoundCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult postNotFoundException(HttpServletRequest request, PostNotFoundCException e) {
        System.out.println(e.getMessage());
        return responseService.getFailResult();
    }
    @ExceptionHandler(CommentNotFoundCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult commentNotFoundCException(HttpServletRequest request, CommentNotFoundCException e) {
        System.out.println(e.getMessage());
        return responseService.getFailResult();
    }
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult catchNullPointException(HttpServletRequest request, CommentNotFoundCException e) {
        System.out.println("catchNullPointException");
        return responseService.getFailResult();
    }

}