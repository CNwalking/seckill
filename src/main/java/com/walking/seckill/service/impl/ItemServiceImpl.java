package com.walking.seckill.service.impl;

import com.walking.seckill.dataobject.dto.ItemDTO;
import com.walking.seckill.dataobject.dto.PromoDTO;
import com.walking.seckill.dataobject.entity.Item;
import com.walking.seckill.dataobject.entity.ItemStock;
import com.walking.seckill.mapper.ItemMapper;
import com.walking.seckill.mapper.ItemStockMapper;
import com.walking.seckill.service.ItemService;
import com.walking.seckill.service.PromoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
* @Author: CNwalking
* @DateTime: 2020/06/25
* @Description: TODO
*/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemStockMapper itemStockMapper;

    @Autowired
    private PromoService promoService;

    @Override
    public ItemDTO getItemById(Integer id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if(item == null){
            return null;
        }

        //操作获得库存数量
        ItemStock itemStock = itemStockMapper.selectByItemId(item.getId());
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item,itemDTO);
        itemDTO.setStock(itemStock.getStock());

        //获取活动商品信息
        PromoDTO promoDTO = promoService.getPromoByItemId(itemDTO.getId());
        if(promoDTO != null && promoDTO.getStatus() != 3){
            itemDTO.setPromoDTO(promoDTO);
        }
        return itemDTO;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = itemStockMapper.decreaseStock(itemId, amount);
        if (affectedRow > 0) {
            //更新库存成功
            return true;
        } else {
            //更新库存失败
            return false;
        }
    }

    @Override
    @Transactional
    public ItemDTO createItem(ItemDTO itemDTO){
        Item item = convertItemDoFromDto(itemDTO);

        //写入数据库
        itemMapper.insertSelective(item);
        itemDTO.setId(item.getId());

        ItemStock itemStock = convertItemStockFromItemDTO(itemDTO);
        itemStockMapper.insertSelective(itemStock);

        //返回创建完成的对象
        return getItemById(itemDTO.getId());
    }

    @Override
    public List<ItemDTO> listItem() {
        List<Item> itemDOList = itemMapper.listItem();
        List<ItemDTO> itemModelList =  itemDOList.stream().map(itemDO -> {
            ItemStock itemStock = itemStockMapper.selectByItemId(itemDO.getId());
            ItemDTO itemDTO = convertModelFromDataObject(itemDO,itemStock);
            return itemDTO;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    private ItemDTO convertModelFromDataObject(Item item,ItemStock itemStock){
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item,itemDTO);
        itemDTO.setStock(itemStock.getStock());
        return itemDTO;
    }

    private Item convertItemDoFromDto(ItemDTO itemDTO){
        if(itemDTO == null){
            return null;
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemDTO, item);
        return item;
    }

    private ItemStock convertItemStockFromItemDTO(ItemDTO itemDTO){
        if(itemDTO == null){
            return null;
        }
        ItemStock itemStock = new ItemStock();
        itemStock.setItemId(itemDTO.getId());
        itemStock.setStock(itemDTO.getStock());
        return itemStock;
    }
}
