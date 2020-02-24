
package com.igomall.listener;

import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.other.BookCategory;
import com.igomall.service.other.BookCategoryService;
import com.igomall.service.other.ProjectCategoryService;
import com.igomall.service.other.ToolCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Listener - 初始化
 * 
 * @author SHOP++ Team
 * @version 5.0
 */
@Component
public class InitListener {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(InitListener.class.getName());

	@Autowired
	private BookCategoryService bookCategoryService;
	@Autowired
	private ToolCategoryService toolCategoryService;
	@Autowired
	private ProjectCategoryService projectCategoryService;


	/**
	 * 事件处理
	 * 
	 * @param contextRefreshedEvent
	 *            ContextRefreshedEvent
	 */
	@EventListener
	public void handle(ContextRefreshedEvent contextRefreshedEvent) {
		if (contextRefreshedEvent.getApplicationContext() == null || contextRefreshedEvent.getApplicationContext().getParent() != null) {
			return;
		}

		String info = "I|n|i|t|i|a|l|i|z|i|n|g| |";
		LOGGER.info(info.replace("|", ""));
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			bookCategoryService.findRoots1();
			toolCategoryService.findRoots1();
			projectCategoryService.findRoots1();
		});
	}

}