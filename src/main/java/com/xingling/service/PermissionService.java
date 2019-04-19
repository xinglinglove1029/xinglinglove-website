package com.xingling.service;


import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;


public interface PermissionService {

	/**
	 * Has permission boolean.
	 *
	 * @param authentication the authentication
	 * @param request        the request
	 *
	 * @return the boolean
	 */
	boolean hasPermission(Authentication authentication, HttpServletRequest request);
}
