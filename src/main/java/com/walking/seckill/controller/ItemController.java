package com.walking.seckill.controller;

import com.walking.seckill.common.Result;
import com.walking.seckill.dataobject.dto.ItemDTO;
import com.walking.seckill.dataobject.vo.CreateItemVO;
import com.walking.seckill.dataobject.vo.ItemVO;
import com.walking.seckill.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: CNwalking
 * @DateTime: 2020/6/26 1:28 下午
 * @Description:
 */
@Slf4j
@Api(tags = "ItemController", description = "商品模块")
@RestController("ItemController")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "创建商品", notes = "创建商品")
    @PostMapping("/create")
    public Result createItem(@RequestBody @Valid CreateItemVO vo,
                           HttpServletRequest httpServletRequest) {
        ItemDTO dto = new ItemDTO();
        dto.setTitle(vo.getTitle());
        dto.setPrice(vo.getPrice());
        dto.setStock(vo.getStock());
        dto.setDescription(vo.getDescription());
        dto.setSales(vo.getSales());
        dto.setImgUrl(vo.getImgUrl());

        ItemDTO returnDto = itemService.createItem(dto);

        return new Result(convertVoFromDto(returnDto));
    }

    @ApiOperation(value = "拿商品信息", notes = "拿商品信息")
    @PostMapping("/get")
    public Result getItem(@RequestParam(name = "id") Integer id) {

        ItemDTO itemDTO = itemService.getItemById(id);

        ItemVO itemVO = convertVoFromDto(itemDTO);

        return new Result(itemVO);
    }


    @ApiOperation(value = "拿商品信息", notes = "拿商品信息")
    @GetMapping("/get")
    public List<ItemVO> listItem() {

        List<ItemDTO> itemModelList = itemService.listItem();

        //使用stream apiJ将list内的itemModel转化为ITEMVO;
        List<ItemVO> itemVOList =  itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = convertVoFromDto(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return itemVOList;
    }

    private ItemVO convertVoFromDto(ItemDTO dto){
        if(dto == null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(dto,itemVO);
        if(dto.getPromoDTO() != null){
            //有正在进行或即将进行的秒杀活动
            itemVO.setPromoStatus(dto.getPromoDTO().getStatus());
            itemVO.setPromoId(dto.getPromoDTO().getId());
            itemVO.setStartDate(dto.getPromoDTO().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(dto.getPromoDTO().getPromoItemPrice());
        }else{
            itemVO.setPromoStatus(0);
        }
        return itemVO;
    }
}
