package com.xingling.service;

import com.xingling.base.BaseService;
import com.xingling.model.domain.UserDept;

import java.util.List;

/**
 * Title: DeptRoleService<br>;
 * Description: 部门角色<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 18:29
 */
public interface UserDeptService extends BaseService<UserDept> {
    List<UserDept> queryDeptByUserIds(List<String> userIds);
}
