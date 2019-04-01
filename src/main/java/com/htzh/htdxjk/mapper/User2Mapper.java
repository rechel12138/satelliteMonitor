package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.User2;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-21
 */
public interface User2Mapper extends BaseMapper<User2> {

    List<User2> getUser2Diy();

    void insertSelective(User2 type);

    void insertSelective(@Param("name")String name, @Param("age")Integer age, @Param("email")String email);

}
