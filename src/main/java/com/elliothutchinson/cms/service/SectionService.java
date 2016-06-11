package com.elliothutchinson.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Section;
import com.elliothutchinson.cms.repository.SectionRepository;

@Service
public class SectionService {

    private SectionRepository sectionRepository;

    protected SectionService() {
    }

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<Section> findAllSections() {
        return sectionRepository.findAllByOrderByTitleAsc();
    }

    public List<Article> findArticlesFromSection(Long id) {
        Section section = sectionRepository.findOne(id);
        List<Article> articles;
        if (section == null) {
            articles = new ArrayList<>();
        } else {
            articles = section.getArticles();
        }
        return articles;
    }

    public String findContentTitle(Long id) {
        Section section = sectionRepository.findOne(id);
        if (section == null) {
            return "Section Not Found";
        } else {
            return section.getTitle() + " Articles";
        }
    }
}
