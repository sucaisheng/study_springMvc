package com.sucaisheng.controller;

import com.sucaisheng.pojo.Item;
import com.sucaisheng.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    IItemService iItemService;

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

    /**
     * 根据id获取item
     * */
    @RequestMapping("/editItem.do")
    public ModelAndView getItemById(HttpServletRequest resp){
        String id = resp.getParameter("id");
        Item item = iItemService.getItemById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("item",item);
        mav.setViewName("editItem");
        return mav;
    }

    @RequestMapping("/updateItem.do")
    public String updateItem(Item item, Model model){
        System.out.println("515587458" + item);
        int crows = iItemService.updateItem(item);
        if (crows > 0){
            List<Item> itemList = iItemService.getAllItems();
            model.addAttribute("itemList",itemList);
            return "itemList";
        }
        else{
            return "editList";
        }
    }

    @RequestMapping("/deleteItems.do")
    public String deleteItems(String[] ids , Model model){
        System.out.println("1656458448" + Arrays.toString(ids));
        int crows = iItemService.deleteItems(ids);
        if(crows > 0){
            List<Item> itemList = iItemService.getAllItems();
            model.addAttribute("itemList",itemList);
        }
        return "itemList";
    }
}
