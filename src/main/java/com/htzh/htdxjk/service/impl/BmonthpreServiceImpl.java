package com.htzh.htdxjk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Bmonthpre;
import com.htzh.htdxjk.entity.BmonthpreSatModel;
import com.htzh.htdxjk.mapper.BmonthpreMapper;
import com.htzh.htdxjk.service.IBmonthpreService;
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
public class BmonthpreServiceImpl extends ServiceImpl<BmonthpreMapper, Bmonthpre> implements IBmonthpreService {


    @Autowired
    BmonthpreMapper bmonthpreMapper;
    @Override
    public Page<BmonthpreSatModel> selectBmonthpreSatModelList(Page<BmonthpreSatModel> page, String satid) {
        return page.setRecords(bmonthpreMapper.selectBmonthpreSatModelList(page,satid));
    }

    @Override
    public List<BmonthpreSatModel> getBySatIdAndSatCode(String satId, String keyWord) {
        Map<String,Object> map = new HashMap<>();
        map.put("satId",satId);
        map.put("keyWord",keyWord);
        return bmonthpreMapper.findBySatIdOrSatCode(map);
    }
}
