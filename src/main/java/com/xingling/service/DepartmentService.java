package com.xingling.service;


import com.xingling.base.BaseService;
import com.xingling.model.domain.Department;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.vo.DeptTreeVo;

import java.util.List;

/**
 * Title: DepartmentService<br>;
 * Description: 部门<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 16:49
 */
public interface DepartmentService extends BaseService<Department> {

    /**
     * <p>Title:     getDeptTree. </p>
     * <p>Description 初始化部门树</p>
     *
     * @return      List<DeptTreeVo>
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/22 15:28
     */
    List<DeptTreeVo> getDeptTree();

    /**
     * <p>Title:     getDeptById. </p>
     * <p>Description 根据id查询部门</p>
     *
     * @param   deptId  String
     * @return  Department
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/22 15:28
     */
    Department getDeptById(String deptId);

    /**
     * <p>Title:     deleteDeptById. </p>
     * <p>Description 根据id删除部门</p>
     *
     * @param     deptId String
     * @return    int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/22 15:28
     */
    int deleteDeptById(String deptId, AuthUserDto authUserDto);

    /**
     * <p>Title:     saveDeptInfo. </p>
     * <p>Description 保存部门信息</p>
     *
     * @param   dept Department
     * @return  int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/22 15:28
     */
    int saveDeptInfo(Department dept, AuthUserDto authUserDto);

    /**
     * <p>Title:     modifyDeptInfo. </p>
     * <p>Description 修改部门信息</p>
     *
     * @param   dept Department
     * @return  int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/22 15:28
     */
    int modifyDeptInfo(Department dept, AuthUserDto authUserDto);
}
