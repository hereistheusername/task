package com.management.task;

import com.management.task.services.RedisSessionDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TaskApplication {

    @Value("${shiro.jessionid}")
    private String jessionId;
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Bean
    public RedisSessionDAO getRedisSessionDAO() {
        return new RedisSessionDAO();
    }

    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 12小时，单位毫秒
        sessionManager.setGlobalSessionTimeout(43200000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionDAO(getRedisSessionDAO());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookie(getSessionIdCookie());
        return sessionManager;
    }

    @Bean
    public SimpleCookie getSessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie(jessionId);
        return simpleCookie;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public Realm realm() {
        TextConfigurationRealm realm = new TextConfigurationRealm();
        realm.setUserDefinitions("joe.coder=password,user\n" +
                "jill.coder=password,admin");

        realm.setRoleDefinitions("admin=TaskInfo:read,write\n" +
                "user=read");
        realm.setCachingEnabled(true);
        return realm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        chainDefinition.addPathDefinition("/login.html", "authc"); // need to accept POSTs from the login form
//        chainDefinition.addPathDefinition("/logout", "logout");
        chainDefinition.addPathDefinition("/TaskInfo/underGoingTasks", "authc,perms[TaskInfo:read]");
        chainDefinition.addPathDefinition("/TaskInfo/add", "authc, perms[TaskInfo:write]");
        chainDefinition.addPathDefinition("/TasksProcessing/getMyTasks", "authc, perms[TasksProcessing:read]");
        chainDefinition.addPathDefinition("/TasksProcessing/getMyTasks", "authc,perms[TasksProcessing:read]");
        chainDefinition.addPathDefinition("/TasksProcessing/setState", "authc, perms[TasksProcessing:write]");
        chainDefinition.addPathDefinition("/TasksProcessing/outOfDateTasks", "authc, perms[TasksProcessing:read]");
        chainDefinition.addPathDefinition("/TasksProcessing/uploadAppendix", "authc, perms[TasksProcessing:read]");
        chainDefinition.addPathDefinition("/TasksProcessing/getAppendix", "authc, perms[TasksProcessing:read]");

        return chainDefinition;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
