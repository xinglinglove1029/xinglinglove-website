package com.xingling.service.impl;

import com.xingling.base.BaseServiceImpl;
import com.xingling.mapper.NoticeMapper;
import com.xingling.model.domain.Notice;
import com.xingling.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/3/26 11:24
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice> implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> queryListPage(Notice notice) {
        return noticeMapper.queryListPage(notice);
    }
}
