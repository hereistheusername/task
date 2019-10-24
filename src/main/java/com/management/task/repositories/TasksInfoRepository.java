package com.management.task.repositories;

import com.management.task.entities.TasksInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksInfoRepository extends JpaRepository<TasksInfo, Long> {

    List<TasksInfo> findByWorkidAndTaskname(String workId, String taskName);
}
