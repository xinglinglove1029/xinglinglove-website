package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.UserDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Title: DeptRoleMapper<br>;
 * Description: <br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 18:28
 */
@Mapper
@Component
public interface UserDeptMapper extends MyMapper<UserDept> {
    List<UserDept> queryDeptByUserIds(@Param("userIds") List<String> userIds);
}
