package com.star.event;

import com.star.entity.OperationLog;
import org.springframework.context.ApplicationEvent;

public class OperationLogEvent  extends ApplicationEvent {

    public OperationLogEvent(OperationLog operationLog) {
        super(operationLog);
    }
}
