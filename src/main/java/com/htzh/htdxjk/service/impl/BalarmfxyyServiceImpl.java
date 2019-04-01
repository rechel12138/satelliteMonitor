package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Balarmfxyy;
import com.htzh.htdxjk.mapper.BalarmfxyyMapper;
import com.htzh.htdxjk.service.IBalarmfxyyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@Service
public class BalarmfxyyServiceImpl extends ServiceImpl<BalarmfxyyMapper, Balarmfxyy> implements IBalarmfxyyService {

    @Autowired
    private BalarmfxyyMapper balarmfxyyMapper;


    @Override
    public Balarmfxyy findByAlarmId(String alarmId) {
        return null;
    }
}
