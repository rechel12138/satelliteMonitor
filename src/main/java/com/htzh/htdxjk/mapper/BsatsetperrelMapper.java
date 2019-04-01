package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bsatsetperrel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-19
 */
public interface BsatsetperrelMapper extends BaseMapper<Bsatsetperrel> {

    boolean deleteByBssId(@Param("bssId") String bssId);

    boolean deleteByBssIdBatch(List<String> list);

}
