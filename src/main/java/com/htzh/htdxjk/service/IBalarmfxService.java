package com.htzh.htdxjk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.AlarmModel;
import com.htzh.htdxjk.entity.AlarmStatisticalModel;
import com.htzh.htdxjk.entity.Balarmfx;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
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
public interface IBalarmfxService extends IService<Balarmfx> {

   Map getBalarmfxByParma(String alarmlevel);

   Page<Map> getBalarmfxUnion(Page<HashMap<String,String>>  page,Map map);
   List<AlarmStatisticalModel> getStatistical(String userId, int dayNum);

   Map<String,Object> getBySatIdParamEzui(String satId,String beginTime,String endTime,String alarmType,String alarmSource);

   Map<String, Object> findAlarmListByParam(int page, int rows, String keyWord,String userId,String alarmlevel,String startTime,String endTime,String sort,String order,String showQr,int days,String param);

   List<AlarmModel> findBySatIdAndSource(String satId,String source);

   Balarmfx findByAlarmId(String alarmId);

   Map<String,Object> alarmStatistical(int dayNum);

   List<Map> countZrrIndexType(Map map);

   List<Map> getBjFlfxCount(Map map);



}
