package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.Suserrole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
public interface ISuserroleService extends IService<Suserrole> {

    List<Suserrole> getUserRoleListByUserId(String userId);

    List<String> getUserRoleIdStringByUserId(String userId);
}
