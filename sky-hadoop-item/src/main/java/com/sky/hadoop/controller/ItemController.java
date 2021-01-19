package com.sky.hadoop.controller;

import com.sky.hadoop.entity.ItemEntity;
import com.sky.hadoop.service.ItemService;
import com.sky.hadoop.view.PageUtils;
import com.sky.hadoop.view.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商品表
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = itemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ItemEntity item = itemService.getById(id);

        return R.ok().put("item", item);
    }

    /**
     * 保存
     */
    @PostMapping("/create")
    public R create(@RequestBody ItemEntity item){
		itemService.save(item);

        return R.ok();
    }
    /**
     * 修改商品库存
     */
    @PostMapping("/decrease")
    public R decrease(@RequestBody ItemEntity item){
        Long id = item.getId();
        Integer stockNum = item.getStockNum();
        ItemEntity byId = itemService.getById(id);
        Integer newStockNum = byId.getStockNum() - stockNum ;
        byId.setStockNum(newStockNum);
        itemService.updateById(item);
        return R.ok();
    }

}
