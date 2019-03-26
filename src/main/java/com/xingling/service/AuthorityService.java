package com.xingling.service;

import com.xingling.base.BaseService;
import com.xingling.model.domain.Authority;
import com.xingling.model.dto.AuthUserDto;

import java.util.List;

/**
 * <p>Title:      AuthorityService. </p>
 * <p>Description TODO </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since 2018 /1/16 14:50
 */
public interface AuthorityService extends BaseService<Authority> {

    /**
     * <p>Title:      getOwnAuthority. </p>
     * <p>Description 根据userId查询拥有的权限</p>
     *
     * @param userId the user id
     * @return own authority
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /1/16 15:24
     */
    List<Authority> getOwnAuthority(String userId);

    /**
     * <p>Title:     saveAuthorityInfo. </p>
     * <p>Description 保存权限信息</p>
     *
     * @param authority   the authority
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:17
     */
    int saveAuthorityInfo(Authority authority, AuthUserDto authUserDto);

    /**
     * <p>Title:     modifyAuthority. </p>
     * <p>Description 修改权限信息</p>
     *
     * @param authority   the authority
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:17
     */
    int modifyAuthority(Authority authority, AuthUserDto authUserDto);

    /**
     * <p>Title:     disableAuthorityById. </p>
     * <p>Description 禁用权限信息</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:17
     */
    int disableAuthorityById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     enableAuthorityById. </p>
     * <p>Description 启用权限信息</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:17
     */
    int enableAuthorityById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     deleteAuthorityById. </p>
     * <p>Description 根据id删除权限信息</p>
     *
     * @param id          the id
     * @param authUserDto the auth user dto
     * @return int
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:17
     */
    int deleteAuthorityById(String id, AuthUserDto authUserDto);

    /**
     * <p>Title:     queryListPage. </p>
     * <p>Description 分页查询权限信息</p>
     *
     * @param authority the authority
     * @return list
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 10:17
     */
    List<Authority> queryListPage(Authority authority);
}