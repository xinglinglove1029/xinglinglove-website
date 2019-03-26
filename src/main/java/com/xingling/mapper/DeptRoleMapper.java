package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.DeptRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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
public interface DeptRoleMapper extends MyMapper<DeptRole> {
}
