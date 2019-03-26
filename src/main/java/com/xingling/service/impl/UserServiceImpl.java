package com.xingling.service.impl;

import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.enums.UserStatusEnums;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.UserMapper;
import com.xingling.model.domain.User;
import com.xingling.model.domain.UserRole;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.RoleDto;
import com.xingling.service.UserRoleService;
import com.xingling.service.UserService;
import com.xingling.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>Title:	  UserServiceImpl <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2017/5/15 13:50
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserRoleService userRoleService;

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public User selectUserByUserName(String username) {
		return userMapper.loadUserByUsername(username);
	}

	@Override
	public AuthUserDto getAuthUserInfo(String loginName) {
		User user = userMapper.loadUserByUsername(loginName);
		AuthUserDto authUserDto = new AuthUserDto();
		BeanUtils.copyProperties(user,authUserDto);
		UserRole query = new UserRole();
		query.setUserId(user.getId());
		List<RoleDto> roleList = userRoleService.queryOwnRoles(query);
		authUserDto.setRoleList(roleList);
		return authUserDto;
	}

	@Override
	public List<User> queryListPage(User user) {
		return userMapper.queryListPage(user);
	}

	@Override
	public int deleteUserById(String id, AuthUserDto authUserDto) {
		User queryUser = new User();
		queryUser.setId(id);
		queryUser.setDel(Constants.DELETE_NO);
		User user = userMapper.selectOne(queryUser);
		if(Objects.isNull(user)) {
			throw new BusinessException("用户信息不存在");
		}
		queryUser.setDel(Constants.DELETE_YES);
		queryUser.setUpdater(authUserDto.getRealName());
		queryUser.setUpdaterId(authUserDto.getUserId());
		queryUser.setUpdateTime(new Date());
		return userMapper.updateByPrimaryKeySelective(queryUser);
	}

	@Override
	public int disableUserById(String id, AuthUserDto authUserDto) {
		User queryUser = new User();
		queryUser.setId(id);
		queryUser.setDel(Constants.DELETE_NO);
		User user = userMapper.selectOne(queryUser);
		if(Objects.isNull(user)) {
			throw new BusinessException("用户信息不存在");
		}
		if(UserStatusEnums.ENABLE.getKey() != user.getStatus()){
			throw new BusinessException("不是启用状态的不能禁用");
		}
		queryUser.setStatus(UserStatusEnums.DISABLE.getKey());
		queryUser.setUpdater(authUserDto.getRealName());
		queryUser.setUpdaterId(authUserDto.getUserId());
		queryUser.setUpdateTime(new Date());
		return userMapper.updateByPrimaryKeySelective(queryUser);
	}

	@Override
	public int enableUserById(String id, AuthUserDto authUserDto) {
		User queryUser = new User();
		queryUser.setId(id);
		queryUser.setDel(Constants.DELETE_NO);
		User user = userMapper.selectOne(queryUser);
		if(Objects.isNull(user)) {
			throw new BusinessException("用户信息不存在");
		}
		if(UserStatusEnums.DISABLE.getKey() != user.getStatus()){
			throw new BusinessException("不是禁用状态的不能启用");
		}
		queryUser.setStatus(UserStatusEnums.ENABLE.getKey());
		queryUser.setUpdater(authUserDto.getRealName());
		queryUser.setUpdaterId(authUserDto.getUserId());
		queryUser.setUpdateTime(new Date());
		return userMapper.updateByPrimaryKeySelective(queryUser);
	}

	@Override
	public int modifyUser(User user, AuthUserDto authUserDto) {
		if(StringUtils.isEmpty(user.getId())) {
			throw new BusinessException("用户编号不能为空！");
		}
		User queryUser = new User();
		queryUser.setId(user.getId());
		queryUser.setDel(Constants.DELETE_NO);
		User us = userMapper.selectOne(queryUser);
		if(Objects.isNull(us)) {
			throw new BusinessException("用户信息不存在");
		}
		user.setUpdater(authUserDto.getRealName());
		user.setUpdaterId(authUserDto.getUserId());
		user.setUpdateTime(new Date());
		int age = DateUtil.getAgeByBirth(user.getBirthday());
		user.setAge(age);
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int saveUserInfo(User user, AuthUserDto authUserDto) {
		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);
		user.setCreator(authUserDto.getRealName());
		user.setCreatorId(authUserDto.getUserId());
		user.setUpdater(authUserDto.getRealName());
		user.setUpdaterId(authUserDto.getUserId());
		int age = DateUtil.getAgeByBirth(user.getBirthday());
		user.setAge(age);
		return userMapper.insertSelective(user);
	}

	@Override
	public List<User> selectAllExcludeSupper() {
		return userMapper.selectAllExcludeSupper();
	}
}
