package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Authority mapper.
 */
@Mapper
@Component
public interface AuthorityMapper extends MyMapper<Authority> {

    /**
     * <p>Title:     getOwnAuthority. </p>
     * <p>Description 根据userId查询权限信息</p>
     *
     * @param userId the user id
     * @return own authority
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:22
     */
    List<Authority> getOwnAuthority(String userId);

    /**
     * <p>Title:     queryListPage. </p>
     * <p>Description 分页查询权限信息</p>
     *
     * @param authority the authority
     * @return list list
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:21
     */
    List<Authority> queryListPage(Authority authority);

    /**
     * <p>Title:      getBindPermissionByRoleIds. </p>
     * <p>Description 根据角色Id查询权限信息</p>
     *
     * @param        roleIds List<String>
     * @return      List<Authority>
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/4/25 19:20
     */
    List<Authority> getBindPermissionByRoleIds(@Param("roleIds") List<String> roleIds);
}