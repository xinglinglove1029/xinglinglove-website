package com.xingling.service;

/**
 * <p>Title:	  spring-cloud-koala <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2017/8/30 11:37
 */
public interface EmailService {

	/**
	 * 发送邮件
	 *
	 * @param to      收件人地址
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @author lance
	 */
	 boolean sender(String to, String subject, String content);

	/**
	 * 发送邮件
	 *
	 * @param to      收件人地址
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param html    是否格式内容为HTML
	 * @author lance
	 */
	boolean sender(String to, String subject, String content, boolean html);

	/**
	 * sender message
	 *
	 * @param to
	 * @param subject
	 * @param content
	 * @param html
	 * @return
	 */
	boolean sender(String[] to, String subject, String content, boolean html) ;
}

