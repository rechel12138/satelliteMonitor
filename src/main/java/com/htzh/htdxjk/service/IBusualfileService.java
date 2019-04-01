package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Busualfile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-02
 */
public interface IBusualfileService extends IService<Busualfile> {

    List<Busualfile> findListByIds(List ids);

}
