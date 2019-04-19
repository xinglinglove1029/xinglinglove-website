package com.xingling.config;

import com.xingling.aspect.AccessLimitInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/3/15 19:27
 */

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Resource
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("add interceptors");
//        registry.addInterceptor(new LoginRequiredInterceptor()).excludePathPatterns(Arrays.asList("/views/**", "/res/**"));
        registry.addInterceptor(accessLimitInterceptor);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
