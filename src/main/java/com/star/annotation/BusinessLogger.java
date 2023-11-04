package com.star.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessLogger {

    /**
     * 操作模块
     */
    String value() default "";

    /**
     * 操作类型
     */
    String type() default "";

    /**
     * 操作说明
     */
    String desc() default "";

    /**
     * 是否将当前日志记录到数据库中
     */
    boolean save() default true;
}
