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

import com.elliothutchinson.cms.domain.SiteDetail;
import com.elliothutchinson.cms.dto.AuthEntity;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.rest.RestSiteDetailService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/sitedetails")
public class SiteDetailController extends AbstractRestController {

    private RestSiteDetailService restSiteDetailService;

    @Autowired
    public SiteDetailController(AuthenticationService authenticationService,
            RestSiteDetailService restSiteDetailService) {
        super(authenticationService);
        this.restSiteDetailService = restSiteDetailService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String getSiteDetails(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restSiteDetailService.findAllSiteDetails();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSiteDetail(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restSiteDetailService.findSiteDetailFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createSiteDetail(@RequestBody AuthEntity<SiteDetail> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restSiteDetailService.createSiteDetailFromProxy(authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSiteDetail(@PathVariable Long id, @RequestBody AuthEntity<SiteDetail> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restSiteDetailService.updateSiteDetailFromProxy(id, authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSiteDetail(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restSiteDetailService.deleteSiteDetailFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
