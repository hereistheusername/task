package com.management.task.controllers;

import com.management.task.entities.TasksInfo;
import com.management.task.repositories.TasksInfoRepository;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path = "/TaskInfo")
public class TaskInfoController {
    @Autowired
    private TasksInfoRepository tasksInfoRepositoryE;

    @GetMapping(path = "login")
    public @ResponseBody String login(@RequestParam String userName, @RequestParam String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.toString();
        }
        return "login success";
    }

//    @Autowired
//    private TaskProcessingRepository taskProcessingRepository_;
    @ApiOperation(value = "添加任务", notes = "添加新的任务。任务的标题，截止时间，任务类型，workid是必填的")
    @PostMapping(path = "/add")
    public @ResponseBody String addNewTask(
            @RequestBody TasksInfo tasksInfo
            ) {
        Date taskDateTime = new Date(System.currentTimeMillis());
        tasksInfo.setTaskdatetime(taskDateTime);
        tasksInfoRepositoryE.save(tasksInfo);
        return "Saved";
    }

    @ApiOperation(value = "“任务审阅”中所有的进行任务", notes = "不含“待审核数”")
    @GetMapping(path = "/underGoingTasks")
    public @ResponseBody List<TasksInfo> underGoingTasks() {
        List<TasksInfo> underGoingTasksList = new ArrayList<>();

        Date currentTime = new Date(System.currentTimeMillis());

        // 寻找还没到截止日期的任务，也就是截止日期比今天大的任务
        Iterator<TasksInfo> tasksInfoIterator = tasksInfoRepositoryE.findAll().iterator();
        while (tasksInfoIterator.hasNext()) {
            TasksInfo temp = tasksInfoIterator.next();
            if (temp.getDeadline().after(currentTime)) {
                underGoingTasksList.add(temp);
            }
        }

        return underGoingTasksList;
    }

}
