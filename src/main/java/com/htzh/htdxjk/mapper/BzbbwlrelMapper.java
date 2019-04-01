package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bzbbwlrel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-23
 */
public interface BzbbwlrelMapper extends BaseMapper<Bzbbwlrel> {

    List<Bzbbwlrel> findByBwlId(@Param("bwlId") String bwlId);

}
