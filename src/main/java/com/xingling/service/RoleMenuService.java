package com.xingling.service;


import com.xingling.base.BaseService;
import com.xingling.model.domain.RoleMenu;

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

    int batchDeleteByRoleId(String roleId);
}