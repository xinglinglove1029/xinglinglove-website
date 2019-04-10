package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.RoleAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RoleAuthorityMapper extends MyMapper<RoleAuthority> {

    int batchDeleteByRoleId(String roleId);
}