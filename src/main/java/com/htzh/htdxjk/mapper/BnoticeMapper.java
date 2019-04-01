package com.htzh.htdxjk.mapper;

import com.htzh.htdxjk.entity.Bnotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-02
 */
public interface BnoticeMapper extends BaseMapper<Bnotice> {

    @Select("SELECT\n" +
            "\tbnte_desc as content\n" +
            "FROM\n" +
            "\thtdxjk_bnotice\n" +
            "WHERE\n" +
            "\ttimestampdiff(\n" +
            "\t\tHOUR,\n" +
            "\t\tbnte_atpcreatedatetime,\n" +
            "\t\tnow()\n" +
            "\t) < bnte_timelong\n" +
            "ORDER BY\n" +
            "\tbnte_atpcreatedatetime DESC")
    List<Map<String,Object>> getAvailableNoticeLists();
}
