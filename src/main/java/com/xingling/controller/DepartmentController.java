package com.xingling.controller;

import com.xingling.base.BaseController;
import com.xingling.common.WrapMapper;
import com.xingling.common.Wrapper;
import com.xingling.model.domain.Department;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.vo.DeptTreeVo;
import com.xingling.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title: DepartmentController<br>;
 * Description: 部门controller<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 16:53
 */
@RestController
@RequestMapping(value = "/department", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "DepartmentController", tags = "DepartmentController", description = "部门controller", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DepartmentController extends BaseController {
    
    @Resource
    private DepartmentService departmentService;
    
    /**
     * <p>Title:     getDicTree. </p>
     * <p>Description 查询部门树</p>
     *
     * @return Dept tree
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 11:54
     */
    @PostMapping(value = "/getDeptTree")
    @ApiOperation(httpMethod = "POST", value = "查询部门树")
    public Wrapper<List<DeptTreeVo>> getDeptTree() {
        List<DeptTreeVo> deptTreeVos = departmentService.getDeptTree();
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, deptTreeVos);
    }

    /**
     * <p>Title:     getDicById. </p>
     * <p>Description 根据部门编码查询数据在字典信息</p>
     *
     * @param         deptId String
     * @return      Wrapper
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/21 16:57
     */
    @PostMapping(value = "/getDeptById/{deptId}")
    @ApiOperation(httpMethod = "POST", value = "根据部门编码查询数据在字典信息")
    public Wrapper<Department> getDeptById(@PathVariable String deptId) {
        Department Dept = departmentService.getDeptById(deptId);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, Dept);
    }

    /**
     * <p>Title:      deleteDeptById. </p>
     * <p>Description 根据id删除部门</p>
     *
     * @param deptId the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/delete/{deptId}")
    @ApiOperation(httpMethod = "POST", value = "根据id删除部门")
    public Wrapper<Integer> deleteDeptById(@PathVariable String deptId) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = departmentService.deleteDeptById(deptId, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }


    /**
     * <p>Title:      modifyDeptInfo. </p>
     * <p>Description 修改部门信息</p>
     *
     * @param Dept the Dept
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 15:38
     */
    @PostMapping(value = "/modifyDeptInfo")
    @ApiOperation(httpMethod = "POST", value = "修改部门信息")
    public Wrapper<Integer> modifyDeptInfo(@ApiParam(name = "Department", value = "部门信息") @RequestBody Department Dept) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = departmentService.modifyDeptInfo(Dept, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      saveDeptInfo. </p>
     * <p>Description 保存部门信息</p>
     *
     * @param Dept the Dept
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/22 17:24
     */
    @PostMapping(value = "/saveDeptInfo")
    @ApiOperation(httpMethod = "POST", value = "保存部门信息")
    public Wrapper<Integer> saveDeptInfo(@ApiParam(name = "Department", value = "部门信息") @RequestBody Department Dept) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = departmentService.saveDeptInfo(Dept, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }
}
