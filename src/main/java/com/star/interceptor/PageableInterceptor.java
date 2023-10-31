package com.star.interceptor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.star.utils.PageUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 分页拦截器
 */
public class PageableInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String currentPage = request.getParameter("current");
        String pageSize = Optional.ofNullable(request.getParameter("size")).orElse("10");
        if (StringUtils.hasText(currentPage)) {
            PageUtils.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        PageUtils.remove();
    }
}
