package com.walking.seckill.mapper;

import com.walking.seckill.dataobject.entity.StockLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockLogMapper {
    int insert(StockLog record);

    int insertSelective(StockLog record);

    StockLog selectByPrimaryKey(String stockLogId);

    int updateByPrimaryKeySelective(StockLog record);

    int updateByPrimaryKey(StockLog record);
}