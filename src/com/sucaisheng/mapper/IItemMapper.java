package com.sucaisheng.mapper;

import com.sucaisheng.pojo.Item;

import java.util.List;

public interface IItemMapper {
    Item getItemById(String id);

    int updateItem(Item item);

    List<Item> getAllItems();
}
