
package com.igomall.service.other.impl;

import java.util.Collections;
import java.util.List;

import com.igomall.dao.other.ToolCategoryDao;
import com.igomall.entity.other.ToolCategory;
import com.igomall.service.impl.BaseServiceImpl;
import com.igomall.service.other.ToolCategoryService;
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
public class ToolCategoryServiceImpl extends BaseServiceImpl<ToolCategory, Long> implements ToolCategoryService {

	@Autowired
	private ToolCategoryDao toolCategoryDao;

	@Transactional(readOnly = true)
	public List<ToolCategory> findRoots() {
		return toolCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<ToolCategory> findRoots(Integer count) {
		return toolCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "toolCategory", condition = "#useCache")
	public List<ToolCategory> findRoots(Integer count, boolean useCache) {
		return toolCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<ToolCategory> findParents(ToolCategory toolCategory, boolean recursive, Integer count) {
		return toolCategoryDao.findParents(toolCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "toolCategory", condition = "#useCache")
	public List<ToolCategory> findParents(Long toolCategoryId, boolean recursive, Integer count, boolean useCache) {
		ToolCategory toolCategory = toolCategoryDao.find(toolCategoryId);
		if (toolCategoryId != null && toolCategory == null) {
			return Collections.emptyList();
		}
		return toolCategoryDao.findParents(toolCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	public List<ToolCategory> findTree() {
		return toolCategoryDao.findChildren(null, true, null);
	}

	@Transactional(readOnly = true)
	public List<ToolCategory> findChildren(ToolCategory toolCategory, boolean recursive, Integer count) {
		return toolCategoryDao.findChildren(toolCategory, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "toolCategory", condition = "#useCache")
	public List<ToolCategory> findChildren(Long toolCategoryId, boolean recursive, Integer count, boolean useCache) {
		ToolCategory toolCategory = toolCategoryDao.find(toolCategoryId);
		if (toolCategoryId != null && toolCategory == null) {
			return Collections.emptyList();
		}
		return toolCategoryDao.findChildren(toolCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public ToolCategory save(ToolCategory toolCategory) {
		Assert.notNull(toolCategory);

		setValue(toolCategory);
		return super.save(toolCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public ToolCategory update(ToolCategory toolCategory) {
		Assert.notNull(toolCategory);

		setValue(toolCategory);
		for (ToolCategory children : toolCategoryDao.findChildren(toolCategory, true, null)) {
			setValue(children);
		}
		return super.update(toolCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public ToolCategory update(ToolCategory toolCategory, String... ignoreProperties) {
		return super.update(toolCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "tool", "toolCategory" }, allEntries = true)
	public void delete(ToolCategory toolCategory) {
		super.delete(toolCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param toolCategory
	 *            文章分类
	 */
	private void setValue(ToolCategory toolCategory) {
		if (toolCategory == null) {
			return;
		}
		ToolCategory parent = toolCategory.getParent();
		if (parent != null) {
			toolCategory.setTreePath(parent.getTreePath() + parent.getId() + ToolCategory.TREE_PATH_SEPARATOR);
		} else {
			toolCategory.setTreePath(ToolCategory.TREE_PATH_SEPARATOR);
		}
		toolCategory.setGrade(toolCategory.getParentIds().length);
	}

}