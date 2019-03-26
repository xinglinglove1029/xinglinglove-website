package com.xingling.service.impl;

import com.google.common.collect.Lists;
import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.MenuMapper;
import com.xingling.model.domain.Menu;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.vo.MenuTreeVo;
import com.xingling.service.MenuService;
import com.xingling.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>Title:	  MenuServiceImpl <br/> </p>
 * <p>Description 菜单service实现 <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/3/23 16:57
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {
    
    @Resource
    private MenuMapper menuMapper;
    
    @Override
    public int saveMenuInfo(Menu menu, AuthUserDto authUserDto) {
        Menu querMenu = new Menu();
        querMenu.setId(menu.getPid());
        querMenu.setDel(Constants.DELETE_NO);
        Menu mn = menuMapper.selectOne(querMenu);
        if (Objects.isNull(mn)) {
            throw new BusinessException("父菜单信息不存在");
        }
        menu.setLevel(mn.getLevel()+1);
        menu.setCreator(authUserDto.getRealName());
        menu.setCreatorId(authUserDto.getUserId());
        menu.setUpdater(authUserDto.getRealName());
        menu.setUpdaterId(authUserDto.getUserId());
        return menuMapper.insertSelective(menu);
    }

    @Override
    public int modifyMenu(Menu menu, AuthUserDto authUserDto) {
        Menu querMenu = new Menu();
        querMenu.setId(menu.getId());
        querMenu.setDel(Constants.DELETE_NO);
        Menu mn = menuMapper.selectOne(querMenu);
        if (Objects.isNull(mn)) {
            throw new BusinessException("菜单信息不存在");
        }
        menu.setCreator(authUserDto.getRealName());
        menu.setCreatorId(authUserDto.getUserId());
        menu.setUpdater(authUserDto.getRealName());
        menu.setUpdaterId(authUserDto.getUserId());
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int disableMenuById(String id, AuthUserDto authUserDto) {
        Menu querMenu = new Menu();
        querMenu.setId(id);
        querMenu.setDel(Constants.DELETE_NO);
        Menu menu = menuMapper.selectOne(querMenu);
        if (Objects.isNull(menu)) {
            throw new BusinessException("菜单信息不存在");
        }
        if (!Constants.ENABLE.equals(menu.getStatus())) {
            throw new BusinessException("不是启用状态的不能禁用");
        }
        querMenu.setStatus(Constants.DISABLE);
        querMenu.setUpdater(authUserDto.getRealName());
        querMenu.setUpdaterId(authUserDto.getUserId());
        querMenu.setUpdateTime(new Date());
        return menuMapper.updateByPrimaryKeySelective(querMenu);
    }

    @Override
    public int enableMenuById(String id, AuthUserDto authUserDto) {
        Menu querMenu = new Menu();
        querMenu.setId(id);
        querMenu.setDel(Constants.DELETE_NO);
        Menu menu = menuMapper.selectOne(querMenu);
        if (Objects.isNull(menu)) {
            throw new BusinessException("菜单信息不存在");
        }
        if (!Constants.DISABLE.equals(menu.getStatus())) {
            throw new BusinessException("不是禁用状态的不能启用");
        }
        querMenu.setStatus(Constants.ENABLE);
        querMenu.setUpdater(authUserDto.getRealName());
        querMenu.setUpdaterId(authUserDto.getUserId());
        querMenu.setUpdateTime(new Date());
        return menuMapper.updateByPrimaryKeySelective(querMenu);
    }

    @Override
    public int deleteMenuById(String id, AuthUserDto authUserDto) {
        Menu querMenu = new Menu();
        querMenu.setId(id);
        querMenu.setDel(Constants.DELETE_NO);
        Menu menu = menuMapper.selectOne(querMenu);
        if (Objects.isNull(menu)) {
            throw new BusinessException("菜单信息不存在");
        }
        // 查询其是否还存在子节点
        Menu selectMenu = new Menu();
        selectMenu.setPid(id);
        int count = menuMapper.selectCount(selectMenu);
        if(count > 0){
            throw new BusinessException("该菜单下还存在子菜单不能将其删除");
        }
        querMenu.setDel(Constants.DELETE_YES);
        querMenu.setUpdater(authUserDto.getRealName());
        querMenu.setUpdaterId(authUserDto.getUserId());
        querMenu.setUpdateTime(new Date());
        return menuMapper.updateByPrimaryKeySelective(querMenu);
    }

    @Override
    public List<MenuTreeVo> getMenuTree(String menuId) {
        List<Menu> menuList = menuMapper.selectAllMenu();
        Map<String, String> menuMap = menuList.stream().collect(Collectors.toMap(Menu::getId, Menu::getMenuName));
        List<MenuTreeVo> trees = Lists.newArrayList();
        MenuTreeVo node = null;
        for (Menu menu : menuList) {
            node = new MenuTreeVo();
            BeanUtils.copyProperties(menu, node);
            node.setParentId(menu.getPid());
            String parentMenuName = menuMap.get(menu.getPid());
            node.setParentMenuName(parentMenuName);
            if("root".equals(menu.getMenuCode())){
                node.setDisabled(true);
            }else{
                node.setDisabled(false);
            }
            node.setNumber(menu.getNumber());
            trees.add(node);
        }
        return TreeUtil.bulid(trees,menuId) ;
    }

    @Override
    public Menu getMenuById(String menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }
}
