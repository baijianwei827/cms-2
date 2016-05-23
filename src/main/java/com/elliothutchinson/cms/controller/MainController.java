package com.elliothutchinson.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elliothutchinson.cms.service.ArchiveService;
import com.elliothutchinson.cms.service.ArticleService;
import com.elliothutchinson.cms.service.FeatureService;
import com.elliothutchinson.cms.service.SectionService;
import com.elliothutchinson.cms.service.SiteDetailService;
import com.elliothutchinson.cms.service.TagService;

@Controller
public class MainController {
	
	private TagService tagService;
	private SectionService sectionService;
	private ArticleService articleService;
	private ArchiveService archiveService;
	private FeatureService featureService;
	private SiteDetailService siteDetailService;
	
	public MainController() {}
	
	@Autowired
	public MainController(TagService tagService, SectionService sectionService,
			ArticleService articleService, ArchiveService archiveService,
			FeatureService featureService, SiteDetailService siteDetailService) {
		this.tagService = tagService;
		this.sectionService = sectionService;
		this.articleService = articleService;
		this.archiveService = archiveService;
		this.featureService = featureService;
		this.siteDetailService = siteDetailService;
	}
	
	@RequestMapping("/")
	public String main(String name, Model model) {
		model.addAttribute("siteName", siteDetailService.findSiteName());
		model.addAttribute("sections", sectionService.findAllSections());
		model.addAttribute("recentArticles", articleService.findRecentArticles());
		model.addAttribute("tags", tagService.findAllTags());
		model.addAttribute("archives", archiveService.findUniqueArchives());
		
		model.addAttribute("title", articleService.findContentTitle());
		model.addAttribute("features", featureService.findAllFeatures());
		model.addAttribute("articles", articleService.findArticlesWithoutSection());
	
		return "main";
	}
	
	@RequestMapping("/tags/{tagId}")
	public String tagArticles(@PathVariable Long tagId, String name, Model model) {
		model.addAttribute("siteName", siteDetailService.findSiteName());
		model.addAttribute("sections", sectionService.findAllSections());
		model.addAttribute("recentArticles", articleService.findRecentArticles());
		model.addAttribute("tags", tagService.findAllTags());
		model.addAttribute("archives", archiveService.findUniqueArchives());
		
		model.addAttribute("title", tagService.findContentTitle(tagId));
		model.addAttribute("articles", tagService.findArticlesFromTag(tagId));
		
		return "articles";
	}
	
	@RequestMapping("/sections/{sectionId}")
	public String sectionArticles(@PathVariable Long sectionId, String name, Model model) {
		model.addAttribute("siteName", siteDetailService.findSiteName());
		model.addAttribute("sections", sectionService.findAllSections());
		model.addAttribute("recentArticles", articleService.findRecentArticles());
		model.addAttribute("tags", tagService.findAllTags());
		model.addAttribute("archives", archiveService.findUniqueArchives());
		
		model.addAttribute("title", sectionService.findContentTitle(sectionId));
		model.addAttribute("sectionId", sectionId);
		model.addAttribute("articles", sectionService.findArticlesFromSection(sectionId));

		return "articles";
	}
	
	@RequestMapping("/archives/{year}/{month}")
	public String archiveArticles(@PathVariable Integer year, @PathVariable Integer month,
				String name, Model model) {
		model.addAttribute("siteName", siteDetailService.findSiteName());
		model.addAttribute("sections", sectionService.findAllSections());
		model.addAttribute("recentArticles", articleService.findRecentArticles());
		model.addAttribute("tags", tagService.findAllTags());
		model.addAttribute("archives", archiveService.findUniqueArchives());
		
		model.addAttribute("title", archiveService.findContentTitle(year, month));
		model.addAttribute("articles", archiveService.findArticlesFromYearAndMonth(year, month));

		return "articles";
	}
	
	@RequestMapping("/articles/{articleId}")
	public String articleDetail(@PathVariable Long articleId, String name, Model model) {
		model.addAttribute("siteName", siteDetailService.findSiteName());
		model.addAttribute("sections", sectionService.findAllSections());
		model.addAttribute("recentArticles", articleService.findRecentArticles());
		model.addAttribute("tags", tagService.findAllTags());
		model.addAttribute("archives", archiveService.findUniqueArchives());
		
		model.addAttribute("title", articleService.findContentTitle(articleId));
		model.addAttribute("sectionId", articleService.findSectionId(articleId));
		model.addAttribute("article", articleService.findArticleFromId(articleId));

		return "article";
	}
	
	@RequestMapping("/about")
	public String about(String name, Model model) {
		model.addAttribute("siteName", siteDetailService.findSiteName());
		model.addAttribute("sections", sectionService.findAllSections());
		model.addAttribute("recentArticles", articleService.findRecentArticles());
		model.addAttribute("tags", tagService.findAllTags());
		model.addAttribute("archives", archiveService.findUniqueArchives());
		
		model.addAttribute("title", siteDetailService.findContentTitle("about"));
		model.addAttribute("sectionId", "about");
		model.addAttribute("siteDetail", siteDetailService.findSiteDetailFromName("about"));

		return "site";
	}
}
