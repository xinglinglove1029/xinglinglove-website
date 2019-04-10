package com.xingling.model.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_user_dept")
public class UserDept implements Serializable {

    private static final long serialVersionUID = 5708803502970200051L;
    /**
     * 角色编号
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 部门编号
     */
    @Column(name = "dept_id")
    private String deptId;

}