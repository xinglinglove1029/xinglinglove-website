package com.xingling.service;

import com.xingling.base.BaseService;
import com.xingling.model.domain.Authority;
import com.xingling.model.domain.RoleAuthority;

import java.util.List;

/**
 * <p>Title:      RoleAuthorityService. </p>
 * <p>Description TODO </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author         <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/1/16 14:50
 */
public interface RoleAuthorityService extends BaseService<RoleAuthority> {

    /**
     * <p>Title:      batchDeleteByRoleId. </p>
     * <p>Description 根据角色删除权限信息</p>
     *
     * @param         roleId String
     * @return        int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/18 12:09
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
    List<Authority> getAuthorityByRoleIds(List<String> roleIds);

    /**
     * <p>Title:      getAuthorityByRoleIds. </p>
     * <p>Description 根据角色编码查询权限信息</p>
     *
     * @param       roleId  String
     * @return        List
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/18 11:36
     */
    List<RoleAuthority> selectRoleAuthorityByRoleId(String roleId);
}