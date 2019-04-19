package com.xingling.config.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * <p>Title:      AuthorizeConfigManager. </p>
 * <p>Description 授权信息管理器,用于收集系统中所有 AuthorizeConfigProvider 并加载其配置</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    xinglinglove </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since 2018 /2/7 15:54
 */
public interface AuthorizeConfigManager {

	/**
	 * Config.
	 *
	 * @param config the config
	 */
	void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
