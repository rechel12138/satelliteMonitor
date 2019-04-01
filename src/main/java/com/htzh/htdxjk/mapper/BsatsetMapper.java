package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bsatset;
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
 * @since 2019-03-11
 */
public interface BsatsetMapper extends BaseMapper<Bsatset> {

    List<Map<String,Object>> findByParam(Map<String,Object> map);

    List<Bsatset> findAllBsatSet();

    Map<String,Object> getOneById(@Param("atpId") String atpId);

}
