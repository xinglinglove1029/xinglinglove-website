package com.xingling.service.impl;

import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.enums.UserStatusEnums;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.UserMapper;
import com.xingling.model.domain.Role;
import com.xingling.model.domain.User;
import com.xingling.model.domain.UserDept;
import com.xingling.model.domain.UserRole;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.RoleDto;
import com.xingling.service.UserDeptService;
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

	@Resource
	private UserDeptService userDeptService;

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public User selectUserByUserName(String username) {
		User user = userMapper.loadUserByUsername(username);
		if(Objects.isNull(user)){
			throw new BusinessException("根据用户名查询用户信息不存在");
		}
		// 查询所属的部门
		UserDept userDept = new UserDept();
		userDept.setUserId(user.getId());
		UserDept dept = userDeptService.selectOne(userDept);
		if(Objects.isNull(userDept)){
			throw new BusinessException("查询用户所属部门不存在");
		}
		user.setDeptId(dept.getDeptId());
		user.setDeptName(dept.getDeptName());
		return user;
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
		List<User> userList = userMapper.queryListPage(user);
		List<String> userIds = userList.stream().map(User::getId).collect(Collectors.toList());
		List<UserDept> userDeptList = userDeptService.queryDeptByUserIds(userIds);
		Map<String, UserDept> depatMap = userDeptList.stream().collect(Collectors.toMap(UserDept :: getUserId, f -> f));
		userList.forEach(f ->{
			UserDept userDept = depatMap.get(f.getId());
			f.setDeptId(userDept.getDeptId());
			f.setDeptName(userDept.getDeptName());
		});
		return userList;
	}

	@Override
	public int deleteUserById(String id, AuthUserDto authUserDto) {
		String currentUserId = authUserDto.getUserId();
		if(currentUserId.equals(id)){
			throw new BusinessException("自己不能删除自己");
		}
		User queryUser = new User();
		queryUser.setId(id);
		queryUser.setDel(Constants.DELETE_NO);
		User user = userMapper.selectOne(queryUser);
		if(Objects.isNull(user)) {
			throw new BusinessException("用户信息不存在");
		}
		// 判断用户是否和角色还存在绑定关系
		List<Role> bindRoleListByUserId = userRoleService.getBindRoleListByUserId(id);
		if(bindRoleListByUserId.size() > 0){
			throw new BusinessException("该用户还与角色存在绑定关系，不能删除");
		}
		queryUser.setDel(Constants.DELETE_YES);
		queryUser.setUpdater(authUserDto.getRealName());
		queryUser.setUpdaterId(authUserDto.getUserId());
		queryUser.setUpdateTime(new Date());
		return userMapper.updateByPrimaryKeySelective(queryUser);
	}

	@Override
	public int disableUserById(String id, AuthUserDto authUserDto) {
		String currentUserId = authUserDto.getUserId();
		if(currentUserId.equals(id)){
			throw new BusinessException("自己不能禁用自己");
		}
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
		userMapper.updateByPrimaryKeySelective(user);

		// 删除用户部门绑定的关系
		UserDept deleteUserDept = new UserDept();
		deleteUserDept.setUserId(user.getId());
		userDeptService.delete(deleteUserDept);

		// 保存用户部门关系
		UserDept userDept = new UserDept();
		userDept.setDeptId(user.getDeptId());
		userDept.setUserId(user.getId());
		return userDeptService.save(userDept);
	}

	@Override
	public int saveUserInfo(User user, AuthUserDto authUserDto) {
		if(StringUtils.isEmpty(user.getDeptId())){
			throw new BusinessException("选择的部门不能为空");
		}
		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);
		user.setCreator(authUserDto.getRealName());
		user.setCreatorId(authUserDto.getUserId());
		user.setUpdater(authUserDto.getRealName());
		user.setUpdaterId(authUserDto.getUserId());
		int age = DateUtil.getAgeByBirth(user.getBirthday());
		user.setAge(age);
		userMapper.insertSelective(user);
		// 保存用户部门关系
		UserDept userDept = new UserDept();
		userDept.setDeptId(user.getDeptId());
		userDept.setUserId(user.getId());
		return userDeptService.save(userDept);
	}

	@Override
	public List<User> selectAllExcludeSupper(Byte excludeSupper) {
		return userMapper.selectAllExcludeSupper(excludeSupper);
	}
}
