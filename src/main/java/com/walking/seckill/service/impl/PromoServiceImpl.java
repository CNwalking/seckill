package com.walking.seckill.service.impl;

import com.walking.seckill.dataobject.dto.PromoDTO;
import com.walking.seckill.mapper.PromoMapper;
import com.walking.seckill.dataobject.entity.Promo;
import com.walking.seckill.service.PromoService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @Author: CNwalking
* @DateTime: 2020/06/25
* @Description: TODO
*/
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoMapper promoMapper;

    @Override
    public PromoDTO getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀活动信息
        Promo promo = promoMapper.selectByItemId(itemId);
        if(promo == null){
            return null;
        }
        //dataobject->model
        PromoDTO dto = new PromoDTO();
        BeanUtils.copyProperties(promo,dto);
        dto.setStartDate(new DateTime(promo.getStartDate()));
        dto.setEndDate(new DateTime(promo.getEndDate()));
        if(dto == null){
            return null;
        }

        //判断当前时间是否秒杀活动即将开始或正在进行
        if(dto.getStartDate().isAfterNow()){
            dto.setStatus(1);
        }else if(dto.getEndDate().isBeforeNow()){
            dto.setStatus(3);
        }else{
            dto.setStatus(2);
        }
        return dto;
    }
}
