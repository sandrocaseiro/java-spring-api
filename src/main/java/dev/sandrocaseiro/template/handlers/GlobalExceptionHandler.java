package dev.sandrocaseiro.template.handlers;

import dev.sandrocaseiro.template.exceptions.AppErrors;
import dev.sandrocaseiro.template.exceptions.BaseException;
import dev.sandrocaseiro.template.exceptions.BindValidationException;
import dev.sandrocaseiro.template.exceptions.PageableBadRequestException;
import dev.sandrocaseiro.template.localization.IMessageSource;
import dev.sandrocaseiro.template.models.DResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final IMessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
        if (BaseException.class.isAssignableFrom(e.getClass()))
            return handleException(((BaseException)e).error(), e.getMessage(), e);
        else
            return handleException(AppErrors.SERVER_ERROR, e.getMessage(), e);
    }

    @ExceptionHandler({AccessDeniedException.class, AuthenticationException.class})
    public ResponseEntity<Object> handleAccessDenied(Exception e, WebRequest request) {
        return handleException(AppErrors.FORBIDDEN_ERROR, e.getMessage(), e);
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
                errors.add(DResponse.Error.error(AppErrors.BINDING_VALIDATION_ERROR.getCode(), message));
            }
            else
                errors.add(DResponse.Error.error(AppErrors.BINDING_VALIDATION_ERROR.getCode(), error.getDefaultMessage()));
        }

        return handleException(AppErrors.BINDING_VALIDATION_ERROR, errors, e);
    }

    @ExceptionHandler(PageableBadRequestException.class)
    public ResponseEntity<Object> handlePageableBadRequestException(PageableBadRequestException e, WebRequest request) {
        return handleException(e.error(), e.getFieldName(), e);
    }

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredToken(io.jsonwebtoken.ExpiredJwtException e, WebRequest request) {
        return handleException(AppErrors.TOKEN_EXPIRED_ERROR, e.getMessage(), e);
    }

    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    public ResponseEntity<Object> handleInvalidToken(io.jsonwebtoken.JwtException e, WebRequest request) {
        return handleException(AppErrors.INVALID_TOKEN_ERROR, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return handleException(AppErrors.BAD_REQUEST_ERROR, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        return handleException(AppErrors.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(AppErrors.NOT_ACCEPTABLE_MEDIA_TYPE, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers,
                                                                         HttpStatus status, WebRequest request) {
        return handleException(AppErrors.METHOD_NOT_ALLOWED_ERROR, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(AppErrors.NOT_FOUND_ERROR, e.getMessage(), e);
    }

    private ResponseEntity<Object> handleException(AppErrors error, String message, Exception e) {
        return handleException(error, new Object[]{ message }, e);
    }

    private ResponseEntity<Object> handleException(AppErrors error, Object[] params, Exception e) {
        String message = messageSource.getMessage(error, params);
        logger.error(message, e);

        return ResponseEntity.status(error.getHttpStatus())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(DResponse.notOk(error.getCode(), message))
            ;
    }

    private ResponseEntity<Object> handleException(AppErrors error, List<DResponse.Error> errors, Exception e) {
        logger.error("Error", e);

        return ResponseEntity.status(error.getHttpStatus())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(DResponse.notOk(errors))
            ;
    }
}
