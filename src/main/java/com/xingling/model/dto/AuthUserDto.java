package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:	  spring-cloud-koala <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/2/8 14:29
 */
@Data
@ApiModel(value = "认证后的用户信息")
public class AuthUserDto implements Serializable{

    private static final long serialVersionUID = 7009696589052519469L;
    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private String userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 电话号
     */
    @ApiModelProperty(value = "电话号")
    private String cellPhone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 拥有的角色列表
     */
    @ApiModelProperty(value = "拥有的角色列表")
    private List<RoleDto> roleList;
}
