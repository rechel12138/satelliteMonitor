package com.htzh.htdxjk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htzh.htdxjk.entity.BsatTop;
import com.htzh.htdxjk.mapper.BsatTopMapper;
import com.htzh.htdxjk.service.IBsatTopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-09
 */
@Service
public class BsatTopServiceImpl extends ServiceImpl<BsatTopMapper,BsatTop> implements IBsatTopService{

    @Autowired
    private BsatTopMapper bsatTopMapper;


    @Override
    public List<Map<String, Object>> findListByUserId(String userId,String isAdmin) {
        HashMap map = new HashMap();
        map.put("isAdmin",isAdmin);
        map.put("userId",userId);
        List<Map<String, Object>> isAdminListBsatTop = bsatTopMapper.getIsAdminListBsatTop(map);
        return isAdminListBsatTop;
    }
}
