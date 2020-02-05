
package com.igomall.controller.admin.member;

import com.igomall.common.Message;
import com.igomall.common.Page;
import com.igomall.common.Pageable;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.member.MemberAttribute;
import com.igomall.service.member.MemberAttributeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AndPredicate;
import org.apache.commons.collections.functors.UniquePredicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Controller - 会员注册项
 * 
 * @author blackboy
 * @version 1.0
 */
@RestController("adminMemberAttributeController")
@RequestMapping("/admin/api/member_attribute")
public class MemberAttributeController extends BaseController {

	@Autowired
	private MemberAttributeService memberAttributeService;

	/**
	 * 检查配比语法是否正确
	 */
	@GetMapping("/check_pattern")
	public @ResponseBody boolean checkPattern(String pattern) {
		if (StringUtils.isEmpty(pattern)) {
			return false;
		}
		try {
			Pattern.compile(pattern);
		} catch (PatternSyntaxException e) {
			return false;
		}
		return true;
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(MemberAttribute memberAttribute) {
		if (!isValid(memberAttribute, BaseEntity.Save.class)) {
			return Message.error("参数错误");
		}
		if (MemberAttribute.Type.select.equals(memberAttribute.getType()) || MemberAttribute.Type.checkbox.equals(memberAttribute.getType())) {
			List<String> options = memberAttribute.getOptions();
			CollectionUtils.filter(options, new AndPredicate(new UniquePredicate(), new Predicate() {
				public boolean evaluate(Object object) {
					String option = (String) object;
					return StringUtils.isNotEmpty(option);
				}
			}));
			if (CollectionUtils.isEmpty(options)) {
				return Message.error("参数错误");
			}
			memberAttribute.setPattern(null);
		} else if (MemberAttribute.Type.text.equals(memberAttribute.getType())) {
			memberAttribute.setOptions(null);
		} else {
			return Message.error("参数错误");
		}
		if (StringUtils.isNotEmpty(memberAttribute.getPattern())) {
			try {
				Pattern.compile(memberAttribute.getPattern());
			} catch (PatternSyntaxException e) {
				return Message.error("参数错误");
			}
		}
		Integer propertyIndex = memberAttributeService.findUnusedPropertyIndex();
		if (propertyIndex == null) {
			return Message.error("参数错误");
		}
		memberAttribute.setPropertyIndex(null);
		memberAttributeService.save(memberAttribute);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public MemberAttribute edit(Long id) {
		return memberAttributeService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(MemberAttribute memberAttribute) {
		if (!isValid(memberAttribute)) {
			return Message.error("参数错误");
		}
		MemberAttribute pMemberAttribute = memberAttributeService.find(memberAttribute.getId());
		if (pMemberAttribute == null) {
			return Message.error("参数错误");
		}
		if (MemberAttribute.Type.select.equals(pMemberAttribute.getType()) || MemberAttribute.Type.checkbox.equals(pMemberAttribute.getType())) {
			List<String> options = memberAttribute.getOptions();
			CollectionUtils.filter(options, new AndPredicate(new UniquePredicate(), new Predicate() {
				public boolean evaluate(Object object) {
					String option = (String) object;
					return StringUtils.isNotEmpty(option);
				}
			}));
			if (CollectionUtils.isEmpty(options)) {
				return Message.error("参数错误");
			}
			memberAttribute.setPattern(null);
		} else {
			memberAttribute.setOptions(null);
		}
		if (StringUtils.isNotEmpty(memberAttribute.getPattern())) {
			try {
				Pattern.compile(memberAttribute.getPattern());
			} catch (PatternSyntaxException e) {
				return Message.error("参数错误");
			}
		}
		memberAttributeService.update(memberAttribute, "type", "propertyIndex");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public Page<MemberAttribute> list(Pageable pageable) {
		return memberAttributeService.findPage(pageable);

	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		memberAttributeService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}