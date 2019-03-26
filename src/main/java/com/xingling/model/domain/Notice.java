package com.xingling.model.domain;

import com.xingling.base.BaseEntiy;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "xl_notice")
public class Notice extends BaseEntiy {

    /**
     *标题
     */
    @Column(name = "title")
    private String title;

    /**
     *链接
     */
    @Column(name = "link_url")
    private String linkUrl;

    /**
     *系统标识
     */
    @Column(name = "system_key")
    private String systemKey;

    /**
     *评论
     */
    @Column(name = "comment")
    private String comment;

    /**
     *评分
     */
    @Column(name = "rate")
    private BigDecimal rate;

    /**
     *类型
     */
    @Column(name = "type")
    private Byte type;

    /**
     *内容
     */
    @Column(name = "content")
    private String content;

}