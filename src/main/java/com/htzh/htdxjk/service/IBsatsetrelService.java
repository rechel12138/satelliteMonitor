package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bsatsetrel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-19
 */
public interface IBsatsetrelService extends IService<Bsatsetrel> {


    boolean deleteByBssId(String bssId);

    boolean deleteByBssIdBatch(List<String> list);

}
