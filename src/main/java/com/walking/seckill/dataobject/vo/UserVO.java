package com.walking.seckill.dataobject.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/10 9:52 上午
 * @Description:
 */
@Data
public class UserVO {

    private Integer id;

    private String name;

    private Integer gender;

    private Integer age;

    private String telphone;

}
