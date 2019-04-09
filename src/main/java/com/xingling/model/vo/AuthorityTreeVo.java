package com.xingling.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("权限树Vo")
public class AuthorityTreeVo extends TreeNode implements Serializable {

	private static final long serialVersionUID = -5643147896084213812L;


	/**
	 * 权限名称
	 */
	@ApiModelProperty("资源名称")
	private String resourceName;


	/**
	 * 类型
	 */
	@ApiModelProperty("类型: 1:菜单  2:权限")
	private String type;
}