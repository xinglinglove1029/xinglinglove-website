package com.xingling.service.impl;


import com.google.common.collect.Lists;
import com.xingling.base.BaseServiceImpl;
import com.xingling.constants.Constants;
import com.xingling.exception.BusinessException;
import com.xingling.mapper.DictionaryMapper;
import com.xingling.model.domain.Dictionary;
import com.xingling.model.dto.AuthUserDto;
import com.xingling.model.vo.DicTreeVo;
import com.xingling.service.DictionaryService;
import com.xingling.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Title: DictionaryService<br>;
 * Description: 数据字典<br>;
 * Copyright: Copyright (c) 2017<br>;
 * Company: 北京云杉世界信息技术有限公司<br>;
 *
 * @author yangwensheng@meicai.cn<br>
 * 2018/11/21 16:50
 */
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    public List<DicTreeVo> getDicTree() {
        List<Dictionary> dicList = dictionaryMapper.selectAll();
        Map<String, String> dicMap = dicList.stream().collect(Collectors.toMap(Dictionary::getId, Dictionary::getName));
        List<DicTreeVo> trees = Lists.newArrayList();
        DicTreeVo node = null;
        for (Dictionary dic : dicList) {
            node = new DicTreeVo();
            BeanUtils.copyProperties(dic, node);
            node.setParentId(dic.getPid());
            String parentDicName = dicMap.get(dic.getPid());
            node.setParentName(parentDicName);
            node.setValue(dic.getValue());
            node.setRemark(dic.getRemark());
            node.setCode(dic.getCode());
            if(Constants.DICTIONARY_ROOT.equals(dic.getCode())){
                node.setDisabled(true);
            }else{
                node.setDisabled(false);
            }
            node.setNumber(dic.getNumber());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, Constants.ROOT_PARENTID) ;
    }

    @Override
    public Dictionary getDicById(String dicId) {
        return dictionaryMapper.selectByPrimaryKey(dicId);
    }

    @Override
    public int deleteDicById(String dicId, AuthUserDto authUserDto) {
        // 日后要记录删除日志
        return dictionaryMapper.deleteByPrimaryKey(dicId);
    }

    @Override
    public int modifyDic(Dictionary dic, AuthUserDto authUserDto) {
        Dictionary queryDic = new Dictionary();
        queryDic.setId(dic.getPid());
        queryDic.setDel(Constants.DELETE_NO);
        Dictionary dictionary = dictionaryMapper.selectOne(queryDic);
        if (Objects.isNull(dictionary)) {
            throw new BusinessException("父编码信息不存在");
        }
        dic.setCreator(authUserDto.getRealName());
        dic.setCreatorId(authUserDto.getUserId());
        dic.setUpdater(authUserDto.getRealName());
        dic.setUpdaterId(authUserDto.getUserId());
        dic.setUpdateTime(new Date());
        return dictionaryMapper.updateByPrimaryKeySelective(dic);
    }

    @Override
    public int saveDicInfo(Dictionary dic, AuthUserDto authUserDto) {
        Dictionary queryDic = new Dictionary();
        queryDic.setId(dic.getPid());
        queryDic.setDel(Constants.DELETE_NO);
        Dictionary dictionary = dictionaryMapper.selectOne(queryDic);
        if (Objects.isNull(dictionary)) {
            throw new BusinessException("父编码信息不存在");
        }
        dic.setLevel(dictionary.getLevel()+1);
        dic.setCreateTime(new Date());
        dic.setCreator(authUserDto.getRealName());
        dic.setCreatorId(authUserDto.getUserId());
        dic.setUpdater(authUserDto.getRealName());
        dic.setUpdaterId(authUserDto.getUserId());
        return dictionaryMapper.insertSelective(dic);
    }
}
