package com.xingling.controller;

import com.xingling.common.WrapMapper;
import com.xingling.common.Wrapper;
import com.xingling.model.domain.Role;
import com.xingling.model.vo.MenuTreeVo;
import com.xingling.service.MenuService;
import com.xingling.util.SecurityUserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private MenuService menuService;

//    @AccessLimit(seconds=5, maxCount=5)
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

    @GetMapping(value = "/error")
    public ModelAndView error() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error_403");
        return modelAndView;
    }

    @PostMapping(value = "/getMenuTreeByUserId")
    @ApiOperation(httpMethod = "POST", value = "查询当前登录人拥有的菜单树")
    public Wrapper<List<MenuTreeVo>> getMenuTreeByUserId() {
        Collection<Role> roleList = SecurityUserUtils.getUser().getRoles();
        List<String> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toList());
        List<MenuTreeVo> menuTreeVos = menuService.getMenuTreeByUserId(roleIds);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, menuTreeVos);
    }
}
