package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_dictionary")
public class Dictionary extends BaseEntiy implements Serializable {

    private static final long serialVersionUID = 5572631974112650563L;

    /**
     * 父编码
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 数据字典编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 数据字典名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 数据字典值
     */
    @Column(name = "value")
    private String value;

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

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;


}