package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bzbjl;
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
 * @since 2019-02-23
 */
public interface BzbjlMapper extends BaseMapper<Bzbjl> {

    Bzbjl findNextDuty(@Param("param") int param);

    List<Map<String,Object>> findBzbjlListByParamEzui(Map<String,Object> map);

    int getCount(Map<String,Object> map);

}
