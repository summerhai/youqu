package com.youquweb.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youquweb.web.pojo.FunItem;
import com.youquweb.web.service.FunItemService;
import com.youquweb.web.utils.Constant;
import com.youquweb.web.utils.DateUtils;
import com.youquweb.web.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public JSONObject home(HttpServletRequest request){
        System.out.println("获取主页");
        String pageNum = request.getParameter("page");
        System.out.println("pageNum:"+pageNum);
        int page = 1;
        if(pageNum != null){
            page = Integer.valueOf(pageNum);
        }
        System.out.println("当前页："+page);
        PageHelper.startPage(page, Constant.PAGE_SHOW_NUM);
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
        result.put("curPage",page);
        result.put("perPage",Constant.PAGE_SHOW_NUM);
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

    @RequestMapping(value={"/init"}, method = RequestMethod.GET)
    public String init() throws IOException {
        System.out.println("init");
        JSONArray dataArray = ExcelUtils.excelToJSON("E:\\Project\\youqu\\src\\main\\resources\\funitem.xlsx");
        for(int i=0;i<dataArray.size();i++){
            JSONObject dataObject = dataArray.getJSONObject(i);
            FunItem funItem = new FunItem();
            funItem.setPostTime(dataObject.getDate("PostTime"));
            funItem.setPostUser(dataObject.getString("PostUser"));
            funItem.setPostUserId(dataObject.getString("PostUserId"));
            funItem.setPostUserAvatar(dataObject.getString("PostUserAvatar"));
            funItem.setTag(dataObject.getString("Tag"));
            funItem.setType(dataObject.getString("Type"));
            funItem.setSource(dataObject.getString("Source"));
            funItem.setContent(dataObject.getString("Content"));
            funItem.setAvailable((short)1);
            funItem.setNiceNum(0);
            funItem.setCollectNum(0);
            funItem.setCommentNum(0);
            funItemService.addFunItem(funItem);
        }
        return null;
    }
}
