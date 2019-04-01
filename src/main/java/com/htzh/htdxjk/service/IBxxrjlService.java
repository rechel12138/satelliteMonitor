package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bxxrjl;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface IBxxrjlService extends IService<Bxxrjl> {

    List<String> findBxxrjlCat();

    List<Map> findBxxrjlBsats(Map map);

    int findBxxrjlBsatsCount(Map map);
}
