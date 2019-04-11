package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:      UserBindRoleDto. </p>
 * <p>Description 用户绑定角色 </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    北京云杉世界信息技术有限公司 </p>
 *
 * @author         <a href="yangwensheng@meicai.cn"/>杨文生</a>
 * @since      2019/4/11 14:57
 */
@Data
@ApiModel(value = "用户绑定角色Dto")
public class UserBindRoleDto implements Serializable {

	private static final long serialVersionUID = 9072884085554674954L;

	/**
	 * 角色Id集合
	 */
	@ApiModelProperty(value = "角色Id集合")
	private List<String> roleIdList;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;

}
