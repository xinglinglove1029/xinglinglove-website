package com.xingling.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingling.base.BaseController;
import com.xingling.common.WrapMapper;
import com.xingling.common.Wrapper;
import com.xingling.model.domain.Role;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.CheckRoleNameDto;
import com.xingling.model.dto.RoleBindUserDto;
import com.xingling.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title:	  UserController. </p>
 * <p>Description 用户controller </p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>
 * @CreateDate 2017 /8/17 19:05
 */
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "RoleController", tags = "RoleController", description = "角色controller", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("role/index");
        return modelAndView;
    }


    /**
     * <p>Title:	  listPage. </p>
     * <p>Description 分页查询角色列表</p>
     *
     * @param role the role
     * @return the wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @CreateDate 2017 /8/17 19:04
     */
    @PostMapping(value = "/listPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询角色列表")
    public Wrapper<PageInfo<Role>> listPage(@ApiParam(name = "role", value = "角色信息") @RequestBody Role role) {
        PageHelper.startPage(role.getPageNum(), role.getPageSize());
        List<Role> listPage = roleService.queryListPage(role);
        PageInfo<Role> pageInfo = new PageInfo<>(listPage);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
    }


    /**
     * <p>Title:      delete. </p>
     * <p>Description 根据id删除角色</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/delete")
    @ApiOperation(httpMethod = "POST", value = "根据id删除角色")
    public Wrapper<Integer> delete(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = roleService.deleteRoleById(id,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      enable. </p>
     * <p>Description 启用角色</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/enable")
    @ApiOperation(httpMethod = "POST", value = "启用角色")
    public Wrapper<Integer> enable(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = roleService.enableRoleById(id,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      disable. </p>
     * <p>Description 禁用角色</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/disable")
    @ApiOperation(httpMethod = "POST", value = "禁用角色")
    public Wrapper<Integer> disable(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = roleService.disableRoleById(id,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      modifyUser. </p>
     * <p>Description 修改角色信息</p>
     *
     * @param role the role
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 15:38
     */
    @PostMapping(value = "/modifyRole")
    @ApiOperation(httpMethod = "POST", value = "修改角色信息")
    public Wrapper<Integer> modifyRole(@ApiParam(name = "role", value = "用户信息") @RequestBody Role role) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = roleService.modifyRole(role,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      saveRoleInfo. </p>
     * <p>Description 保存角色信息</p>
     *
     * @param role the role
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/22 17:24
     */
    @PostMapping(value = "/saveRoleInfo")
    @ApiOperation(httpMethod = "POST", value = "保存角色信息")
    public Wrapper<Integer> saveRoleInfo(@ApiParam(name = "role", value = "用户信息") @RequestBody Role role) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = roleService.saveRoleInfo(role,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      checkRoleName. </p>
     * <p>Description 校验角色名唯一性</p>
     *
     * @param checkRoleNameDto the check role name dto
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/24 17:08
     */
    @PostMapping(value = "/checkRoleName")
    @ApiOperation(httpMethod = "POST", value = "校验角色名唯一性")
    public Wrapper<Boolean> checkRoleName(@ApiParam(name = "checkRoleNameDto", value = "用户名dto") @RequestBody CheckRoleNameDto checkRoleNameDto) {
        boolean flag = false;
        String roleId = checkRoleNameDto.getRoleId();
        String roleName = checkRoleNameDto.getRoleName();
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", roleName);
        if (StringUtils.isNotEmpty(roleId)) {
            criteria.andNotEqualTo("id", roleId);
        }
        int result = roleService.selectCountByExample(example);
        if(result > 0) {
            flag = true;
        }
        return WrapMapper.ok(flag);
    }

    @RequestMapping(value = "/getBindUserByRoleId/{roleId}", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "获取角色绑定用户页面数据")
    public Wrapper<RoleBindUserDto> getBindUserByRoleId(@ApiParam(name = "roleId", value = "角色id") @PathVariable String roleId) {
        AuthUserDto authUserDto = getLoginAuthDto();
        String currentUserId = authUserDto.getUserId();
        RoleBindUserDto bindUserDto = roleService.getBindUserByRoleId(roleId, currentUserId);
        return WrapMapper.ok(bindUserDto);
    }
}
