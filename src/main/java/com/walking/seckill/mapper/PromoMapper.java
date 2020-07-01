package com.walking.seckill.mapper;

import com.walking.seckill.dataobject.entity.Promo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromoMapper {
    int insert(Promo record);

    int insertSelective(Promo record);

    Promo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Promo record);

    int updateByPrimaryKey(Promo record);

    Promo selectByItemId(Integer itemId);
}