package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bckplan;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface IBckplanService extends IService<Bckplan> {

    List<Bckplan> findBySatId(String satId);

    List<Map> getBckPlanInfo(Map map);

    int countBckPlanInfo(Map map);

    Map getOneBckById(String myid);

}
