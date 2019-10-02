package com.management.task.repositories;

import com.management.task.entities.TasksInfo;
import org.springframework.data.repository.CrudRepository;

public interface TasksInfoRepository extends CrudRepository<TasksInfo, Long> {
}
