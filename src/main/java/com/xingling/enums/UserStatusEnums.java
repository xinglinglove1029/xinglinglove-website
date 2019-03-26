package com.xingling.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:	  spring-cloud-koala <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.xinglinglove.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2018/2/20 14:43
 */
public enum UserStatusEnums {

    /**
     * 启用"
     */
    ENABLE(1, "启用"),
    /**
     * 禁用
     */
    DISABLE(0, "禁用");

    int key;
    String value;

    UserStatusEnums(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取key获取value
     * @param key key
     * @return value
     */
    public static String getValue(int key) {
        for (UserStatusEnums ele : UserStatusEnums.values()) {
            if (ele.getKey() == key)
                return ele.getValue();
        }
        return null;
    }

    /**
     * 根据key获取该对象
     * @param key key
     * @return this
     */
    public static UserStatusEnums getEnum(int key) {
        for (UserStatusEnums ele : UserStatusEnums.values()) {
            if (ele.getKey() == key)
                return ele;
        }
        return null;
    }

    /**
     * 获取List集合
     * @return List
     */
    public static List<UserStatusEnums> getList(){
        return Arrays.asList(UserStatusEnums.values());
    }


    /**
     * 获取map类型集合
     * @return Map类型List集合
     */
    public static List<Map<String, String>> getMap2List() {
        List<Map<String, String>> list = Lists.newArrayList();
        for (UserStatusEnums ele : UserStatusEnums.values()) {
            Map<String, String> map = Maps.newHashMap();
            map.put("key", String.valueOf(ele.getKey()));
            map.put("value", ele.getValue());
            list.add(map);
        }
        return list;
    }
}
