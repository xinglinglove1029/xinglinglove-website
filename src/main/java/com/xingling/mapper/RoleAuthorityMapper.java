package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.Authority;
import com.xingling.model.domain.RoleAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleAuthorityMapper extends MyMapper<RoleAuthority> {

    /**
     * <p>Title:      batchDeleteByRoleId. </p>
     * <p>Description 根据角色删除权限信息</p>
     *
     * @param         roleId String
     * @return      int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/18 11:37
     */
    int batchDeleteByRoleId(String roleId);

    /**
     * <p>Title:      getAuthorityByRoleIds. </p>
     * <p>Description 根据角色查询权限信息</p>
     *
     * @param       roleIds  List<String>
     * @return        List
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/18 11:36
     */
    List<Authority> getAuthorityByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
     * <p>Title:      selectRoleAuthorityByRoleId. </p>
     * <p>Description 根据角色查询权限信息</p>
     *
     * @param       roleId  List<String>
     * @return        List
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/18 11:36
     */
    List<RoleAuthority> selectRoleAuthorityByRoleId(String roleId);
}