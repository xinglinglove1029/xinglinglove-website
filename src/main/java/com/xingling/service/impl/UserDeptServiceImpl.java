package com.xingling.service.impl;


import com.xingling.base.BaseServiceImpl;
import com.xingling.mapper.UserDeptMapper;
import com.xingling.model.domain.UserDept;
import com.xingling.service.UserDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title: DeptRoleServiceImpl<br>;
 * Description: 部门角色<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 18:29
 */
@Service
public class UserDeptServiceImpl extends BaseServiceImpl<UserDept> implements UserDeptService {

    @Resource
    private UserDeptMapper userDeptMapper;

    @Override
    public List<UserDept> queryDeptByUserIds(List<String> userIds) {
        return userDeptMapper.queryDeptByUserIds(userIds);
    }
}
