package com.elliothutchinson.cms.service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Archive;
import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.repository.ArchiveRepository;
import com.elliothutchinson.cms.repository.ArticleRepository;

@Service
public class ArchiveService {

    private ArchiveRepository archiveRepository;
    private ArticleRepository articleRepository;

    protected ArchiveService() {
    }

    @Autowired
    public ArchiveService(ArchiveRepository archiveRepository, ArticleRepository articleRepository) {
        this.archiveRepository = archiveRepository;
        this.articleRepository = articleRepository;
    }

    public List<Archive> findUniqueArchives() {
        Map<Integer, Set<Integer>> map = new HashMap<>();

        List<Archive> archives = archiveRepository.findAllByOrderByYearDescMonthDesc();

        // remove duplicates from archive (e.g. only need one July 2015)
        for (Archive a : new ArrayList<>(archives)) {
            if (map.containsKey(a.getYear())) {
                if (map.get(a.getYear()).contains(a.getMonth())) {
                    archives.remove(a);
                } else { // add month to set for that year
                    map.get(a.getYear()).add(a.getMonth());
                }
            } else { // add year to map with new set containing month
                map.put(a.getYear(), new HashSet<>(Arrays.asList(a.getMonth())));
            }
        }
        return archives;
    }

    public List<Article> findArticlesFromYearAndMonth(Integer year, Integer month) {
        List<Article> articles = new ArrayList<>();

        List<Archive> archives = archiveRepository.findByYearAndMonth(year, month);

        for (Archive a : archives) {
            Article article = articleRepository.findOne(a.getArticle().getId());
            articles.add(article);
        }

        return articles;
    }

    public String findContentTitle(Integer year, Integer month) {
        String msg = "Archive: ";

        if (month > 0 && month < 13) {
            msg += Month.of(month).getDisplayName(TextStyle.FULL, Locale.US) + " " + year;
        } else {
            msg += "Invalid Date";
        }

        return msg;
    }
}
