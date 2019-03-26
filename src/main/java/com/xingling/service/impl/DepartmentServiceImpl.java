package com.xingling.service.impl;


import com.google.common.collect.Lists;
import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.DepartmentMapper;
import com.xingling.model.domain.Department;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.vo.DeptTreeVo;
import com.xingling.service.DepartmentService;
import com.xingling.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Title: DepartmentServiceImpl<br>;
 * Description: 部门<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 16:49
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<DeptTreeVo> getDeptTree() {
        List<Department> deptList = departmentMapper.selectAll();
        Map<String, String> deptMap = deptList.stream().collect(Collectors.toMap(Department::getId, Department::getDeptName));
        List<DeptTreeVo> trees = Lists.newArrayList();
        DeptTreeVo node = null;
        for (Department dept : deptList) {
            node = new DeptTreeVo();
            BeanUtils.copyProperties(dept, node);
            node.setParentId(dept.getPid());
            String parentDicName = deptMap.get(dept.getPid());
            node.setParentdeptName(parentDicName);
            if(Constants.DICTIONARY_ROOT.equals(dept.getDeptCode())){
                node.setDisabled(true);
            }else{
                node.setDisabled(false);
            }
            node.setNumber(dept.getNumber());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, Constants.ROOT_PARENTID) ;
    }

    @Override
    public Department getDeptById(String deptId) {
        return departmentMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public int deleteDeptById(String deptId, AuthUserDto authUserDto) {
        Department queryDept = new Department();
        queryDept.setId(deptId);
        queryDept.setDel(Constants.DELETE_NO);
        Department department = departmentMapper.selectOne(queryDept);
        if (Objects.isNull(department)) {
            throw new BusinessException("父编码信息不存在");
        }
        queryDept.setVersion(department.getVersion()+1);
        queryDept.setDel(Constants.DELETE_YES);
        queryDept.setUpdater(authUserDto.getRealName());
        queryDept.setUpdaterId(authUserDto.getUserId());
        queryDept.setUpdateTime(new Date());
        return departmentMapper.insertSelective(queryDept);
    }

    @Override
    public int saveDeptInfo(Department dept, AuthUserDto authUserDto) {
        Department queryDept = new Department();
        queryDept.setId(dept.getPid());
        queryDept.setDel(Constants.DELETE_NO);
        Department department = departmentMapper.selectOne(queryDept);
        if (Objects.isNull(department)) {
            throw new BusinessException("父编码信息不存在");
        }
        dept.setCreator(authUserDto.getRealName());
        dept.setCreatorId(authUserDto.getUserId());
        dept.setUpdater(authUserDto.getRealName());
        dept.setUpdaterId(authUserDto.getUserId());
        dept.setUpdateTime(new Date());
        dept.setCreateTime(new Date());
        return departmentMapper.insertSelective(dept);
    }

    @Override
    public int modifyDeptInfo(Department dept, AuthUserDto authUserDto) {
        Department queryDept = new Department();
        queryDept.setId(dept.getPid());
        queryDept.setDel(Constants.DELETE_NO);
        Department department = departmentMapper.selectOne(queryDept);
        if (Objects.isNull(department)) {
            throw new BusinessException("父编码信息不存在");
        }
        dept.setVersion(department.getVersion()+1);
        dept.setCreator(authUserDto.getRealName());
        dept.setCreatorId(authUserDto.getUserId());
        dept.setUpdater(authUserDto.getRealName());
        dept.setUpdaterId(authUserDto.getUserId());
        dept.setUpdateTime(new Date());
        return departmentMapper.updateByPrimaryKeySelective(dept);
    }
}
