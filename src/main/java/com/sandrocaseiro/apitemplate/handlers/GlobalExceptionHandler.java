package com.sandrocaseiro.apitemplate.handlers;

import com.sandrocaseiro.apitemplate.exceptions.BaseException;
import com.sandrocaseiro.apitemplate.exceptions.BindValidationException;
import com.sandrocaseiro.apitemplate.exceptions.CustomErrors;
import com.sandrocaseiro.apitemplate.exceptions.PageableBadRequestException;
import com.sandrocaseiro.apitemplate.models.DResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
        if (BaseException.class.isAssignableFrom(e.getClass()))
            return handleException(((BaseException)e).error(), e.getMessage(), e);
        else
            return handleException(CustomErrors.SERVER_ERROR, e.getMessage(), e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException e, WebRequest request) {
        return handleException(CustomErrors.FORBIDDEN_ERROR, e.getMessage(), e);
    }

    @ExceptionHandler(BindValidationException.class)
    public ResponseEntity<Object> handleBindingValidation(BindValidationException e, WebRequest request) {
        List<DResponse.Error> errors = new ArrayList<>();
        for (ObjectError error : e.getBindErrors().getAllErrors()) {
            if (FieldError.class.isAssignableFrom(error.getClass())) {
                FieldError fieldError = (FieldError)error;
                if (StringUtils.isEmpty(fieldError.getField()))
                    continue;

                String message = fieldError.getDefaultMessage().replace("{field}", fieldError.getField());
                errors.add(DResponse.Error.error(CustomErrors.BINDING_VALIDATION_ERROR.getCode(), message));
            }
            else
                errors.add(DResponse.Error.error(CustomErrors.BINDING_VALIDATION_ERROR.getCode(), error.getDefaultMessage()));
        }

        return handleException(CustomErrors.BINDING_VALIDATION_ERROR, errors, e);
    }

    @ExceptionHandler(PageableBadRequestException.class)
    public ResponseEntity<Object> handlePageableBadRequestException(PageableBadRequestException e, WebRequest request) {
        return handleException(e.error(), e.getFieldName(), e);
    }

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredToken(io.jsonwebtoken.ExpiredJwtException e, WebRequest request) {
        return handleException(CustomErrors.TOKEN_EXPIRED_ERROR, e.getMessage(), e);
    }

    @ExceptionHandler(io.jsonwebtoken.SignatureException.class)
    public ResponseEntity<Object> handleInvalidToken(io.jsonwebtoken.SignatureException e, WebRequest request) {
        return handleException(CustomErrors.INVALID_TOKEN_ERROR, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return handleException(CustomErrors.BAD_REQUEST_ERROR, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        return handleException(CustomErrors.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers,
                                                                         HttpStatus status, WebRequest request) {
        return handleException(CustomErrors.METHOD_NOT_ALLOWED_ERROR, e.getMessage(), e);
    }

    private ResponseEntity<Object> handleException(CustomErrors error, String message, Exception e) {
        return handleException(error, new Object[]{ message }, e);
    }

    private ResponseEntity<Object> handleException(CustomErrors error, Object[] params, Exception e) {
        String message = messageSource.getMessage(error.getMessageRes(), params, LocaleContextHolder.getLocale());
        logger.error(message, e);

        return new ResponseEntity<>(DResponse.notOk(error.getCode(), message), error.getHttpStatus());
    }

    private ResponseEntity<Object> handleException(CustomErrors error, List<DResponse.Error> errors, Exception e) {
        logger.error("Error", e);

        return new ResponseEntity<>(DResponse.notOk(errors), error.getHttpStatus());
    }
}
