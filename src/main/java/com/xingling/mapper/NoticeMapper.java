package com.xingling.mapper;

import com.xingling.base.MyMapper;
import com.xingling.model.domain.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NoticeMapper extends MyMapper<Notice> {

    List<Notice> queryListPage(Notice notice);
}