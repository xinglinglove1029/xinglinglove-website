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
@ApiModel(value = "校验菜单编码唯一性Dto ")
public class CheckMenuCodeDto implements Serializable{

    private static final long serialVersionUID = -8592932659951408281L;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    private String menuId;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码")
    private String menuCode;
}
