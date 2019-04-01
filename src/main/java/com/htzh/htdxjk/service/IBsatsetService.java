package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.AllBsatModel;
import com.htzh.htdxjk.entity.Bsatset;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-11
 */
public interface IBsatsetService extends IService<Bsatset> {

    List<Map<String,Object>> findByParam(String keyWord,String userId);

    List<Bsatset> findAllBsatSet();

    Map<String,Object> getOneById(String atpId);



}
