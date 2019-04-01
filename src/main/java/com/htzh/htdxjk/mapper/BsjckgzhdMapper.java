package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bsjckgzhd;
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
public interface BsjckgzhdMapper extends BaseMapper<Bsjckgzhd> {

    List<Map> getBsjckgzhdInfo (Map map);

    int countBsjckgzhdInfo (Map map);


    Map getOneBsjckById(String myid);

}
