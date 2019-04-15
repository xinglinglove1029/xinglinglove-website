package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.Role;
import com.xingling.model.domain.User;
import com.xingling.model.domain.UserRole;
import com.xingling.model.dto.RoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface User role mapper.
 */
@Mapper
@Component
public interface UserRoleMapper extends MyMapper<UserRole> {

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
     * @since 2018 /4/28 14:57
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
     * <p>Title:      deleteByUserId. </p>
     * <p>Description 刪除用戶和角色的關係</p>
     *
     * @param       userId   String
     * @return
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/11 17:06
     */
    int deleteByUserId(String userId);
}