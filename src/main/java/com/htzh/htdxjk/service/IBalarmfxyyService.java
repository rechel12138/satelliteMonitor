package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Balarmfxyy;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface IBalarmfxyyService extends IService<Balarmfxyy> {

    Balarmfxyy findByAlarmId(String alarmId);

}
