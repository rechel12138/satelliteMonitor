package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Spageconfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-29
 */
public interface ISpageconfigService extends IService<Spageconfig> {
    List<Map> getSpageList(Map map);
    List<Map> getOneSpageList(Map map);
}
