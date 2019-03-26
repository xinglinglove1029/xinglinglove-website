package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Role mapper.
 */
@Mapper
@Component
public interface RoleMapper extends MyMapper<Role> {

    /**
     * <p>Title:     queryListPage. </p>
     * <p>Description 分页查询角色列表信息</p>
     *
     * @param role the role
     * @return list
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:33
     */
    List<Role> queryListPage(Role role);
}