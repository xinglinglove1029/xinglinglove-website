package com.xingling.service.impl;

import com.google.common.collect.Lists;
import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.AuthorityMapper;
import com.xingling.mapper.MenuMapper;
import com.xingling.model.domain.Authority;
import com.xingling.model.domain.Menu;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.vo.AuthorityTreeVo;
import com.xingling.service.AuthorityService;
import com.xingling.util.TreeUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>Title:	  AuthorityServiceImpl <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/1/16 14:45
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl<Authority> implements AuthorityService {

    @Resource
    private AuthorityMapper authorityMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Authority> getOwnAuthority(String userId) {
        return authorityMapper.getOwnAuthority(userId);
    }

    @Override
    public int saveAuthorityInfo(Authority authority, AuthUserDto authUserDto) {
        authority.setCreator(authUserDto.getRealName());
        authority.setCreatorId(authUserDto.getUserId());
        authority.setUpdater(authUserDto.getRealName());
        authority.setUpdaterId(authUserDto.getUserId());
        return authorityMapper.insertSelective(authority);
    }

    @Override
    public int modifyAuthority(Authority authority, AuthUserDto authUserDto) {
        Authority queryAuthority = new Authority();
        queryAuthority.setId(authority.getId());
        queryAuthority.setDel(Constants.DELETE_NO);
        Authority auth = authorityMapper.selectOne(queryAuthority);
        if (Objects.isNull(auth)) {
            throw new BusinessException("权限信息不存在");
        }
        authority.setUpdater(authUserDto.getRealName());
        authority.setUpdaterId(authUserDto.getUserId());
        authority.setUpdateTime(new Date());
        return authorityMapper.updateByPrimaryKeySelective(authority);
    }

    @Override
    public int disableAuthorityById(String id, AuthUserDto authUserDto) {
        Authority queryAuthority = new Authority();
        queryAuthority.setId(id);
        queryAuthority.setDel(Constants.DELETE_NO);
        Authority authority = authorityMapper.selectOne(queryAuthority);
        if (Objects.isNull(authority)) {
            throw new BusinessException("权限信息不存在");
        }
        if (!Constants.ENABLE.equals(authority.getStatus())) {
            throw new BusinessException("不是启用状态的不能禁用");
        }
        queryAuthority.setStatus(Constants.DISABLE);
        queryAuthority.setUpdater(authUserDto.getRealName());
        queryAuthority.setUpdaterId(authUserDto.getUserId());
        queryAuthority.setUpdateTime(new Date());
        return authorityMapper.updateByPrimaryKeySelective(queryAuthority);
    }

    @Override
    public int enableAuthorityById(String id, AuthUserDto authUserDto) {
        Authority queryAuthority = new Authority();
        queryAuthority.setId(id);
        queryAuthority.setDel(Constants.DELETE_NO);
        Authority authority = authorityMapper.selectOne(queryAuthority);
        if (Objects.isNull(authority)) {
            throw new BusinessException("权限信息不存在");
        }
        if (!Constants.DISABLE.equals(authority.getStatus())) {
            throw new BusinessException("不是禁用状态的不能启用");
        }
        queryAuthority.setStatus(Constants.ENABLE);
        queryAuthority.setUpdater(authUserDto.getRealName());
        queryAuthority.setUpdaterId(authUserDto.getUserId());
        queryAuthority.setUpdateTime(new Date());
        return authorityMapper.updateByPrimaryKeySelective(queryAuthority);
    }

    @Override
    public int deleteAuthorityById(String id, AuthUserDto authUserDto) {
        Authority queryAuthority = new Authority();
        queryAuthority.setId(id);
        queryAuthority.setDel(Constants.DELETE_NO);
        Authority authority = authorityMapper.selectOne(queryAuthority);
        if (Objects.isNull(authority)) {
            throw new BusinessException("权限信息不存在");
        }
        queryAuthority.setDel(Constants.DELETE_YES);
        queryAuthority.setUpdater(authUserDto.getRealName());
        queryAuthority.setUpdaterId(authUserDto.getUserId());
        queryAuthority.setUpdateTime(new Date());
        return authorityMapper.updateByPrimaryKeySelective(queryAuthority);
    }

    @Override
    public List<Authority> queryListPage(Authority authority) {
        return authorityMapper.queryListPage(authority);
    }

    @Override
    public List<AuthorityTreeVo> getAllAuthorityInfoList() {
        // 查询所有菜单信息
        List<Menu> menuList = menuMapper.selectAllMenu();
//        menuList = menuList.stream().filter(f -> !Constants.ROOT_PARENTID.equals(f.getPid())).collect(Collectors.toList());

        // 查询所有权限信息
        List<Authority> authorityList = authorityMapper.selectAll();

        // 合并菜单和权限数据
        List<AuthorityTreeVo> authorityTreeVos = this.buildResourceData(menuList, authorityList);
        return TreeUtil.bulid(authorityTreeVos, Constants.ROOT_PARENTID);
    }


    private List<AuthorityTreeVo> buildResourceData(List<Menu> menuList, List<Authority> authorityList) {
        List<AuthorityTreeVo> authorityTreeVos = Lists.newArrayList();
        AuthorityTreeVo authorityTreeVo;
        for (Menu menu : menuList) {
            authorityTreeVo = new AuthorityTreeVo();
            authorityTreeVo.setId(menu.getId());
            authorityTreeVo.setParentId(menu.getPid());
            authorityTreeVo.setResourceName(menu.getMenuName());
            authorityTreeVo.setType("1");
            authorityTreeVos.add(authorityTreeVo);
        }
        if (authorityList != null) {
            for (Authority authority : authorityList) {
                authorityTreeVo = new AuthorityTreeVo();
                authorityTreeVo.setId(authority.getId());
                authorityTreeVo.setParentId(authority.getMenuId());
                authorityTreeVo.setResourceName(authority.getAuthorityName());
                authorityTreeVo.setType("2");
                authorityTreeVos.add(authorityTreeVo);
            }
        }
        return authorityTreeVos;
    }

    @Override
    public List<Authority> getBindPermissionByRoleIds(List<String> roleIds) {
        return authorityMapper.getBindPermissionByRoleIds(roleIds);
    }


    @Override
    public Collection<GrantedAuthority> loadUserAuthorities(List<String> roleIds) {
        List<Authority> ownAuthList = authorityMapper.getBindPermissionByRoleIds(roleIds);
        List<GrantedAuthority> authList = Lists.newArrayList();
        for (Authority authority : ownAuthList) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getUrl());
            authList.add(grantedAuthority);
        }
        return authList;
    }
}
