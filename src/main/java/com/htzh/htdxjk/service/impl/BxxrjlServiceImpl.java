package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bxxrjl;
import com.htzh.htdxjk.mapper.BxxrjlMapper;
import com.htzh.htdxjk.service.IBxxrjlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2019-02-23
 */
@Service
public class BxxrjlServiceImpl extends ServiceImpl<BxxrjlMapper, Bxxrjl> implements IBxxrjlService {

    @Autowired
    BxxrjlMapper bxxrjlMapper;

    @Override
    public List<String> findBxxrjlCat() {
        return bxxrjlMapper.findBxxrjlCat();
    }

    @Override
    public List<Map> findBxxrjlBsats(Map map) {


        return bxxrjlMapper.findBxxrjlBsats(map);
    }

    @Override
    public int findBxxrjlBsatsCount(Map map) {
        return bxxrjlMapper.findBxxrjlBsatsCount(map);
    }
}
