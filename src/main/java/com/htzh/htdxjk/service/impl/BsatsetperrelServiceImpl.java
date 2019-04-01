package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bsatsetperrel;
import com.htzh.htdxjk.mapper.BsatsetperrelMapper;
import com.htzh.htdxjk.service.IBsatsetperrelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-19
 */
@Service
public class BsatsetperrelServiceImpl extends ServiceImpl<BsatsetperrelMapper, Bsatsetperrel> implements IBsatsetperrelService {


    @Autowired
    BsatsetperrelMapper bsatsetperrelMapper;

    @Override
    public boolean deleteByBssId(String bssId) {
        return bsatsetperrelMapper.deleteByBssId(bssId);
    }

    @Override
    public boolean deleteByBssIdBatch(List<String> list) {
        return bsatsetperrelMapper.deleteByBssIdBatch(list);
    }
}
