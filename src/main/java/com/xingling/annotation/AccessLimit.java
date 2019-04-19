package com.xingling.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/4/19 12:03
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

    int seconds();

    int maxCount();
}
