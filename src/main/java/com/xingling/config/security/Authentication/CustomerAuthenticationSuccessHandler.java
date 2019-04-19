package com.xingling.config.security.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingling.common.WrapMapper;
import com.xingling.model.domain.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * <p>Title:      CustomerAuthenticationSuccessHandler. </p>
 * <p>Description 认证成功处理器. </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    xinglinglove </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/2/7 11:16
 */
@Component("customerAuthenticationSuccessHandler")
@Slf4j
public class CustomerAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Resource
	private ObjectMapper objectMapper;

	private static final String BEARER_TOKEN_TYPE = "Basic ";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

		logger.info("登录成功");

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);


		SecurityUser principal = (SecurityUser)authentication.getPrincipal();

		log.info("用户【 {} 】记录登录日志", principal.getUsername());

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write((objectMapper.writeValueAsString(WrapMapper.ok())));

	}


}
