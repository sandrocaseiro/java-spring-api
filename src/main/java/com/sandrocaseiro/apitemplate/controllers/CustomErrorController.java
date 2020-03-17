package com.sandrocaseiro.apitemplate.controllers;

import com.sandrocaseiro.apitemplate.exceptions.ForbiddenException;
import com.sandrocaseiro.apitemplate.exceptions.MethodNotAllowedException;
import com.sandrocaseiro.apitemplate.exceptions.NotFoundException;
import com.sandrocaseiro.apitemplate.exceptions.UnauthorizedException;
import com.sandrocaseiro.apitemplate.exceptions.UnsupportedMediaTypeException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.valueOf;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public void error(HttpServletRequest request) throws Exception {
        Object exception = request.getAttribute("javax.servlet.error.exception");
        if (exception != null)
            throw (Exception)exception;

        Object attr = request.getAttribute("javax.servlet.error.status_code");
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        if (attr != null)
            statusCode = (int)attr;

        switch (valueOf(statusCode)) {
            case UNAUTHORIZED:
                throw new UnauthorizedException();
            case FORBIDDEN:
                throw new ForbiddenException();
            case NOT_FOUND:
                throw new NotFoundException();
            case METHOD_NOT_ALLOWED:
                throw new MethodNotAllowedException();
            case UNSUPPORTED_MEDIA_TYPE:
                throw new UnsupportedMediaTypeException();
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
