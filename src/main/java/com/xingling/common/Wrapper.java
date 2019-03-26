package com.xingling.common;

import com.github.pagehelper.Page;

import java.io.Serializable;

/**
 * <p>Title:	  Wrapper. </p>
 * <p>Description 包装类 </p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @Author         <a href="190332447@qq.com"/>杨文生</a>
 * @CreateDate     2017/4/19 10:58
 */
public class Wrapper<T> implements Serializable {

    /** 序列化标识 */

    /** 成功码. */
    public static final int SUCCESS_CODE = 200;

    /** 成功信息. */
    public static final String SUCCESS_MESSAGE = "操作成功";
    
    /** 错误码. */
    public static final int ERROR_CODE = 500;

    /** 错误信息. */
    public static final String ERROR_MESSAGE = "内部异常";

    /** 错误码：参数非法 */
    public static final int ILLEGAL_ARGUMENT_CODE_ = 100;

    /** 错误信息：参数非法 */
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "参数非法";

    /** 编号. */
    private int code;

    /** 信息. */
    private String message;

    /** 结果数据 */
    private T result;
    
    /**
     * Instantiates a new wrapper. default code=200
     */
    public Wrapper() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    /**
     * Instantiates a new wrapper.
     * 
     * @param code
     *            the code
     * @param message
     *            the message
     */
    public Wrapper(int code, String message) {
        this.code(code).message(message);
    }

    /**
     * Instantiates a new wrapper.
     * 
     * @param code
     *            the code
     * @param message
     *            the message
     * @param result
     *            the result
     */
    public Wrapper(int code, String message, T result) {
        super();
        this.code(code).message(message).result(result);
    }
    
    /**
     * Instantiates a new wrapper.
     * 
     * @param code
     *            the code
     * @param message
     *            the message
     * @param result
     *            the result
     */
    public Wrapper(int code, String message, T result, Page<T> page) {
        super();
        this.code(code).message(message).result(result);
    }

    /**
     * Gets the 编号.
     * 
     * @return the 编号
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the 编号.
     * 
     * @param code
     *            the new 编号
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets the 信息.
     * 
     * @return the 信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the 信息.
     * 
     * @param message
     *            the new 信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the 结果数据.
     * 
     * @return the 结果数据
     */
    public T getResult() {
        return result;
    }

    /**
     * Sets the 结果数据.
     * 
     * @param result
     *            the new 结果数据
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * Sets the 编号 ，返回自身的引用.
     * 
     * @param code
     *            the new 编号
     * 
     * @return the wrapper
     */
    public Wrapper<T> code(int code) {
        this.setCode(code);
        return this;
    }

    /**
     * Sets the 信息 ，返回自身的引用.
     * 
     * @param message
     *            the new 信息
     * 
     * @return the wrapper
     */
    public Wrapper<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * Sets the 结果数据 ，返回自身的引用.
     * 
     * @param result
     *            the new 结果数据
     * 
     * @return the wrapper
     */
    public Wrapper<T> result(T result) {
        this.setResult(result);
        return this;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .append("result", result)
                .toString();
    }
}
