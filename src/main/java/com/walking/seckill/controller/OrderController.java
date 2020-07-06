package com.walking.seckill.controller;

import static com.walking.seckill.common.ProjectConstant.IS_LOGIN;
import static com.walking.seckill.common.ProjectConstant.LOGIN_USER;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.walking.seckill.common.APIException;
import com.walking.seckill.common.Result;
import com.walking.seckill.common.ResultCode;
import com.walking.seckill.dataobject.entity.UserInfo;
import com.walking.seckill.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/25 10:49 下午
 * @Description:
 */
@Slf4j
@Api(tags = "OrderController", description = "订单模块")
@RestController("OrderController")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "建order", notes = "建order")
    @PostMapping("/createorder")
    public Result createOrder(
            @ApiParam(name = "itemId", value = "商品id") @RequestParam(name = "itemId") Integer itemId,
            @ApiParam(name = "amount", value = "数量") @RequestParam(name = "amount") Integer amount,
            @ApiParam(name = "promoId", value = "促销活动id") @RequestParam(name = "promoId", required = false) Integer promoId,
            HttpServletRequest httpServletRequest) {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute(IS_LOGIN);
        if(isLogin == null || !isLogin){
            throw new APIException(ResultCode.USER_NOT_LOGIN);
        }
        //获取用户的登陆信息
        UserInfo userInfo = (UserInfo) httpServletRequest.getSession().getAttribute(LOGIN_USER);

        orderService.createOrder(userInfo.getId(), itemId, promoId, amount);

        return Result.defaultSuccess();
    }

}
