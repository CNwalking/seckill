package com.walking.seckill.dataobject.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/25 9:41 下午
 * @Description:
 */
@Data
public class RegisterVO {

    @ApiParam(name = "telphone", value = "手机号")
    @NotBlank(message = "手机号不能为空")
    String telphone;

    @ApiParam(name = "otpCode", value = "验证码(一次性密码)")
    String otpCode;

    @ApiParam(name = "name", value = "名字")
    @NotBlank(message = "用户名不能为空")
    String name;

    @ApiParam(name = "gender", value = "性别")
    Integer gender;

    @ApiParam(name = "age", value = "年龄")
    @NotNull(message = "年龄不能不填写")
    @Min(value = 0,message = "年龄必须大于0岁")
    @Max(value = 150,message = "年龄必须小于150岁")
    Integer age;

    @ApiParam(name = "password", value = "密码")
    @NotBlank(message = "密码不能为空")
    String password;
}
