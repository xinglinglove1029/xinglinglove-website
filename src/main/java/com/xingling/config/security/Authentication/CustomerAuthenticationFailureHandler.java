package com.xingling.config.security.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingling.common.WrapMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Title:      CustomerAuthenticationFailureHandler. </p>
 * <p>Description 认证失败处理器 </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    xinglinglove </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/2/7 11:16
 */
@Component("customerAuthenticationFailureHandler")
public class CustomerAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Resource
	private ObjectMapper objectMapper;

	/**
	 * On authentication failure.
	 *
	 * @param request   the request
	 * @param response  the response
	 * @param exception the exception
	 *
	 * @throws IOException      the io exception
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
		
		logger.info("登录失败");

		// 记录失败次数 和原因 ip等信息 5次登录失败,锁定用户, 不允许在此登录
		
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(WrapMapper.error(exception.getMessage())));
		
	}

}
