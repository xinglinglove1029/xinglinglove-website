package com.xingling.service.impl;

import com.xingling.model.domain.Role;
import com.xingling.model.domain.SecurityUser;
import com.xingling.model.domain.User;
import com.xingling.service.UserRoleService;
import com.xingling.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * <p>Title:	  SecurityUserDetailsService <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/1/16 14:45
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService{

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public SecurityUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.selectUserByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        Collection<GrantedAuthority> grantedAuthorities = userRoleService.loadUserAuthorities(user.getId());
        Collection<Role> roles = userRoleService.getBindRoleListByUserId(user.getId());
        return new SecurityUser(user, grantedAuthorities,roles);
    }
}
