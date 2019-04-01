package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.AlarmInfoModel;
import com.htzh.htdxjk.entity.AlarmModel;
import com.htzh.htdxjk.entity.Bsjmxss;
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
public interface IBsjmxssService extends IService<Bsjmxss> {

    Map<String, Object> getBsjmxssBySatId(String satId);

    Map<String, Object> getBalarmfxByParma(Map<String,Object> map);

    Map<String,Object> getAlarmByAtpId(String atpId,String beginTime,String endTime,String alarmType);

    Map<String,Object> incrementReturnData(String alarmlevel,String satIds,String source,String satId,String alarmStartTime,String timeStamp);

    AlarmInfoModel getByAlarmIdAndSource(String alarmId, String source);

}
