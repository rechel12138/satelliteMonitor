package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bsatdatamsg;
import com.htzh.htdxjk.mapper.BsatdatamsgMapper;
import com.htzh.htdxjk.service.IBsatdatamsgService;
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
 * @since 2019-03-06
 */
@Service
public class BsatdatamsgServiceImpl extends ServiceImpl<BsatdatamsgMapper, Bsatdatamsg> implements IBsatdatamsgService {

    @Autowired
    private BsatdatamsgMapper bsatdatamsgMapper;


    @Override
    public List<Bsatdatamsg> findByUserId(String userId) {
        return bsatdatamsgMapper.findByUserId(userId);
    }
}
