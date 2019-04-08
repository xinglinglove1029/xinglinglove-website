package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Title: CheckMenuCodeDto<br>;
 * Description: <br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: http://www.xinglinglove.com<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/4/27 10:45
 */
@Data
@ApiModel(value = "校验部门编码唯一性Dto ")
public class CheckDeptCodeDto implements Serializable{

    private static final long serialVersionUID = -8592932659951408281L;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "部门ID")
    private String deptId;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "部门编码")
    private String deptCode;
}
