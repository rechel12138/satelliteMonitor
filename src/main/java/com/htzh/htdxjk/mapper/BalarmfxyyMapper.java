package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Balarmfxyy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface BalarmfxyyMapper extends BaseMapper<Balarmfxyy> {

    Balarmfxyy findByAlarmId(@Param("alarmid") String alarmid);

}
