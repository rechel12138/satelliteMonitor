package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.AlarmInfoModel;
import com.htzh.htdxjk.entity.AlarmModel;
import com.htzh.htdxjk.entity.Balarmfx;
import com.htzh.htdxjk.entity.Bsjmxss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface BsjmxssMapper extends BaseMapper<Bsjmxss> {

    List<Map<String,Object>> getBsjmxssBySatId(Map map);


    List<AlarmModel> getBsjmxssByParam(Map map);

    int getCount(Map map);

    List<Balarmfx> getAlarmByAtpId(Map map);

    List<AlarmModel> incrementReturnData(Map<String,Object> map);

    AlarmInfoModel getByAtpId(@Param("alarmId") String alarmId);

}
