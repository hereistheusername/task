package com.management.task.controllers;

import com.alibaba.fastjson.JSON;
import com.management.task.entities.AddingTask;
import com.management.task.entities.Member;
import com.management.task.entities.TasksInfo;
import com.management.task.entities.TasksProcessing;
import com.management.task.repositories.TasksInfoRepository;
import com.management.task.repositories.TasksProcessingRepository;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping(path = "/TaskInfo")
public class TaskInfoController {
    @Autowired
    private TasksInfoRepository tasksInfoRepositoryE;

    @Autowired
    private TasksProcessingRepository tasksProcessingRepository;

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
    @ApiOperation(value = "添加任务", notes = "添加新的任务。任务的标题，截止时间，任务类型，workid是必填的。前端在获取了参加这项任务的老师，顺便把workid和名字也提供一下")
    @PostMapping(path = "/add")
    public @ResponseBody String addNewTask(
            @RequestBody AddingTask addingTask
            ) {
        Date taskDateTime = new Date(System.currentTimeMillis());

        List<TasksProcessing> tasksProcessingList = new LinkedList<>();
        addingTask.tasksInfo.setTaskdatetime(taskDateTime);
        tasksInfoRepositoryE.save(addingTask.tasksInfo);


        // taskId交给数据库自动生成，所以数据要写入数据库
        // 使用该时刻时间戳找刚才的taskid
        Iterator<TasksInfo> iteratorOfTasksInfo = tasksInfoRepositoryE.
                findByWorkidAndTaskname(addingTask.tasksInfo.getWorkid(),
                        addingTask.tasksInfo.getTaskname()).iterator();
        if (!iteratorOfTasksInfo.hasNext()) {
            return "taskInfo wasn't saved; Aborting";
        }
        Timestamp timestamp = new Timestamp(taskDateTime.getTime());

        while (iteratorOfTasksInfo.hasNext()) {
            TasksInfo tasksInfo = iteratorOfTasksInfo.next();
            if (tasksInfo.getTaskdatetime().getTime() == timestamp.getTime()) {
                // 把涉及的相关人员添加到任务队列
                Iterator<Member> iteratorOfMember = addingTask.memberList.iterator();
                while (iteratorOfMember.hasNext()) {
                    Member temp = iteratorOfMember.next();
                    TasksProcessing tasksProcessing = new TasksProcessing();
                    tasksProcessing.setTaskid(tasksInfo.getTaskid());
                    tasksProcessing.setTaskname(tasksInfo.getTaskname());
                    tasksProcessing.setWorkid(temp.workId);
                    tasksProcessing.setName(temp.name);
                    tasksProcessing.setDeadline(tasksInfo.getDeadline());
                    tasksProcessing.setTaskstate("新任务");
                    tasksProcessingList.add(tasksProcessing);
                }

                tasksProcessingRepository.saveAll(tasksProcessingList);
                break;
            }
        }

        return "Saved";
    }

    @ApiOperation(value = "“任务审阅”中所有的进行任务", notes = "含“待审核数”")
    @GetMapping(path = "/underGoingTasks")
    public @ResponseBody ResponseEntity<String> underGoingTasks() {
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

        // 生成审核数的数组
        /**
         * 只需要搜索一次数据库，遍历随时间大于2 * underGoingTasksList.size()常数次
         *
         * 先搜索所有待审核任务，放入taskid, 任务数哈希表
         * 遍历正在进行的任务，从哈希表中取数据放入一个数组。这样做可以保证数组数据和对象的顺序是一样的
         *
         * 随着数据的增大，会有大量过期的数据影响哈希表的生成，导致性能下降。但是与根据taskid多次取数据相比的界限暂时不知道
         *
         * 后期需要把这一段逻辑写入service
        */
        List<TasksProcessing> waitingTasksProcessingList = tasksProcessingRepository.findByTaskstate("待审阅");
        Map<Long, Long> waitingTasksProcessingMap = new HashMap<>();

        for (TasksProcessing tasksProcessing: waitingTasksProcessingList) {
            if (waitingTasksProcessingMap.containsKey(tasksProcessing.getTaskid())) {
                long num = waitingTasksProcessingMap.get(tasksProcessing.getTaskid());
                num++;
                waitingTasksProcessingMap.put(tasksProcessing.getTaskid(), num);
            } else {
                waitingTasksProcessingMap.put(tasksProcessing.getTaskid(), 0L);
            }
        }

        long[] waitingArray = new long[underGoingTasksList.size()];
        for (int i = 0; i < underGoingTasksList.size(); i++) {
            if (waitingTasksProcessingMap.containsKey(underGoingTasksList.get(i))) {
                waitingArray[i] = waitingTasksProcessingMap.get(underGoingTasksList.get(i));
            }
        }

        return ResponseEntity.ok().body(
                JSON.toJSON(underGoingTasksList).toString() +
                        "," +
                        JSON.toJSON(waitingArray).toString()
        );

    }

}
