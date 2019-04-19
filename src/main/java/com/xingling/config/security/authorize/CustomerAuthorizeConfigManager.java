package com.xingling.config.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Title:      CustomerAuthorizeConfigManager. </p>
 * <p>Description 默认的授权配置管理器</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    xinglinglove </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since 2018 /2/7 15:54
 */
@Component
public class CustomerAuthorizeConfigManager implements AuthorizeConfigManager {

	private final List<AuthorizeConfigProvider> authorizeConfigProviders;


	@Autowired
	public CustomerAuthorizeConfigManager(List<AuthorizeConfigProvider> authorizeConfigProviders) {
		this.authorizeConfigProviders = authorizeConfigProviders;
	}


	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		boolean existAnyRequestConfig = false;
		String existAnyRequestConfigName = null;

		for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
			boolean currentIsAnyRequestConfig = authorizeConfigProvider.config(config);
			if (existAnyRequestConfig && currentIsAnyRequestConfig) {
				throw new RuntimeException("重复的anyRequest配置:" + existAnyRequestConfigName + ","
						+ authorizeConfigProvider.getClass().getSimpleName());
			} else if (currentIsAnyRequestConfig) {
				existAnyRequestConfig = true;
				existAnyRequestConfigName = authorizeConfigProvider.getClass().getSimpleName();
			}
		}

		if (!existAnyRequestConfig) {
			config.anyRequest().authenticated();
		}
	}

}
