package com.xingling.service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/6/13 11:12
 */
public interface TokenService {

    String createToken();

    void checkToken(HttpServletRequest request);
}
