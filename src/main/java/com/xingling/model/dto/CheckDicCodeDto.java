package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Title: CheckDicCodeDto<br>;
 * Description: 数据字典<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 18:01
 */
@Data
@ApiModel(value = "校验数据字典编码唯一性Dto ")
public class CheckDicCodeDto {

    /**
     * 数据字典ID
     */
    @ApiModelProperty(value = "数据字典ID")
    private String dicId;

    /**
     * 数据字典编码
     */
    @ApiModelProperty(value = "数据字典编码")
    private String dicCode;
}
