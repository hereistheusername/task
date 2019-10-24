package com.management.task.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "log")
@ApiModel(value = "日志")
public class Log extends BaseEntity{

    @ApiModelProperty(value = "工号")
    private String workId;

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "涉及")
    private String refer;
}
