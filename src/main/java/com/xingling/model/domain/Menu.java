package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_menu")
public class Menu extends BaseEntiy implements Serializable {

    private static final long serialVersionUID = 3275345625828742364L;

    /**
     * 菜单编码
     */
    @Column(name = "menu_code")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 菜单url
     */
    @Column(name = "url")
    private String url;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 父菜单编号
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 层级
     */
    @Column(name = "level")
    private Integer level;

    /**
     * 是否有子节点
     */
    @Column(name = "leaf")
    private Integer leaf;

    /**
     * 排序编号
     */
    @Column(name = "number")
    private Integer number;

}