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
import com.elliothutchinson.cms.dto.FeatureDto;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.rest.RestArticleService;
import com.elliothutchinson.cms.service.rest.RestFeatureService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/features")
public class FeatureController extends AbstractRestController {

    private RestFeatureService restFeatureService;

    @Autowired
    public FeatureController(AuthenticationService authenticationService, RestFeatureService restFeatureService) {
        super(authenticationService);
        this.restFeatureService = restFeatureService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String getFeatures(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restFeatureService.findAllFeatures();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFeature(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restFeatureService.findFeatureFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createFeature(@RequestBody AuthEntity<FeatureDto> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restFeatureService.createFeatureFromProxy(authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFeature(@PathVariable Long id, @RequestBody AuthEntity<FeatureDto> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restFeatureService.updateFeatureFromProxy(id, authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFeature(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restFeatureService.deleteFeatureFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
