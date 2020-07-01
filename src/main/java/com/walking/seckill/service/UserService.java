package com.walking.seckill.service;

import com.walking.seckill.dataobject.dto.RegisterDTO;
import com.walking.seckill.dataobject.entity.UserInfo;
import com.walking.seckill.dataobject.vo.UserVO;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/25 7:49 下午
 * @Description:
 */
public interface UserService {

    void register(RegisterDTO registerDTO);

    UserVO getUserById(Integer id);

    UserInfo login(String telphone, String encryptPassword);
}
