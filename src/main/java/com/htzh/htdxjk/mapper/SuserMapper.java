package com.htzh.htdxjk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
public interface SuserMapper extends BaseMapper<Suser> {
    @Select("SELECT * FROM htdxjk.htdxjk_srole sr WHERE sr.sr_atpid IN ( SELECT sur.sur_fksroleid  FROM htdxjk.htdxjk_suserrole sur WHERE sur.sur_fksuserid=#{userId})")
    List<Srole> getUserRoles(String userId);

    @Select("SELECT * FROM htdxjk.htdxjk_smodule sm WHERE sm.sm_atpid IN ( SELECT srm.srm_fksmoduleid FROM htdxjk.htdxjk_srolemodule srm WHERE srm.srm_fksroleid=#{roleCode})")
    List<Smodule> getRoleSmodules(String roleCode);


    @Select("select\n" +
            " sm.sm_iscurrent,sm.sm_islogineddisplay,sm.sm_icon,sm.sm_webpath,sm.sm_atpid,sm.sm_name,sm.sm_fksmoduleid\n" +
            "from\n" +
            "htdxjk_suser su \n" +
            " join htdxjk_suserrole sur on su.su_atpid = sur.sur_fksuserid\n" +
            " join htdxjk_srole sr on sr.sr_atpid = sur.sur_fksroleid\n" +
            " join htdxjk_srolemodule srm on srm.srm_fksroleid = sr.sr_atpid\n" +
            " join htdxjk_smodule sm on sm_atpid = srm_fksmoduleid\n" +
            "where  su.su_atpid=#{userId} \n" +
            "and sm.sm_fksmoduleid=''\n" +
            "and sm.sm_atpid is not null\n" +
            "group by sm.sm_iscurrent,sm.sm_islogineddisplay,sm.sm_icon,sm.sm_webpath,sm.sm_atpid,sm.sm_name,sm.sm_fksmoduleid\n" +
            "order by sm.sm_atpid")
    List<Smodule> getFatherMenusByUserid(String userId);

    @Select("select\n" +
            " sm.sm_iscurrent,sm.sm_islogineddisplay,sm.sm_icon,sm.sm_webpath,sm.sm_atpid,sm.sm_name,sm.sm_fksmoduleid,group_concat(srm.srm_smaction) sm_action \n" +
            "from\n" +
            "htdxjk_suser su \n" +
            " join htdxjk_suserrole sur on su.su_atpid = sur.sur_fksuserid\n" +
            " join htdxjk_srole sr on sr.sr_atpid = sur.sur_fksroleid\n" +
            " join htdxjk_srolemodule srm on srm.srm_fksroleid = sr.sr_atpid\n" +
            " join htdxjk_smodule sm on sm_atpid = srm_fksmoduleid\n" +
            "where  su.su_atpid=#{userId} \n" +
            "and sm.sm_fksmoduleid=#{fkid}\n" +
            "group by sm.sm_iscurrent,sm.sm_islogineddisplay,sm.sm_icon,sm.sm_webpath,sm.sm_atpid,sm.sm_name,sm.sm_fksmoduleid\n" +
            "order by sm.sm_atpid")
    List<SonMenuListModel> getSonMenusByUseridFkid(@Param("userId") String userId, @Param("fkid") String fkid);

    @Select("SELECT\n" +
            "    htdxjk_suser.su_atpid,\n" +
            "    htdxjk_suser.su_chinesename,\n" +
            "    htdxjk_suser.su_account,\n" +
            "    htdxjk_suser.su_atpcreatedatetime,\n" +
            "    htdxjk_suser.su_lastlogindatetime,\n" +
            "    htdxjk_suser.su_atpstatus,\n" +
            "    group_concat(htdxjk_srole.sr_name) sr_name\n" +
            "FROM\n" +
            "    htdxjk_suser\n" +
            "LEFT JOIN\n" +
            "    htdxjk_suserrole\n" +
            "ON\n" +
            "    htdxjk_suser.su_atpid=htdxjk_suserrole.sur_fksuserid\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_srole\n" +
            "ON\n" +
            "    htdxjk_suserrole.sur_fksroleid=htdxjk_srole.sr_atpid\n" +
            "WHERE\n" +
            "    su_account <> 'admin'\n" +
            "AND (\n" +
            "        su_chinesename LIKE #{keyword}\n" +
            "    OR  su_englishname LIKE #{keyword}\n" +
            "    OR  su_nickname LIKE #{keyword}\n" +
            "    OR  su_account LIKE #{keyword}\n" +
            "    OR  su_tele_phone_num LIKE #{keyword}\n" +
            "    OR  su_work_phone_num LIKE #{keyword}\n" +
            "    OR  sr_name LIKE #{keyword})\n" +
            "GROUP BY\n" +
            "    htdxjk_suser.su_atpid,\n" +
            "    htdxjk_suser.su_chinesename,\n" +
            "    htdxjk_suser.su_account,\n" +
            "    htdxjk_suser.su_atpcreatedatetime,\n" +
            "    htdxjk_suser.su_lastlogindatetime,\n" +
            "    htdxjk_suser.su_atpstatus")
    List<UserListModel> selectUserListPage(Page page, @Param("keyword") String keyword);


    @Select("SELECT\n" +
            "        concat(group_concat(sr.sr_name SEPARATOR '/') ,'&',if(isnull(group_concat(sr.sr_indexurl SEPARATOR ',')),0,group_concat(sr.sr_indexurl SEPARATOR ',')) )\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_suser su\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suserrole sur\n" +
            "ON\n" +
            "    su.su_atpid=sur.sur_fksuserid\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_srole sr\n" +
            "ON\n" +
            "    sur.sur_fksroleid=sr.sr_atpid\n" +
            "WHERE\n" +
            "    su.su_atpid=#{userid} ")
    String getUserRoleNamesBySuserid(String userid);


    /**
     * 获取默认api权限资源
     * @return
     */
    HashSet getDefultAPI();

    HashSet getApiByUserid(Map map);

    List<String> findUserIdList();

    List<Map> getUserInfo(Map map);



}
