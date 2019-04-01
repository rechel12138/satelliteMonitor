package com.htzh.htdxjk.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Borbit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-01
 */
public interface BorbitMapper extends BaseMapper<Borbit> {



    @Select("SELECT\n" +
            "    b.*,\n" +
            "    concat(b.borbit_satname,b.borbit_type,b.borbit_a,b.borbit_e,b.borbit_i,b.borbit_o,b.borbit_w,\n" +
            "    b.borbit_m) ewm\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_borbit b\n" +
            "WHERE\n" +
            "    b.borbit_atplastmodifydatetime>'${startTime}'\n" +
            "AND b.borbit_atplastmodifydatetime<'${endTime}'\n" +
            "AND b.borbit_satname LIKE '%${satCode}%'")
    List<HashMap<String,String>> getBorbitInfoList(Page page, @Param("satCode") String satCode, @Param("startTime") String startTime, @Param("endTime") String endTime);



    List<Map> getBorbitInfo(Map map);

    int countBorbitInfo(Map map);

    Map getOneBorbitById(String myid);
}
