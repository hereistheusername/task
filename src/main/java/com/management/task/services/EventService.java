package com.management.task.services;

import com.management.task.entities.Event;

public interface EventService {
    /**
     * 通过url和method 判别事件
     * @param url
     * @param method
     * @return
     */
    Event findByUrlAndMethod(String url, String method);
}
