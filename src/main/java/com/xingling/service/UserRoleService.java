package com.xingling.service;

import com.xingling.base.BaseService;
import com.xingling.model.domain.Role;
import com.xingling.model.domain.User;
import com.xingling.model.domain.UserRole;
import com.xingling.model.dto.RoleDto;
import com.xingling.model.dto.UserBindRoleDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * <p>Title:      UserRoleService. </p>
 * <p>Description TODO </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since 2018 /1/16 14:50
 */
public interface UserRoleService extends BaseService<UserRole> {

    /**
     * <p>Title:      loadUserAuthorities. </p>
     * <p>Description 根据userId查询拥有的权限</p>
     *
     * @param userId the user id
     * @return own authority
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /1/16 15:24
     */
    Collection<GrantedAuthority> loadUserAuthorities(String userId);

    /**
     * <p>Title:      queryOwnRoles. </p>
     * <p>Description 根据userId查询拥有的角色信息</p>
     *
     * @param query the query
     * @return list list
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/8 14:50
     */
    List<RoleDto> queryOwnRoles(UserRole query);

    /**
     * <p>Title:     getBindUserByRoleId. </p>
     * <p>Description 根据角色编号查询绑定的用户信息</p>
     *
     * @param roleId the role id
     * @return bind user by role id
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 14:55
     */
    List<User> getBindUserByRoleId(String roleId);

    /**
     * <p>Title:      getRoleList. </p>
     * <p>Description 查詢当前用户所绑定的角色信息</p>
     *
     * @return userId String
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/11 14:35
     */
    List<Role> getBindRoleListByUserId(String userId);

    /**
     * <p>Title:      bindRole. </p>
     * <p>Description 用户绑定角色</p>
     *
     * @param       userBindRoleDto UserBindRoleDto
     * @return
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/11 14:55
     */
    void bindRole(UserBindRoleDto userBindRoleDto);

    /**
     * <p>Title:      batchDeleteByRoleId. </p>
     * <p>Description 根据角色编码删除用户关系</p>
     *
     * @param      roleId   String
     * @return        void
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/17 18:12
     */
    void batchDeleteByRoleId(String roleId);
}