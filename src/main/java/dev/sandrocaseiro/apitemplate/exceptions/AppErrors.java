package dev.sandrocaseiro.apitemplate.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AppErrors {

    SUCCESS(HttpStatus.OK, 200, "error.success"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,500, "error.server"),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 400, "error.badrequest"),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED,401, "error.unauthorized"),
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN,403, "error.forbidden"),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,404, "error.notfound"),
    METHOD_NOT_ALLOWED_ERROR(HttpStatus.METHOD_NOT_ALLOWED, 405, "error.methodnotallowed"),
    NOT_ACCEPTABLE_MEDIA_TYPE(HttpStatus.NOT_ACCEPTABLE, 406, "error.notacceptable"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 415, "error.unsupportedmediatype"),
    TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED,900, "error.tokenexpired"),
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, 901, "error.invalidtoken"),
    API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,902, "error.api"),
    ITEM_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, 903, "error.itemnotfound"),
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,904, "error.usernamealreadyexists"),
    BINDING_VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, 905, "error.bindvalidation"),
    PAGEABLE_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 906, "error.pageablerequest"),
    FILTER_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 907, "error.filterrequest"),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, 909, "error.invalidcredentials"),
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String messageRes;
}
