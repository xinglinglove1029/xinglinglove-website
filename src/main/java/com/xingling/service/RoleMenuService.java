package com.xingling.service;


import com.xingling.base.BaseService;
import com.xingling.model.domain.Menu;
import com.xingling.model.domain.RoleMenu;

import java.util.List;

/**
 * <p>Title:      RoleMenuService. </p>
 * <p>Description 角色菜单service </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author         <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/1/16 14:50
 */
public interface RoleMenuService extends BaseService<RoleMenu> {

    /**
     * <p>Title:      batchDeleteByRoleId. </p>
     * <p>Description 根据角色id删除菜单信息</p>
     *
     * @param         roleId String
     * @return        int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/18 12:03
     */
    int batchDeleteByRoleId(String roleId);

    /**
     * <p>Title:      selectRoleMenuByRoleId. </p>
     * <p>Description 根据角色id查询绑定的菜单信息</p>
     *
     * @param         roleIds List<String>
     * @return        List<Menu>
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/18 12:03
     */
    List<Menu> getMenuByRoleIds(List<String> roleIds);

    /**
     * <p>Title:      selectRoleMenuByRoleId. </p>
     * <p>Description 根据角色id查询绑定的菜单信息</p>
     *
     * @param         roleId String
     * @return        List<RoleMenu>
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/6/18 12:03
     */
    List<RoleMenu> selectRoleMenuByRoleId(String roleId);
}