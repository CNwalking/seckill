package com.walking.seckill.service;

import com.walking.seckill.dataobject.vo.OrderVO;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/25 7:57 下午
 * @Description:
 */
public interface OrderService {

    OrderVO createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount);

}
