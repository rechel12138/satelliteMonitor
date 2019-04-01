package com.htzh.htdxjk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.AllBsatModel;
import com.htzh.htdxjk.entity.Bsat;
import com.htzh.htdxjk.mapper.BsatMapper;
import com.htzh.htdxjk.service.IBsatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@Service
public class BsatServiceImpl extends ServiceImpl<BsatMapper, Bsat> implements IBsatService {

    @Autowired
    BsatMapper bsatMapper;

    @Override
    public Page<AllBsatModel> selectAllSatListPage(Page<AllBsatModel> page, String keyword) {
        return page.setRecords(bsatMapper.selectAllSatListPage(page, keyword));
    }

    @Override
    public Page<AllBsatModel> selectAllSatListPageNoKeyWord(Page<AllBsatModel> page) {
        return page.setRecords(bsatMapper.selectAllSatListPageNoKeyWord(page));
    }

    @Override
    public AllBsatModel selectOneSatInfoById(String atpid) {
        return bsatMapper.selectOneSatInfoById(atpid);
    }

    @Override
    public Page<AllBsatModel> selectAllSatListByIdsPage(Page<AllBsatModel> page, String satids) {
        return page.setRecords(bsatMapper.selectAllSatListByIdsPage(page, satids));
    }



    @Override
    public Page<HashMap<String, String>> getBsatDomainsPage(Page<HashMap<String, String>> page, String domain) {
        return page.setRecords(bsatMapper.getBsatDomainsPage(page, domain));
    }

    @Override
    public List<HashMap<String, String>> getBsatDomains(String domain) {
        return bsatMapper.getBsatDomains(domain);
    }

    @Override
    public List<HashMap<String, String>> getBsatDomain(String domain) {
        return bsatMapper.getBsatDomain(domain);
    }

    @Override
    public Page<AllBsatModel> selectSatInfoForEZUI(Page<AllBsatModel> page, String satid, String startTime, String endTime) {
        return page.setRecords(bsatMapper.selectSatInfoForEZUI(page, satid, startTime, endTime));
    }

    @Override
    public List<AllBsatModel> findByParamEzuiForBsat(Map<String,Object> map){

        return bsatMapper.findByParamEzuiForBsat(map);
    }

    @Override
    public List<Map<String, Object>> getSatelliteCodeList() {

        return bsatMapper.getSatelliteCodeList();
    }

    @Override
    public void updateSatelliteDomainByCodes(String domain, String[] codes) {

        Map<String,Object> map = new HashMap<>();
        map.put("domain",domain);
        map.put("codes",codes);
        bsatMapper.updateSatelliteDomainByBsatAtpid(map);
    }

    @Override
    public Map<String, Object> getSatelliteCodesByDomain(String domain) {

        Map<String,Object> map = new HashMap<>();
        map.put("domain",domain);
        Map<String,Object> result = bsatMapper.getSatelliteCodesByDomain(map);
        String[] codes = String.valueOf(result.get("codes")).split(",");
        List<Map<String,Object>> codeList = new ArrayList<>();
        for(String c:codes){

            Map<String,Object> item = new HashMap<>();
            item.put("code",c);
            codeList.add(item);
            item = null;
        }

        result.put("codes",codeList);
        return result;
    }

    @Override
    public List<Bsat> selectByIds(List ids) {
        return bsatMapper.selectBatchIds(ids);
    }

    @Override
    public List<String> getDistDomain() {
        return bsatMapper.getDistDomain();
    }

    @Override
    public Map<String,Object> getOneBySatId(String satId) {
        return bsatMapper.selectBySatId(satId);
    }


    @Override
    public int countByParamEzuiForBsat(Map<String, Object> map) {
        return bsatMapper.countByParamEzuiForBsat(map);
    }

    @Override
    public List<Map<String, Object>> countBsat() {
        return bsatMapper.countBsat();
    }

    @Override
    public void deleteSateDomainByDomainIds(Map<String, Object> map) {
        bsatMapper.deleteSateDomainByDomainIds(map);
    }
}
