package com.walking.seckill.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.walking.seckill.common.ResultCode;
import com.walking.seckill.dataobject.dto.RegisterDTO;
import com.walking.seckill.dataobject.entity.UserInfo;
import com.walking.seckill.dataobject.vo.RegisterVO;
import com.walking.seckill.service.UserService;
import com.walking.seckill.utils.MD5Encrypt;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.walking.seckill.common.APIException;
import com.walking.seckill.common.Result;
import com.walking.seckill.dataobject.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

import static com.walking.seckill.common.ProjectConstant.*;


/**
 * @Author: CNwalking
 * @DateTime: 2020/4/10 9:47 上午
 * @Description:
 */
@Slf4j
@Api(tags = "UserController", description = "用户模块")
@RestController("UserController")
@RequestMapping("/user")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户获取otp短信接口", notes = "用户获取otp短信接口")
    @PostMapping("/getotp")
    public String getOtp(
            @ApiParam(name = "telphone", value = "手机号") @RequestParam(name="telphone") @NotNull String telphone,
                                     HttpServletRequest httpServletRequest) {
        Random random = new Random();
        int randomInt =  random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //将OTP验证码同对应用户的手机号关联，使用httpsession的方式绑定他的手机号与OTPCODE
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        //将OTP验证码通过短信通道发送给用户,省略
        System.out.println("telphone = " + telphone + " & otpCode = "+otpCode);
        return otpCode;
    }

    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody @Valid RegisterVO vo,
            HttpServletRequest httpServletRequest) {
        //验证手机号和对应的otpCode相符合
        String inSessionOtpCode = (String) httpServletRequest.getSession().getAttribute(vo.getTelphone());
        if (!com.alibaba.druid.util.StringUtils.equals(vo.getOtpCode(), inSessionOtpCode)) {
            throw new APIException(ResultCode.FAILED.getCode(), "短信验证码不符合");
        }
        //用户的注册流程
        RegisterDTO dto = new RegisterDTO();
        dto.setName(vo.getName());
        dto.setGender(new Byte(String.valueOf(vo.getGender().intValue())));
        dto.setAge(vo.getAge());
        dto.setTelphone(vo.getTelphone());
        dto.setRegisterMode("byphone");
        dto.setEncryptPassword(MD5Encrypt.md5Encrypt(vo.getPassword()));
        userService.register(dto);
        return new Result(null);
    }

    @ApiOperation(value = "取用户信息", notes = "取用户信息")
    @PostMapping("/get")
    public UserVO getUser(
            @ApiParam(name = "id", value = "用户的id") @RequestParam(name="id") Integer id,
            HttpServletRequest httpServletRequest) {
        //调用service服务获取对应id的用户对象并返回给前端
        UserVO vo = userService.getUserById(id);
        //若获取的对应用户信息不存在
        if(vo == null){
            throw new APIException(ResultCode.USER_NOT_EXIST);
        }
        return vo;
    }

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/login")
    public Result login(
            @ApiParam(name = "telphone", value = "手机号")@RequestParam(name="telphone")String telphone,
            @ApiParam(name = "password", value = "密码")@RequestParam(name="password")String password,
            HttpServletRequest httpServletRequest) {
        //入参校验
        if(org.apache.commons.lang3.StringUtils.isEmpty(telphone)||
                StringUtils.isEmpty(password)){
            throw new APIException(ResultCode.VALIDATE_FAILED);
        }

        //用户登陆服务,用来校验用户登陆是否合法
        UserInfo userInfo = userService.login(telphone,MD5Encrypt.md5Encrypt(password));
        //将登陆凭证加入到用户登陆成功的session内
        httpServletRequest.getSession().setAttribute(IS_LOGIN,true);
        httpServletRequest.getSession().setAttribute(LOGIN_USER,userInfo);

        return new Result(null);
    }

}
