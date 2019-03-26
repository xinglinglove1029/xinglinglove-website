package com.xingling.service.impl;

import com.xingling.base.BaseEntiy;
import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.RoleMapper;
import com.xingling.model.domain.Role;
import com.xingling.model.domain.User;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.RoleBindUserDto;
import com.xingling.service.RoleService;
import com.xingling.service.UserRoleService;
import com.xingling.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>Title:	  spring-cloud-koala <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/1/16 14:45
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserService userService;

    @Override
    public List<Role> queryListPage(Role role) {
        return roleMapper.queryListPage(role);
    }

    @Override
    public int deleteRoleById(String id, AuthUserDto authUserDto) {
        Role queryRole = new Role();
        queryRole.setId(id);
        queryRole.setDel(Constants.DELETE_NO);
        Role role = roleMapper.selectOne(queryRole);
        if (Objects.isNull(role)) {
            throw new BusinessException("角色信息不存在");
        }
        queryRole.setDel(Constants.DELETE_YES);
        queryRole.setUpdater(authUserDto.getRealName());
        queryRole.setUpdaterId(authUserDto.getUserId());
        queryRole.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(queryRole);
    }

    @Override
    public int enableRoleById(String id, AuthUserDto authUserDto) {
        Role queryRole = new Role();
        queryRole.setId(id);
        queryRole.setDel(Constants.DELETE_NO);
        Role role = roleMapper.selectOne(queryRole);
        if (Objects.isNull(role)) {
            throw new BusinessException("角色信息不存在");
        }
        if (!Constants.DISABLE.equals(role.getStatus())) {
            throw new BusinessException("不是禁用状态的不能启用");
        }
        queryRole.setStatus(Constants.ENABLE);
        queryRole.setUpdater(authUserDto.getRealName());
        queryRole.setUpdaterId(authUserDto.getUserId());
        queryRole.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(queryRole);
    }

    @Override
    public int disableRoleById(String id, AuthUserDto authUserDto) {
        Role queryRole = new Role();
        queryRole.setId(id);
        queryRole.setDel(Constants.DELETE_NO);
        Role role = roleMapper.selectOne(queryRole);
        if (Objects.isNull(role)) {
            throw new BusinessException("角色信息不存在");
        }
        if (!Constants.ENABLE.equals(role.getStatus())) {
            throw new BusinessException("不是启用状态的不能禁用");
        }
        queryRole.setStatus(Constants.DISABLE);
        queryRole.setUpdater(authUserDto.getRealName());
        queryRole.setUpdaterId(authUserDto.getUserId());
        queryRole.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(queryRole);
    }

    @Override
    public int modifyRole(Role role, AuthUserDto authUserDto) {
        Role queryRole = new Role();
        queryRole.setId(role.getId());
        queryRole.setDel(Constants.DELETE_NO);
        Role rl = roleMapper.selectOne(queryRole);
        if (Objects.isNull(rl)) {
            throw new BusinessException("角色信息不存在");
        }
        role.setCreator(authUserDto.getRealName());
        role.setCreatorId(authUserDto.getUserId());
        role.setUpdater(authUserDto.getRealName());
        role.setUpdaterId(authUserDto.getUserId());
        role.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int saveRoleInfo(Role role, AuthUserDto authUserDto) {
        role.setCreator(authUserDto.getRealName());
        role.setCreatorId(authUserDto.getUserId());
        role.setUpdater(authUserDto.getRealName());
        role.setUpdaterId(authUserDto.getUserId());
        return roleMapper.insertSelective(role);
    }

    @Override
    public RoleBindUserDto getBindUserByRoleId(String roleId, String currentUserId) {
        // 查询全部用户集合
        List<User> allUserList = userService.selectAllExcludeSupper();

        // 查询已绑定的用户集合
        List<User> alreadyBindUserList = userRoleService.getBindUserByRoleId(roleId);

        // 查询未绑定的用户集合
        List<User> notBindUserList = allUserList.stream().filter(item -> !alreadyBindUserList.contains(item)).collect(Collectors.toList());

        List<String> alreadyBindUserIds = alreadyBindUserList.stream().map(BaseEntiy::getId).collect(Collectors.toList());

        RoleBindUserDto bindUserDto = new RoleBindUserDto();
        bindUserDto.setAlreadyBindUserList(alreadyBindUserList);
        bindUserDto.setNotBindUserList(notBindUserList);
        bindUserDto.setAlreadyBindUserIds(alreadyBindUserIds);
        return bindUserDto;
    }
}
