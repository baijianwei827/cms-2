package com.elliothutchinson.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.rest.RestArchiveService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/archives")
public class ArchiveController extends AbstractRestController {

    private RestArchiveService restArchiveService;
    
    @Autowired
    public ArchiveController(AuthenticationService authenticationService, RestArchiveService restArchiveService) {
        super(authenticationService);
        this.restArchiveService = restArchiveService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String getSections(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restArchiveService.findAllArchives();
    }
}
