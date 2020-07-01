package com.walking.seckill.dataobject.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/25 11:05 下午
 * @Description:
 */
@Data
public class ItemDTO {

    private Integer id;
    // 商品名称
    private String title;
    // 商品价格
    private BigDecimal price;
    // 商品的库存
    private Integer stock;
    // 商品的描述
    private String description;
    // 商品的销量
    private Integer sales;
    // 商品描述图片的url
    private String imgUrl;
    // 如果promoDTO不为空，则表示其拥有还未结束的秒杀活动
    private PromoDTO promoDTO;
}
