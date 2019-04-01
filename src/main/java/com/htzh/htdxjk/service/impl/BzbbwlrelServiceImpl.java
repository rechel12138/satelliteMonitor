package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bzbbwlrel;
import com.htzh.htdxjk.mapper.BzbbwlrelMapper;
import com.htzh.htdxjk.service.IBzbbwlrelService;
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
 * @since 2019-03-23
 */
@Service
public class BzbbwlrelServiceImpl extends ServiceImpl<BzbbwlrelMapper, Bzbbwlrel> implements IBzbbwlrelService {

    @Autowired
    private BzbbwlrelMapper bzbbwlrelMapper;
    //通过备忘录ID查找
    @Override
    public List<Bzbbwlrel> findByBwlId(String bwlId) {
        return null;
    }
}
