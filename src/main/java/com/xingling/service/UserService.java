package com.xingling.service;

import com.xingling.base.BaseService;
import com.xingling.model.domain.User;
import com.xingling.model.dto.AuthUserDto;

import java.util.List;

/**
 * <p>Title:	  UserService <br/> </p>
 * <p>Description 用户service <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2017 /5/15 13:49
 */
public interface UserService extends BaseService<User> {

	/**
	 * <p>Title:	  selectUserByUserName. </p>
	 * <p>Description 根据用户名查询用户</p>
	 *
	 * @param username the username
	 * @return User user
	 * @Author <a href="190332447@qq.com"/>杨文生</a>
	 * @CreateDate 2017 /6/10 14:16
	 */
	User selectUserByUserName(String username);

	/**
	 * <p>Title:      getAuthUserInfo. </p>
	 * <p>Description 获取认证后的用户信息</p>
	 *
	 * @param loginName the login name
	 * @return auth user info
	 * @Author <a href="190332447@qq.com"/>杨文生</a>
	 * @since 2018 /2/8 14:43
	 */
	AuthUserDto getAuthUserInfo(String loginName);

	/**
	 * <p>Title:      分页查询用户信息. </p>
	 * <p>Description </p>
	 *
	 * @param param the param
	 * @return page info
	 * @Author <a href="190332447@qq.com"/>杨文生</a>
	 * @since 2018 /2/9 12:09
	 */
	List<User> queryListPage(User param);

	/**
	 * <p>Title:      deleteUserById. </p>
	 * <p>Description 根据id删除用户信息</p>
	 *
	 * @param id          the id
	 * @param authUserDto the auth user dto
	 * @return int int
	 * @Author <a href="190332447@qq.com"/>杨文生</a>
	 * @since 2018 /2/20 14:15
	 */
	int deleteUserById(String id, AuthUserDto authUserDto);

	/**
	 * <p>Title:      disableUserById. </p>
	 * <p>Description 禁用用户</p>
	 *
	 * @param id          the id
	 * @param authUserDto the auth user dto
	 * @return int int
	 * @Author <a href="190332447@qq.com"/>杨文生</a>
	 * @since 2018 /2/20 14:32
	 */
	int disableUserById(String id, AuthUserDto authUserDto);

	/**
	 * <p>Title:      enableUserById. </p>
	 * <p>Description 启用用户</p>
	 *
	 * @param id          the id
	 * @param authUserDto the auth user dto
	 * @return int int
	 * @Author <a href="190332447@qq.com"/>杨文生</a>
	 * @since 2018 /2/20 14:32
	 */
	int enableUserById(String id, AuthUserDto authUserDto);

	/**
	 * <p>Title:      modifyUser. </p>
	 * <p>Description 修改用户信息</p>
	 *
	 * @param user        the user
	 * @param authUserDto the auth user dto
	 * @return int int
	 * @Author <a href="190332447@qq.com"/>杨文生</a>
	 * @since 2018 /2/20 15:38
	 */
	int modifyUser(User user, AuthUserDto authUserDto);

	/**
	 * <p>Title:      saveUserInfo. </p>
	 * <p>Description 保存用户信息</p>
	 *
	 * @param user        the user
	 * @param authUserDto the auth user dto
	 * @return int int
	 * @Author <a href="190332447@qq.com"/>杨文生</a>
	 * @since 2018 /2/22 17:25
	 */
	int saveUserInfo(User user, AuthUserDto authUserDto);

	/**
	 * <p>Title:     selectAllExcludeSupper. </p>
	 * <p>Description 查询全部用户排除超级管理员</p>
	 *
	 * @return list list
	 * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
	 * @since 2018 /4/28 15:01
	 */
	List<User> selectAllExcludeSupper(Byte excludeSupper);
}
