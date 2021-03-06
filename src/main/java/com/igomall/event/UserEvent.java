
package com.igomall.event;

import com.igomall.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * Event - 用户
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public abstract class UserEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7432438231982667326L;

	/**
	 * 用户
	 */
	private User user;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserEvent(Object source, User user) {
		super(source);
		this.user = user;
	}

	/**
	 * 获取用户
	 * 
	 * @return 用户
	 */
	public User getUser() {
		return user;
	}

}