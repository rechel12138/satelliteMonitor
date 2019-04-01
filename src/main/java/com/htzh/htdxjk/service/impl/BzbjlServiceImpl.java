package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bzbjl;
import com.htzh.htdxjk.mapper.BzbjlMapper;
import com.htzh.htdxjk.service.IBzbjlService;
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
public class BzbjlServiceImpl extends ServiceImpl<BzbjlMapper, Bzbjl> implements IBzbjlService {

    @Autowired
    private BzbjlMapper bzbjlMapper;

    @Override
    public Bzbjl findNextDuty(int param) {
        return bzbjlMapper.findNextDuty(param);
    }

    @Override
    public Map<String, Object> findBzbjlListByParamEzui(Map<String,Object> map) {
        Map<String,Object> hashMap = new HashMap<>();
        int count = bzbjlMapper.getCount(map);
        List<Map<String, Object>> bzbjlListByParamEzui = bzbjlMapper.findBzbjlListByParamEzui(map);
        hashMap.put("total",count);
        hashMap.put("rows",bzbjlListByParamEzui);
        return hashMap;
    }
}
