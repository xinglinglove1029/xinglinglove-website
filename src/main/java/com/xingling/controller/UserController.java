package com.xingling.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingling.base.BaseController;
import com.xingling.common.WrapMapper;
import com.xingling.common.Wrapper;
import com.xingling.model.domain.Department;
import com.xingling.model.domain.Role;
import com.xingling.model.domain.User;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.CheckUserNameDto;
import com.xingling.model.dto.UserBindRoleDto;
import com.xingling.service.DepartmentService;
import com.xingling.service.RoleService;
import com.xingling.service.UserRoleService;
import com.xingling.service.UserService;
import com.xingling.util.SecurityUserUtils;
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
import java.util.stream.Collectors;

/**
 * <p>Title:	  UserController. </p>
 * <p>Description 用户controller </p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>
 * @CreateDate 2017 /8/17 19:05
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "UserController", tags = "UserController", description = "用户controller", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private DepartmentService departmentService;

    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/index");
        modelAndView.addObject("userId",SecurityUserUtils.getUser().getId());
        return modelAndView;
    }



    /**
     * <p>Title:	  selectUserById. </p>
     * <p>Description 根据id查询用户信息</p>
     *
     * @param id String
     * @return the wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @CreateDate 2017 /8/17 19:04
     */
    @GetMapping("/{id}")
    @ApiOperation(notes = "根据id查询用户信息", httpMethod = "GET", value = "根据id查询用户信息")
    public Wrapper<User> selectUserById(@PathVariable String id) {
        User user = this.userService.selectByKey(id);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, user);
    }

    /**
     * <p>Title:	  listPage. </p>
     * <p>Description 分页查询用户列表</p>
     *
     * @param user the user
     * @return the wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @CreateDate 2017 /8/17 19:04
     */
    @PostMapping(value = "/listPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询用户列表")
    public Wrapper<PageInfo<User>> listPage(@ApiParam(name = "user", value = "用户信息") @RequestBody User user) {
        PageHelper.startPage(user.getPageNum(), user.getPageSize());
        List<User> listPage = userService.queryListPage(user);
        PageInfo<User> pageInfo = new PageInfo<>(listPage);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
    }

    /**
     * <p>Title:      getCurrentUser. </p>
     * <p>Description 获取当前用户信息</p>
     *
     * @param
     * @return current user
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:10
     */
    @GetMapping(value = "/getCurrentUser")
    @ApiOperation(httpMethod = "GET", value = "获取当前用户信息")
    public Wrapper<AuthUserDto> getCurrentUser() {
        String loginName = SecurityUserUtils.getUser().getUserName();
        AuthUserDto authUserDto = userService.getAuthUserInfo(loginName);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, authUserDto);
    }

    /**
     * <p>Title:      delete. </p>
     * <p>Description 根据id删除用户</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/delete")
    @ApiOperation(httpMethod = "POST", value = "根据id删除用户")
    public Wrapper<Integer> delete(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = userService.deleteUserById(id,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      enable. </p>
     * <p>Description 启用用户</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/enable")
    @ApiOperation(httpMethod = "POST", value = "启用用户")
    public Wrapper<Integer> enable(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = userService.enableUserById(id,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      disable. </p>
     * <p>Description 禁用用户</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/disable")
    @ApiOperation(httpMethod = "POST", value = "禁用用户")
    public Wrapper<Integer> disable(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = userService.disableUserById(id,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      modifyUser. </p>
     * <p>Description 修改用户信息</p>
     *
     * @param user the user
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 15:38
     */
    @PostMapping(value = "/modifyUser")
    @ApiOperation(httpMethod = "POST", value = "修改用户信息")
    public Wrapper<Integer> modifyUser(@ApiParam(name = "user", value = "用户信息") @RequestBody User user) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = userService.modifyUser(user,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      saveUserInfo. </p>
     * <p>Description 保存用户信息</p>
     *
     * @param user the user
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/22 17:24
     */
    @PostMapping(value = "/saveUserInfo")
    @ApiOperation(httpMethod = "POST", value = "保存用户信息")
    public Wrapper<Integer> saveUserInfo(@ApiParam(name = "user", value = "用户信息") @RequestBody User user) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = userService.saveUserInfo(user,authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      checkUserName. </p>
     * <p>Description 校验用户名唯一性</p>
     *
     * @param checkUserNameDto the check user name dto
     * @return wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/24 17:08
     */
    @PostMapping(value = "/checkUserName")
    @ApiOperation(httpMethod = "POST", value = "校验用户名唯一性")
    public Wrapper<Boolean> checkUserName(@ApiParam(name = "checkUserNameDto", value = "用户名dto") @RequestBody CheckUserNameDto checkUserNameDto) {
        boolean flag = false;
        String userId = checkUserNameDto.getUserId();
        String userName = checkUserNameDto.getUserName();
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userName);
        if (StringUtils.isNotEmpty(userId)) {
            criteria.andNotEqualTo("id", userId);
        }
        int result = userService.selectCountByExample(example);
        if(result > 0) {
            flag = true;
        }
        return WrapMapper.ok(flag);
    }

    /**
     * <p>Title:      getRoleList. </p>
     * <p>Description 查詢所有角色信息</p>
     *
     * @return
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/11 14:35
     */
    @PostMapping(value = "/getRoleList")
    @ApiOperation(httpMethod = "POST", value = "查询角色信息")
    public Wrapper<List<Role>> getRoleList() {
        List<Role> roleList = roleService.selectAll();
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, roleList);
    }

    /**
     * <p>Title:      getRoleList. </p>
     * <p>Description 查詢当前用户所绑定的角色信息</p>
     *
     * @param userId String
     * @return
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/11 14:35
     */
    @PostMapping(value = "/getBindRoleListByUserId")
    @ApiOperation(httpMethod = "POST", value = "查询角色信息")
    public Wrapper<List<String>> getBindRoleListByUserId(@RequestBody String userId) {
        List<Role> bindRoleList = userRoleService.getBindRoleListByUserId(userId);
        List<String> roleIds = bindRoleList.stream().map(Role::getId).collect(Collectors.toList());
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, roleIds);
    }

    /**
     * <p>Title:      bindRole. </p>
     * <p>Description 用户绑定角色</p>
     *
     * @param         userBindRoleDto UserBindRoleDto
     * @return        Wrapper
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/11 17:23
     */
    @PostMapping(value = "/bindRole")
    @ApiOperation(httpMethod = "POST", value = "用户绑定角色")
    public Wrapper<?> bindRole(@RequestBody UserBindRoleDto userBindRoleDto) {
        userRoleService.bindRole(userBindRoleDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }


    /**
     * <p>Title:      getDepartmentList. </p>
     * <p>Description 查询部门信息</p>
     *
     * @return  List<Department>
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/11 20:17
     */
    @PostMapping(value = "/getDepartmentList")
    @ApiOperation(httpMethod = "POST", value = "查询部门信息")
    public Wrapper<List<Department>> getDepartmentList() {
        List<Department> departmentList = departmentService.selectAll();
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,departmentList);
    }

}
