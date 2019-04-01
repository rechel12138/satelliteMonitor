package com.htzh.htdxjk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htzh.htdxjk.entity.Bsat;
import com.htzh.htdxjk.entity.BsatTop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-09
 */
public interface BsatTopMapper extends BaseMapper<BsatTop> {

    List<Map<String,Object>> getIsAdminListBsatTop(HashMap map);
}
