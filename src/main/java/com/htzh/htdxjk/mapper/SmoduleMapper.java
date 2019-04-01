package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Smodule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
public interface SmoduleMapper extends BaseMapper<Smodule> {

    @Select("select * from htdxjk_smodule where sm_fksmoduleid=#{id} order by sm_atpsort asc limit  #{offset},#{rows}")
    List<Map<String,Object>> getSecondMenuByFId(Map<String,Object> map);

    @Select("select count(1) from htdxjk_smodule where sm_fksmoduleid=#{id}")
    int getSecondMenuByFIdCount(Map<String,Object> map);
}
