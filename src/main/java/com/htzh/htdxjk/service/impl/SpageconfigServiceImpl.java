package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Spageconfig;
import com.htzh.htdxjk.mapper.SpageconfigMapper;
import com.htzh.htdxjk.service.ISpageconfigService;
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
 * @since 2019-03-29
 */
@Service
public class SpageconfigServiceImpl extends ServiceImpl<SpageconfigMapper, Spageconfig> implements ISpageconfigService {

    @Autowired
    SpageconfigMapper spageconfigMapper;
    @Override
    public List<Map> getSpageList(Map map) {
        return spageconfigMapper.getSpageList(map);
    }

    @Override
    public List<Map> getOneSpageList(Map map) {
        return spageconfigMapper.getOneSpageList(map);
    }
}
