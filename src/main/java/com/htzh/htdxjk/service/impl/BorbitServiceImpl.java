package com.htzh.htdxjk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Borbit;
import com.htzh.htdxjk.mapper.BorbitMapper;
import com.htzh.htdxjk.service.IBorbitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-01
 */
@Service
public class BorbitServiceImpl extends ServiceImpl<BorbitMapper, Borbit> implements IBorbitService {

    @Autowired
    BorbitMapper borbitMapper;
    @Override
    public Page<HashMap<String,String>> getBorbitInfoList(Page<HashMap<String, String>> page, String satCode, String startTime, String endTime) {
        return page.setRecords(borbitMapper.getBorbitInfoList(page,satCode,startTime,endTime));
    }

    @Override
    public List<Map> getBorbitInfo(Map map) {
        return borbitMapper.getBorbitInfo(map);
    }

    @Override
    public int countBorbitInfo(Map map) {
        return borbitMapper.countBorbitInfo(map);
    }

    @Override
    public Map getOneBorbitById(String myid) {
        return borbitMapper.getOneBorbitById(myid);
    }
}
