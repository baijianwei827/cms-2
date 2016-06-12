package com.elliothutchinson.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.AuthorRepository;
import com.elliothutchinson.cms.repository.CommentRepository;
import com.elliothutchinson.cms.repository.FileRepository;
import com.elliothutchinson.cms.repository.SectionRepository;
import com.elliothutchinson.cms.repository.TagRepository;
import com.elliothutchinson.cms.service.date.CurrentTimeDateTimeService;
import com.elliothutchinson.cms.service.date.DateTimeService;

@SpringBootApplication
public class CmsApplication {

    private static final Logger log = LoggerFactory.getLogger(CmsApplication.class);

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    @Bean
    DateTimeService currentTimeDateService() {
        return new CurrentTimeDateTimeService();
    }

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AuthorRepository ar, SectionRepository sr, ArticleRepository rr, CommentRepository cr,
            FileRepository fr, TagRepository tr) {
        return (args) -> {
            log.info("hello cms");
        };
    }
}
