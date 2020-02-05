
package com.igomall.service;

import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.entity.Admin;
import com.igomall.entity.Department;
import com.igomall.security.AuthenticationProvider;

import java.util.Date;

/**
 * Service - 管理员
 * 
 * @author blackboy
 * @version 1.0
 */
public interface AdminService extends BaseService<Admin, Long>, AuthenticationProvider {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByUsername(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param id
	 *            ID
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	boolean emailUnique(Long id, String email);

	/**
	 * 根据E-mail查找管理员
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByEmail(String email);

	Page<Admin> findPage(Pageable pageable, String name, String username, String email, Department department, Date beginDate, Date endDate);

}