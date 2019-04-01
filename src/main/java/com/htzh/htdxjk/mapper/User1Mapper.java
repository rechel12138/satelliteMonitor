package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.User1;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Rechel
 * @since 2019-02-20
 */
public interface User1Mapper extends BaseMapper<User1> {

    List<User1> listUser1();
}
