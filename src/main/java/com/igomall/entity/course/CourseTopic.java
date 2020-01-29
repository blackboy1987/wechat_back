
package com.igomall.entity.course;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.entity.OrderedEntity;
import com.igomall.entity.setting.Article;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 文章标签
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
@Table(name = "edu_course_topic")
public class CourseTopic extends OrderedEntity<Long> {

	private static final long serialVersionUID = -2735037966597250149L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	@JsonView({Article.LoadView.class})
	private String name;

	/**
	 * 图标
	 */
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	private String icon;

	/**
	 * 备注
	 */
	@Length(max = 200)
	private String memo;

	/**
	 * 文章
	 */
	@ManyToMany(mappedBy = "courseTopic", fetch = FetchType.LAZY)
	private Set<Course> courses = new HashSet<>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取图标
	 * 
	 * @return 图标
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置图标
	 * 
	 * @param icon
	 *            图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取文章
	 * 
	 * @return 文章
	 */
	public Set<Course> getCourses() {
		return courses;
	}

	/**
	 * 设置文章
	 * 
	 * @param courses
	 *            文章
	 */
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Course> courses = getCourses();
		if (courses != null) {
			for (Course course : courses) {
				course.getCourseTopics().remove(this);
			}
		}
	}

}