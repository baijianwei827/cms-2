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

import com.elliothutchinson.cms.dto.ArticleDto;
import com.elliothutchinson.cms.dto.AuthEntity;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.RestArticleService;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController extends AbstractRestController {

    private RestArticleService restArticleService;

    @Autowired
    public ArticleController(AuthenticationService authenticationService, RestArticleService restArticleService) {
        super(authenticationService);
        this.restArticleService = restArticleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getArticles(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restArticleService.findAllArticles();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getArticle(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restArticleService.findArticleFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createArticle(@RequestBody AuthEntity<ArticleDto> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restArticleService.createArticleFromProxy(authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody AuthEntity<ArticleDto> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restArticleService.updateArticleFromProxy(id, authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArticle(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restArticleService.deleteArticleFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
