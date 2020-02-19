
package com.igomall.service.course.impl;

import com.igomall.common.Filter;
import com.igomall.common.Order;
import com.igomall.dao.course.FolderDao;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Folder;
import com.igomall.service.course.FolderService;
import com.igomall.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Service - 地区
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class FolderServiceImpl extends BaseServiceImpl<Folder, Long> implements FolderService {

	@Autowired
	private FolderDao folderDao;

	@Transactional(readOnly = true)
	public List<Folder> findRoots() {
		return folderDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<Folder> findRoots(Integer count) {
		return folderDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<Folder> findParents(Folder folder, boolean recursive, Integer count) {
		return folderDao.findParents(folder, recursive, count);
	}

	@Transactional(readOnly = true)
	public List<Folder> findChildren(Folder folder, boolean recursive, Integer count) {
		return folderDao.findChildren(folder, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = "folder", allEntries = true)
	public Folder save(Folder folder) {
		Assert.notNull(folder);

		setValue(folder);
		return super.save(folder);
	}

	@Override
	@Transactional
	@CacheEvict(value = "folder", allEntries = true)
	public Folder update(Folder folder) {
		Assert.notNull(folder);

		setValue(folder);
		for (Folder children : folderDao.findChildren(folder, true, null)) {
			setValue(children);
		}
		return super.update(folder);
	}

	@Override
	@Transactional
	@CacheEvict(value = "folder", allEntries = true)
	public Folder update(Folder folder, String... ignoreProperties) {
		return super.update(folder, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "folder", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "folder", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "folder", allEntries = true)
	public void delete(Folder folder) {
		super.delete(folder);
	}

	/**
	 * 设置值
	 * 
	 * @param folder
	 *            地区
	 */
	private void setValue(Folder folder) {
		if (folder == null) {
			return;
		}
		Folder parent = folder.getParent();
		if (parent != null) {
			folder.setFullName(parent.getFullName() + folder.getName());
			folder.setTreePath(parent.getTreePath() + parent.getId() + Folder.TREE_PATH_SEPARATOR);
		} else {
			folder.setFullName(folder.getName());
			folder.setTreePath(Folder.TREE_PATH_SEPARATOR);
		}
		folder.setGrade(folder.getParentIds().length);
	}


	@Override
	public List<Folder> findList(Course course, Integer count, List<Filter> filters, List<Order> orders) {
		return folderDao.findList(course,count,filters,orders);
	}
}