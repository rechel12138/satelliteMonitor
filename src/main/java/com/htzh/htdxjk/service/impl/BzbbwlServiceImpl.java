package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bzbbwl;
import com.htzh.htdxjk.mapper.BzbbwlMapper;
import com.htzh.htdxjk.service.IBzbbwlService;
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
 * @since 2019-02-23
 */
@Service
public class BzbbwlServiceImpl extends ServiceImpl<BzbbwlMapper, Bzbbwl> implements IBzbbwlService {

    @Autowired
    private BzbbwlMapper bzbbwlMapper;

    @Override
    public Map<String, Object> listNowBzbbwlByParam(Map<String,Object> map) {
        Map<String,Object> resultMap = new HashMap<>();
        int count = bzbbwlMapper.getCount(map);
        List<Map<String, Object>> list = bzbbwlMapper.listNowBzbbwlByParam(map);
        resultMap.put("total",count);
        resultMap.put("rows",list);
        return resultMap;
    }
}
