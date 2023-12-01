package com.star.event;

import com.star.entity.ExceptionLog;
import org.springframework.context.ApplicationEvent;

public class ExceptionLogEvent extends ApplicationEvent {
    public ExceptionLogEvent(ExceptionLog exceptionLog) { super(exceptionLog); }
}
