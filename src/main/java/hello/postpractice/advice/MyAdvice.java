package hello.postpractice.advice;

import hello.postpractice.advice.exception.*;
import hello.postpractice.model.response.CommonResult;
import hello.postpractice.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class MyAdvice {
    @Autowired
    ResponseService responseService;

    /***
     * 유저를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(UserNotFoundCException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundCException e) {
        log.info(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }

    /*
    이메일 찾지못했을때 발생시키는 예외
     */
    @ExceptionHandler(EmailNotFailedCException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult emailLoginFailedCException(HttpServletRequest request, EmailNotFailedCException e) {
        log.info(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }

    @ExceptionHandler(PasswordFailCException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult passwordFailException(HttpServletRequest request, PasswordFailCException e) {
        log.info(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }

    @ExceptionHandler(EmailExsistFailedCException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult emailExsistFailedCException(HttpServletRequest request, EmailExsistFailedCException e) {
        log.info(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }

    @ExceptionHandler(UnMatchedUserCException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult unMathedUserException(HttpServletRequest request, UnMatchedUserCException e) {
        log.info(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }
    @ExceptionHandler(PostNotFoundCException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult postNotFoundException(HttpServletRequest request, PostNotFoundCException e) {
        log.info(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }
    @ExceptionHandler(CommentNotFoundCException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult commentNotFoundCException(HttpServletRequest request, CommentNotFoundCException e) {
        log.info(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }
    // DataField Invalid
    @ExceptionHandler(DataFieldInvalidCException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult catchDataFieldInvalidCException(HttpServletRequest request,DataFieldInvalidCException e){
        log.error(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult catchNullPointException(HttpServletRequest request, NullPointerException e) {
        log.info(e.getMessage());
        return responseService.getFailResult(e.getMessage());
    }

}