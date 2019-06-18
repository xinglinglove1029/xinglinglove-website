package com.xingling.service.impl;

import com.xingling.base.BaseServiceImpl;
import com.xingling.mapper.RoleAuthorityMapper;
import com.xingling.model.domain.Authority;
import com.xingling.model.domain.RoleAuthority;
import com.xingling.service.RoleAuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title:	  spring-cloud-koala <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/1/16 14:45
 */
@Service
public class RoleAuthorityServiceImpl extends BaseServiceImpl<RoleAuthority> implements RoleAuthorityService {

    @Resource
    private RoleAuthorityMapper roleAuthorityMapper;

    @Override
    public int batchDeleteByRoleId(String roleId) {
        return roleAuthorityMapper.batchDeleteByRoleId(roleId);
    }

    @Override
    public List<Authority> getAuthorityByRoleIds(List<String> roleIds) {
        return roleAuthorityMapper.getAuthorityByRoleIds(roleIds);
    }

    @Override
    public List<RoleAuthority> selectRoleAuthorityByRoleId(String roleId) {
        return roleAuthorityMapper.selectRoleAuthorityByRoleId(roleId);
    }
}
