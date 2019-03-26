package com.xingling.service;

import com.xingling.base.BaseService;
import com.xingling.model.domain.Menu;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.vo.MenuTreeVo;

import java.util.List;

/**
 * <p>Title:      MenuService. </p>
 * <p>Description 菜单service </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since 2018 /1/16 14:50
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * <p>Title:     saveMenuInfo. </p>
     * <p>Description 保存菜单信息</p>
     *
     * @param menu        the menu
     * @param authUserDto the auth user dto
     * @return int int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:46
     */
    int saveMenuInfo(Menu menu, AuthUserDto authUserDto);

    /**
     * <p>Title:    modifyMenu. </p>
     * <p>Description 修改菜单信息</p>
     *
     * @param menu        the menu
     * @param authUserDto the auth user dto
     * @return int int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:46
     */
    int modifyMenu(Menu menu, AuthUserDto authUserDto);

    /**
     * <p>Title:     disableMenuById. </p>
     * <p>Description 禁用菜单信息</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:46
     */
    int disableMenuById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     enableMenuById. </p>
     * <p>Description 启用菜单信息</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:46
     */
    int enableMenuById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     deleteMenuById. </p>
     * <p>Description 根据id删除菜单信息</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:46
     */
    int deleteMenuById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     getMenuTree. </p>
     * <p>Description 查询菜单树</p>
     *
     * @return menu tree
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 11:55
     * @param menuId
     */
    List<MenuTreeVo> getMenuTree(String menuId);

    /**
     * <p>Title:     getMenuById. </p>
     * <p>Description 根据菜单id查询菜单信息</p>
     *
     * @param menuId the menu id
     * @return menu by id
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 18:23
     */
    Menu getMenuById(String menuId);
}