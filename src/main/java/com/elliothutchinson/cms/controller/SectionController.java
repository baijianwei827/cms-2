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

import com.elliothutchinson.cms.domain.Section;
import com.elliothutchinson.cms.dto.AuthEntity;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.RestSectionService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/sections")
public class SectionController extends AbstractRestController {

    private RestSectionService restSectionService;

    @Autowired
    public SectionController(AuthenticationService authenticationService, RestSectionService restSectionService) {
        super(authenticationService);
        this.restSectionService = restSectionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getSections(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restSectionService.findAllSections();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSection(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restSectionService.findSectionFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createSection(@RequestBody AuthEntity<Section> authEntity) throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restSectionService.createSectionFromProxy(authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSection(@PathVariable Long id, @RequestBody AuthEntity<Section> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restSectionService.updateSectionFromProxy(id, authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSection(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restSectionService.deleteSectionFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
