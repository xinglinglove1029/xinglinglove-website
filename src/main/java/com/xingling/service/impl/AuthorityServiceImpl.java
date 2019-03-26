package com.xingling.service.impl;

import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.AuthorityMapper;
import com.xingling.model.domain.Authority;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.service.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        queryAuthority.setCreator(authUserDto.getRealName());
        queryAuthority.setCreatorId(authUserDto.getUserId());
        queryAuthority.setUpdater(authUserDto.getRealName());
        queryAuthority.setUpdaterId(authUserDto.getUserId());
        queryAuthority.setUpdateTime(new Date());
        return authorityMapper.updateByPrimaryKeySelective(queryAuthority);
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
}
