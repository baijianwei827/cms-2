package com.elliothutchinson.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Comment;
import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.CommentRepository;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;

    protected CommentService() {
    }

    @Autowired
    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    public void saveNewComment(Long articleId, Comment comment) {
        Article article = articleRepository.findOne(articleId);

        if (article == null) {
            return;
        }

        if (comment.getName() != null && comment.getName().length() == 0) {
            comment.setName(null);
        }

        comment.setArticle(article);
        comment.setAuthorResponse(false);
        commentRepository.save(comment);
    }
}
