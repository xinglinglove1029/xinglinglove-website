package com.xingling.service.impl;

import com.xingling.service.PermissionService;
import com.xingling.util.SecurityUserUtils;
import lombok.extern.slf4j.Slf4j;
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
		String currentLoginName = SecurityUserUtils.getUser().getUsername();
		Set<String> currentAuthorityUrl = SecurityUserUtils.getCurrentAuthorityUrl();
		String requestURI = request.getRequestURI();
		return true;
	}
}
