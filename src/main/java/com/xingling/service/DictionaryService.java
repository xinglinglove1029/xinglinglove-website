package com.xingling.service;


import com.xingling.base.BaseService;
import com.xingling.model.domain.Dictionary;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.vo.DicTreeVo;

import java.util.List;

/**
 * Title: DictionaryService<br>;
 * Description: 数据字典<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 16:50
 */
public interface DictionaryService extends BaseService<Dictionary> {

    /**
     * <p>Title:     getDicTree. </p>
     * <p>Description 初始化数据字典树</p>
     *
     * @return       List<DicTreeVo>
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/21 17:07
     */
    List<DicTreeVo> getDicTree();

    /**
     * <p>Title:     getDicById. </p>
     * <p>Description 根据id查询数据字典信息</p>
     *
     * @param   dicId Long
     * @return  Dictionary
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/21 17:07
     */
    Dictionary getDicById(String dicId);

    /**
     * <p>Title:     deleteDicById. </p>
     * <p>Description 根据id删除数据字典信息</p>
     *
     * @param  dicId Long
     * @return  int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/21 17:07
     */
    int deleteDicById(String dicId, AuthUserDto authUserDto);

    /**
     * <p>Title:     modifyDic. </p>
     * <p>Description 修改删除数据字典信息</p>
     *
     * @param  dic Dictionary
     * @return  int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/21 17:07
     */
    int modifyDic(Dictionary dic, AuthUserDto authUserDto);

    /**
     * <p>Title:     saveDicInfo. </p>
     * <p>Description 保存数据字典信息</p>
     *
     * @param  dic Dictionary
     * @return  int
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2018/11/21 17:07
     */
    int saveDicInfo(Dictionary dic, AuthUserDto authUserDto);
}
