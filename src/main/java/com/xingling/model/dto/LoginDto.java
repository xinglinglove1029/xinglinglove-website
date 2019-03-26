package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/3/19 15:17
 */
@Data
@ApiModel(value = "登录Dto ")
public class LoginDto implements Serializable {

    private static final long serialVersionUID = -8413933754652935193L;

    private String username;

    private String password;


}
