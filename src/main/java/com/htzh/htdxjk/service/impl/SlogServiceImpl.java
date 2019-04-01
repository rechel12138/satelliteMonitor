package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Slog;
import com.htzh.htdxjk.mapper.BnoticeMapper;
import com.htzh.htdxjk.mapper.SlogMapper;
import com.htzh.htdxjk.service.ISlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@Service
public class SlogServiceImpl extends ServiceImpl<SlogMapper, Slog> implements ISlogService {

    @Autowired
    private SlogMapper slogMapper;
    @Override
    public Map<String, Object> getLogListForEzui(int page, int rows, String keyword) {

        int offset = page*rows - rows;
        Map<String,Object> map = new HashMap<>();
        map.put("offset",offset);
        map.put("rows",rows);
        map.put("keyword",keyword);

        int count = slogMapper.getLogsCount(map);
        List<Map<String,Object>> list = slogMapper.getLogsLists(map);

        Map<String,Object> result = new HashMap<>();
        result.put("total",count);
        result.put("rows",list);
        return result;
    }

    @Override
    public List<Map<String, Object>> logToExcel(String keyword) {

        Map<String,Object> map = new HashMap<>();
        map.put("keyword",keyword);
        List<Map<String, Object>> list = slogMapper.logToExcel(map);
        return list;

    }


}
