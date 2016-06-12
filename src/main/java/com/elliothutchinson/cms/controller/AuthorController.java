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

import com.elliothutchinson.cms.domain.Author;
import com.elliothutchinson.cms.dto.AuthEntity;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.rest.RestAuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController extends AbstractRestController {

    private RestAuthorService restAuthorService;

    @Autowired
    public AuthorController(RestAuthorService restAuthorService, AuthenticationService authenticationService) {
        super(authenticationService);
        this.restAuthorService = restAuthorService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAuthors(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restAuthorService.findAllAuthors();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAuthor(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restAuthorService.findAuthorFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createAuthor(@RequestBody AuthEntity<Author> authEntity) throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restAuthorService.createAuthorFromProxy(authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody AuthEntity<Author> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restAuthorService.updateAuthorFromProxy(id, authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restAuthorService.deleteAuthorFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}