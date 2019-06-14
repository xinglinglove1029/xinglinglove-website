package com.xingling.constants;

/**
 * <p>Title:	  Constants. </p>
 * <p>Description 常量 </p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @Author         <a href="190332447@qq.com"/>杨文生</a>
 * @CreateDate     2017/4/19 11:03
 */
public class Constants {

    /**
     * 默认分页页数
     */
    public static final  int DEFAULT_PAGE_NUM = 1;
    /**
     * 默认分页数量
     */
    public static final  int DEFAULT_PAGE_SIZE = 20;

    /**
     * 删除
     */
    public static final  int DELETE_YES = 1;

    /**
     * 不删除
     */
    public static final  int DELETE_NO = 0;


    /**
     * 状态:启用
     */
    public static final  String ENABLE = "1";

    /**
     * 状态:禁用
     */
    public static final  String DISABLE = "0";


    public static final String TOKEN_AUTH_INFO = "CURRENT_USER_INFO";

    /**
     * 根菜单
     */
    public static final  String MENU_ROOT = "1";


    /**
     * 数据字典根节点
     */
    public static final  String DICTIONARY_ROOT = "root";

    /**
     * 根节点父编码
     */
    public static final  String ROOT_PARENTID = "-1";


    /**
     * 超级管理员的用户编号
     */
    public static final String SUPER_MANAGER = "1";

    public interface Redis {
        String OK = "OK";

        // 过期时间, 60s, 一分钟
        int EXPIRE_TIME_MINUTE = 60;

        // 过期时间, 一小时
        int EXPIRE_TIME_HOUR = 60 * 60;

        // 过期时间, 一天
        int EXPIRE_TIME_DAY = 60 * 60 * 24;

        String TOKEN_PREFIX = "token:";

        String ACCESS_LIMIT_PREFIX = "accessLimit:";
    }

}
