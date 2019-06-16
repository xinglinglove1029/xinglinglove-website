package com.xingling.controller;

import com.xingling.base.BaseController;
import com.xingling.common.WrapMapper;
import com.xingling.common.Wrapper;
import com.xingling.model.domain.Menu;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.CheckMenuCodeDto;
import com.xingling.model.vo.MenuTreeVo;
import com.xingling.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title:      MenuController. </p>
 * <p>Description TODO </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @author <a href="190332447@qq.com"/>杨文生</a>
 * @since 2018 /4/27 9:57
 */
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "MenuController", tags = "MenuController", description = "菜单controller", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuController extends BaseController {

    @Resource
    private MenuService menuService;

    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/index");
        return modelAndView;
    }

    /**
     * <p>Title:     getMenuTree. </p>
     * <p>Description 查询菜单树</p>
     *
     * @return menu tree
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 11:54
     */
    @PostMapping(value = "/getMenuTree")
    @ApiOperation(httpMethod = "POST", value = "查询菜单树")
    public Wrapper<List<MenuTreeVo>> getMenuTree(@RequestBody String menuId) {
        List<MenuTreeVo> menuTreeVos = menuService.getMenuTree(menuId);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, menuTreeVos);
    }

    @PostMapping(value = "/getMenuById")
    @ApiOperation(httpMethod = "POST", value = "分页查询权限列表")
    public Wrapper<Menu> getMenuById(@RequestBody String menuId) {
        Menu menu = menuService.getMenuById(menuId);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, menu);
    }

    /**
     * <p>Title:      delete. </p>
     * <p>Description 根据id删除菜单</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/delete")
    @ApiOperation(httpMethod = "POST", value = "根据id删除菜单")
    public Wrapper<Integer> delete(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = menuService.deleteMenuById(id, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      enable. </p>
     * <p>Description 启用菜单</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/enable")
    @ApiOperation(httpMethod = "POST", value = "启用菜单")
    public Wrapper<Integer> enable(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = menuService.enableMenuById(id, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      disable. </p>
     * <p>Description 禁用菜单</p>
     *
     * @param id the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/disable")
    @ApiOperation(httpMethod = "POST", value = "禁用菜单")
    public Wrapper<Integer> disable(@RequestBody String id) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = menuService.disableMenuById(id, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      modifyMenu. </p>
     * <p>Description 修改菜单信息</p>
     *
     * @param menu the menu
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 15:38
     */
    @PostMapping(value = "/modifyMenu")
    @ApiOperation(httpMethod = "POST", value = "修改菜单信息")
    public Wrapper<Integer> modifyMenu(@ApiParam(name = "menu", value = "用户信息") @RequestBody Menu menu) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = menuService.modifyMenu(menu, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      saveMenuInfo. </p>
     * <p>Description 保存菜单信息</p>
     *
     * @param menu the menu
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/22 17:24
     */
    @PostMapping(value = "/saveMenuInfo")
    @ApiOperation(httpMethod = "POST", value = "保存菜单信息")
    public Wrapper<Integer> saveMenuInfo(@ApiParam(name = "menu", value = "菜单信息") @RequestBody Menu menu) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = menuService.saveMenuInfo(menu, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      checkMenuCode. </p>
     * <p>Description 校验菜单编码唯一性</p>
     *
     * @param checkMenuCodeDto the check menu code dto
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/24 17:08
     */
    @PostMapping(value = "/checkMenuCode")
    @ApiOperation(httpMethod = "POST", value = "校验菜单编码唯一性")
    public Wrapper<Boolean> checkMenuCode(@ApiParam(name = "checkMenuCodeDto", value = "菜单编码dto") @RequestBody CheckMenuCodeDto checkMenuCodeDto) {
        boolean flag = false;
        String menuId = checkMenuCodeDto.getMenuId();
        String menuCode = checkMenuCodeDto.getMenuCode();
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("menuCode", menuCode);
        if (StringUtils.isNotEmpty(menuId)) {
            criteria.andNotEqualTo("id", menuId);
        }
        int result = menuService.selectCountByExample(example);
        if (result > 0) {
            flag = true;
        }
        return WrapMapper.ok(flag);
    }

    /**
     * <p>Title:     select2MenuList. </p>
     * <p>Description 下来列表查询所有菜单</p>
     *
     * @return Wrapper
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/5/14 10:00
     */
    @PostMapping(value = "/select2MenuList")
    @ApiOperation(httpMethod = "POST", value = "下来列表查询所有菜单")
    public Wrapper<List<Menu>> select2MenuList() {
        List<Menu> menuList = menuService.selectAll();
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, menuList);
    }
}
