package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Smodule;
import com.htzh.htdxjk.mapper.SmoduleMapper;
import com.htzh.htdxjk.service.ISmoduleService;
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
 * @since 2019-02-20
 */
@Service
public class SmoduleServiceImpl extends ServiceImpl<SmoduleMapper, Smodule> implements ISmoduleService {

    @Autowired
    private SmoduleMapper smoduleMapper;

    @Override
    public Map<String,Object> getSecondMenuByFidForEzui(Map<String, Object> map) {

        int count =smoduleMapper.getSecondMenuByFIdCount(map);
        List<Map<String,Object>> list = smoduleMapper.getSecondMenuByFId(map);
        Map<String,Object> result = new HashMap<>();
        result.put("total",count);
        result.put("rows",list);
        return result;

    }
}
