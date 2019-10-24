package com.management.task.services;


import com.management.task.entities.Log;

import java.util.List;

public interface LogService {
    /**
     * 返回所有Log
     * @return
     */
    List<Log> findAll();

    /**
     * 通过workid和name查询Log
     * @param workId
     * @param name
     * @return
     */
    List<Log> findByWorkIdAndName(String workId, String name);

    /**
     * 新建日志
     * @param log
     * @return
     */
    Log save(Log log);
}
