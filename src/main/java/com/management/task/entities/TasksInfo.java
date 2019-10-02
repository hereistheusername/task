package com.management.task.entities;


import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "taskinfo")
@ApiModel(description = "任务处理实体")
public class TasksInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskid;

    private String taskname;

    private String workid;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date taskdatetime;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    private Boolean ismustdo;

    private String templateurl;

    private String taskurl;

    private String taskcontent;

    private String field1;

    private String field2;

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsmustdo() {
        return ismustdo;
    }

    public void setIsmustdo(Boolean ismustdo) {
        this.ismustdo = ismustdo;
    }

    public String getTemplateurl() {
        return templateurl;
    }

    public void setTemplateurl(String templateurl) {
        this.templateurl = templateurl;
    }

    public String getTaskurl() {
        return taskurl;
    }

    public void setTaskurl(String taskurl) {
        this.taskurl = taskurl;
    }

    public String getTaskcontent() {
        return taskcontent;
    }

    public void setTaskcontent(String taskcontent) {
        this.taskcontent = taskcontent;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public Date getTaskdatetime() {
        return taskdatetime;
    }

    public void setTaskdatetime(Date taskdatetime) {
        this.taskdatetime = taskdatetime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
