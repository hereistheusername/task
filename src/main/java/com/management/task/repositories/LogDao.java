package com.management.task.repositories;

import com.management.task.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogDao extends JpaRepository<Log,Integer>, JpaSpecificationExecutor<Log> {
    /**
     * 通过name查询Log
     * @param name
     * @return
     */
    List<Log> findByName(String name);

    /**
     * 通过workId查询Log
     * @param workId
     * @return
     */
    List<Log> findByWorkId(String workId);

    /**
     * 通过workid和name查询Log
     * @param workId
     * @param name
     * @return
     */
    List<Log> findByWorkIdAndName(String workId, String name);

}
