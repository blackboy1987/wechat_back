
package com.igomall.listener;

import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.setting.Article;
import com.igomall.service.SearchService;
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
	private SearchService searchService;

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

		String info = "I|n|i|t|i|a|l|i|z|i|n|g| |S|H|O|P|+|+| |B|2|B|2|C| |";
		LOGGER.info(info.replace("|", ""));


		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			searchService.index(Article.class);
			searchService.index(Course.class);
			searchService.index(Lesson.class);
		});
	}

}