package com.xingling.service.impl;

import com.google.common.collect.Lists;
import com.xingling.base.BaseServiceImpl;
import com.xingling.mapper.UserRoleMapper;
import com.xingling.model.domain.Authority;
import com.xingling.model.domain.User;
import com.xingling.model.domain.UserRole;
import com.xingling.model.dto.RoleDto;
import com.xingling.service.AuthorityService;
import com.xingling.service.UserRoleService;
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

    @Override
    public Collection<GrantedAuthority> loadUserAuthorities(String userId) {
        List<Authority> ownAuthList = authorityService.getOwnAuthority(userId);
        List<GrantedAuthority> authList = Lists.newArrayList();
        for (Authority authority : ownAuthList) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthorityCode());
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
}
