package com.htzh.htdxjk.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.AllBsatModel;
import com.htzh.htdxjk.entity.Bsat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htzh.htdxjk.entity.UserListModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface BsatMapper extends BaseMapper<Bsat> {

    @Select("SELECT\n" +
            "    b.*,\n" +
            "    u1.su_chinesename                                        zrrname,\n" +
            "    concat(u1.su_tele_phone_num, ',' , u1.su_work_phone_num) zrrdh,\n" +
            "    u2.su_chinesename                                        tdrname,\n" +
            "    concat(u2.su_tele_phone_num, ',' , u2.su_work_phone_num) tdrdh,\n" +
            "    b.bsat_lifeyear                                          sjsm,\n" +
            "    b.bsat_launch_time                                       fssj,\n" +
            "    CASE\n" +
            "        WHEN b.bsat_launch_time IS NULL\n" +
            "        OR  b.bsat_launch_time = \"\"\n" +
            "        THEN '待发射'\n" +
            "        WHEN TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-b.bsat_lifeyear*365<0\n" +
            "        THEN '未超寿'\n" +
            "        WHEN TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-b.bsat_lifeyear*365>0\n" +
            "        THEN CONCAT('超寿 ：', floor((TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-\n" +
            "            b.bsat_lifeyear*365)/365),'年',floor(mod((TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-\n" +
            "            b.bsat_lifeyear*365),365)/30),'月')\n" +
            "    END valuestatus\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_bsat b\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suser u1\n" +
            "ON\n" +
            "    b.bsat_admin_subs_id=u1.su_atpid\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suser u2\n" +
            "ON\n" +
            "    b.bsat_admin_id=u2.su_atpid\n" +
            "WHERE\n" +
            "    b.bsat_domain LIKE #{keyword}")
    List<AllBsatModel> selectAllSatListPage(Page page, @Param("keyword") String keyword);

    @Select("SELECT\n" +
            "    b.*,\n" +
            "    u1.su_chinesename                                        zrrname,\n" +
            "    concat(u1.su_tele_phone_num, ',' , u1.su_work_phone_num) zrrdh,\n" +
            "    u2.su_chinesename                                        tdrname,\n" +
            "    concat(u2.su_tele_phone_num, ',' , u2.su_work_phone_num) tdrdh,\n" +
            "    b.bsat_lifeyear                                          sjsm,\n" +
            "    b.bsat_launch_time                                       fssj,\n" +
            "    CASE\n" +
            "        WHEN b.bsat_launch_time IS NULL\n" +
            "        OR  b.bsat_launch_time = \"\"\n" +
            "        THEN '待发射'\n" +
            "        WHEN TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-b.bsat_lifeyear*365<0\n" +
            "        THEN '未超寿'\n" +
            "        WHEN TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-b.bsat_lifeyear*365>0\n" +
            "        THEN CONCAT('超寿 ：', floor((TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-\n" +
            "            b.bsat_lifeyear*365)/365),'年',floor(mod((TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-\n" +
            "            b.bsat_lifeyear*365),365)/30),'月')\n" +
            "    END valuestatus\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_bsat b\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suser u1\n" +
            "ON\n" +
            "    b.bsat_admin_subs_id=u1.su_atpid\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suser u2\n" +
            "ON\n" +
            "    b.bsat_admin_id=u2.su_atpid ")
    List<AllBsatModel> selectAllSatListPageNoKeyWord(Page page);



    AllBsatModel selectOneSatInfoById(@Param("atpid") String atpid);



    Map<String,Object> selectBySatId(@Param("satId") String satId);



    @Select("SELECT\n" +
            "    b.*,\n" +
            "    u1.su_chinesename                                        zrrname,\n" +
            "    concat(u1.su_tele_phone_num, ',' , u1.su_work_phone_num) zrrdh,\n" +
            "    u2.su_chinesename                                        tdrname,\n" +
            "    concat(u2.su_tele_phone_num, ',' , u2.su_work_phone_num) tdrdh,\n" +
            "    b.bsat_lifeyear                                          sjsm,\n" +
            "    b.bsat_launch_time                                       fssj,\n" +
            "    CASE\n" +
            "        WHEN b.bsat_launch_time IS NULL\n" +
            "        OR  b.bsat_launch_time = \"\"\n" +
            "        THEN '待发射'\n" +
            "        WHEN TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-b.bsat_lifeyear*365<0\n" +
            "        THEN '未超寿'\n" +
            "        WHEN TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-b.bsat_lifeyear*365>0\n" +
            "        THEN CONCAT('超寿 ：', floor((TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-\n" +
            "            b.bsat_lifeyear*365)/365),'年',floor(mod((TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-\n" +
            "            b.bsat_lifeyear*365),365)/30),'月')\n" +
            "    END valuestatus\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_bsat b\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suser u1\n" +
            "ON\n" +
            "    b.bsat_admin_subs_id=u1.su_atpid\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suser u2\n" +
            "ON\n" +
            "    b.bsat_admin_id=u2.su_atpid\n" +
            "WHERE\n" +
            "    b.bsat_code in ('${satids}')")
    List<AllBsatModel> selectAllSatListByIdsPage(Page page, @Param("satids") String satids);



    @Select("SELECT\n" +
            "    b.bsat_domain             domainName,\n" +
            "    group_concat(b.bsat_code) bsatCodes,\n" +
            "    b.bsat_last_modifed_time  lastupdatetime,\n" +
            "    b.bsat_atpcreateuser      createuser\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_bsat b\n" +
            "WHERE\n" +
            "    b.bsat_domain LIKE '%${domain}%' and \n" +
            "    b.bsat_domain <>''\n"+
            "GROUP BY\n" +
            "    b.bsat_domain")
    List<HashMap<String,String>> getBsatDomainsPage(Page page,@Param("domain") String domain);

    @Select("SELECT\n" +
            "    b.bsat_domain             domainName,\n" +
            "    group_concat(b.bsat_code) bsatCodes,\n" +
            "    b.bsat_last_modifed_time  lastupdatetime,\n" +
            "    b.bsat_atpcreateuser      createuser\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_bsat b\n" +
            "WHERE\n" +
            "    b.bsat_domain = '${domain}'\n" +
            "GROUP BY\n" +
            "    b.bsat_domain")
    List<HashMap<String,String>> getBsatDomain(@Param("domain") String domain);


    @Select("SELECT\n" +
            "    b.bsat_domain             domainName,\n" +
            "    group_concat(b.bsat_code) bsatCodes,\n" +
            "    b.bsat_last_modifed_time  lastupdatetime,\n" +
            "    b.bsat_atpcreateuser      createuser\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_bsat b\n" +
            "WHERE\n" +
            "    b.bsat_domain LIKE '%${domain}%'\n" +
            "GROUP BY\n" +
            "    b.bsat_domain")
    List<HashMap<String,String>> getBsatDomains(@Param("domain") String domain);


    @Select("SELECT\n" +
            "    b.*,\n" +
            "    u1.su_chinesename                                        zrrname,\n" +
            "    concat(u1.su_tele_phone_num, ',' , u1.su_work_phone_num) zrrdh,\n" +
            "    u2.su_chinesename                                        tdrname,\n" +
            "    concat(u2.su_tele_phone_num, ',' , u2.su_work_phone_num) tdrdh,\n" +
            "    b.bsat_lifeyear                                          sjsm,\n" +
            "    b.bsat_launch_time                                       fssj,\n" +
            "    CASE\n" +
            "        WHEN b.bsat_launch_time IS NULL\n" +
            "        OR  b.bsat_launch_time = \"\"\n" +
            "        THEN '待发射'\n" +
            "        WHEN TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-b.bsat_lifeyear*365<0\n" +
            "        THEN '未超寿'\n" +
            "        WHEN TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-b.bsat_lifeyear*365>0\n" +
            "        THEN CONCAT('超寿 ：', floor((TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-\n" +
            "            b.bsat_lifeyear*365)/365),'年',floor(mod((TIMESTAMPDIFF(DAY, b.bsat_launch_time, CURDATE())-\n" +
            "            b.bsat_lifeyear*365),365)/30),'月')\n" +
            "    END valuestatus\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_bsat b\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suser u1\n" +
            "ON\n" +
            "    b.bsat_admin_subs_id=u1.su_atpid\n" +
            "LEFT JOIN\n" +
            "    htdxjk.htdxjk_suser u2\n" +
            "ON\n" +
            "    b.bsat_admin_id=u2.su_atpid\n" +
            "WHERE\n" +
            "    b.bsat_code like '%${atpid}%' " +
//            "AND b.bsat_last_modifed_time>'${startTime}'\n" +
//            "AND b.bsat_last_modifed_time<'${endTime}' " +
            "")
    List<AllBsatModel>  selectSatInfoForEZUI(Page page,@Param("atpid") String atpid,@Param("startTime") String startTime,@Param("endTime") String endTime);


    List<AllBsatModel> findByParamEzuiForBsat(Map<String,Object> map);

    int countByParamEzuiForBsat(Map<String,Object> map);
    /**
     * 查询所有卫星的型号 by boris_deng
     * @return
     */
    List<Map<String,Object>>getSatelliteCodeList();

    /**
     * 根据卫星型号(code)更新卫星分类(domain) by boris_deng
     * @param map
     * @return
     */
    void updateSatelliteDomainByBsatAtpid(Map<String,Object> map);


    /**
     * 根据领域分类id 将bsat表的外键清空
     * @param map
     */
    void deleteSateDomainByDomainIds(Map<String,Object> map);

    /**
     *
     * @param map
     * @return
     */
    Map<String,Object> getSatelliteCodesByDomain(Map<String,Object> map);


    List<String> getDistDomain();

    List<Map<String, Object>> countBsat();

}
