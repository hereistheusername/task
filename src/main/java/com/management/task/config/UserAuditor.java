package com.management.task.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class UserAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Subject subject = SecurityUtils.getSubject();
        String name = (String) subject.getPrincipal();
        if (name!=null){
            return Optional.ofNullable(name);
        }
        else {
            return Optional.ofNullable("null");
        }
    }
}
