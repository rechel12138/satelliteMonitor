package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bzbbwlrel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-23
 */
public interface IBzbbwlrelService extends IService<Bzbbwlrel> {

    List<Bzbbwlrel> findByBwlId(String bwlId);

}
