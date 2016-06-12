package com.elliothutchinson.cms.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elliothutchinson.cms.dto.AuthEntity;
import com.elliothutchinson.cms.dto.FileDto;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.RestFileService;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/files")
public class FileController extends AbstractRestController {

    private RestFileService restFileService;

    @Autowired
    public FileController(AuthenticationService authenticationService, RestFileService restFileService) {
        super(authenticationService);
        this.restFileService = restFileService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getFiles(@RequestParam("auth") String auth) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        return restFileService.findAllFiles();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFile(@PathVariable Long id,
            @RequestParam(value = "download", defaultValue = "false") String download,
            @RequestParam("auth") String auth, HttpServletResponse response) throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result;
        if (download.equals("true")) {
            result = restFileService.serveFileFromId(id, response);
        } else {
            result = restFileService.findFileFromId(id);
        }

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createFile(@RequestParam("file") MultipartFile mFile,
            @RequestParam("filename") String filename,
            @RequestParam(value = "articleId", required = false) Long articleId, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restFileService.createFileFromProxy(filename, articleId, mFile);

        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFile(@PathVariable Long id, @RequestBody AuthEntity<FileDto> authEntity)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(authEntity.getAuth());

        String result = restFileService.updateFileFromProxy(id, authEntity.getEntity());

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFile(@PathVariable Long id, @RequestParam("auth") String auth)
            throws JsonProcessingException {
        authenticationService.verifyAuthentication(auth);

        String result = restFileService.deleteFileFromId(id);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
