package com.xingling.service;


import com.xingling.base.BaseService;
import com.xingling.model.domain.Role;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.RoleBindAuthorityDto;
import com.xingling.model.dto.RoleBindUserDto;
import com.xingling.model.vo.RoleBindUserVo;

import java.util.List;

/**
 * <p>Title:      RoleService. </p>
 * <p>Description TODO </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since 2018 /1/16 14:50
 */
public interface RoleService extends BaseService<Role> {

    /**
     * <p>Title:     queryListPage. </p>
     * <p>Description 查询角色信息列表</p>
     *
     * @param role the role
     * @return list
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 14:21
     */
    List<Role> queryListPage(Role role);

    /**
     * <p>Title:     deleteRoleById. </p>
     * <p>Description 删除角色信息</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 14:21
     */
    int deleteRoleById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     enableRoleById. </p>
     * <p>Description 启用角色</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 14:21
     */
    int enableRoleById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     disableRoleById. </p>
     * <p>Description 根据角色编号禁用角色信息</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 14:21
     */
    int disableRoleById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     modifyRole. </p>
     * <p>Description 修改角色信息</p>
     *
     * @param role        the role
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 14:21
     */
    int modifyRole(Role role, AuthUserDto authUserDto);

    /**
     * <p>Title:     saveRoleInfo. </p>
     * <p>Description 保存角色信息</p>
     *
     * @param role        the role
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 14:21
     */
    int saveRoleInfo(Role role, AuthUserDto authUserDto);

    /**
     * <p>Title:     getBindUserByRoleId. </p>
     * <p>Description 根据角色编号查询已绑定的用户信息</p>
     *
     * @param roleId        the role id
     * @param currentUserId the current user id
     * @return bind user by role id
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 14:21
     */
    RoleBindUserVo getBindUserByRoleId(String roleId, String currentUserId);

    /**
     * <p>Title:      roleBindResource. </p>
     * <p>Description 角色绑定资源</p>
     *
     * @param        roleBindAuthority  RoleBindAuthority
     * @return
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/10 20:18
     */
    void roleBindResource(RoleBindAuthorityDto roleBindAuthority, AuthUserDto authUserDto);

    List<String> getBindResourceInfoByRoleId(String roleId);

    /**
     * <p>Title:      roleBindUser. </p>
     * <p>Description 角色绑定用户</p>
     *
     * @param        roleBindUserDto  RoleBindUserDto
     * @return        void
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/17 18:05
     */
    void roleBindUser(RoleBindUserDto roleBindUserDto, AuthUserDto authUserDto);
}