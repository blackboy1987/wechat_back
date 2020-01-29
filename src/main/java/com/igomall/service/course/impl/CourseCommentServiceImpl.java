
package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.dao.course.CourseCommentDao;
import com.igomall.dao.course.CourseDao;
import com.igomall.dao.member.MemberDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.CourseComment;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.Member;
import com.igomall.entity.setting.ArticleComment;
import com.igomall.service.course.CourseCommentService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * Service - 评论
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class CourseCommentServiceImpl extends BaseServiceImpl<CourseComment, Long> implements CourseCommentService {

	@Autowired
	private CourseCommentDao courseCommentDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private CourseDao courseDao;

	@Override
	@Transactional(readOnly = true)
	public List<CourseComment> findList(Member member, Course course, CourseComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		return courseCommentDao.findList(member, course, type, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "courseComment", condition = "#useCache")
	public List<CourseComment> findList(Long memberId, Long courseId, CourseComment.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}
		Course course = courseDao.find(courseId);
		if (courseId != null && course == null) {
			return Collections.emptyList();
		}
		return courseCommentDao.findList(member, course, type, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CourseComment> findPage(Member member, Course course, Lesson lesson,CourseComment.Type type, Boolean isShow, Pageable pageable) {
		return courseCommentDao.findPage(member, course, lesson, type, isShow, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Member member, Course course, CourseComment.Type type, Boolean isShow) {
		return courseCommentDao.count(member, course, type, isShow);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseComment", allEntries = true)
	public CourseComment save(CourseComment review) {
		Assert.notNull(review,"");

		CourseComment pReview = super.save(review);
		Course course = pReview.getCourse();
		if (course != null) {
			courseCommentDao.flush();
			long totalScore = courseCommentDao.calculateTotalScore(course);
			long scoreCount = courseCommentDao.calculateScoreCount(course);
			course.setTotalScore(totalScore);
			course.setScoreCount(scoreCount);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseComment", allEntries = true)
	public CourseComment update(CourseComment review) {
		Assert.notNull(review);

		CourseComment pReview = super.update(review);
		Course course = ((CourseComment) pReview).getCourse();
		if (course != null) {
			courseCommentDao.flush();
			long totalScore = courseCommentDao.calculateTotalScore(course);
			long scoreCount = courseCommentDao.calculateScoreCount(course);
			course.setTotalScore(totalScore);
			course.setScoreCount(scoreCount);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseComment", allEntries = true)
	public CourseComment update(CourseComment review, String... ignoreProperties) {
		return super.update(review, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseComment", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseComment", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "courseComment", allEntries = true)
	public void delete(CourseComment review) {
		if (review != null) {
			super.delete(review);
			Course course = review.getCourse();
			if (course != null) {
				courseCommentDao.flush();
				long totalScore = courseCommentDao.calculateTotalScore(course);
				long scoreCount = courseCommentDao.calculateScoreCount(course);
				course.setTotalScore(totalScore);
				course.setScoreCount(scoreCount);
			}
		}
	}

	@Override
	@CacheEvict(value = "courseComment", allEntries = true)
	public void reply(CourseComment courseComment, CourseComment replyArticleComment) {
		if (courseComment == null || replyArticleComment == null) {
			return;
		}

		replyArticleComment.setIsShow(true);
		replyArticleComment.setCourse(courseComment.getCourse());
		replyArticleComment.setForReview(courseComment);
		replyArticleComment.setScore(courseComment.getScore());
		replyArticleComment.setMember(courseComment.getMember());
		courseCommentDao.persist(replyArticleComment);
	}
}