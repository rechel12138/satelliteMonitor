package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bzgyc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface IBzgycService extends IService<Bzgyc> {


    List<Bzgyc> getBzgycBySatIdAndParam(String satId,String bzgycName);

}
