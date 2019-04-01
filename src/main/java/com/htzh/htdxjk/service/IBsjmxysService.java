package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bsjmxys;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-24
 */
public interface IBsjmxysService extends IService<Bsjmxys> {

    Map<String, Object> findAlarmListByParam(int page, int rows, String keyword,String userId);

}
