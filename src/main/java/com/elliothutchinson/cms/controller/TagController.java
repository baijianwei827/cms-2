package com.elliothutchinson.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elliothutchinson.cms.dto.AuthEntity;
import com.elliothutchinson.cms.dto.TagDto;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.rest.RestTagService;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController extends AbstractRestController {
    
    private RestTagService restTagService;
    
    @Autowired
    public TagController(AuthenticationService authenticationService, RestTagService restTagService) {
        super(authenticationService);
        this.restTagService = restTagService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String getTags(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restTagService.findAllTags();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTag(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restTagService.findTagFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createTag(@RequestBody AuthEntity<TagDto> authEntity) throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restTagService.createTagFromProxy(authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTag(@PathVariable Long id, @RequestBody AuthEntity<TagDto> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restTagService.updateTagFromProxy(id, authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTag(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restTagService.deleteTagFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
