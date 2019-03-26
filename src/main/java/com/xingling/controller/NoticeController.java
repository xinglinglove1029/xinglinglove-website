package com.xingling.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingling.common.WrapMapper;
import com.xingling.common.Wrapper;
import com.xingling.model.domain.Notice;
import com.xingling.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/3/26 11:27
 */
@RestController
@RequestMapping(value = "/notice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "NoticeController", tags = "NoticeController", description = "公告controller", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * <p>Title:      index. </p>
     * <p>Description 加载公告首页</p>
     *
     * @return
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/3/26 11:29
     */
    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("notice/index");
        return modelAndView;
    }

    /**
     * <p>Title:      listPage. </p>
     * <p>Description 分页查询公告</p>
     *
     * @param         notice Notice
     * @return     Wrapper
     * @Author        <a href="yangwensheng@meicai.cn"/>杨文生</a>
     * @since     2019/3/26 11:31
     */
    @PostMapping(value = "/listPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询公告列表")
    public Wrapper<PageInfo<Notice>> listPage(@ApiParam(name = "notice", value = "公告信息") @RequestBody Notice notice) {
        PageHelper.startPage(notice.getPageNum(), notice.getPageSize());
        List<Notice> listPage = noticeService.queryListPage(notice);
        PageInfo<Notice> pageInfo = new PageInfo<>(listPage);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
    }

}
