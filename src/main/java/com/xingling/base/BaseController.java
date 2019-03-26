package com.xingling.base;

import com.xingling.model.dto.AuthUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	private static final long serialVersionUID = 6357869213649815390L;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 获取当前请求对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	protected AuthUserDto getLoginAuthDto() {
        AuthUserDto authUserDto = new AuthUserDto();
//        Object principal = ThreadLocalMap.get(Constants.TOKEN_AUTH_INFO);
//		String userId = Reflections.getFieldValue(principal, "userId").toString();
//		String userName = Reflections.getFieldValue(principal, "userName").toString();
//		String realName = Reflections.getFieldValue(principal, "realName").toString();
//		String email = Reflections.getFieldValue(principal,"email").toString();
//		String cellPhone = Reflections.getFieldValue(principal,"cellPhone").toString();
//		String sex = Reflections.getFieldValue(principal,"sex").toString();
//		authUserDto.setUserName(userName.toString());
//		authUserDto.setRealName(realName.toString());
//		authUserDto.setEmail(email.toString());
//		authUserDto.setSex(sex.toString());
//		authUserDto.setCellPhone(cellPhone.toString());
//		authUserDto.setUserId(userId.toString());
		return authUserDto;
	}
}
