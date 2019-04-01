package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Bzbbwl;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface IBzbbwlService extends IService<Bzbbwl> {

    Map<String,Object> listNowBzbbwlByParam(Map<String,Object> map);

}
