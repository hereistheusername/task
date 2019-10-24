package com.management.task.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
@ApiModel(value = "用户")

public class User extends BaseEntity{


    @ApiModelProperty(value = "工号")
    private String workId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "政治面貌")
    private String politicalAttitude;

    @ApiModelProperty(value = "身份证号")
    private String identityCard;

    @ApiModelProperty(value = "用户头像")
    private String accountPhoto;

    @ApiModelProperty(value = "个人照片")
    private String personalPhoto;

    @ApiModelProperty(value = "部门")
    private String department;

    @ApiModelProperty(value = "职务")
    private String job;

    @ApiModelProperty(value = "职称")
    private String title;

    @ApiModelProperty(value = "称谓")
    private String appellation;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "短号")
    private String shortenedPhone;

    @ApiModelProperty(value = "第二联系电话")
    private String extraPhone;

    @ApiModelProperty(value = "办公地点")
    private String officeLocation;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "权限")
    private String authority;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上次登陆时间")
    private Date lastLogin;

    @ApiModelProperty(value = "上次登陆ip")
    private String lastIp;

    @ApiModelProperty(value = "校内校外的字段")
    private String origin;

    @ApiModelProperty(value = "专家字段")
    private String isExpert;

    @ApiModelProperty(value = "冗余")
    private String field1;


}
