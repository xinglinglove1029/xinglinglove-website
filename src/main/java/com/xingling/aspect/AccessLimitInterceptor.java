package com.xingling.aspect;

import com.xingling.annotation.AccessLimit;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/4/19 12:04
 */
@Component
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断请求是否属于方法的请求
        if(handler instanceof HandlerMethod){

            HandlerMethod hm = (HandlerMethod) handler;

            //获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            String key = request.getRequestURI();


            //从redis中获取用户访问的次数
//            Integer count = Integer.parseInt(RedisUtil.get(key).toString());
//            if(count == null){
//                //第一次访问
//                RedisUtil.set(key,key,1);
//            }else if(count < maxCount){
//                //加1
//                RedisUtil.incr(key,1L);
//            }else{
//                //超出访问次数
////                render(response,CodeMsg.ACCESS_LIMIT_REACHED); //这里的CodeMsg是一个返回参数
//
//            }
            return false;
        }

        return true;

    }
//    private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
//        response.setContentType("application/json;charset=UTF-8");
//        OutputStream out = response.getOutputStream();
//        String str  = JSON.toJSONString(Result.error(cm));
//        out.write(str.getBytes("UTF-8"));
//        out.flush();
//        out.close();
//    }
}
