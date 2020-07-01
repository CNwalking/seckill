package com.walking.seckill.mapper;

import com.walking.seckill.dataobject.entity.UserPassword;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPasswordMapper {
    int insert(UserPassword record);

    int insertSelective(UserPassword record);

    UserPassword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPassword record);

    int updateByPrimaryKey(UserPassword record);

    UserPassword selectByUserId(Integer userId);

}