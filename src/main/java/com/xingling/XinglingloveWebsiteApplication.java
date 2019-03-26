package com.xingling;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.xingling.mapper")
@EnableTransactionManagement
public class XinglingloveWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(XinglingloveWebsiteApplication.class, args);
    }

}
