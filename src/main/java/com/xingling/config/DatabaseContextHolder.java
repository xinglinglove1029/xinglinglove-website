package com.xingling.config;

import com.xingling.enums.DatabaseType;

/**
 * <p>Title:      DatabaseContextHolder. </p>
 * <p>Description 保存一个线程安全的DatabaseType容器,用于保存数据源类型</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    xinglinglove </p>
 *
 * @author         <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/1/29 12:03
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type) {
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType() {
        return contextHolder.get();
    }
}
