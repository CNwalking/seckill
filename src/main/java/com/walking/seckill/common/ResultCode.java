package com.walking.seckill.common;

import lombok.Getter;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 22:08
 * @Description:
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),

    FAILED(400, "响应失败"),

    VALIDATE_FAILED(401, "参数校验失败"),

    ERROR(500, "未知错误"),

    PARAM_ERROR_OR_EMPTY(600001, "参数错误或空"),
    REGISTER_FILED(600002, "注册失败"),
    USER_NOT_EXIST(600003, "用户不存在"),
    PASSWORD_ERROR(600004, "手机号或密码错误"),
    USER_NOT_LOGIN(600005, "用户未登录"),
    STOCK_NOT_ENOUGH(600006, "库存数量不足"),
    ;

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
