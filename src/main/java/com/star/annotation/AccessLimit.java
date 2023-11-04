package com.star.annotation;

import java.lang.annotation.*;

/**
 * Redis限流注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 在时间窗内的限流次数
     */
    int count() default 10;

    /**
     * 限流时间窗
     */
    int time() default 10;

    /**
     * 超过限流次数是否也放行
     */
    boolean pass() default false;
}
