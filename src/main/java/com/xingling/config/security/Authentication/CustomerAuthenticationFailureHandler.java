package com.xingling.config.security.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingling.common.WrapMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
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
		//密码错误
		String msg = "";
		if ( exception instanceof BadCredentialsException) {
			msg = "密码错误";
		} else if ( exception instanceof LockedException) {
			//账户被锁
			msg = "用户被锁定";
		} else if ( exception instanceof DisabledException) {
			//账户未启用
			msg = "用户未启用";
		} else if ( exception instanceof CredentialsExpiredException) {
			//账户过期
			msg = "用户已过期";
		}else {
			//其他情况
			msg = "exception.getMessage()";
		}
		// 记录失败次数 和原因 ip等信息 5次登录失败,锁定用户, 不允许在此登录
		
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(WrapMapper.error(msg)));
		
	}

}
