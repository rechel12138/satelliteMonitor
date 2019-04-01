package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bxhzcdw;
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
public interface IBxhzcdwService extends IService<Bxhzcdw> {

    List<Bxhzcdw> findBySatIdOrSatCode(String satId,String keyWord);

    List<Bxhzcdw> findZrrBySatAtpId(String bsatAtpid);

    List<Bxhzcdw> findZcdwBySatAtpId(String bsatAtpid);

}
