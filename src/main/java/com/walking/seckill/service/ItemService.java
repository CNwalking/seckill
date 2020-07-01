package com.walking.seckill.service;
import com.walking.seckill.dataobject.dto.ItemDTO;
import com.walking.seckill.dataobject.entity.Item;

import java.util.List;

/**
* @Author: CNwalking
* @DateTime: 2020/06/25
* @Description: TODO
*/
public interface ItemService {

    ItemDTO getItemById(Integer id);

    boolean decreaseStock(Integer itemId, Integer amount);

    ItemDTO createItem(ItemDTO itemDTO);

    List<ItemDTO> listItem();

}
