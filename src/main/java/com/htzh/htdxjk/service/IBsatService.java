package com.htzh.htdxjk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.AllBsatModel;
import com.htzh.htdxjk.entity.Bsat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htzh.htdxjk.entity.UserListModel;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface IBsatService extends IService<Bsat> {

    Page<AllBsatModel> selectAllSatListPage(Page<AllBsatModel> page, String keyword);

    Page<AllBsatModel> selectAllSatListPageNoKeyWord(Page<AllBsatModel> page);

    Page<AllBsatModel> selectAllSatListByIdsPage(Page<AllBsatModel> page, String satids);

    AllBsatModel selectOneSatInfoById(String atpid);

    Page<HashMap<String,String>> getBsatDomainsPage(Page<HashMap<String,String>>  page,String domain);

    List<HashMap<String,String>> getBsatDomains(String domain);

    List<HashMap<String,String>> getBsatDomain(String domain);


    Page<AllBsatModel> selectSatInfoForEZUI(Page<AllBsatModel> page, String satid, String startTime, String endTime);

    /**
     *
     * 获取所有卫星型号 by boris_deng
     * @return
     */
    List<Map<String,Object>> getSatelliteCodeList();

    /**
     * 根据卫星型号列表更新domain值 by boris_deng
     * @param domain 分类名称
     * @param codes  型号列表
     * @return
     */
    void updateSatelliteDomainByCodes(String domain,String[] codes);

    void deleteSateDomainByDomainIds(Map<String,Object> map);

    /**
     * 根据卫星分类(domain)列出所属型号(code) by boris_deng
     * @param domain 分类名称
     * @return
     */
    Map<String,Object>getSatelliteCodesByDomain(String domain);

    List<Bsat> selectByIds(List ids);


    List<String> getDistDomain();


    Map<String,Object> getOneBySatId(String satId);

    List<AllBsatModel> findByParamEzuiForBsat(Map<String,Object> map);

    int countByParamEzuiForBsat(Map<String,Object> map);

    List<Map<String, Object>> countBsat();


}
