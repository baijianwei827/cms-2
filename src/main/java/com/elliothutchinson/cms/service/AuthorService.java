package com.elliothutchinson.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Author;
import com.elliothutchinson.cms.repository.AuthorRepository;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAllByOrderByUsernameAsc();
    }

    public Author findAuthorFromId(Long id) {
        return authorRepository.findOne(id);
    }

    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }

    public void createAuthor(Author author) {
        authorRepository.save(author);
    }
}
