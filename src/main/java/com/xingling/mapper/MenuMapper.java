package com.xingling.mapper;


import com.xingling.base.MyMapper;
import com.xingling.model.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Menu mapper.
 */
@Mapper
@Component
public interface MenuMapper extends MyMapper<Menu> {


    /**
     * Select all menu list.
     *
     * @return the list
     */
    List<Menu> selectAllMenu();
}