package com.walking.seckill.service.impl;

import com.walking.seckill.common.APIException;
import com.walking.seckill.common.ResultCode;
import com.walking.seckill.dataobject.dto.ItemDTO;
import com.walking.seckill.dataobject.entity.OrderInfo;
import com.walking.seckill.dataobject.vo.OrderVO;
import com.walking.seckill.dataobject.vo.UserVO;
import com.walking.seckill.mapper.ItemMapper;
import com.walking.seckill.mapper.OrderInfoMapper;
import com.walking.seckill.mapper.SequenceInfoMapper;
import com.walking.seckill.service.ItemService;
import com.walking.seckill.service.OrderService;
import com.walking.seckill.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.walking.seckill.utils.SnowFlakeIdGenerator.getIdGenerator;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/25 7:57 下午
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private SequenceInfoMapper sequenceInfoMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ItemMapper itemMapper;


    @Override
    @Transactional
    public OrderVO createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws APIException {
        //1.校验下单状态,下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemDTO itemDTO = itemService.getItemById(itemId);
        if(itemDTO == null){
            throw new APIException(ResultCode.PARAM_ERROR_OR_EMPTY,"商品信息不存在");
        }

        UserVO userVO = userService.getUserById(userId);
        if(userVO == null){
            throw new APIException(ResultCode.PARAM_ERROR_OR_EMPTY,"用户信息不存在");
        }
        if(amount <= 0 || amount > 99){
            throw new APIException(ResultCode.PARAM_ERROR_OR_EMPTY,"数量信息不正确");
        }

        //校验活动信息
        if(promoId != null){
            //（1）校验对应活动是否存在这个适用商品
            if(promoId.intValue() != itemDTO.getPromoDTO().getId()){
                throw new APIException(ResultCode.PARAM_ERROR_OR_EMPTY,"活动信息不正确");
                //（2）校验活动是否正在进行中
            } else if (itemDTO.getPromoDTO().getStatus() != 2) {
                throw new APIException(ResultCode.PARAM_ERROR_OR_EMPTY, "活动信息还未开始");
            }
        }

        //2.落单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            throw new APIException(ResultCode.STOCK_NOT_ENOUGH);
        }

        //3.订单入库
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(userId);
        orderVO.setItemId(itemId);
        orderVO.setAmount(amount);
        if(promoId != null){
            orderVO.setItemPrice(itemDTO.getPromoDTO().getPromoItemPrice());
        }else{
            orderVO.setItemPrice(itemDTO.getPrice());
        }
        orderVO.setPromoId(promoId);
        orderVO.setOrderPrice(orderVO.getItemPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号,订单号
        orderVO.setId(String.valueOf(getIdGenerator()));
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderVO, orderInfo);
        orderInfoMapper.insertSelective(orderInfo);

        //加上商品的销量
        itemMapper.increaseSales(itemId, amount);
        //4.返回前端
        return orderVO;
    }

}
