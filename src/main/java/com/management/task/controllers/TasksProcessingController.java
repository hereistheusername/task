package com.management.task.controllers;

import com.management.task.entities.TasksProcessing;
import com.management.task.repositories.TasksProcessingRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path = "/TasksProcessing")
public class TasksProcessingController {

    @Autowired
    private TasksProcessingRepository tasksProcessingRepository;

    private static String[] states = {"已查看", "已忽略", "已提交", "已审核", "已完成"};

    @ApiOperation(value = "获取我的所有正在进行的任务", notes = "提供用户的工号，返回该工号的所有没有过期的任务")
    @GetMapping(path = "/getMyTasks")
    public @ResponseBody List<TasksProcessing> getMyTasks(@RequestParam String workId) {
        List<TasksProcessing> myTasksProcessing = new ArrayList<>();

        Date currentTime = new Date(System.currentTimeMillis());

        Iterator<TasksProcessing> iterator = tasksProcessingRepository.findAll().iterator();
        while (iterator.hasNext()) {
            TasksProcessing temp = iterator.next();
            if (temp.getWorkid().equals(workId) && temp.getDeadline().after(currentTime)) {
                myTasksProcessing.add(temp);
            }
        }

        return myTasksProcessing;
    }

    @ApiOperation(value = "改变正在进行中的任务的状态", notes = "提供要变成什么状态，以及在getMyTasks获得的id")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "要变成的状态。0-已查看，1-已忽略，2-已提交，3-已审核，4-已完成",
                    dataType = "int", name = "state"),
            @ApiImplicitParam(value = "getMyTasks获得的id", dataType = "long", name = "id")
    })
    @PostMapping(path = "/setState")
    public @ResponseBody String setState(@RequestParam int state,
                                         @RequestParam long id) {
        Optional<TasksProcessing> optionalTasksProcessing = tasksProcessingRepository.findById(id);
        if (!optionalTasksProcessing.isPresent()) {
            return "No Such Task";
        }
        TasksProcessing tasksProcessing = optionalTasksProcessing.get();

        tasksProcessing.setTaskstate(states[state]);
        tasksProcessingRepository.save(tasksProcessing);
        return "Saved";
    }

    @ApiOperation(value = "“历史任务”", notes = "查看我的历史任务")
    @ApiImplicitParam(value = "工号", name = "workId")
    @PostMapping(path = "/outOfDateTasks")
    public @ResponseBody List<TasksProcessing> outOfDateTasks(@RequestParam String workId) {
        List<TasksProcessing> outOfDateTasksList = new ArrayList<>();

        Date currentTime = new Date(System.currentTimeMillis());

        Iterator<TasksProcessing> iterator = tasksProcessingRepository.findAll().iterator();
        while (iterator.hasNext()) {
            TasksProcessing temp = iterator.next();
            if (temp.getWorkid().equals(workId) && temp.getDeadline().before(currentTime)) {
                outOfDateTasksList.add(temp);
            }
        }


        return outOfDateTasksList;
    }

    @ApiOperation(value = "上传附件", notes = "根据用户看到的正在处理的任务的id，上传附件")
    @PostMapping("/uploadAppendix")
    public @ResponseBody String uploadAppendix(@RequestParam long id, @RequestParam String appendix) {
        if(!tasksProcessingRepository.existsById(id)){
            return "No Such Task";
        }
        Optional<TasksProcessing> optionalTasksProcessing = tasksProcessingRepository.findById(id);
        TasksProcessing tasksProcessing = optionalTasksProcessing.get();
        tasksProcessing.setAppendix(appendix);
        tasksProcessingRepository.save(tasksProcessing);

        return "Saved";
    }

    @ApiOperation(value = "获取附件连接", notes = "根据正在处理的任务id获取上传的附件url,可能为空")
    @GetMapping("/getAppendix")
    public @ResponseBody String getAppendix(@RequestParam long id){
        Optional<TasksProcessing> optionalTasksProcessing = tasksProcessingRepository.findById(id);
        if (!optionalTasksProcessing.isPresent()) {
            return "No Such Task";
        }
        TasksProcessing tasksProcessing = optionalTasksProcessing.get();
        return tasksProcessing.getAppendix();
    }

}
