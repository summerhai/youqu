package com.youquweb.web.dao;

import com.youquweb.web.pojo.FunItem;

import java.util.List;

public interface FunItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(FunItem record);

    int insertSelective(FunItem record);

    FunItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FunItem record);

    int updateByPrimaryKey(FunItem record);

    List<FunItem> selectHotList();

    List<FunItem> selectByTime();
}