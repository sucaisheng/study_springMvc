package com.sucaisheng.service.impl;

import com.sucaisheng.pojo.Item;
import com.sucaisheng.mapper.IItemMapper;
import com.sucaisheng.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    IItemMapper iItemMapper;

    @Override
    public Item getItemById(String id) {
        return iItemMapper.getItemById(id);
    }

    @Override
    public int updateItem(Item item) {
        return iItemMapper.updateItem(item);
    }

    @Override
    public List<Item> getAllItems() {
        return iItemMapper.getAllItems();
    }

    @Override
    public int deleteItems(String[] ids) {
        return iItemMapper.deleteItems(ids);
    }
}
