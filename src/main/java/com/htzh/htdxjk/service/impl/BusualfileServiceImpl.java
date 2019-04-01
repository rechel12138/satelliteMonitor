package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Busualfile;
import com.htzh.htdxjk.mapper.BusualfileMapper;
import com.htzh.htdxjk.service.IBusualfileService;
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
 * @since 2019-03-02
 */
@Service
public class BusualfileServiceImpl extends ServiceImpl<BusualfileMapper, Busualfile> implements IBusualfileService {

    @Autowired
    private BusualfileMapper busualfileMapper;


    @Override
    public List<Busualfile> findListByIds(List ids) {
        return busualfileMapper.selectBatchIds(ids);
    }
}
