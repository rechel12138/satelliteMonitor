package com.htzh.htdxjk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htzh.htdxjk.entity.BusualFileTop;
import com.htzh.htdxjk.mapper.BusualFileTopMapper;
import com.htzh.htdxjk.service.IBusualFileTopService;
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
 * @since 2019-03-02
 */

@Service
public class BusualFileTopServiceImpl extends ServiceImpl<BusualFileTopMapper,BusualFileTop> implements IBusualFileTopService{

    @Autowired
    private BusualFileTopMapper busualFileTopMapper;


    @Override
    public List<Map<String, Object>> findListByUserId(String userId, String isAdmin) {
        HashMap map = new HashMap();
        map.put("isAdmin",isAdmin);
        map.put("userId",userId);
        List<Map<String, Object>> isAdminListBsatTop = busualFileTopMapper.findIsAdminListBsatTop(map);
        return isAdminListBsatTop;
    }
}
