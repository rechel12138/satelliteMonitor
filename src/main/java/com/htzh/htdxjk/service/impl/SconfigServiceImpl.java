package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Sconfig;
import com.htzh.htdxjk.mapper.SconfigMapper;
import com.htzh.htdxjk.service.ISconfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@Service
@Transactional
public class SconfigServiceImpl extends ServiceImpl<SconfigMapper, Sconfig> implements ISconfigService {

}
