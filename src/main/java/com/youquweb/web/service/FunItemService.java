package com.youquweb.web.service;

import com.youquweb.web.pojo.FunItem;

import java.util.List;

public interface FunItemService {
    /**
     * 根据热门程度对内容进行排序
     * @return
     */
    List<FunItem> getFunItemByHot();

    /**
     * 根据时间先后对内容进行排序
     * @return
     */
    List<FunItem> getFunItemByTime();


}
