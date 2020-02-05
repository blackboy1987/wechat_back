
package com.igomall.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.common.BaseAttributeConverter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Entity - 权限
 * 
 * @author blackboy
 * @version 1.0
 */
@Entity
@Table(name = "edu_permission")
public class Permission extends OrderedEntity<Long> {

	private static final long serialVersionUID = 5095521437302782717L;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	@JsonView({ListView.class})
	private String name;

	@JsonView({ListView.class})
	private Boolean isEnabled;

	/**
	 * 菜单
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false,updatable = false)
	private Menu menu;

	/**
	 * url
	 */
	@NotEmpty
	@Column(nullable = false, length = 4000)
	@Convert(converter = PermissionConverter.class)
	private Map<String,String> permissions;

	@NotEmpty
	@Column(nullable = false, length = 4000)
	@Convert(converter = UrlConverter.class)
	@JsonView({ListView.class})
	private List<String> urls;

	/**
	 * 描述
	 */
	@JsonView({ListView.class})
	private String memo;

	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Map<String, String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Map<String, String> permissions) {
		this.permissions = permissions;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	@Transient
	@JsonView({ListView.class})
	public String getMenuName(){
		if(menu!=null){
			return menu.getName();
		}
		return null;
	}
	@Transient
	@JsonView({ListView.class})
	public Long getMenuId(){
		if(menu!=null){
			return menu.getId();
		}
		return null;
	}

	public interface ListView extends BaseView {}

	/**
	 * 类型转换 - 可选项
	 *
	 * @author blackboy
	 * @version 1.0
	 */
	@Converter
	public static class PermissionConverter extends BaseAttributeConverter<Map<String,String>> {
	}
	@Converter
	public static class UrlConverter extends BaseAttributeConverter<List<String>> {
	}
}