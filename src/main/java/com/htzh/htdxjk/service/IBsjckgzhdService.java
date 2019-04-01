package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bsjckgzhd;
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
public interface IBsjckgzhdService extends IService<Bsjckgzhd> {

    List<Map> getBsjckgzhdInfo (Map map);

    int countBsjckgzhdInfo (Map map);


    Map getOneBsjckById(String myid);
}
