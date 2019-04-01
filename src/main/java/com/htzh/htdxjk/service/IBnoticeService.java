package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bnotice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-02
 */
public interface IBnoticeService extends IService<Bnotice> {

    List<Map<String,Object>> getAvailableNoticeLists();
}
