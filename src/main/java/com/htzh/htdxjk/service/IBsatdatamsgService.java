package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bsatdatamsg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-06
 */
public interface IBsatdatamsgService extends IService<Bsatdatamsg> {

    List<Bsatdatamsg> findByUserId(String userId);

}
