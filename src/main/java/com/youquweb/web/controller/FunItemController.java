package com.youquweb.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youquweb.web.pojo.FunItem;
import com.youquweb.web.service.FunItemService;
import com.youquweb.web.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author hanlaiming
 * 主页处理逻辑
 * @date 2018-08-16
 */
@Controller
public class FunItemController {
    @Autowired
    private FunItemService funItemService;

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value={"/error"}, method = RequestMethod.GET)
    public String error(){
        System.out.println("error");
        return "index.html";
    }

    @RequestMapping(value={"/home","/index"}, method = RequestMethod.GET)
    @ResponseBody
    public JSONObject home(){
        System.out.println("获取主页");
        PageHelper.startPage(2, Constant.PAGE_SHOW_NUM);
        //1.共有多少条数据
        List<FunItem> funItemList = funItemService.getFunItemByHot();
        for(FunItem funItem:funItemList){
            System.out.println(funItem.getPostTime());
        }
        PageInfo<FunItem> pageInfo = new PageInfo<FunItem>(funItemList);
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getPages());
        System.out.println(pageInfo.getPageSize());
        System.out.println(pageInfo.getStartRow());
        System.out.println(pageInfo.getEndRow());
        JSONArray data = JSONArray.parseArray(JSON.toJSONString(funItemList));
        JSONObject result = new JSONObject();
        result.put("flag",true);
        result.put("data",data);
        result.put("curPage",1);
        result.put("totalRecords",pageInfo.getTotal());
        result.put("totalPage",getTotalPage(pageInfo.getTotal(),Constant.PAGE_SHOW_NUM));
        return result;
    }

    private int getTotalPage(long total, int pageShowNum) {
        if(total <= pageShowNum){
            return 1;
        }
        return (int)total/pageShowNum +1;
    }
}
