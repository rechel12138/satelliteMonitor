package com.htzh.htdxjk.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Bckplan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htzh.htdxjk.entity.UserListModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface BckplanMapper extends BaseMapper<Bckplan> {

    List<Bckplan> findBySatId(String satId);


    List<Map> getBckPlanInfo(Map map);

    int countBckPlanInfo(Map map);

    Map getOneBckById(String myid);
}
