package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bxxrjl;
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
public interface BxxrjlMapper extends BaseMapper<Bxxrjl> {

    List<String> findBxxrjlCat();

    List<Map> findBxxrjlBsats(Map map);

    int findBxxrjlBsatsCount(Map map);

}
