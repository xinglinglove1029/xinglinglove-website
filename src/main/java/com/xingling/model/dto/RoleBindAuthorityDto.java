package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:      RoleBindAuthority. </p>
 * <p>Description 角色绑定资源 </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    北京云杉世界信息技术有限公司 </p>
 *
 * @author         <a href="yangwensheng@meicai.cn"/>杨文生</a>
 * @since      2019/4/10 20:08
 */
@Data
@ApiModel(value = "角色绑定资源Dto")
public class RoleBindAuthorityDto implements Serializable {

	private static final long serialVersionUID = 6518810380878732988L;
	/**
	 * 未绑定的用户集合
	 */
	@ApiModelProperty(value = "未绑定的用户集合")
	private List<ResourceDto> resourceInfoList;

	/**
	 * 已经绑定的用户集合
	 */
	@ApiModelProperty(value = "角色id")
	private String roleId;

}
