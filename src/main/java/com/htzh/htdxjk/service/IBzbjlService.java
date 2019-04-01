package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bzbjl;
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
public interface IBzbjlService extends IService<Bzbjl> {

    Bzbjl findNextDuty(int param);

    Map<String,Object> findBzbjlListByParamEzui(Map<String,Object> map);

}
