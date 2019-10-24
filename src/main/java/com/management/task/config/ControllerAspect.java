package com.management.task.config;

import com.management.task.entities.Event;
import com.management.task.entities.Log;
import com.management.task.services.EventService;
import com.management.task.services.LogService;
import com.management.task.services.UserService;
import com.management.task.utils.IpInfoUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerAspect {
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private EventService eventService;
    //匹配所有RequestMapping注解
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void anyController() {}
    @AfterReturning("anyController()")
    public void before(JoinPoint joinPoint){
        //报错的时候默认会执行 BasicErrorController.error()方法
        if (joinPoint.getSignature().getName().equals("error")){
            return;
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject==null||subject.getPrincipal()==null){
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        String method = request.getMethod();
        if ((url.equals("/unauthorized")||url.equals("/login"))&&method.equals("GET")){
            return;
        }
        String workId= (String)subject.getPrincipal();
        String name = userService.findByWorkId(workId).getName();
        String ip = IpInfoUtil.getIpAddr(request);
        Event event= eventService.findByUrlAndMethod(url,method);
        String eventName = event!=null?event.getName():"该事件未定义";
        Log log = new Log();
        log.setWorkId(workId);
        log.setName(name);
        log.setIp(ip);
        log.setEvent(eventName);
        logService.save(log);
    }
}




