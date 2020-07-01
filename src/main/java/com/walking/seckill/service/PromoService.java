package com.walking.seckill.service;

import com.walking.seckill.dataobject.dto.PromoDTO;

/**
* @Author: CNwalking
* @DateTime: 2020/06/25
* @Description: TODO
*/
public interface PromoService {

    PromoDTO getPromoByItemId(Integer itemId);

}
