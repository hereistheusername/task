package com.management.task.repositories;

import com.management.task.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    /**
     * 通过工号查找用户
     * @param workId
     * @return
     */
    User findByWorkId(String workId);
}
