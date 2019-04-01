package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.AlarmInfoModel;
import com.htzh.htdxjk.entity.Bdxznzdxtssbj;
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
public interface BdxznzdxtssbjMapper extends BaseMapper<Bdxznzdxtssbj> {

    AlarmInfoModel getByAtpId(@Param("alarmId") String alarmId);

}
