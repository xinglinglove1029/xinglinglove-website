package com.xingling.service.impl;

import com.xingling.base.BaseServiceImpl;
import com.xingling.mapper.RoleMenuMapper;
import com.xingling.model.domain.RoleMenu;
import com.xingling.service.RoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>Title:	  spring-cloud-koala <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/3/23 16:57
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu> implements RoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public int batchDeleteByRoleId(String roleId) {
        return roleMenuMapper.batchDeleteByRoleId(roleId);
    }
}
