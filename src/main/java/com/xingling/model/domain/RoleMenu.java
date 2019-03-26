package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_role_menu")
public class RoleMenu extends BaseEntiy implements Serializable {

    private static final long serialVersionUID = -4275468683311261833L;

    /**
     * 角色编号
     */
    @Column(name = "status")
    private String roleId;

    /**
     * 菜单编号
     */
    @Column(name = "status")
    private String menuId;

}