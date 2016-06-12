package com.elliothutchinson.cms.service.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Author;
import com.elliothutchinson.cms.repository.AuthorRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestAuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public RestAuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public String findAllAuthors() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("authors");

        return writer.writeValueAsString(authorRepository.findAllByOrderByUsernameAsc());
    }

    public String findAuthorFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Author author = authorRepository.findOne(id);

        if (author == null) {
            map.put("error", "There was a problem requesting resource: author not found");
            return mapper.writeValueAsString(map);
        }

        return mapper.writeValueAsString(author);
    }

    public String createAuthorFromProxy(Author proxyAuthor) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Author author = new Author(proxyAuthor.getUsername(), proxyAuthor.getPassword(), proxyAuthor.getEmail(),
                proxyAuthor.isAdmin());

        authorRepository.save(author);

        return mapper.writeValueAsString(author);
    }

    public String updateAuthorFromProxy(long id, Author proxyAuthor) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (id != proxyAuthor.getId()) {
            map.put("error", "There was a problem updating resource: author id mismatch");
            return mapper.writeValueAsString(map);
        }

        Author author = authorRepository.findOne(proxyAuthor.getId());
        if (author == null) {
            map.put("error", "There was an error updating resource: author not found");
            return mapper.writeValueAsString(map);
        }

        Author testUniqueUsername = authorRepository.findOneByUsername(proxyAuthor.getUsername());
        if (testUniqueUsername != null && testUniqueUsername.getId() != author.getId()) {
            map.put("error", "There was an error updating resource: username already exists");
            return mapper.writeValueAsString(map);
        }

        author.setUsername(proxyAuthor.getUsername());
        author.setPassword(proxyAuthor.getPassword());
        author.setEmail(proxyAuthor.getEmail());
        author.setAdmin(proxyAuthor.isAdmin());

        authorRepository.save(author);

        return mapper.writeValueAsString(author);
    }

    public String deleteAuthorFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Author author = authorRepository.findOne(id);

        if (author == null) {
            map.put("error", "There was an error deleting resource: author not found");
            return mapper.writeValueAsString(map);
        }

        authorRepository.delete(author);

        map.put("success", "Author resource successfully deleted");
        return mapper.writeValueAsString(map);
    }
}
