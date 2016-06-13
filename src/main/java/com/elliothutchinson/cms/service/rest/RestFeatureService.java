package com.elliothutchinson.cms.service.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Feature;
import com.elliothutchinson.cms.domain.File;
import com.elliothutchinson.cms.dto.FeatureDto;
import com.elliothutchinson.cms.dto.ForeignEntity;
import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.FeatureRepository;
import com.elliothutchinson.cms.repository.FileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class RestFeatureService {
    
    private FeatureRepository featureRepository;
    private ArticleRepository articleRepository;
    private FileRepository fileRepository;

    @Autowired
    public RestFeatureService(FeatureRepository featureRepository, ArticleRepository articleRepository,
            FileRepository fileRepository) {
        this.featureRepository = featureRepository;
        this.articleRepository = articleRepository;
        this.fileRepository = fileRepository;
    }
    
    public String findAllFeatures() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withRootName("features");

        List<FeatureDto> featureDtos = new ArrayList<>();
        List<Feature> features = featureRepository.findAllByOrderByDateCreatedDesc();
        for (Feature f : features) {
            featureDtos.add(new FeatureDto(f));
        }

        return writer.writeValueAsString(featureDtos);
    }
    
    public String findFeatureFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Feature feature = featureRepository.findOne(id);
        FeatureDto featureDto;

        if (feature == null && id != -1) {
            map.put("error", "There was a problem requesting resource: feature not found");
            return mapper.writeValueAsString(map);
        }

        if (id == -1) {
            featureDto = new FeatureDto();
        } else {
            featureDto = new FeatureDto(feature);
        }
        
        List<File> files = fileRepository.findAllByOrderByFilename();
        List<ForeignEntity> allFiles = new ArrayList<>();
        for (File f : files) {
            ForeignEntity fe;
            if (f.getFilename().equals(featureDto.getCover())) {
                fe = new ForeignEntity(f.getId(), f.getFilename(), true);
            } else {
                fe = new ForeignEntity(f.getId(), f.getFilename(), false);
            }
            allFiles.add(fe);
        }
        featureDto.setFilenames(allFiles);

        List<Article> articles = articleRepository.findAllByOrderByDateCreatedDesc();
        List<ForeignEntity> allArticles = new ArrayList<>();
        for (Article a : articles) {
            if (id == -1 && a.getFeature() != null) {
                continue;
            }
            ForeignEntity fe;
            if (featureDto.getArticleId() == null) {
                fe = new ForeignEntity(a.getId(), a.getTitle(), false);
            } else {
                fe = new ForeignEntity(a.getId(), a.getTitle(), (a.getId() == featureDto.getArticleId()));
            }
            allArticles.add(fe);
        }
        featureDto.setArticles(allArticles);

        return mapper.writeValueAsString(featureDto);
    }
    
    public String createFeatureFromProxy(FeatureDto proxyFeature) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Article article = articleRepository.findOne(proxyFeature.getArticleId());
        
        Feature feature = new Feature(proxyFeature.getCover(), proxyFeature.isPublish(), article);

        featureRepository.save(feature);

        return mapper.writeValueAsString(feature);
    }
    
    public String updateFeatureFromProxy(long id, FeatureDto proxyFeature) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (id != proxyFeature.getId()) {
            map.put("error", "There was a problem updating resource: feature id mismatch");
            return mapper.writeValueAsString(map);
        }

        Feature feature = featureRepository.findOne(proxyFeature.getId());
        if (feature == null) {
            map.put("error", "There was an error updating resource: feature not found");
            return mapper.writeValueAsString(map);
        }
        
        feature.setCover(proxyFeature.getCover());
        feature.setPublish(proxyFeature.isPublish());
        feature.setArticle(articleRepository.findOne(proxyFeature.getArticleId()));
        
        featureRepository.save(feature);

        return mapper.writeValueAsString(feature);
    }
    
    public String deleteFeatureFromId(Long id) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        Feature feature = featureRepository.findOne(id);

        if (feature == null) {
            map.put("error", "There was an error deleting resource: feature not found");
            return mapper.writeValueAsString(map);
        }

        featureRepository.delete(feature);

        map.put("success", "Feature resource successfully deleted");
        return mapper.writeValueAsString(map);
    }
}
