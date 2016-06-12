package com.elliothutchinson.cms.service.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Comment;
import com.elliothutchinson.cms.dto.CommentDto;
import com.elliothutchinson.cms.dto.ForeignEntity;
import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.CommentRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestCommentService {
    
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    
    @Autowired
    public RestCommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }
    
    public String findAllComments() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("comments");

        List<CommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = commentRepository.findAllByOrderByDateCreatedDesc();
        for (Comment c : comments) {
            commentDtos.add(new CommentDto(c));
        }

        return writer.writeValueAsString(commentDtos);
    }
    
    public String findCommentFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Comment comment = commentRepository.findOne(id);
        CommentDto commentDto;

        if (comment == null && id != -1) {
            map.put("error", "There was a problem requesting resource: comment not found");
            return mapper.writeValueAsString(map);
        }

        if (id == -1) {
            commentDto = new CommentDto();
        } else {
            commentDto = new CommentDto(comment);
        }

        List<Article> articles = articleRepository.findAllByOrderByDateCreatedDesc();
        List<ForeignEntity> allArticles = new ArrayList<>();
        for (Article a : articles) {
            ForeignEntity fe;
            if (commentDto.getArticleId() == null) {
                fe = new ForeignEntity(a.getId(), a.getTitle(), false);
            } else {
                fe = new ForeignEntity(a.getId(), a.getTitle(), (a.getId() == commentDto.getArticleId()));
            }
            allArticles.add(fe);
        }
        commentDto.setArticles(allArticles);

        return mapper.writeValueAsString(commentDto);
    }
    
    public String createCommentFromProxy(CommentDto proxyComment) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Article article = articleRepository.findOne(proxyComment.getArticleId());

        Comment comment = new Comment(proxyComment.getName(), proxyComment.getEmail(), proxyComment.isAuthorResponse(),
                proxyComment.getContent(), article);

        commentRepository.save(comment);

        return mapper.writeValueAsString(comment);
    }
    
    public String updateCommentFromProxy(long id, CommentDto proxyComment) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (id != proxyComment.getId()) {
            map.put("error", "There was a problem updating resource: comment id mismatch");
            return mapper.writeValueAsString(map);
        }

        Comment comment = commentRepository.findOne(proxyComment.getId());
        if (comment == null) {
            map.put("error", "There was an error updating resource: comment not found");
            return mapper.writeValueAsString(map);
        }

        comment.setName(proxyComment.getName());
        comment.setEmail(proxyComment.getEmail());
        comment.setAuthorResponse(proxyComment.isAuthorResponse());
        comment.setContent(proxyComment.getContent());
        comment.setArticle(articleRepository.findOne(proxyComment.getArticleId()));

        commentRepository.save(comment);

        return mapper.writeValueAsString(comment);
    }
    
    public String deleteCommentFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Comment comment = commentRepository.findOne(id);

        if (comment == null) {
            map.put("error", "There was an error deleting resource: comment not found");
            return mapper.writeValueAsString(map);
        }

        commentRepository.delete(comment);

        map.put("success", "Comment resource successfully deleted");
        return mapper.writeValueAsString(map);
    }
}
