package com.xingling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title:	  xinglinglove-website <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>  <br/>
 * @Date 2019/4/10 20:58
 */
@Data
@ApiModel(value = "资源")
public class ResourceDto {

    private static final long serialVersionUID = -2149529373440981833L;

    /**
     * 资源id(包括菜单id和权限id)
     */
    @ApiModelProperty(value = "资源id(包括菜单id和权限id)")
    private String resourceId;

    /**
     * 类型 1:菜单 2:按钮权限
     */
    @ApiModelProperty(value = "类型 1:菜单 2:按钮权限")
    private String type;
}
