package com.htzh.htdxjk.mapper;

import com.alibaba.druid.sql.visitor.functions.If;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.AlarmModel;
import com.htzh.htdxjk.entity.AlarmStatisticalModel;
import com.htzh.htdxjk.entity.Balarmfx;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htzh.htdxjk.entity.DutyAlarmStatisticalModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface BalarmfxMapper extends BaseMapper<Balarmfx> {

    List<Map> getBalarmfxByParma(Map map);




    @Select("SELECT\n" +
            "        *\n" +
            "        FROM\n" +
            "        (\n" +
            "        SELECT\n" +
            "        sat.bsat_id,\n" +
            "        sat.bsat_code,\n" +
            "        ys.bsjmxys_begintime bjsj,\n" +
            "        '三级门限延时' stype,\n" +
            "        ys.bsjmxys_responetime,\n" +
            "        ys.bsjmxys_endtime,\n" +
            "        ys.bsjmxys_alarmmsg,\n" +
            "        ys.bsjmxys_alarmvalue,\n" +
            "        ys.bsjmxys_alarmlevel,\n" +
            "        '' bjlb,\n" +
            "        '' cljg,\n" +
            "        fx.balarmfx_type,\n" +
            "        fx.balarmfx_response\n" +
            "        FROM\n" +
            "        htdxjk_bsjmxys ys\n" +
            "        LEFT JOIN\n" +
            "        htdxjk.htdxjk_bsat sat\n" +
            "        ON\n" +
            "        ys.bsjmxys_satid=sat.bsat_id\n" +
            "        LEFT JOIN\n" +
            "        htdxjk_balarmfx fx\n" +
            "        ON\n" +
            "        ys.bsjmxys_satid=fx.balarmfx_satid\n" +
            "        UNION ALL\n" +
            "        SELECT\n" +
            "        sat.bsat_id,\n" +
            "        sat.bsat_code,\n" +
            "        fx1.balarmfx_begintime bjsj,\n" +
            "        fx1.balarmfx_source,\n" +
            "        fx1.balarmfx_responetime,\n" +
            "        fx1.balarmfx_endtime,\n" +
            "        fx1.balarmfx_alarmmsg,\n" +
            "        fx1.balarmfx_alarmvalue,\n" +
            "        fx1.balarmfx_alarmlevel,\n" +
            "        fx1.balarmfx_type bjlb,\n" +
            "        fx1.balarmfx_response cljg,\n" +
            "        fx2.balarmfx_type,\n" +
            "        fx2.balarmfx_response\n" +
            "        FROM\n" +
            "        htdxjk_balarmfx fx1\n" +
            "        LEFT JOIN\n" +
            "        htdxjk_balarmfx fx2\n" +
            "        ON\n" +
            "        fx1.balarmfx_satid=fx2.balarmfx_satid\n" +
            "        LEFT JOIN\n" +
            "        htdxjk.htdxjk_bsat sat\n" +
            "        ON\n" +
            "        fx1.balarmfx_satid=sat.bsat_id\n" +
            "        WHERE\n" +
            "        fx1.balarmfx_source='三级门限实时'\n" +
            "        AND fx1.balarmfx_iden='预判'\n" +
            "        AND fx2.balarmfx_iden='实判'\n" +
            "        UNION ALL\n" +
            "        SELECT\n" +
            "        sat.bsat_id,\n" +
            "        sat.bsat_code,\n" +
            "        fx1.balarmfx_begintime bjsj,\n" +
            "        fx1.balarmfx_source,\n" +
            "        fx1.balarmfx_responetime,\n" +
            "        fx1.balarmfx_endtime,\n" +
            "        fx1.balarmfx_alarmmsg,\n" +
            "        fx1.balarmfx_alarmvalue,\n" +
            "        fx1.balarmfx_alarmlevel,\n" +
            "        fx1.balarmfx_type bjlb,\n" +
            "        fx1.balarmfx_response cljg,\n" +
            "        fx2.balarmfx_type,\n" +
            "        fx2.balarmfx_response\n" +
            "        FROM\n" +
            "        htdxjk_balarmfx fx1\n" +
            "        LEFT JOIN\n" +
            "        htdxjk_balarmfx fx2\n" +
            "        ON\n" +
            "        fx1.balarmfx_satid=fx2.balarmfx_satid\n" +
            "        LEFT JOIN\n" +
            "        htdxjk.htdxjk_bsat sat\n" +
            "        ON\n" +
            "        fx1.balarmfx_satid=sat.bsat_id\n" +
            "        WHERE\n" +
            "        fx1.balarmfx_source='多星智能诊断系统实时报警'\n" +
            "        AND fx1.balarmfx_iden='预判'\n" +
            "        AND fx2.balarmfx_iden='实判') tab\n" +
            "        WHERE\n" +
            "        tab.bsat_code IN ('${bsatCodes}')  " +
            "        AND tab.bjsj>='${startTime}'\n" +
            "        AND tab.bjsj<='${endTime}'\n" +
            "        and tab.bsjmxys_alarmlevel in ('${levels}')\n" +
            "        AND (\n" +
            "        tab.bsjmxys_alarmmsg LIKE '%${keyword}%'\n" +
            "        OR tab.balarmfx_response LIKE '%${keyword}%'\n" +
            "        OR tab.cljg LIKE '%${keyword}%')")
    List<Map> getBalarmfxUnion1(Page page, @Param("keyword") String keyword, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("levels") String levels, @Param("bsatCodes") String bsatCodes);


    @Select("SELECT\n" +
            "    *\n" +
            "FROM\n" +
            "    (\n" +
            "        SELECT\n" +
            "            sat.bsat_id,\n" +
            "            sat.bsat_code,\n" +
            "            ys.bsjmxys_begintime bjsj,\n" +
            "            '三级门限延时'             stype,\n" +
            "            ys.bsjmxys_responetime,\n" +
            "            ys.bsjmxys_endtime,\n" +
            "            ys.bsjmxys_alarmmsg,\n" +
            "            ys.bsjmxys_alarmvalue,\n" +
            "            ys.bsjmxys_alarmlevel,\n" +
            "            '' bjlb,\n" +
            "            '' cljg,\n" +
            "            fx.balarmfx_type,\n" +
            "            fx.balarmfx_response\n" +
            "        FROM\n" +
            "            htdxjk_bsjmxys ys\n" +
            "        LEFT JOIN\n" +
            "            htdxjk.htdxjk_bsat sat\n" +
            "        ON\n" +
            "            ys.bsjmxys_satid=sat.bsat_id\n" +
            "        LEFT JOIN\n" +
            "            htdxjk_balarmfx fx\n" +
            "        ON\n" +
            "            ys.bsjmxys_satid=fx.balarmfx_satid\n" +
            "        WHERE\n" +
            "            fx.balarmfx_alarmid IS NULL\n" +
            "        UNION ALL\n" +
            "        SELECT\n" +
            "            sat.bsat_id,\n" +
            "            sat.bsat_code,\n" +
            "            fx1.balarmfx_begintime bjsj,\n" +
            "            fx1.balarmfx_source,\n" +
            "            fx1.balarmfx_responetime,\n" +
            "            fx1.balarmfx_endtime,\n" +
            "            fx1.balarmfx_alarmmsg,\n" +
            "            fx1.balarmfx_alarmvalue,\n" +
            "            fx1.balarmfx_alarmlevel,\n" +
            "            fx1.balarmfx_type     bjlb,\n" +
            "            fx1.balarmfx_response cljg,\n" +
            "            fx2.balarmfx_type,\n" +
            "            fx2.balarmfx_response\n" +
            "        FROM\n" +
            "            htdxjk_balarmfx fx1\n" +
            "        LEFT JOIN\n" +
            "            htdxjk_balarmfx fx2\n" +
            "        ON\n" +
            "            fx1.balarmfx_satid=fx2.balarmfx_satid\n" +
            "        LEFT JOIN\n" +
            "            htdxjk.htdxjk_bsat sat\n" +
            "        ON\n" +
            "            fx1.balarmfx_satid=sat.bsat_id\n" +
            "        WHERE\n" +
            "            fx1.balarmfx_source='三级门限实时'\n" +
            "        AND fx1.balarmfx_iden='预判'\n" +
            "        AND fx2.balarmfx_alarmid is null\n" +
            "        UNION ALL\n" +
            "        SELECT\n" +
            "            sat.bsat_id,\n" +
            "            sat.bsat_code,\n" +
            "            fx1.balarmfx_begintime bjsj,\n" +
            "            fx1.balarmfx_source,\n" +
            "            fx1.balarmfx_responetime,\n" +
            "            fx1.balarmfx_endtime,\n" +
            "            fx1.balarmfx_alarmmsg,\n" +
            "            fx1.balarmfx_alarmvalue,\n" +
            "            fx1.balarmfx_alarmlevel,\n" +
            "            fx1.balarmfx_type     bjlb,\n" +
            "            fx1.balarmfx_response cljg,\n" +
            "            fx2.balarmfx_type,\n" +
            "            fx2.balarmfx_response\n" +
            "        FROM\n" +
            "            htdxjk_balarmfx fx1\n" +
            "        LEFT JOIN\n" +
            "            htdxjk_balarmfx fx2\n" +
            "        ON\n" +
            "            fx1.balarmfx_satid=fx2.balarmfx_satid\n" +
            "        LEFT JOIN\n" +
            "            htdxjk.htdxjk_bsat sat\n" +
            "        ON\n" +
            "            fx1.balarmfx_satid=sat.bsat_id\n" +
            "        WHERE\n" +
            "            fx1.balarmfx_source='多星智能诊断系统实时报警'\n" +
            "        AND fx1.balarmfx_iden='预判'\n" +
            "        AND fx2.balarmfx_alarmid is null ) tab\n" +
            "WHERE\n" +
            "    tab.bsat_code IN ( '${bsatCodes}' )\n" +
            "AND tab.bjsj>='${startTime}'\n" +
            "AND tab.bjsj<='${endTime}'\n" +
            "AND tab.bsjmxys_alarmlevel IN ('${levels}')\n" +
            "AND (\n" +
            "        tab.bsjmxys_alarmmsg LIKE '%${keyword}%'\n" +
            "    OR  tab.balarmfx_response LIKE '%${keyword}%'\n" +
            "    OR  tab.cljg LIKE '%${keyword}%' )")
    List<Map> getBalarmfxUnion2(Page page, @Param("keyword") String keyword, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("levels") String levels, @Param("bsatCodes") String bsatCodes);


    List<AlarmStatisticalModel> getStatistical(Map map);

    List<Map> getStatisticals();

    int countBalarm(Map map);

    List<Map<String, Object>> getBySatIdParamEzui(Map map);

    List<AlarmModel> findAlarmListByParam(Map<String,Object> map);

    int getCount(Map<String,Object> map);

    Balarmfx findByAlarmId(@Param("alarmId") String alarmId);

    List<DutyAlarmStatisticalModel> alarmStatistical(@Param("dayNum") int dayNum);


    List<Map> countZrrIndexType(Map map);

    List<Map> getBjFlfxCount(Map map);

}
