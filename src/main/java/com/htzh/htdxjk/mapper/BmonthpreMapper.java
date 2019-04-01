package com.htzh.htdxjk.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Bmonthpre;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htzh.htdxjk.entity.BmonthpreSatModel;
import com.htzh.htdxjk.entity.UserListModel;
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
public interface BmonthpreMapper extends BaseMapper<Bmonthpre> {

    @Select("SELECT\n" +
            "    bm.bmonthpre_atpid,\n" +
            "    bm.bmonthpre_atpcreatedatetime,\n" +
            "    bm.bmonthpre_atpcreateuser,\n" +
            "    bm.bmonthpre_atplastmodifydatetime,\n" +
            "    bm.bmonthpre_atplastmodifyuser,\n" +
            "    bm.bmonthpre_atpstatus,\n" +
            "    bm.bmonthpre_atpsort,\n" +
            "    bm.bmonthpre_atpdotype,\n" +
            "    bm.bmonthpre_atpremark,\n" +
            "    bm.bmonthpre_id,\n" +
            "    bm.bmonthpre_btime,\n" +
            "    bm.bmonthpre_etime,\n" +
            "    bm.bmonthpre_satellite,\n" +
            "    bm.bmonthpre_mstype,\n" +
            "    bm.bmonthpre_span,\n" +
            "    bs.bsat_code\n" +
            "FROM\n" +
            "    htdxjk.htdxjk_bmonthpre bm ,\n" +
            "    htdxjk.htdxjk_bsat bs\n" +
            "WHERE\n" +
            "    bm.bmonthpre_id=bs.bsat_id\n" +
            "AND bs.bsat_code=#{satid}")
    List<BmonthpreSatModel> selectBmonthpreSatModelList(Page page, @Param("satid") String satid);


    List<BmonthpreSatModel> findBySatIdOrSatCode(Map map);
}
