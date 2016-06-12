package com.elliothutchinson.cms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Author;
import com.elliothutchinson.cms.dto.Authentication;
import com.elliothutchinson.cms.exception.InvalidAuthTokenException;
import com.elliothutchinson.cms.repository.AuthorRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthenticationService {

    private AuthorRepository authorRepository;

    protected AuthenticationService() {
    }

    @Autowired
    public AuthenticationService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public String getAuthToken(Authentication authentication) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        String username = authentication.getUsername();
        String password = authentication.getPassword();

        Author author = authorRepository.findOneByUsernameAndPassword(username, password);

        if (author == null) {
            map.put("error", "there was an issue authenticating");
        } else {
            // todo: implement token generation
            map.put("success", "You have been logged in as " + username);
            map.put("token", "some-temporary-token");
            map.put("username", username);
        }

        return mapper.writeValueAsString(map);
    }

    public void verifyAuthentication(String auth) {
        // todo: check database
        if (!auth.equals("some-temporary-token")) {
            throw new InvalidAuthTokenException();
        }
    }
}
