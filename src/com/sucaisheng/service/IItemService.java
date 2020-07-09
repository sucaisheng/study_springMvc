package com.sucaisheng.service;

import com.sucaisheng.pojo.Item;

import java.util.List;

public interface IItemService {
    //根据id获取item
    Item getItemById(String id);
    //更新item
    int updateItem(Item item);
    //获取所有item
    List<Item> getAllItems();
}
