package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Suserrole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
public interface SuserroleMapper extends BaseMapper<Suserrole> {

    @Select("select a.sur_fksroleid,a.sur_fksuserid,b.sr_name from htdxjk_suserrole as a \n" +
            "left join htdxjk_srole as b on a.sur_fksroleid = b.sr_atpid\n" +
            "where a.sur_fksuserid=#{userId}")
    List<Suserrole> getUserRoleListByUserId(String userId);


    List<String> getUserRoleIdStringByUserId(String userId);
}
