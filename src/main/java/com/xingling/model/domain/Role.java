package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_role")
public class Role extends BaseEntiy implements Serializable {

    private static final long serialVersionUID = -3034820223936073111L;
    /**
     * 角色编码
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;
}