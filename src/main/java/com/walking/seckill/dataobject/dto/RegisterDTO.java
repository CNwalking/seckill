package com.walking.seckill.dataobject.dto;

import lombok.Data;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/25 7:41 下午
 * @Description:
 */
@Data
public class RegisterDTO {

    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;

    private String registerMode;

    private String thirdPartyId;

    private String encryptPassword;

}
