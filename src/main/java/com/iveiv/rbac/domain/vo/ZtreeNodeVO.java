package com.iveiv.rbac.domain.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Setter;

/**
 * ztree插件node数据
 * 
 * 去除空数据
 * 
 * @author irays
 *
 */
@JsonInclude(Include.NON_NULL)
@Setter
public class ZtreeNodeVO {
	/**
	 * 编码
	 */
	private String id;
	/**
	 * 父级编码
	 */
	private String pId;
	/**
	 * true 表示节点的输入框被勾选
	 * false 表示节点的输入框未勾选
	 */
	private boolean  checked;
	/**
	 * true 表示此节点的 checkbox / radio 被禁用。
	 * false 表示此节点的 checkbox / radio 可以使用。
	 */
	private boolean chkDisabled;
	/**
	 * 标准 javascript 语法， 例如：alert("test"); 等
	 */
	private String click;
	/**
	 * true 表示节点的输入框 强行设置为半勾选
	 * false 表示节点的输入框 根据 zTree 的规则自动计算半勾选状态
	 */
	private boolean halfCheck;
	/**
	 * 图标图片的 url 可以是相对路径也可以是绝对路径
	 * 设置相对路径请注意页面与图片之间的关系，确保图片能够正常加载
	 */
	private String icon;
	/**
	 * 图标图片的 url 可以是相对路径也可以是绝对路径
	 * 设置相对路径请注意页面与图片之间的关系，确保图片能够正常加载
	 */
	private String iconClose;
	/**
	 * 图标图片的 url 可以是相对路径也可以是绝对路径
	 * 设置相对路径请注意页面与图片之间的关系，确保图片能够正常加载
	 */
	private String iconOpen;
	/**
	 * 设置个性图标的 className
	 */
	private String iconSkin;
	/**
	 * true 表示被隐藏
	 * false 表示被显示
	 */
	private boolean isHidden;
	/**
	 * true 表示是父节点
	 * false 表示不是父节点
	 */
	private boolean isParent;
	/**
	 * 节点显示的名称字符串，标准 String 即可，所有特殊字符都会被自动转义
	 */
	private String name;
	/**
	 * true 表示此节点不显示 checkbox / radio，不影响勾选的关联关系，不影响父节点的半选状态。
	 * false 表示节点具有正常的勾选功能
	 */
	private String nocheck;
	/**
	 * true 表示节点为 展开 状态
	 *  false 表示节点为 折叠 状态
	 */
	private boolean open;
	/**
	 * 同超链接 target 属性: "_blank", "_self" 或 其他指定窗口名称
	 *	省略此属性，则默认为 "_blank"
	 */
	private String target;
	/**
	 * 节点链接的目标 URL
	 * 
	 */
	private String url;
	/**
	 * 标准的 JSON 数据对象
	 */
	private List<ZtreeNodeVO> children;
	
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}



	public String getpId() {
		return pId;
	}



	public void setpId(String pId) {
		this.pId = pId;
	}



	public String getId() {
		return id;
	}



	public boolean isChecked() {
		return checked;
	}



	public boolean isChkDisabled() {
		return chkDisabled;
	}



	public String getClick() {
		return click;
	}



	public boolean isHalfCheck() {
		return halfCheck;
	}



	public String getIcon() {
		return icon;
	}



	public String getIconClose() {
		return iconClose;
	}



	public String getIconOpen() {
		return iconOpen;
	}



	public String getIconSkin() {
		return iconSkin;
	}



	public boolean isHidden() {
		return isHidden;
	}



	public boolean isParent() {
		return isParent;
	}



	public String getName() {
		return name;
	}



	public String getNocheck() {
		return nocheck;
	}



	public boolean isOpen() {
		return open;
	}



	public String getTarget() {
		return target;
	}



	public String getUrl() {
		return url;
	}



	public List<ZtreeNodeVO> getChildren() {
		return children;
	}

}
