package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>Title:      CheckUserNameDto. </p>
 * <p>Description TODO </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author         <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/2/24 17:06
 */
@Data
@ApiModel(value = "校验用户唯一性Dto ")
public class CheckUserNameDto implements Serializable {

	private static final long serialVersionUID = -819578099604622577L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private String userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户姓名")
	private String userName;
}
