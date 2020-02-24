
package com.igomall.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.member.Member;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity - 部门
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_feedback")
public class Feedback extends BaseEntity<Long> {

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 4000)
	@Column(length = 400,nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	private Integer status;


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}