package com.management.task.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "event")
@ApiModel(value = "事件")
public class Event extends BaseEntity {

    @ApiModelProperty(value = "URL")
    private String url;
    @ApiModelProperty(value = "方法")
    private String method;
    @ApiModelProperty(value = "事件名称")
    private String name;
}

