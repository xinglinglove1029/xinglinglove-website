package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>Title:      CheckRoleNameDto. </p>
 * <p>Description TODO </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author         <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/2/24 17:06
 */
@Data
@ApiModel(value = "校验用户唯一性Dto ")
public class CheckRoleNameDto implements Serializable {
	
	private static final long serialVersionUID = -4910469405441910726L;

	/**
	 * 角色ID
	 */
	@ApiModelProperty(value = "角色ID")
	private String roleId;

	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;
}
