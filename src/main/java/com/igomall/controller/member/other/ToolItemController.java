
package com.igomall.controller.member.other;

import com.igomall.common.Message;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.member.Member;
import com.igomall.entity.other.ToolItem;
import com.igomall.entity.other.ToolItemError;
import com.igomall.security.CurrentUser;
import com.igomall.service.other.ToolItemErrorService;
import com.igomall.service.other.ToolItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 文章
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("memberToolItemController")
@RequestMapping("/api/member/tool")
public class ToolItemController extends BaseController {

	@Autowired
	private ToolItemService toolItemService;
	@Autowired
	private ToolItemErrorService toolItemErrorService;

	/**
	 * 删除
	 */
	@PostMapping("/error")
	public Message error(ToolItem toolItem, @CurrentUser Member member) {
		if(member==null){
			return Message.error("请先登陆");
		}


		ToolItem toolItem1 = toolItemService.find(toolItem.getId());
		if(toolItem1==null){
			return Message.error("书籍不存在");
		}
		ToolItemError toolItemError = new ToolItemError();
		BeanUtils.copyProperties(toolItem,toolItemError,"id","toolCategory","isPublication");
		toolItemError.setToolItem(toolItem1);
		toolItemError.setMember(member);
		toolItemError.setIsPublication(false);
		toolItemErrorService.save(toolItemError);
		return Message.success("操作成功");

	}
}