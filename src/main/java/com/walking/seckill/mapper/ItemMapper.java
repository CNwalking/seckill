package com.walking.seckill.mapper;

import com.walking.seckill.dataobject.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemMapper {
    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    int increaseSales(@Param("id")Integer id, @Param("amount")Integer amount);

    List<Item> listItem();
}