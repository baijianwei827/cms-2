package com.elliothutchinson.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.elliothutchinson.cms.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByName(String name);

    @Query(value = "select count(*) from comment where author_response=true and article_id in "
            + "(select id from article where author_id=?1)", nativeQuery = true)
    Long countAuthorCommentsById(Long id);

    Long countByArticleId(Long id);
}
