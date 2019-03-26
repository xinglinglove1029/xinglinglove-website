package com.xingling.mapper;


import com.xingling.base.MyMapper;
import com.xingling.model.domain.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Title: DepartmentMapper<br>;
 * Description: 部门<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/16 19:47
 */
@Mapper
@Component
public interface DepartmentMapper extends MyMapper<Department> {
}
