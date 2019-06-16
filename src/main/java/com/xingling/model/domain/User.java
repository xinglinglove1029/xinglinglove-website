package com.xingling.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xingling.base.BaseEntiy;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "xl_user")
public class User extends BaseEntiy implements Serializable {

	/**
	 * 用户名
	 */
	@Column(name = "user_name")
	private String userName;
	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;
	/**
	 * 真实姓名
	 */
	@Column(name = "real_name")
	private String realName;
	/**
	 * 性别
	 */
	@Column(name = "sex")
	private String sex;
	/**
	 * 年龄
	 */
	@Column(name = "age")
	private Integer age;
	/**
	 * 出生日期
	 */
	@Column(name = "birthday")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;
	/**
	 * 密码
	 */
	@Column(name = "cell_phone")
	private String cellPhone;
	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;
	/**
	 * 地址
	 */
	@Column(name = "address")
	private String address;
	/**
	 * 城市
	 */
	@Column(name = "city")
	private String city;
	/**
	 * 国家
	 */
	@Column(name = "country")
	private String country;
	/**
	 * 状态
	 */
	@Column(name = "status")
	private Integer status;
	/**
	 * 盐值
	 */
	@Column(name = "salt")
	private String salt;
	/**
	 * 最近登录时间
	 */
	@Column(name = "last_login_date")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date  lastLoginDate;

	@Transient
	private Boolean enabled;


	@Transient
	private Boolean disabled;

	@Transient
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastPasswordResetDate;

	@Transient
	private String deptId;

	@Transient
	private String deptName;
}