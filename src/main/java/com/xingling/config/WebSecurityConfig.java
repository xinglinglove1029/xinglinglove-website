package com.xingling.config;

import com.xingling.config.security.Authentication.CustomerAuthenticationFailureHandler;
import com.xingling.config.security.Authentication.CustomerAuthenticationSuccessHandler;
import com.xingling.config.security.Authentication.CustomerLoginAuthEntryPoint;
import com.xingling.config.security.Authentication.CustomerLogoutSuccessHandler;
import com.xingling.config.security.authorize.AuthorizeConfigManager;
import com.xingling.config.security.authorize.CustomerAccessDecisionManager;
import com.xingling.config.security.authorize.CustomerMetadataSource;
import com.xingling.service.impl.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/3/15 18:55
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthorizeConfigManager authorizeConfigManager;

    @Resource
    private CustomerAuthenticationSuccessHandler customerAuthenticationSuccessHandler;

    @Resource
    private CustomerAuthenticationFailureHandler customerAuthenticationFailureHandler;

    @Resource
    private CustomerMetadataSource customerMetadataSource;

    @Resource
    private CustomerAccessDecisionManager customerAccessDecisionManager;

    @Resource
    private CustomerLoginAuthEntryPoint customerLoginAuthEntryPoint;

    @Resource
    private CustomerLogoutSuccessHandler customerLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/static/**", "/userLogin", "/druid/**","/favicon.ico", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/api/applications").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(customerLoginAuthEntryPoint)
                .and()
//                .addFilterAt(getCustomerSecurityInterceptor(), FilterSecurityInterceptor.class)
                .logout().logoutSuccessHandler(customerLogoutSuccessHandler)
                .and()
                .formLogin()
                .loginPage("/login") // 登录跳转 URL
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and().csrf().disable();
        http.headers().frameOptions().sameOrigin();
        authorizeConfigManager.config(http.authorizeRequests());
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getDaoAuthenticationProvider());
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //解决静态资源被拦截的问题
//        web.ignoring().antMatchers("/static/**", "/userLogin", "/druid/**","/favicon.ico", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/api/applications");
//    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
        auth.eraseCredentials(false);
    }


    @Bean
    public SecurityUserDetailsService userDetailsService() {
        return new SecurityUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    /**
     * 用户验证
     * @return
     */
    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    /**
     * 登陆
     * @return
     */
    @Bean
    public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(customerAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customerAuthenticationFailureHandler);

        return filter;
    }


    /**
     * 过滤器
     * @return
     */
//    @Bean
//    public CustomerSecurityInterceptor getCustomerSecurityInterceptor() {
//        CustomerSecurityInterceptor interceptor = new CustomerSecurityInterceptor();
//        interceptor.setAccessDecisionManager(customerAccessDecisionManager);
//        interceptor.setSecurityMetadataSource(customerMetadataSource);
//        try {
//            interceptor.setAuthenticationManager(this.authenticationManagerBean());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return interceptor;
//    }

}
