package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bzbsw;
import com.htzh.htdxjk.mapper.BzbswMapper;
import com.htzh.htdxjk.service.IBzbswService;
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
public class BzbswServiceImpl extends ServiceImpl<BzbswMapper, Bzbsw> implements IBzbswService {

    @Autowired
    BzbswMapper bzbswMapper;

    @Override
    public Map<String,Object> listByAtpId(Map<String,Object> map) {
        Map<String,Object> resultMap = new HashMap<>();
        int count = bzbswMapper.getCount(map);
        resultMap.put("total",count);
        resultMap.put("rows",bzbswMapper.findByAtpId(map));
        return resultMap;
    }
    /*
     *
     * @author guoconglin
     * @date 2019/3/25 16:37
     * @param: [infoId] 值班列表atpId
     * @return: java.util.List<com.htzh.htdxjk.entity.Bzbsw>
     */
    @Override
    public List<Bzbsw> findByInfoid(String infoId) {
        return bzbswMapper.findByInfoid(infoId);
    }
}
