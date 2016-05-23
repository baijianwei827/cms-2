package com.elliothutchinson.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elliothutchinson.cms.domain.Article;
import com.elliothutchinson.cms.domain.Author;
import com.elliothutchinson.cms.domain.Comment;
import com.elliothutchinson.cms.domain.Feature;
import com.elliothutchinson.cms.domain.File;
import com.elliothutchinson.cms.domain.Section;
import com.elliothutchinson.cms.domain.SiteDetail;
import com.elliothutchinson.cms.domain.Tag;
import com.elliothutchinson.cms.repository.ArticleRepository;
import com.elliothutchinson.cms.repository.AuthorRepository;
import com.elliothutchinson.cms.repository.CommentRepository;
import com.elliothutchinson.cms.repository.FeatureRepository;
import com.elliothutchinson.cms.repository.FileRepository;
import com.elliothutchinson.cms.repository.SectionRepository;
import com.elliothutchinson.cms.repository.SiteDetailRepository;
import com.elliothutchinson.cms.repository.TagRepository;

@Service
public class TestService {

	private TagRepository tagRepository;
	private ArticleRepository articleRepository;
	private SectionRepository sectionRepository;
	private AuthorRepository authorRepository;
	private FileRepository fileRepository;
	private CommentRepository commentRepository;
	private FeatureRepository featureRepository;
	private SiteDetailRepository siteDetailRepository;
	
	protected TestService() {}
	
	@Autowired
	public TestService(TagRepository tagRepository, ArticleRepository articleRepository,
			SectionRepository sectionRepository, AuthorRepository authorRepository,
			FileRepository fileRepository, CommentRepository commentRepository,
			FeatureRepository featureRepository, SiteDetailRepository siteDetailRepository) {
		this.tagRepository = tagRepository;
		this.articleRepository = articleRepository;
		this.authorRepository = authorRepository;
		this.sectionRepository = sectionRepository;
		this.fileRepository = fileRepository;
		this.commentRepository = commentRepository;
		this.featureRepository = featureRepository;
		this.siteDetailRepository = siteDetailRepository;
	}
	
	public void test() {
		
	}
	
	public void initializeData() {
		deleteSampleData();
		createSampleData();
		addTags();
	}
	
	public void createSampleData() {
		authorRepository.save(new Author("abe", "pass", "a@t.com", true));
		authorRepository.save(new Author("bob", "pass", "b@t.com", true));
		authorRepository.save(new Author("cat", "pass", "c@f.com", false));
		authorRepository.save(new Author("dan", "pass", "d@f.com", false));
		
		sectionRepository.save(new Section("Android", "Android description"));
		sectionRepository.save(new Section("Apple", "Apple description"));
		sectionRepository.save(new Section("Microsoft", "Microsoft description"));
		sectionRepository.save(new Section("Nintendo", "Nintendo description"));
		sectionRepository.save(new Section("Sony", "Sony description"));
		
		tagRepository.save(new Tag("game design"));
		tagRepository.save(new Tag("programming"));
		tagRepository.save(new Tag("engineering"));
		tagRepository.save(new Tag("sci-fi"));
		tagRepository.save(new Tag("Python"));
		
		Author abe = authorRepository.findByUsername("abe").get(0);
		Author bob = authorRepository.findByUsername("bob").get(0);
		Author cat = authorRepository.findByUsername("cat").get(0);
		// author dan will be author without any articles
		
		Section android = sectionRepository.findByTitle("Android").get(0);
		Section apple = sectionRepository.findByTitle("Apple").get(0);
		Section microsoft = sectionRepository.findByTitle("Microsoft").get(0);
		Section nintendo = sectionRepository.findByTitle("Nintendo").get(0);
		// section sony will be section without any articles
		
		articleRepository.save(new Article("An Interesting Article Title",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				abe, null));
		articleRepository.save(new Article("New Scientific Study Suggests A Implies a",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				bob, null));
		articleRepository.save(new Article("Another Article About XYZ",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				bob, null));
		articleRepository.save(new Article("Can We Ever Truly Understand the Void?",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				cat, null));
		
		articleRepository.save(new Article("Android Mashed Potato Released",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				abe, android));
		articleRepository.save(new Article("Apple Announces 25 inch iPad",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				bob, apple));
		articleRepository.save(new Article("Security Update for iOS",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				bob, apple));
		articleRepository.save(new Article("Microsoft Calls it Quits!",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				bob, microsoft));
		articleRepository.save(new Article("Windows 10 Will Automatically Install on iPhones",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				bob, microsoft));
		articleRepository.save(new Article("Xbox One Update",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				bob, microsoft));
		articleRepository.save(new Article("Will Nintendo Be Able to Keep up with Demand?",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
				cat, nintendo));
		
		Article androidArticle1 = articleRepository.findByTitle("Android Mashed Potato Released").get(0);
		Article appleArticle1 = articleRepository.findByTitle("Apple Announces 25 inch iPad").get(0);
		Article appleArticle2 = articleRepository.findByTitle("Security Update for iOS").get(0);
		Article microsoftArticle1 = articleRepository.findByTitle("Microsoft Calls it Quits!").get(0);
		Article microsoftArticle2 = articleRepository.findByTitle("Windows 10 Will Automatically Install on iPhones").get(0);
		Article microsoftArticle3 = articleRepository.findByTitle("Xbox One Update").get(0);
		
		fileRepository.save(new File("/path/android.jpg", androidArticle1));
		fileRepository.save(new File("/path/apple.jpg", appleArticle1));
		fileRepository.save(new File("/path/ipad.jpg", appleArticle1));
		fileRepository.save(new File("/path/iphone.jpg", appleArticle1));
		
		commentRepository.save(new Comment("ann", "a@f.com", false,
				"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos.",
				androidArticle1));
		commentRepository.save(new Comment("ben", "b@f.com", false,
				"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos.",
				appleArticle1));
		commentRepository.save(new Comment(null, null, false,
				"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos.",
				appleArticle1));
		commentRepository.save(new Comment("abe", "a@t.com", true,
				"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos.",
				appleArticle1));	
		
		featureRepository.save(new Feature("beach.jpg", true, androidArticle1));
		featureRepository.save(new Feature("forest.jpg", true, appleArticle1));
		featureRepository.save(new Feature(null, true, appleArticle2));
		featureRepository.save(new Feature(null, false, microsoftArticle1));
		
		siteDetailRepository.save(new SiteDetail("siteName", null, "Tech Future Today", null));
		siteDetailRepository.save(new SiteDetail("about", "About",
				"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est.",
				null));
	}
	
	public void addTags() {
		Article androidArticle1 = articleRepository.findByTitle("Android Mashed Potato Released").get(0);
		Article appleArticle1 = articleRepository.findByTitle("Apple Announces 25 inch iPad").get(0);
		Article appleArticle2 = articleRepository.findByTitle("Security Update for iOS").get(0);
		Article microsoftArticle1 = articleRepository.findByTitle("Microsoft Calls it Quits!").get(0);
		Article microsoftArticle2 = articleRepository.findByTitle("Windows 10 Will Automatically Install on iPhones").get(0);
		Article microsoftArticle3 = articleRepository.findByTitle("Xbox One Update").get(0);
		
		Tag gameDesign = tagRepository.findByTitle("game design").get(0);
		Tag programming = tagRepository.findByTitle("programming").get(0);
		Tag engineering = tagRepository.findByTitle("engineering").get(0);
		Tag sciFi = tagRepository.findByTitle("sci-fi").get(0);
		
		/*
		// same as below
		microsoftArticle1.addTag(gameDesign);
		articleRepository.save(microsoftArticle1);
		
		microsoftArticle2.addTag(gameDesign);
		microsoftArticle2.addTag(engineering);
		microsoftArticle2.addTag(sciFi);
		articleRepository.save(microsoftArticle2);

		microsoftArticle3.addTag(gameDesign);
		articleRepository.save(microsoftArticle3);
		
		androidArticle1.addTag(gameDesign);
		articleRepository.save(androidArticle1);
		
		appleArticle1.addTag(programming);
		articleRepository.save(appleArticle1);
		
		appleArticle2.addTag(engineering);
		articleRepository.save(appleArticle2);
		*/
			
		// same as above
		gameDesign.addArticle(microsoftArticle1);
		gameDesign.addArticle(microsoftArticle2);
		gameDesign.addArticle(microsoftArticle3);
		gameDesign.addArticle(androidArticle1);
		tagRepository.save(gameDesign);
		
		programming.addArticle(appleArticle1);
		tagRepository.save(programming);
		
		engineering.addArticle(microsoftArticle2);
		engineering.addArticle(appleArticle2);
		tagRepository.save(engineering);
		
		sciFi.addArticle(microsoftArticle2);
		tagRepository.save(sciFi);
	}
	
	public void deleteSampleData() {
		List<Author> authors = authorRepository.findAllByOrderByUsernameAsc();
		for (Author a : authors) {
			authorRepository.delete(a);
		}
		
		List<Section> sections = sectionRepository.findAllByOrderByTitleAsc();
		for (Section s : sections) {
			sectionRepository.delete(s);
		}
		
		List<Tag> tags = tagRepository.findAllByOrderByTitleAsc();
		for (Tag t : tags) {
			tagRepository.delete(t);
		}
		
		List<File> files = fileRepository.findAllByOrderByFilename();
		for (File f : files) {
			fileRepository.delete(f);
		}
		
		List<SiteDetail> siteDetails = siteDetailRepository.findAllByOrderByName();
		for (SiteDetail s : siteDetails) {
			siteDetailRepository.delete(s);
		}
	}
}
