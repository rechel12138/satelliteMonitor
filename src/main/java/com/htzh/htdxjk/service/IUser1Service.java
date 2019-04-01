package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.User1;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Rechel
 * @since 2019-02-20
 */
public interface IUser1Service extends IService<User1> {

    Map<String,Object> listUser1();
}
