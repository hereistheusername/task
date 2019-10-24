package com.management.task.repositories;

import com.management.task.entities.TasksProcessing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksProcessingRepository extends JpaRepository<TasksProcessing, Long> {

    List<TasksProcessing> findByTaskid(Long taskid);

    List<TasksProcessing> findByTaskstate(String taskState);
}
