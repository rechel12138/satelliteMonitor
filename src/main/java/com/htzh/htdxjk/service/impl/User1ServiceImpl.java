package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.User1;
import com.htzh.htdxjk.mapper.User1Mapper;
import com.htzh.htdxjk.service.IUser1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rechel
 * @since 2019-02-20
 */
@Service
public class User1ServiceImpl extends ServiceImpl<User1Mapper, User1> implements IUser1Service {

    @Autowired
    private User1Mapper user1Mapper;

    @Override
    public Map<String, Object> listUser1() {
        Map<String,Object> map = new HashMap<>();

        List<User1> user = user1Mapper.listUser1();
        map.put("user",user);
        return map;
    }
}
