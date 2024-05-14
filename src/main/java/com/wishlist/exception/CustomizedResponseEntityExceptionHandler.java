package com.wishlist.exception;

import com.wishlist.core.response.DefaultResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
@Log4j2
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<DefaultResponse> handleAllExceptions(Exception ex) {
        Optional<ResponseStatus> annotationResponse = getResponseAnnotation(ex.getClass());
        var httpStatus = INTERNAL_SERVER_ERROR;
        String status = httpStatus.name();
        try {
            if (annotationResponse.isPresent()) {
                httpStatus = annotationResponse.get().value();
                log(ex, httpStatus);
                status = httpStatus.name();
            } else {
                log(ex);
            }
        } catch (Exception e) {
            log(e);
        }
        return new ResponseEntity<>(new DefaultResponse(status, extractMessage(ex)), httpStatus);
    }

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<DefaultResponse> handleBusinessExceptions(BusinessException exception, WebRequest request) {
        Optional<ResponseStatus> annotationResponse = getResponseAnnotation(exception.getClass());

        ResponseEntity<DefaultResponse> defaultResponseResponseEntity = getDefaultResponseResponseEntity(exception, annotationResponse, UNPROCESSABLE_ENTITY);
        var body = defaultResponseResponseEntity.getBody();
        if (body != null) {
            body.setMessages(exception.getMessages());
        }
        return defaultResponseResponseEntity;
    }

    private void log(Exception ex, HttpStatus httpStatus) {
        if (httpStatus.is4xxClientError()) {
            log.warn(ex.getMessage(), ex);
        } else {
            log.error(ex.getMessage(), ex);
        }
    }

    private void log(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    private Optional<ResponseStatus> getResponseAnnotation(Class<?> exceptionClass) {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(exceptionClass, ResponseStatus.class));
    }

    private String extractMessage(Exception ex) {
        return StringUtils.isEmpty(ex.getMessage()) ? ex.getClass().getSimpleName() : ex.getMessage();
    }

    private ResponseEntity<DefaultResponse> getDefaultResponseResponseEntity(Exception ex, Optional<ResponseStatus> annotationResponse, HttpStatus httpStatus) {
        String status = httpStatus.name();
        try {
            log.error(ex.getMessage(), ex);
            if (annotationResponse.isPresent()) {
                httpStatus = annotationResponse.get().value();
                status = httpStatus.name();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ResponseEntity<>(new DefaultResponse(status, extractMessage(ex)), httpStatus);
    }
}
