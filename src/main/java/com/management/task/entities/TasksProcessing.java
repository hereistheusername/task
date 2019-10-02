package com.management.task.entities;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "taskprocessing")
public class TasksProcessing {

    @Id
    @GeneratedValue
    private Long id;

    private Integer taskid;

    private String taskname;

    private String workid;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    private Timestamp lasttimeprocessing;

    @Column(nullable = true, columnDefinition = "新任务")
    private String taskstate;

    @Column(nullable = false)
    private boolean ismustdo;

    private String appendix;

    private String field1;

    private String field2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
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

    public Timestamp getLasttimeprocessing() {
        return lasttimeprocessing;
    }

    public void setLasttimeprocessing(Timestamp lasttimeprocessing) {
        this.lasttimeprocessing = lasttimeprocessing;
    }

    public String getTaskstate() {
        return taskstate;
    }

    public void setTaskstate(String taskstate) {
        this.taskstate = taskstate;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isIsmustdo() {
        return ismustdo;
    }

    public void setIsmustdo(boolean ismustdo) {
        this.ismustdo = ismustdo;
    }
}
