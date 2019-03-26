package com.xingling.mapper;


import com.xingling.base.MyMapper;
import com.xingling.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Title:	  koala-umc <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2017 /5/15 13:50
 */
@Mapper
@Component
public interface UserMapper extends MyMapper<User> {

    /**
     * Load user by username user.
     *
     * @param userName the user name
     * @return the user
     */
    User loadUserByUsername(String userName);

    /**
     * <p>Title:      分页查询用户信息. </p>
     * <p>Description </p>
     *
     * @param user the user
     * @return page info
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/9 12:09
     */
    List<User> queryListPage(User user);

    /**
     * <p>Title:     selectAllExcludeSupper. </p>
     * <p>Description 查询全部用户排除超级管理员</p>
     *
     * @return list list
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/28 15:01
     */
    List<User> selectAllExcludeSupper();
}
