package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Smodule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
public interface ISmoduleService extends IService<Smodule> {

    public Map<String,Object> getSecondMenuByFidForEzui(Map<String,Object> map);
}
