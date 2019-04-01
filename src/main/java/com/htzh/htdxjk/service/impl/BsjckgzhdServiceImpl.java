package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bsjckgzhd;
import com.htzh.htdxjk.mapper.BsjckgzhdMapper;
import com.htzh.htdxjk.service.IBsjckgzhdService;
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
public class BsjckgzhdServiceImpl extends ServiceImpl<BsjckgzhdMapper, Bsjckgzhd> implements IBsjckgzhdService {

    @Autowired
    BsjckgzhdMapper bsjckgzhdMapper;
    @Override
    public List<Map> getBsjckgzhdInfo(Map map) {
        return bsjckgzhdMapper.getBsjckgzhdInfo(map);
    }

    @Override
    public int countBsjckgzhdInfo(Map map) {
        return bsjckgzhdMapper.countBsjckgzhdInfo(map);
    }

    @Override
    public Map getOneBsjckById(String myid) {
        return bsjckgzhdMapper.getOneBsjckById(myid);
    }
}
