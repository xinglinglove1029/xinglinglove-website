package com.xingling.service;

import com.xingling.model.domain.Notice;

import java.util.List;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/3/26 11:24
 */
public interface NoticeService {

    List<Notice> queryListPage(Notice notice);
}
