package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bxhzcdw;
import com.htzh.htdxjk.mapper.BxhzcdwMapper;
import com.htzh.htdxjk.service.IBxhzcdwService;
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
public class BxhzcdwServiceImpl extends ServiceImpl<BxhzcdwMapper, Bxhzcdw> implements IBxhzcdwService {

    @Autowired
    BxhzcdwMapper bxhzcdwMapper;

    @Override
    public List<Bxhzcdw> findBySatIdOrSatCode(String satId, String keyWord) {
        Map<String,Object> map = new HashMap<>();
        map.put("satId",satId);
        map.put("keyWord",keyWord);
        return bxhzcdwMapper.findBySatIdOrSatCode(map);
    }

    @Override
    public List<Bxhzcdw> findZrrBySatAtpId(String bsatAtpid) {
        Map<String,Object> map = new HashMap<>();
        map.put("bsatAtpid",bsatAtpid);
        return bxhzcdwMapper.findZrrBySatAtpId(map);
    }

    @Override
    public List<Bxhzcdw> findZcdwBySatAtpId(String bsatAtpid) {
        Map<String,Object> map = new HashMap<>();
        map.put("bsatAtpid",bsatAtpid);
        return bxhzcdwMapper.findZcdwBySatAtpId(map);
    }
}
