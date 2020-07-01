package com.walking.seckill.mapper;

import com.walking.seckill.dataobject.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}