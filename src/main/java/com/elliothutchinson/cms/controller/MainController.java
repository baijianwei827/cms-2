package com.elliothutchinson.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elliothutchinson.cms.domain.Comment;
import com.elliothutchinson.cms.dto.Authentication;
import com.elliothutchinson.cms.service.ArchiveService;
import com.elliothutchinson.cms.service.ArticleService;
import com.elliothutchinson.cms.service.AuthenticationService;
import com.elliothutchinson.cms.service.CommentService;
import com.elliothutchinson.cms.service.FeatureService;
import com.elliothutchinson.cms.service.SectionService;
import com.elliothutchinson.cms.service.SiteDetailService;
import com.elliothutchinson.cms.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class MainController {

    private TagService tagService;
    private SectionService sectionService;
    private ArticleService articleService;
    private ArchiveService archiveService;
    private FeatureService featureService;
    private SiteDetailService siteDetailService;
    private CommentService commentService;
    private AuthenticationService authenticationService;

    public MainController() {
    }

    @Autowired
    public MainController(TagService tagService, SectionService sectionService, ArticleService articleService,
            ArchiveService archiveService, FeatureService featureService, SiteDetailService siteDetailService,
            CommentService commentService, AuthenticationService authenticationService) {
        this.tagService = tagService;
        this.sectionService = sectionService;
        this.articleService = articleService;
        this.archiveService = archiveService;
        this.featureService = featureService;
        this.siteDetailService = siteDetailService;
        this.commentService = commentService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping("/")
    public String main(Model model) {
        addCommonModelAttributes(model);

        model.addAttribute("title", articleService.findContentTitle());
        model.addAttribute("features", featureService.findAllFeatures());
        model.addAttribute("articles", articleService.findArticlesWithoutSection());

        return "main";
    }

    @RequestMapping("/tags/{tagId}")
    public String tagArticles(@PathVariable Long tagId, Model model) {
        addCommonModelAttributes(model);

        model.addAttribute("title", tagService.findContentTitle(tagId));
        model.addAttribute("articles", tagService.findArticlesFromTag(tagId));

        return "articles";
    }

    @RequestMapping("/sections/{sectionId}")
    public String sectionArticles(@PathVariable Long sectionId, Model model) {
        addCommonModelAttributes(model);

        model.addAttribute("title", sectionService.findContentTitle(sectionId));
        model.addAttribute("sectionId", sectionId);
        model.addAttribute("articles", sectionService.findArticlesFromSection(sectionId));

        return "articles";
    }

    @RequestMapping("/archives/{year}/{month}")
    public String archiveArticles(@PathVariable Integer year, @PathVariable Integer month, Model model) {
        addCommonModelAttributes(model);

        model.addAttribute("title", archiveService.findContentTitle(year, month));
        model.addAttribute("articles", archiveService.findArticlesFromYearAndMonth(year, month));

        return "articles";
    }

    @RequestMapping("/articles/{articleId}")
    public String articleDetail(@PathVariable Long articleId, Model model) {
        addCommonModelAttributes(model);

        model.addAttribute("title", articleService.findContentTitle(articleId));
        model.addAttribute("sectionId", articleService.findSectionId(articleId));
        model.addAttribute("article", articleService.findArticleFromId(articleId));

        model.addAttribute("commentForm", new Comment());

        return "article";
    }

    @RequestMapping(value = "/articles/{articleId}/comment", method = RequestMethod.POST)
    public String comment(@PathVariable Long articleId, @ModelAttribute Comment comment,
            RedirectAttributes redirectAttrs) {
        commentService.saveNewComment(articleId, comment);

        redirectAttrs.addAttribute("id", articleId).addFlashAttribute("message", "Comment added successfully.");
        return "redirect:/articles/{id}";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        addCommonModelAttributes(model);

        model.addAttribute("title", siteDetailService.findContentTitle("about"));
        model.addAttribute("sectionId", "about");
        model.addAttribute("siteDetail", siteDetailService.findSiteDetailFromName("about"));

        return "site";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("siteName", siteDetailService.findSiteName());

        model.addAttribute("title", "Admin");
        return "admin";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String authenticate(@RequestBody Authentication authentication)
            throws JsonProcessingException {
        String result = authenticationService.getAuthToken(authentication);

        return result;
    }

    public void addCommonModelAttributes(Model model) {
        model.addAttribute("siteName", siteDetailService.findSiteName());
        model.addAttribute("sections", sectionService.findAllSections());
        model.addAttribute("recentArticles", articleService.findRecentArticles());
        model.addAttribute("tags", tagService.findAllTags());
        model.addAttribute("archives", archiveService.findUniqueArchives());
    }
}
