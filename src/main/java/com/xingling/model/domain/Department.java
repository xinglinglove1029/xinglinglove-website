package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_dept")
public class Department extends BaseEntiy implements Serializable {

    private static final long serialVersionUID = -3239705851288745459L;

    /**
     * 部门编码
     */
    @Column(name = "dept_code")
    private String deptCode;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 父编码
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 层级
     */
    @Column(name = "level")
    private Integer level;

    /**
     * 排序
     */
    @Column(name = "number")
    private Integer number;


}