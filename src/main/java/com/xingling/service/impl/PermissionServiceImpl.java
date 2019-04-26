package com.xingling.service.impl;

import com.xingling.constants.Constants;
import com.xingling.service.PermissionService;
import com.xingling.util.SecurityUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;


@Slf4j
@Component("permissionService")
public class PermissionServiceImpl implements PermissionService {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();


	@Override
	public boolean hasPermission(Authentication authentication, HttpServletRequest request) {
		Set<String> currentAuthorityUrl = SecurityUserUtils.getCurrentAuthorityUrl();
		String requestURI = request.getRequestURI();
		// 超级管理员 全部都可以访问
		if (StringUtils.equals(SecurityUserUtils.getUser().getUserId(), Constants.SUPER_MANAGER)) {
			return true;
		}
		for (final String authority : currentAuthorityUrl) {
			if (antPathMatcher.match(authority, requestURI)) {
				return true;
			}
		}
		return true;
	}
}
