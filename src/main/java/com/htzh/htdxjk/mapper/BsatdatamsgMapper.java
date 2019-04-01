package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bsatdatamsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-06
 */
public interface BsatdatamsgMapper extends BaseMapper<Bsatdatamsg> {

    List<Bsatdatamsg> findByUserId(String userId);

}
