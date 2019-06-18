package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.Menu;
import com.xingling.model.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleMenuMapper extends MyMapper<RoleMenu> {

    int batchDeleteByRoleId(String roleId);

    List<Menu> getMenuByRoleIds(@Param("roleIds") List<String> roleIds);

    List<RoleMenu> selectRoleMenuByRoleId(String roleId);
}