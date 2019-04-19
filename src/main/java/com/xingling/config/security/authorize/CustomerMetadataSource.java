package com.xingling.config.security.authorize;


import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 获取请求url需要的权限
 */
@Component
public class CustomerMetadataSource implements FilterInvocationSecurityMetadataSource {


    private PathMatcher matcher = new AntPathMatcher();


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取当前访问url
        String url = ((FilterInvocation) object).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }

        List<ConfigAttribute> result = new ArrayList<>();

        try {
            //如果不是拦截列表里的
            if (!isIntercept(url)) {
                ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
                result.add(attribute);
                return result;
            }
//
//            //查询匹配的url
//            List<BaseMenu> menuList = baseMenuService.selectMenusByUrl(url);
//            if (menuList != null && menuList.size() > 0) {
//                for (BaseMenu menu : menuList) {
//                    //查询拥有该菜单权限的角色列表
//                    List<BaseRole> roles = baseRoleService.selectRolesByMenuId(menu.getId());
//                    if (roles != null && roles.size() > 0) {
//                        for (BaseRole role : roles) {
//                            ConfigAttribute conf = new SecurityConfig(role.getRoleCode());
//                            result.add(conf);
//                        }
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


    private boolean isIntercept(String url) {
        String[] filterPaths = {"/login","/home","/userLogin","/static/**","/index"};
        for (String filter : filterPaths) {
            if (matcher.match(StringUtils.replace(filter," ", ""), url)) {
                return true;
            }
        }
        return false;
    }
}
