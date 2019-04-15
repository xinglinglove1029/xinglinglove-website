package com.xingling.controller;

import com.xingling.model.domain.Role;
import com.xingling.util.SecurityUserUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/3/19 15:04
 */
@RestController
public class IndexController {

    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/main");
        Collection<Role> roles = SecurityUserUtils.getUser().getRoles();
        String roleNameList = roles.stream().map(Role::getRoleName).reduce((x, y) -> x + "," + y).get();
        modelAndView.addObject("roleNameList",roleNameList);
        return modelAndView;
    }

    @GetMapping(value = "/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/home");
        return modelAndView;
    }
}
