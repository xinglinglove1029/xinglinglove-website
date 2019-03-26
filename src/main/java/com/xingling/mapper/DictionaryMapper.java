package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.Dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Title: DictionaryMapper<br>;
 * Description: 数据字典<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/16 19:48
 */
@Mapper
@Component
public interface DictionaryMapper extends MyMapper<Dictionary> {
}
