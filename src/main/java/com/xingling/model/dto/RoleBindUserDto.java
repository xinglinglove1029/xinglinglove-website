package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:      RoleBindUserDto. </p>
 * <p>Description 角色绑定用户 </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    北京云杉世界信息技术有限公司 </p>
 *
 * @author         <a href="yangwensheng@meicai.cn"/>杨文生</a>
 * @since      2019/4/10 20:08
 */
@Data
@ApiModel(value = "角色绑定用户Dto")
public class RoleBindUserDto implements Serializable {

	private static final long serialVersionUID = 6295607363243681934L;
	/**
	 * 用户集合
	 */
	@ApiModelProperty(value = "用户集合")
	private List<String> userIds;

	/**
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id")
	private String roleId;

}
