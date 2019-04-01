package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Slog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
public interface SlogMapper extends BaseMapper<Slog> {


    List<Map<String,Object>> getLogsLists(Map<String,Object> map);

    List<Map<String,Object>> logToExcel(Map<String,Object> map);

    int getLogsCount(Map<String,Object> map);

}
