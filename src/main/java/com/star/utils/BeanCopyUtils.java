package com.star.utils;

import org.springframework.beans.BeanUtils;

/**
 * 拷贝工具
 */
public class BeanCopyUtils {

    public static <T> T copyBean(Object source, Class<T> target) {
        // 创建目标对象
        T result = null;
        try {
            result = target.getDeclaredConstructor().newInstance();
            if (source != null) {
                // 实现属性copy
                BeanUtils.copyProperties(source, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }
}
