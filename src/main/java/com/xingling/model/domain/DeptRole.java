package com.xingling.model.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "xl_dept_role")
public class DeptRole  implements Serializable {

    private static final long serialVersionUID = 5708803502970200051L;
    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 部门编号
     */
    @Column(name = "dept_id")
    private String deptId;

}