package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bnotice;
import com.htzh.htdxjk.mapper.BnoticeMapper;
import com.htzh.htdxjk.service.IBnoticeService;
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
 * @since 2019-03-02
 */
@Service
public class BnoticeServiceImpl extends ServiceImpl<BnoticeMapper, Bnotice> implements IBnoticeService {

    @Autowired
    private BnoticeMapper bnoticeMapper;
    @Override
    public List<Map<String, Object>> getAvailableNoticeLists() {

        return bnoticeMapper.getAvailableNoticeLists();
    }
}
