
package com.igomall.service.other.impl;

import java.util.Collections;
import java.util.List;

import com.igomall.dao.other.BookCategoryDao;
import com.igomall.entity.other.BookCategory;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.other.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 文章分类
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class BookCategoryServiceImpl extends BaseServiceImpl<BookCategory, Long> implements BookCategoryService {

	@Autowired
	private BookCategoryDao bookCategoryDao;

	@Transactional(readOnly = true)
	public List<BookCategory> findRoots() {
		return bookCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<BookCategory> findRoots(Integer count) {
		return bookCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "bookCategory", condition = "#useCache")
	public List<BookCategory> findRoots(Integer count, boolean useCache) {
		return bookCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<BookCategory> findParents(BookCategory bookCategory, boolean recursive, Integer count) {
		return bookCategoryDao.findParents(bookCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "bookCategory", condition = "#useCache")
	public List<BookCategory> findParents(Long bookCategoryId, boolean recursive, Integer count, boolean useCache) {
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if (bookCategoryId != null && bookCategory == null) {
			return Collections.emptyList();
		}
		return bookCategoryDao.findParents(bookCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	public List<BookCategory> findTree() {
		return bookCategoryDao.findChildren(null, true, null);
	}

	@Transactional(readOnly = true)
	public List<BookCategory> findChildren(BookCategory bookCategory, boolean recursive, Integer count) {
		return bookCategoryDao.findChildren(bookCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "bookCategory", condition = "#useCache")
	public List<BookCategory> findChildren(Long bookCategoryId, boolean recursive, Integer count, boolean useCache) {
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if (bookCategoryId != null && bookCategory == null) {
			return Collections.emptyList();
		}
		return bookCategoryDao.findChildren(bookCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "book", "bookCategory" }, allEntries = true)
	public BookCategory save(BookCategory bookCategory) {
		Assert.notNull(bookCategory);

		setValue(bookCategory);
		return super.save(bookCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "book", "bookCategory" }, allEntries = true)
	public BookCategory update(BookCategory bookCategory) {
		Assert.notNull(bookCategory);

		setValue(bookCategory);
		for (BookCategory children : bookCategoryDao.findChildren(bookCategory, true, null)) {
			setValue(children);
		}
		return super.update(bookCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "book", "bookCategory" }, allEntries = true)
	public BookCategory update(BookCategory bookCategory, String... ignoreProperties) {
		return super.update(bookCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "book", "bookCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "book", "bookCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "book", "bookCategory" }, allEntries = true)
	public void delete(BookCategory bookCategory) {
		super.delete(bookCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param bookCategory
	 *            文章分类
	 */
	private void setValue(BookCategory bookCategory) {
		if (bookCategory == null) {
			return;
		}
		BookCategory parent = bookCategory.getParent();
		if (parent != null) {
			bookCategory.setTreePath(parent.getTreePath() + parent.getId() + BookCategory.TREE_PATH_SEPARATOR);
		} else {
			bookCategory.setTreePath(BookCategory.TREE_PATH_SEPARATOR);
		}
		bookCategory.setGrade(bookCategory.getParentIds().length);
	}

}