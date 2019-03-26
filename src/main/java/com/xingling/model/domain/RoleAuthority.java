package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_role_authority")
public class RoleAuthority extends BaseEntiy implements Serializable {

    private static final long serialVersionUID = 3427359556351421571L;
    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 权限id
     */
    @Column(name = "authority_id")
    private String authorityId;

}