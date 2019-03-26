package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Table(name = "xl_authority")
public class Authority extends BaseEntiy implements Serializable {

    private static final long serialVersionUID = -1875702988552461507L;
    /**
     * 权限编码
     */
    @Column(name = "authority_code")
    private String authorityCode;

    /**
     * 权限名称
     */
    @Column(name = "authority_name")
    private String authorityName;

    /**
     * 权限状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 权限url
     */
    @Column(name = "url")
    private String url;

    /**
     * 菜单编号
     */
    @Column(name = "menu_id")
    private String menuId;

    /**
     * 菜单编号
     */
    @Transient
    private String menuName;
}