package com.elliothutchinson.cms.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractController {
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IO issue")
    @ExceptionHandler(IOException.class)
    public ModelAndView handleAllException(Exception ex) {

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errMsg", "IO exception / file not found");

        return mav;
    }
}
