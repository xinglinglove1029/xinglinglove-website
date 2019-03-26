package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_user_role")
public class UserRole extends BaseEntiy implements Serializable {

    private static final long serialVersionUID = 7514905054821282053L;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

}