package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bxhzcdw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
public interface BxhzcdwMapper extends BaseMapper<Bxhzcdw> {

    List<Bxhzcdw> findBySatIdOrSatCode(Map map);

    List<Bxhzcdw> findZrrBySatAtpId(Map map);

    List<Bxhzcdw> findZcdwBySatAtpId(Map map);

}
