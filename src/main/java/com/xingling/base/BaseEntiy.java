package com.xingling.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@ApiModel(value = "baseEntiy")
public class BaseEntiy {
	/**
	 * 主键 id
	 */
	@Id
	@GeneratedValue(generator = "UUID")
	@ApiModelProperty(value = "主键")
	private String id;

	/**
	 * 版本号
	 */
	@Column(name = "version")
	@ApiModelProperty(value = "版本号")
	private Integer version;

	/**
	 * 创建者id
	 */
	@Column(name = "creator_id")
	@ApiModelProperty(value = "创建者id")
	private String creatorId;

	/**
	 * 创建者名称
	 */
	@Column(name = "creator")
	@ApiModelProperty(value = "创建者名称")
	private String creator;

	/**
	 * 修改者id
	 */
	@Column(name = "updater_id")
	@ApiModelProperty(value = "修改者id")
	private String updaterId;

	/**
	 * 修改者名称
	 */
	@Column(name = "updater")
	@ApiModelProperty(value = "修改者名称")
	private String updater;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 更改时间
	 */
	@Column(name = "update_time")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty(value = "更改时间")
	private Date updateTime;

	/**
	 *删除标识
	 */
	@Column(name = "del")
	@ApiModelProperty(value = "删除标识")
	private Integer del;

	@Transient
	@ApiModelProperty(value = "页数")
	private int pageNum;

	@Transient
	@ApiModelProperty(value = "页大小")
	private int pageSize;
}
