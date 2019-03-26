package com.xingling.model.dto;

import com.xingling.model.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:      RoleBindUserDto. </p>
 * <p>Description TODO </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author         <a href="yangwensheng@meicai.cn"/>杨文生</a>
 * @since      2018/4/28 14:15
 */
@Data
@ApiModel(value = "角色绑定用户Dto")
public class RoleBindUserDto implements Serializable {

	private static final long serialVersionUID = 6518810380878732988L;
	/**
	 * 未绑定的用户集合
	 */
	@ApiModelProperty(value = "未绑定的用户集合")
	private List<User> notBindUserList;

	/**
	 * 已经绑定的用户集合
	 */
	@ApiModelProperty(value = "已经绑定的用户集合")
	private List<User> alreadyBindUserList;

	private List<String> alreadyBindUserIds;


}
