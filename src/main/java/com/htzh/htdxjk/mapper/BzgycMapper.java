package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bzgyc;
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
public interface BzgycMapper extends BaseMapper<Bzgyc> {


    List<Bzgyc> getBzgycBySatIdOrParam(Map<String,Object> map);

}
