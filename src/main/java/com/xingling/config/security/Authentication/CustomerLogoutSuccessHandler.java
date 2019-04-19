package com.xingling.config.security.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingling.common.WrapMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Title:      CustomerLogoutSuccessHandler. </p>
 * <p>Description 默认的退出成功处理器 </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    xinglinglove </p>
 *
 * @author         <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/2/7 11:17
 */
@Component("customerLogoutSuccessHandler")
@Slf4j
public class CustomerLogoutSuccessHandler implements LogoutSuccessHandler {


	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String BEARER_AUTHENTICATION = "Bearer ";

	private static final String HEADER_AUTHORIZATION = "authorization";


	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		log.info("退出成功");
		String token = request.getHeader(HEADER_AUTHORIZATION);


		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(WrapMapper.ok("退出成功")));
	}

}
