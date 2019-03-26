package com.xingling.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <p>Title:	  MyMapper. </p>
 * <p>Description 继承自己的MyMapper </p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @Author         <a href="190332447@qq.com"/>杨文生</a>
 * @CreateDate     2017/6/2 13:22
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
