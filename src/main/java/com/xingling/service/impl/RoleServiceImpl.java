package com.xingling.service.impl;

import com.google.common.collect.Lists;
import com.xingling.base.BaseEntiy;
import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.RoleMapper;
import com.xingling.model.domain.Role;
import com.xingling.model.domain.RoleAuthority;
import com.xingling.model.domain.RoleMenu;
import com.xingling.model.domain.User;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.ResourceDto;
import com.xingling.model.dto.RoleBindAuthorityDto;
import com.xingling.model.dto.RoleBindUserDto;
import com.xingling.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>Title:	  spring-cloud-koala <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/1/16 14:45
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserService userService;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RoleAuthorityService roleAuthorityService;

    @Override
    public List<Role> queryListPage(Role role) {
        return roleMapper.queryListPage(role);
    }

    @Override
    public int deleteRoleById(String id, AuthUserDto authUserDto) {
        Role queryRole = new Role();
        queryRole.setId(id);
        queryRole.setDel(Constants.DELETE_NO);
        Role role = roleMapper.selectOne(queryRole);
        if (Objects.isNull(role)) {
            throw new BusinessException("角色信息不存在");
        }
        queryRole.setDel(Constants.DELETE_YES);
        queryRole.setUpdater(authUserDto.getRealName());
        queryRole.setUpdaterId(authUserDto.getUserId());
        queryRole.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(queryRole);
    }

    @Override
    public int enableRoleById(String id, AuthUserDto authUserDto) {
        Role queryRole = new Role();
        queryRole.setId(id);
        queryRole.setDel(Constants.DELETE_NO);
        Role role = roleMapper.selectOne(queryRole);
        if (Objects.isNull(role)) {
            throw new BusinessException("角色信息不存在");
        }
        if (!Constants.DISABLE.equals(role.getStatus())) {
            throw new BusinessException("不是禁用状态的不能启用");
        }
        queryRole.setStatus(Constants.ENABLE);
        queryRole.setUpdater(authUserDto.getRealName());
        queryRole.setUpdaterId(authUserDto.getUserId());
        queryRole.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(queryRole);
    }

    @Override
    public int disableRoleById(String id, AuthUserDto authUserDto) {
        Role queryRole = new Role();
        queryRole.setId(id);
        queryRole.setDel(Constants.DELETE_NO);
        Role role = roleMapper.selectOne(queryRole);
        if (Objects.isNull(role)) {
            throw new BusinessException("角色信息不存在");
        }
        if (!Constants.ENABLE.equals(role.getStatus())) {
            throw new BusinessException("不是启用状态的不能禁用");
        }
        queryRole.setStatus(Constants.DISABLE);
        queryRole.setUpdater(authUserDto.getRealName());
        queryRole.setUpdaterId(authUserDto.getUserId());
        queryRole.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(queryRole);
    }

    @Override
    public int modifyRole(Role role, AuthUserDto authUserDto) {
        Role queryRole = new Role();
        queryRole.setId(role.getId());
        queryRole.setDel(Constants.DELETE_NO);
        Role rl = roleMapper.selectOne(queryRole);
        if (Objects.isNull(rl)) {
            throw new BusinessException("角色信息不存在");
        }
        role.setCreator(authUserDto.getRealName());
        role.setCreatorId(authUserDto.getUserId());
        role.setUpdater(authUserDto.getRealName());
        role.setUpdaterId(authUserDto.getUserId());
        role.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int saveRoleInfo(Role role, AuthUserDto authUserDto) {
        role.setCreator(authUserDto.getRealName());
        role.setCreatorId(authUserDto.getUserId());
        role.setUpdater(authUserDto.getRealName());
        role.setUpdaterId(authUserDto.getUserId());
        return roleMapper.insertSelective(role);
    }

    @Override
    public RoleBindUserDto getBindUserByRoleId(String roleId, String currentUserId) {
        // 查询全部用户集合
        List<User> allUserList = userService.selectAllExcludeSupper();

        // 查询已绑定的用户集合
        List<User> alreadyBindUserList = userRoleService.getBindUserByRoleId(roleId);

        // 查询未绑定的用户集合
        List<User> notBindUserList = allUserList.stream().filter(item -> !alreadyBindUserList.contains(item)).collect(Collectors.toList());

        List<String> alreadyBindUserIds = alreadyBindUserList.stream().map(BaseEntiy::getId).collect(Collectors.toList());

        RoleBindUserDto bindUserDto = new RoleBindUserDto();
        bindUserDto.setAlreadyBindUserList(alreadyBindUserList);
        bindUserDto.setNotBindUserList(notBindUserList);
        bindUserDto.setAlreadyBindUserIds(alreadyBindUserIds);
        return bindUserDto;
    }

    @Override
    public void roleBindResource(RoleBindAuthorityDto roleBindAuthority, AuthUserDto authUserDto) {
        // 根据角色id查询该角色
        Role queryRole = new Role();
        queryRole.setId(roleBindAuthority.getRoleId());
        queryRole.setDel(Constants.DELETE_NO);
        Role rl = roleMapper.selectOne(queryRole);
        if (Objects.isNull(rl)) {
            throw new BusinessException("角色信息不存在");
        }

        if(roleBindAuthority.getResourceInfoList().size() == 0){
            throw  new BusinessException("选择的权限不能为空");
        }

        // 删除该角色所绑定的菜单和权限关系
        roleMenuService.batchDeleteByRoleId(roleBindAuthority.getRoleId());
        roleAuthorityService.batchDeleteByRoleId(roleBindAuthority.getRoleId());


        List<ResourceDto> resourceInfoList = roleBindAuthority.getResourceInfoList();
        // 过滤出菜单
        List<ResourceDto> menuResourceInfo = resourceInfoList.stream().filter(f -> "1".equals(f.getType())).collect(Collectors.toList());
        List<String> menuIdList = menuResourceInfo.stream().map(ResourceDto::getResourceId).collect(Collectors.toList());

        // 过滤出按钮权限
        List<ResourceDto> authorityResourceInfo = resourceInfoList.stream().filter(f -> "1".equals(f.getType())).collect(Collectors.toList());
        List<String> authorityIdList = authorityResourceInfo.stream().map(ResourceDto::getResourceId).collect(Collectors.toList());

        List<RoleMenu> roleMenuList = this.buildMenuRoleInfo(menuIdList, roleBindAuthority.getRoleId());
        List<RoleAuthority> roleAuthorities = this.buildAuthorityRoleInfo(authorityIdList, roleBindAuthority.getRoleId());

        // 保存菜单权限与角色的关系
        roleMenuService.batchSave(roleMenuList);
        roleAuthorityService.batchSave(roleAuthorities);

    }


    private List<RoleMenu> buildMenuRoleInfo(List<String> menuIdList, String roleId) {
        List<RoleMenu> roleMenuList = Lists.newArrayList();
        RoleMenu roleMenu = null;
        for (String menuId : menuIdList) {
            roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }
        return roleMenuList;
    }

    private List<RoleAuthority> buildAuthorityRoleInfo(List<String> authorityIdList, String roleId) {
        List<RoleAuthority> roleAuthorities = Lists.newArrayList();
        RoleAuthority roleAuthority = null;
        for (String authorityId : authorityIdList) {
            roleAuthority = new RoleAuthority();
            roleAuthority.setRoleId(roleId);
            roleAuthority.setAuthorityId(authorityId);
            roleAuthorities.add(roleAuthority);
        }
        return roleAuthorities;
    }

    @Override
    public List<String> getBindResourceInfoByRoleId(String roleId) {
        List<String> resourceIdList = Lists.newArrayList();
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        List<RoleMenu> roleMenuList = roleMenuService.select(roleMenu);
        List<String> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        RoleAuthority roleAuthority = new RoleAuthority();
        roleAuthority.setRoleId(roleId);
        List<RoleAuthority> roleAuthorityList = roleAuthorityService.select(roleAuthority);
        List<String> authorityIdList = roleAuthorityList.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());

        resourceIdList.addAll(menuIdList);
        resourceIdList.addAll(authorityIdList);
        return resourceIdList;
    }

}
