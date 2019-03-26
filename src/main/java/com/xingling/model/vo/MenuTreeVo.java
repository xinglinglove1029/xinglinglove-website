package com.xingling.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("菜单树Vo")
public class MenuTreeVo extends TreeNode implements Serializable {

	private static final long serialVersionUID = -2099147126084213856L;

	/**
	 * 菜单编码
	 */
	@ApiModelProperty("菜单编码")
	private String menuCode;

	/**
	 * 菜单名称
	 */
	@ApiModelProperty("菜单名称")
	private String menuName;

	/**
	 * 父菜单名称
	 */
	@ApiModelProperty("父菜单名称")
	private String parentMenuName;

	/**
	 * 菜单URL
	 */
	@ApiModelProperty("菜单URL")
	private String url;
	/**
	 * 图标
	 */
	@ApiModelProperty("图标")
	private String icon;

	/**
	 * 序号
	 */
	@ApiModelProperty("序号")
	private Integer number;

	/**
	 * 禁用
	 */
	@ApiModelProperty("禁用")
	private boolean disabled;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态")
	private String status;
}