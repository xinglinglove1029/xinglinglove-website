package com.xingling.service.impl;

import com.google.common.collect.Lists;
import com.xingling.base.BaseServiceImpl;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.UserRoleMapper;
import com.xingling.model.domain.Authority;
import com.xingling.model.domain.Role;
import com.xingling.model.domain.User;
import com.xingling.model.domain.UserRole;
import com.xingling.model.dto.RoleDto;
import com.xingling.model.dto.UserBindRoleDto;
import com.xingling.service.AuthorityService;
import com.xingling.service.UserRoleService;
import com.xingling.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * <p>Title:	  UserRoleServiceImpl <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/1/16 14:45
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {

    @Resource
    private AuthorityService authorityService;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserService userService;

    @Override
    public Collection<GrantedAuthority> loadUserAuthorities(String userId) {
        List<Authority> ownAuthList = authorityService.getOwnAuthority(userId);
        List<GrantedAuthority> authList = Lists.newArrayList();
        for (Authority authority : ownAuthList) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getUrl());
            authList.add(grantedAuthority);
        }
        return authList;
    }

    @Override
    public List<RoleDto> queryOwnRoles(UserRole query) {
        return userRoleMapper.queryOwnRoles(query);
    }

    @Override
    public List<User> getBindUserByRoleId(String roleId) {
        return userRoleMapper.getBindUserByRoleId(roleId);
    }

    @Override
    public List<Role> getBindRoleListByUserId(String userId) {
        return userRoleMapper.getBindRoleListByUserId(userId);
    }

    @Override
    public void bindRole(UserBindRoleDto userBindRoleDto) {
        // 校验用户信息
        User user = userService.selectByKey(userBindRoleDto.getUserId());
        if(user == null){
            throw  new BusinessException("查询用户信息不存在");
        }
        if(userBindRoleDto.getRoleIdList().size() == 0){
            throw  new BusinessException("绑定的角色不能为空");
        }
        // 删除原来的关系
        userRoleMapper.deleteByUserId(userBindRoleDto.getUserId());
        List<UserRole> userRoles = this.bulidUserRoleInfo(userBindRoleDto);
        // 批量保存
        userRoleMapper.insertList(userRoles);
    }

    @Override
    public void batchDeleteByRoleId(String roleId) {
        userRoleMapper.batchDeleteByRoleId(roleId);
    }

    private List<UserRole> bulidUserRoleInfo(UserBindRoleDto userBindRoleDto){
        List<UserRole>  list = Lists.newArrayList();
        UserRole userRole = null;
        List<String> roleIdList = userBindRoleDto.getRoleIdList();
        for (String roleId : roleIdList) {
            userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userBindRoleDto.getUserId());
            list.add(userRole);
        }
        return list;
    }
}
