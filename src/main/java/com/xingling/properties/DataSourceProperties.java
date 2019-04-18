package com.xingling.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title:      DataSourceProperties. </p>
 * <p>Description mydatasource </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    xinglinglove </p>
 *
 * @author         <a href="190332447@qq.com"/>杨文生</a>
 * @since      2018/1/29 11:17
 */
@Configuration
@ConfigurationProperties(prefix = DataSourceProperties.DATA_SOURCE)
@Data
public class DataSourceProperties {

    public final static String DATA_SOURCE = "datasource";

    private Master master;

    private Slaver slaver;

    private String read;

    private String write;

    @Data
    public static class Master {
        private String jdbcUrl;

        private String username;

        private String password;

        private String driverClassName;
    }

    @Data
    public static class Slaver {
        private String jdbcUrl;

        private String username;

        private String password;

        private String driverClassName;
    }

}
