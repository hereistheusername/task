package com.management.task.services.imps;

import cn.hutool.core.util.StrUtil;
import com.management.task.entities.Log;
import com.management.task.repositories.LogDao;
import com.management.task.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImp implements LogService {
    @Autowired
    private LogDao logDao;
    @Override
    public List<Log> findAll() {
        return logDao.findAll();
    }

    @Override
    public List<Log> findByWorkIdAndName(String workId, String name) {
        if (StrUtil.isNotBlank(workId)&&StrUtil.isNotBlank(name)){
            return logDao.findByWorkIdAndName(workId,name);
        }else if (StrUtil.isNotBlank(workId)){
            return logDao.findByWorkId(workId);
        }else if (StrUtil.isNotBlank(name)){
            return logDao.findByName(name);
        }else {
            return findAll();
        }
    }

    @Override
    public Log save(Log log) {
        return logDao.save(log);
    }
}
