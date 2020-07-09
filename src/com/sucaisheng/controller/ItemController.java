package com.sucaisheng.controller;

import com.sucaisheng.controller.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Controller :
 * 1、将ItemController  ioc到你的spring容器中（将对象在spring容器中创建出来）
 * 2、标记controller层，前端控制器会在controller层中寻找匹配
 */
@Controller
public class ItemController {

    @Autowired
    JdbcTemplate jt;

    @RequestMapping("/itemList.do")
    public ModelAndView getItemList(){

        List<Item> itemList = jt.query("select * from items", new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int i) throws SQLException {
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setName(resultSet.getString("name"));
                item.setDetail(resultSet.getString("detail"));
                item.setPrice(resultSet.getString("price"));
                item.setCreatetime(resultSet.getString("createtime"));
                return item;
            }
        });
        ModelAndView mav = new ModelAndView();
        mav.addObject("itemList",itemList);
        mav.setViewName("itemList");
        return mav;
    }
}
