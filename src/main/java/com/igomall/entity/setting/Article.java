
package com.igomall.entity.setting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.Member;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.Length;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Entity - 文章
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Indexed
@Entity
@Table(name = "edu_article")
public class Article extends BaseEntity<Long> {

	private static final long serialVersionUID = 1475773294701585482L;

	/**
	 * 点击数缓存名称
	 */
	public static final String HITS_CACHE_NAME = "articleHits";

	/**
	 * 路径
	 */
	private static final String PATH = "/article/detail/%d_%d";

	/**
	 * 内容分页长度
	 */
	private static final int PAGE_CONTENT_LENGTH = 2000;

	/**
	 * 内容分页标签
	 */
	private static final String PAGE_BREAK_TAG = "shopxx_page_break_tag";

	/**
	 * 段落配比
	 */
	private static final Pattern PARAGRAPH_PATTERN = Pattern.compile("[^,;\\.!?，；。！？]*([,;\\.!?，；。！？]+|$)");

	/**
	 * 标题
	 */
	@JsonView({ViewView.class,BaseView.class,ListView.class,EditView.class})
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Boost(1.5F)
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String title;

	/**
	 * 作者
	 */
	@JsonView({ViewView.class,BaseView.class,ListView.class,EditView.class})
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@Length(max = 200)
	private String author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false,updatable = false)
	@JsonIgnore
	private Member member;

	/**
	 * 内容
	 */
	@JsonView({ViewView.class,EditView.class})
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Lob
	private String content;

	/**
	 * 是否发布
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(nullable = false)
	private Boolean isPublication;

	/**
	 * 是否置顶
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(nullable = false)
	private Boolean isTop;

	/**
	 * 点击数
	 */
	@Column(nullable = false)
	@JsonView({ViewView.class,BaseView.class,ListView.class})
	private Long hits;

	/**
	 * 文章分类
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ArticleCategory articleCategory;

	/**
	 * 文章标签
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("order asc")
	private Set<ArticleTag> articleTags = new HashSet<>();

	/**
	 * 文章专题
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("order asc")
	private Set<ArticleTopic> articleTopics = new HashSet<>();

	@NotEmpty
	@Length(max = 200)
	@JsonView({ListView.class,EditView.class})
	@javax.validation.constraints.Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	private String image;

	@Length(max = 500)
	@JsonView({BaseView.class,EditView.class})
	private String memo;

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取作者
	 * 
	 * @return 作者
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 设置作者
	 * 
	 * @param author
	 *            作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取是否发布
	 * 
	 * @return 是否发布
	 */
	public Boolean getIsPublication() {
		return isPublication;
	}

	/**
	 * 设置是否发布
	 * 
	 * @param isPublication
	 *            是否发布
	 */
	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */
	public Boolean getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否置顶
	 * 
	 * @param isTop
	 *            是否置顶
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取点击数
	 * 
	 * @return 点击数
	 */
	public Long getHits() {
		return hits;
	}

	/**
	 * 设置点击数
	 * 
	 * @param hits
	 *            点击数
	 */
	public void setHits(Long hits) {
		this.hits = hits;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取文章分类
	 * 
	 * @return 文章分类
	 */
	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	/**
	 * 设置文章分类
	 * 
	 * @param articleCategory
	 *            文章分类
	 */
	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	/**
	 * 获取文章标签
	 * 
	 * @return 文章标签
	 */
	public Set<ArticleTag> getArticleTags() {
		return articleTags;
	}

	/**
	 * 设置文章标签
	 * 
	 * @param articleTags
	 *            文章标签
	 */
	public void setArticleTags(Set<ArticleTag> articleTags) {
		this.articleTags = articleTags;
	}

	public Set<ArticleTopic> getArticleTopics() {
		return articleTopics;
	}

	public void setArticleTopics(Set<ArticleTopic> articleTopics) {
		this.articleTopics = articleTopics;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@JsonView(BaseView.class)
	@Transient
	public String getPath() {
		return getPath(1);
	}

	/**
	 * 获取路径
	 * 
	 * @param pageNumber
	 *            页码
	 * @return 路径
	 */
	@Transient
	public String getPath(Integer pageNumber) {
		return String.format(Article.PATH, getId(), pageNumber);
	}

	/**
	 * 获取文本内容
	 * 
	 * @return 文本内容
	 */
	@JsonView(BaseView.class)
	@Transient
	public String getText() {
		if (StringUtils.isEmpty(getContent())) {
			return StringUtils.EMPTY;
		}
		return StringUtils.remove(Jsoup.parse(getContent()).text(), PAGE_BREAK_TAG);
	}

	/**
	 * 获取分页内容
	 * 
	 * @return 分页内容
	 */
	@Transient
	public String[] getPageContents() {
		if (StringUtils.isEmpty(getContent())) {
			return new String[] { StringUtils.EMPTY };
		}
		if (StringUtils.contains(getContent(), PAGE_BREAK_TAG)) {
			return StringUtils.splitByWholeSeparator(getContent(), PAGE_BREAK_TAG);
		}
		List<Node> childNodes = Jsoup.parse(getContent()).body().childNodes();
		if (CollectionUtils.isEmpty(childNodes)) {
			return new String[] { getContent() };
		}
		List<String> pageContents = new ArrayList<>();
		int textLength = 0;
		StringBuilder paragraph = new StringBuilder();
		for (Node node : childNodes) {
			if (node instanceof Element) {
				Element element = (Element) node;
				paragraph.append(element.outerHtml());
				textLength += element.text().length();
				if (textLength >= PAGE_CONTENT_LENGTH) {
					pageContents.add(paragraph.toString());
					textLength = 0;
					paragraph.setLength(0);
				}
			} else if (node instanceof TextNode) {
				TextNode textNode = (TextNode) node;
				Matcher matcher = PARAGRAPH_PATTERN.matcher(textNode.text());
				while (matcher.find()) {
					String content = matcher.group();
					paragraph.append(content);
					textLength += content.length();
					if (textLength >= PAGE_CONTENT_LENGTH) {
						pageContents.add(paragraph.toString());
						textLength = 0;
						paragraph.setLength(0);
					}
				}
			}
		}
		String pageContent = paragraph.toString();
		if (StringUtils.isNotEmpty(pageContent)) {
			pageContents.add(pageContent);
		}
		return pageContents.toArray(new String[pageContents.size()]);
	}

	/**
	 * 获取分页内容
	 * 
	 * @param pageNumber
	 *            页码
	 * @return 分页内容
	 */
	@Transient
	public String getPageContent(Integer pageNumber) {
		if (pageNumber == null || pageNumber < 1) {
			return null;
		}
		String[] pageContents = getPageContents();
		if (pageContents.length < pageNumber) {
			return null;
		}
		return pageContents[pageNumber - 1];
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	@Transient
	public int getTotalPages() {
		return getPageContents().length;
	}

	public String getArticleCategoryName(){
		if(articleCategory!=null){
			return articleCategory.getName();
		}
		return null;
	}

	@Transient
	@JsonView({EditView.class})
	public Long getArticleCategoryId(){
		if(articleCategory!=null){
			return articleCategory.getId();
		}
		return null;
	}

	@Transient
	public List<String> getArticleTopicNames(){
		List<String> articleTopicNames = new ArrayList<>();
		if(articleTopics!=null){
			for (ArticleTopic articleTopic:articleTopics) {
				articleTopicNames.add(articleTopic.getName());
			}
		}
		return articleTopicNames;
	}

	@Transient
	@JsonView({EditView.class})
	public List<Long> getArticleTopicIds(){
		List<Long> articleTopicIds = new ArrayList<>();
		if(articleTopics!=null){
			for (ArticleTopic articleTopic:articleTopics) {
				articleTopicIds.add(articleTopic.getId());
			}
		}
		return null;
	}

	@Transient
	public List<String> getArticleTagNames(){
		List<String> articleTagNames = new ArrayList<>();
		if(articleTags!=null){
			for (ArticleTag articleTag:articleTags) {
				articleTagNames.add(articleTag.getName());
			}
		}
		return articleTagNames;
	}

	@Transient
	@JsonView({EditView.class})
	public List<Long> getArticleTagIds(){
		List<Long> articleTagIds = new ArrayList<>();
		if(articleTags!=null){
			for (ArticleTag articleTag:articleTags) {
				articleTagIds.add(articleTag.getId());
			}
		}
		return articleTagIds;
	}


	@Transient
	@JsonView({ListView.class})
	public String getAvatar(){
		if(member!=null){
			return member.getAvatar();
		}
		return null;
	}

	public interface ViewView extends IdView{}
	public interface EditView extends IdView{}
	public interface ListView extends BaseView{}

	public interface LoadView extends IdView{}

}