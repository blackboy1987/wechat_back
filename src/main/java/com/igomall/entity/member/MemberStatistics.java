
package com.igomall.entity.member;

import com.igomall.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity - 会员
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
@Table(name = "edu_member_statistics")
public class MemberStatistics extends BaseEntity<Long> {

	private static final long serialVersionUID = 1533130686714725835L;

	@NotNull
	@Column(nullable = false,updatable = false,unique = true)
	private Long memberId;

	@NotNull
	@Column(nullable = false)
	private Integer articleCount;

	@NotNull
	@Column(nullable = false)
	private Integer commentCount;

	@NotNull
	@Column(nullable = false)
	private Integer praise;

	@NotNull
	@Column(nullable = false)
	private Integer visitCount;


	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public void init(){
		setArticleCount(0);
		setCommentCount(0);
		setPraise(0);
		setVisitCount(0);
	}
}