package com.xingling.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Title: DeptTreeVo<br>;
 * Description: <br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/22 15:12
 */
@Data
@ApiModel("部门树Vo")
public class DeptTreeVo extends TreeNode implements Serializable {

    private static final long serialVersionUID = -8965673656771646351L;

    /**
     * 部门编码
     */
    @ApiModelProperty("部门编码")
    private String deptCode;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 父部门名称
     */
    @ApiModelProperty("父部门名称")
    private String parentDeptName;

    /**
     * 序号
     */
    @ApiModelProperty("序号")
    private Integer number;

    /**
     * 禁用
     */
    @ApiModelProperty("禁用")
    private boolean disabled;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;
}
