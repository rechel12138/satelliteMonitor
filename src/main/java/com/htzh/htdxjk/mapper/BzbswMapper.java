package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bzbsw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface BzbswMapper extends BaseMapper<Bzbsw> {


    List<Map<String,Object>> findByAtpId(Map<String,Object> map);

    int getCount(Map<String,Object> map);

    List<Bzbsw> findByInfoid(@Param("infoId") String infoId);

}
