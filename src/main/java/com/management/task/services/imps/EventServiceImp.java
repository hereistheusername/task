package com.management.task.services.imps;

import com.management.task.entities.Event;
import com.management.task.services.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImp implements EventService {
    @Override
    public Event findByUrlAndMethod(String url, String method) {
        return null;
    }
}
