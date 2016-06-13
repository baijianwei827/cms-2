package com.elliothutchinson.cms.service.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.File;
import com.elliothutchinson.cms.dto.FileDto;
import com.elliothutchinson.cms.dto.ForeignEntity;
import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.FileRepository;
import com.elliothutchinson.cms.service.ResourceService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestFileService {

    private FileRepository fileRepository;
    private ArticleRepository articleRepository;
    private ResourceService resourceService;

    @Autowired
    public RestFileService(FileRepository fileRepository, ArticleRepository articleRepository,
            ResourceService resourceService) {
        this.fileRepository = fileRepository;
        this.articleRepository = articleRepository;
        this.resourceService = resourceService;
    }

    public String findAllFiles() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("files");

        List<FileDto> fileDtos = new ArrayList<>();
        List<File> files = fileRepository.findAllByOrderByFilename();
        for (File f : files) {
            fileDtos.add(new FileDto(f));
        }

        return writer.writeValueAsString(fileDtos);
    }

    public String findFileFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        File file = fileRepository.findOne(id);
        FileDto fileDto;

        if (file == null && id != -1) {
            map.put("error", "There was a problem requesting resource: file not found");
            return mapper.writeValueAsString(map);
        }

        if (id == -1) {
            fileDto = new FileDto();
        } else {
            fileDto = new FileDto(file);
        }

        List<Article> articles = articleRepository.findAllByOrderByDateCreatedDesc();
        List<ForeignEntity> allArticles = new ArrayList<>();
        for (Article a : articles) {
            ForeignEntity fe;
            if (fileDto.getArticleId() == null) {
                fe = new ForeignEntity(a.getId(), a.getTitle(), false);
            } else {
                fe = new ForeignEntity(a.getId(), a.getTitle(), (a.getId() == fileDto.getArticleId()));
            }
            allArticles.add(fe);
        }
        fileDto.setArticles(allArticles);

        return mapper.writeValueAsString(fileDto);
    }

    public String serveFileFromId(Long id, HttpServletResponse response) throws IOException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        File file = fileRepository.findOne(id);
        if (file == null) {
            map.put("error", "There was a problem requesting resource: file not found");
            return mapper.writeValueAsString(map);
        }
        
        resourceService.getResource(File.getRoot() + file.getFilename(), response);

        return "";
    }

    public String createFileFromProxy(String filename, Long articleId, MultipartFile mFile)
            throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Article article = null;
        if (articleId != null) {
            article = articleRepository.findOne(articleId);
        }

        File file = new File(filename, article);
        if (resourceService.saveResource(File.getRoot() + file.getFilename(), mFile)) {
            fileRepository.save(file);
        } else {
            map.put("error", "There was a problem creating resource: file io issue");
            return mapper.writeValueAsString(map);
        }

        return mapper.writeValueAsString(file);
    }

    public String updateFileFromProxy(long id, FileDto proxyFile) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (id != proxyFile.getId()) {
            map.put("error", "There was a problem updating resource: file id mismatch");
            return mapper.writeValueAsString(map);
        }

        File file = fileRepository.findOne(proxyFile.getId());
        if (file == null) {
            map.put("error", "There was an error updating resource: file not found");
            return mapper.writeValueAsString(map);
        }

        if (proxyFile.getArticleId() != null) {
            file.setArticle(articleRepository.findOne(proxyFile.getArticleId()));
        } else {
            file.setArticle(null);
        }

        fileRepository.save(file);

        return mapper.writeValueAsString(file);
    }

    public String deleteFileFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        File file = fileRepository.findOne(id);

        if (file == null) {
            map.put("error", "There was an error deleting resource: file not found");
            return mapper.writeValueAsString(map);
        }

        boolean deleted = resourceService.deleteResource(File.getRoot() + file.getFilename());
        if (!deleted) {
            map.put("error", "There was an error deleting resource: file io issue");
            return mapper.writeValueAsString(map);
        }

        fileRepository.delete(file);

        map.put("success", "File resource successfully deleted");
        return mapper.writeValueAsString(map);
    }
}
