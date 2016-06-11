package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Author findOneByUsername(String username);

    Author findOneByUsernameAndPassword(String username, String password);

    List<Author> findByEmail(String email);

    List<Author> findAllByOrderByUsernameAsc();

    Long countByAdminIsTrue();
}
