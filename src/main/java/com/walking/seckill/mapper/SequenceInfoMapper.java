package com.walking.seckill.mapper;

import com.walking.seckill.dataobject.entity.SequenceInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceInfoMapper {
    int insert(SequenceInfo record);

    int insertSelective(SequenceInfo record);

    SequenceInfo selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(SequenceInfo record);

    int updateByPrimaryKey(SequenceInfo record);
}