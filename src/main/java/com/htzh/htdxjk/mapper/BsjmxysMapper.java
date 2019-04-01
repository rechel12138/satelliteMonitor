package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.AlarmInfoModel;
import com.htzh.htdxjk.entity.Bsjmxys;
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
 * @since 2019-02-24
 */
public interface BsjmxysMapper extends BaseMapper<Bsjmxys> {

    List<Map<String,Object>> findAlarmListByParam(Map<String,Object> map);

    int getCount(Map<String,Object> map);

    AlarmInfoModel getByAtpId(@Param("alarmId") String alarmId);

}
