package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title:      CheckAuthorityCodeDto. </p>
 * <p>Description 权限编码dto </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author         <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/2/24 17:06
 */
@Data
@ApiModel(value = "校验权限编码唯一性Dto ")
public class CheckAuthorityCodeDto implements Serializable {

    private static final long serialVersionUID = 6961919671624709226L;

    /**
     * 权限ID
     */
    @ApiModelProperty(value = "权限ID")
    private String authorityId;

    /**
     * 权限编码
     */
    @ApiModelProperty(value = "权限编码")
    private String authorityCode;
}
