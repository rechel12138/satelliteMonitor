package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bzgyc;
import com.htzh.htdxjk.mapper.BzgycMapper;
import com.htzh.htdxjk.service.IBzgycService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class BzgycServiceImpl extends ServiceImpl<BzgycMapper, Bzgyc> implements IBzgycService {

    @Autowired
    private BzgycMapper bzgycMapper;

    @Override
    public List<Bzgyc> getBzgycBySatIdAndParam(String satId, String bzgycName) {
        Map<String,Object> map = new HashMap<>();
        map.put("satId",satId);
        map.put("bzgycName",bzgycName);
        return bzgycMapper.getBzgycBySatIdOrParam(map);
    }
}
