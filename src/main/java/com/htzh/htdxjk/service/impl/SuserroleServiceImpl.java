package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Suserrole;
import com.htzh.htdxjk.mapper.SuserroleMapper;
import com.htzh.htdxjk.service.ISuserroleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@Service
public class SuserroleServiceImpl extends ServiceImpl<SuserroleMapper, Suserrole> implements ISuserroleService {

    @Autowired
    private  SuserroleMapper suserroleMapper;

    @Override
    public List<Suserrole> getUserRoleListByUserId(String userId){

        return suserroleMapper.getUserRoleListByUserId(userId);
    }

    @Override
    public List<String> getUserRoleIdStringByUserId(String userId) {
        return suserroleMapper.getUserRoleIdStringByUserId(userId);
    }
}
