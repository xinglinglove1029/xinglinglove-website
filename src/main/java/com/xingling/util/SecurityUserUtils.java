package com.xingling.util;

import com.google.common.collect.Sets;
import com.xingling.model.domain.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;


public class SecurityUserUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public static Collection<? extends GrantedAuthority> getAllPermission() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }

    public static boolean hasPermission(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = false;
        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            if (authority.equals(permission)) {
                hasPermission = true;
            }
        }
        return hasPermission;
    }


    public static SecurityUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) authentication.getPrincipal();
    }


    public static void logout() {
        SecurityContextHolder.clearContext();
    }


    public static Set<String> getCurrentAuthorityUrl() {
        Set<String> path = Sets.newHashSet();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority authority : authorities) {
            String url = authority.getAuthority();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(url)) {
                path.add(url);
            }
        }
        return path;
    }
}
