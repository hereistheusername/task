package com.management.task.services.imps;

import com.management.task.entities.User;
import com.management.task.repositories.UserDao;
import com.management.task.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByWorkId(String workId) {
        return userDao.findByWorkId(workId);
    }

    @Override
    public User update(User user) {
        return userDao.saveAndFlush(user);
    }
}
