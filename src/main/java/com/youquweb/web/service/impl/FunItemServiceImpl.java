package com.youquweb.web.service.impl;

import com.youquweb.web.dao.FunItemMapper;
import com.youquweb.web.pojo.FunItem;
import com.youquweb.web.service.FunItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FunItemServiceImpl implements FunItemService {
    @Resource
    private FunItemMapper funItemMapper;
    /**
     * 根据热门程度对内容进行排序
     *
     * @return
     */
    @Override
    public List<FunItem> getFunItemByHot() {
        return funItemMapper.selectHotList();
    }

    /**
     * 根据时间先后对内容进行排序
     *
     * @return
     */
    @Override
    public List<FunItem> getFunItemByTime() {
        return funItemMapper.selectByTime();
    }

    @Override
    public void addFunItem(FunItem funItem) {
        funItemMapper.insert(funItem);
    }
}
