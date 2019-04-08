package com.xingling.controller;


import com.xingling.base.BaseController;
import com.xingling.common.WrapMapper;
import com.xingling.common.Wrapper;
import com.xingling.model.domain.Dictionary;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.dto.CheckDicCodeDto;
import com.xingling.model.vo.DicTreeVo;
import com.xingling.service.DictionaryService;
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
 * Title: DictionaryController<br>;
 * Description: 数据字典controller<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 16:53
 */
@RestController
@RequestMapping(value = "/dictionary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "DictionaryController", tags = "DictionaryController", description = "数据字典controller", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DictionaryController extends BaseController {
    
    @Resource
    private DictionaryService dictionaryService;

    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dictionary/index");
        return modelAndView;
    }


    /**
     * <p>Title:     getDicTree. </p>
     * <p>Description 查询数据字典树</p>
     *
     * @return Dic tree
     * @Author <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since 2018 /4/27 11:54
     */
    @PostMapping(value = "/getDicTree")
    @ApiOperation(httpMethod = "POST", value = "查询数据字典树")
    public Wrapper<List<DicTreeVo>> getDicTree() {
        List<DicTreeVo> dicTreeVos = dictionaryService.getDicTree();
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, dicTreeVos);
    }

    /**
     * <p>Title:     getDicById. </p>
     * <p>Description 根据数据字典编码查询数据在字典信息</p>
     *
     * @param         dicId String
     * @return      Wrapper
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/21 16:57
     */
    @PostMapping(value = "/getDicById/{dicId}")
    @ApiOperation(httpMethod = "POST", value = "根据数据字典编码查询数据在字典信息")
    public Wrapper<Dictionary> getDicById(@PathVariable String dicId) {
        Dictionary dic = dictionaryService.getDicById(dicId);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, dic);
    }

    /**
     * <p>Title:      delete. </p>
     * <p>Description 根据id删除数据字典</p>
     *
     * @param dicId the id
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 14:14
     */
    @PostMapping(value = "/delete")
    @ApiOperation(httpMethod = "POST", value = "根据id删除数据字典")
    public Wrapper<Integer> delete(@RequestBody String dicId) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = dictionaryService.deleteDicById(dicId, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }


    /**
     * <p>Title:      modifyDic. </p>
     * <p>Description 修改数据字典信息</p>
     *
     * @param Dic the Dic
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/20 15:38
     */
    @PostMapping(value = "/modifyDic")
    @ApiOperation(httpMethod = "POST", value = "修改数据字典信息")
    public Wrapper<Integer> modifyDic(@ApiParam(name = "Dictionary", value = "数据字典信息") @RequestBody Dictionary Dic) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = dictionaryService.modifyDic(Dic, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      saveDicInfo. </p>
     * <p>Description 保存数据字典信息</p>
     *
     * @param Dic the Dic
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/22 17:24
     */
    @PostMapping(value = "/saveDicInfo")
    @ApiOperation(httpMethod = "POST", value = "保存数据字典信息")
    public Wrapper<Integer> saveDicInfo(@ApiParam(name = "Dictionary", value = "数据字典信息") @RequestBody Dictionary Dic) {
        AuthUserDto authUserDto = getLoginAuthDto();
        int result = dictionaryService.saveDicInfo(Dic, authUserDto);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * <p>Title:      checkDicCode. </p>
     * <p>Description 校验数据字典编码唯一性</p>
     *
     * @param checkDicCodeDto the check Dic code dto
     * @return wrapper wrapper
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @since 2018 /2/24 17:08
     */
    @PostMapping(value = "/checkDicCode")
    @ApiOperation(httpMethod = "POST", value = "校验数据字典编码唯一性")
    public Wrapper<Boolean> checkDicCode(@ApiParam(name = "checkDicCodeDto", value = "数据字典编码dto") @RequestBody CheckDicCodeDto checkDicCodeDto) {
        boolean flag = false;
        String dicId = checkDicCodeDto.getDicId();
        String dicCode = checkDicCodeDto.getDicCode();
        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", dicCode);
        if (StringUtils.isNotEmpty(dicId)) {
            criteria.andNotEqualTo("id", dicId);
        }
        int result = dictionaryService.selectCountByExample(example);
        if (result > 0) {
            flag = true;
        }
        return WrapMapper.ok(flag);
    }
}
