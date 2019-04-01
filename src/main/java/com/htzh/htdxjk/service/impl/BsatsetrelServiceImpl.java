package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bsatsetrel;
import com.htzh.htdxjk.mapper.BsatsetrelMapper;
import com.htzh.htdxjk.service.IBsatsetrelService;
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
public class BsatsetrelServiceImpl extends ServiceImpl<BsatsetrelMapper, Bsatsetrel> implements IBsatsetrelService {

    @Autowired
    BsatsetrelMapper bsatsetrelMapper;

    @Override
    public boolean deleteByBssId(String bssId) {
        return bsatsetrelMapper.deleteByBssId(bssId);
    }

    @Override
    public boolean deleteByBssIdBatch(List<String> list) {
        return bsatsetrelMapper.deleteByBssIdBatch(list);
    }
}
