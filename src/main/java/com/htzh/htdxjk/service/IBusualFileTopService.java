package com.htzh.htdxjk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htzh.htdxjk.entity.BusualFileTop;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-09
 */


public interface IBusualFileTopService extends IService<BusualFileTop>{

    List<Map<String, Object>> findListByUserId(String userId, String isAdmin);
}
