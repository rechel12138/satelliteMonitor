package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Spageconfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-29
 */
public interface SpageconfigMapper extends BaseMapper<Spageconfig> {


    List<Map> getSpageList(Map map);

    List<Map> getOneSpageList(Map map);
}
