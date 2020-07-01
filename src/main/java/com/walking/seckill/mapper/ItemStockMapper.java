package com.walking.seckill.mapper;

import com.walking.seckill.dataobject.entity.ItemStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemStockMapper {
    int insert(ItemStock record);

    int insertSelective(ItemStock record);

    ItemStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStock record);

    int updateByPrimaryKey(ItemStock record);

    ItemStock selectByItemId(Integer itemId);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);

}