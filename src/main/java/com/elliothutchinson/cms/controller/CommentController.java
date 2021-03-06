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
import com.elliothutchinson.cms.dto.CommentDto;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.rest.RestCommentService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController extends AbstractRestController {

    private RestCommentService restCommentService;

    @Autowired
    public CommentController(AuthenticationService authenticationService, RestCommentService restCommentService) {
        super(authenticationService);
        this.restCommentService = restCommentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getComments(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restCommentService.findAllComments();
    }

    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getComment(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restCommentService.findCommentFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createComment(@RequestBody AuthEntity<CommentDto> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restCommentService.createCommentFromProxy(authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody AuthEntity<CommentDto> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restCommentService.updateCommentFromProxy(id, authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restCommentService.deleteCommentFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
}
