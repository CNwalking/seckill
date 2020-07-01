package com.walking.seckill.service.impl;

import com.walking.seckill.common.APIException;
import com.walking.seckill.common.ResultCode;
import com.walking.seckill.dataobject.dto.RegisterDTO;
import com.walking.seckill.dataobject.entity.UserInfo;
import com.walking.seckill.dataobject.entity.UserPassword;
import com.walking.seckill.dataobject.vo.UserVO;
import com.walking.seckill.mapper.UserInfoMapper;
import com.walking.seckill.mapper.UserPasswordMapper;
import com.walking.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/25 7:53 下午
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Override
    @Transactional
    public void register(RegisterDTO registerDTO) {
        if (ObjectUtils.isEmpty(registerDTO)) {
            throw new APIException(ResultCode.REGISTER_FILED);
        }
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(registerDTO, info);
        userInfoMapper.insert(info);

        UserPassword password = new UserPassword();
        password.setEncrptPassword(registerDTO.getEncryptPassword());
        password.setUserId(info.getId());
        userPasswordMapper.insert(password);
    }

    @Override
    public UserVO getUserById(Integer id) {
        //调用userdomapper获取到对应的用户dataobject
        UserInfo user = userInfoMapper.selectByPrimaryKey(id);
        if(user == null){
            return null;
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user,vo);
        vo.setGender(user.getGender().intValue());
        return vo;
    }

    @Override
    public UserInfo login(String telphone, String encryptPassword){
        //通过用户的手机获取用户信息
        UserInfo userInfo = userInfoMapper.selectByTel(telphone);
        if(userInfo == null){
            throw new APIException(ResultCode.USER_NOT_EXIST);
        }
        UserPassword password = userPasswordMapper.selectByPrimaryKey(userInfo.getId());
        //比对用户信息内加密的密码是否和传输进来的密码相匹配
        if(!StringUtils.equals(encryptPassword,password.getEncrptPassword())){
            throw new APIException(ResultCode.PASSWORD_ERROR);
        }
        return userInfo;
    }

}
