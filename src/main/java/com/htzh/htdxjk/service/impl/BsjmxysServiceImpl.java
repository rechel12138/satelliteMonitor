package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bsjmxys;
import com.htzh.htdxjk.mapper.BsjmxysMapper;
import com.htzh.htdxjk.service.IBsjmxysService;
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
 * @since 2019-02-24
 */
@Service
public class BsjmxysServiceImpl extends ServiceImpl<BsjmxysMapper, Bsjmxys> implements IBsjmxysService {

    @Autowired
    BsjmxysMapper bsjmxysMapper;

    @Override
    public Map<String, Object>findAlarmListByParam(int page, int rows, String keyword,String userId) {
        int offset = page*rows - rows;
        Map<String,Object> map = new HashMap<>();
        map.put("offset",offset);
        map.put("rows",rows);
        map.put("keyword",keyword);
        map.put("userId",userId);

        int count = bsjmxysMapper.getCount(map);
        List<Map<String,Object>> list = bsjmxysMapper.findAlarmListByParam(map);

        Map<String,Object> result = new HashMap<>();
        result.put("total",count);
        result.put("rows",list);
        return result;
    }
}
