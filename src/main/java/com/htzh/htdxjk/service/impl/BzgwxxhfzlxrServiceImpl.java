package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bzgwxxhfzlxr;
import com.htzh.htdxjk.mapper.BzgwxxhfzlxrMapper;
import com.htzh.htdxjk.service.IBzgwxxhfzlxrService;
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
public class BzgwxxhfzlxrServiceImpl extends ServiceImpl<BzgwxxhfzlxrMapper, Bzgwxxhfzlxr> implements IBzgwxxhfzlxrService {

    @Autowired
    BzgwxxhfzlxrMapper bzgwxxhfzlxrMapper;
    @Override
    public List<Map<String, Object>> findListBySatId(String satid) {
        return bzgwxxhfzlxrMapper.findListBySatId(satid);
    }
}
