package com.elliothutchinson.cms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.elliothutchinson.cms.CmsApplication;
import com.elliothutchinson.cms.dto.ErrorMessage;
import com.elliothutchinson.cms.exception.InvalidAuthTokenException;
import com.elliothutchinson.cms.service.AuthenticationService;

public abstract class AbstractRestController {

    private static final Logger log = LoggerFactory.getLogger(CmsApplication.class);

    protected AuthenticationService authenticationService;

    public AbstractRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ExceptionHandler(InvalidAuthTokenException.class)
    public ResponseEntity<?> invalidAuth() {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage("There was an issue with request: invalid authentication token"), HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> conflict() {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage("There was a problem creating/updating resource: integrity violation"), HttpStatus.OK);
    }

    public static Logger getLog() {
        return log;
    }
}
