package com.xingling.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Title: DicTreeVo<br>;
 * Description: <br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 17:05
 */
@Data
@ApiModel("数据字典树Vo")
public class DicTreeVo extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1517247638328913631L;

    /**
     * 数据字典名称
     */
    @ApiModelProperty("数据字典编码")
    private String code;

    /**
     * 数据字典名称
     */
    @ApiModelProperty("数据字典名称")
    private String name;

    /**
     * 父数据字典名称
     */
    @ApiModelProperty("父数据字典名称")
    private String parentName;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer number;

    /**
     * 禁用
     */
    @ApiModelProperty("禁用")
    private boolean disabled;

    /**
     * 数据字典名称
     */
    @ApiModelProperty("数据字典值")
    private String value;

    /**
     * 数据字典名称
     */
    @ApiModelProperty("数据字典备注")
    private String remark;
}
