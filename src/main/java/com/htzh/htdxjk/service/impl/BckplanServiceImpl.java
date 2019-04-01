package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bckplan;
import com.htzh.htdxjk.mapper.BckplanMapper;
import com.htzh.htdxjk.service.IBckplanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BckplanServiceImpl extends ServiceImpl<BckplanMapper, Bckplan> implements IBckplanService {

    @Autowired
    private BckplanMapper bckplanMapper;

    @Override
    public List<Bckplan> findBySatId(String satId) {
        return bckplanMapper.findBySatId(satId);
    }

    @Override
    public List<Map> getBckPlanInfo(Map map) {
        return bckplanMapper.getBckPlanInfo(map);
    }

    @Override
    public int countBckPlanInfo(Map map) {
        return bckplanMapper.countBckPlanInfo(map);
    }

    @Override
    public Map getOneBckById(String myid) {
        return bckplanMapper.getOneBckById(myid);
    }
}
