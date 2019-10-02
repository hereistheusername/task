package com.management.task.services;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class RedisSessionDAO extends AbstractSessionDAO {
    private long expireTime = 43200000;

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisSessionDAO() {
        super();
    }

    public RedisSessionDAO(long expireTime, RedisTemplate redisTemplate) {
        super();
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        if (serializable == null) {
            return null;
        }
        return (Session) redisTemplate.opsForValue().get(serializable);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }
        session.setTimeout(expireTime);
        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(Session session) {
        if (null == session) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return redisTemplate.keys("*");
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
