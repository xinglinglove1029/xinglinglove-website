package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RoleMenuMapper extends MyMapper<RoleMenu> {

    int batchDeleteByRoleId(String roleId);
}