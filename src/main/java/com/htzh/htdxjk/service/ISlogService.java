package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Slog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
public interface ISlogService extends IService<Slog> {

    Map<String, Object> getLogListForEzui(int page, int rows, String keyword);

    List<Map<String,Object>> logToExcel(String keyword);
}
