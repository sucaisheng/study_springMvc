package com.sucaisheng.controller;

import com.sucaisheng.pojo.Item;
import com.sucaisheng.service.IItemService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    public String updateItem(MultipartFile pictureFile, Item item, Model model, Boolean status, HttpServletRequest request){
        //获取需要存储位置的真实路径
        String realPath = request.getServletContext().getRealPath("pic");
        //通过UUID获取一个随机字符串作为文件新的名字，避免上传同一个文件出现重名
        String newFileName = UUID.randomUUID().toString().replace("-", "");
        //获取文件名的后缀
        String extension = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
        //真正储存的名字
        newFileName = newFileName + "." + extension;
        try {
            pictureFile.transferTo(new File(realPath + "/" + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        item.setPic(newFileName);
        int crows = iItemService.updateItem(item);
        if (crows > 0){
            //List<Item> itemList = iItemService.getAllItems();
            model.addAttribute("item",item);
            return "editItem";
        }
        else{
            return "editItem";
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

    /**
     * @RequestBody将json格式的数据转换成对象，不经常使用
     * @ResponseBody将对象转换成json格式
     * @param item
     * @return
     */
    @RequestMapping("/json.do")
    @ResponseBody
    public Item jsonTest(@RequestBody Item item){
        System.out.println(item);
        return item;
    }
}
