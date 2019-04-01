package com.htzh.htdxjk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Borbit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htzh.htdxjk.entity.UserListModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-01
 */
public interface IBorbitService extends IService<Borbit> {

    Page<HashMap<String,String>> getBorbitInfoList(Page<HashMap<String,String>> page, String satCode, String startTime, String endTime);

    List<Map> getBorbitInfo(Map map);
    int countBorbitInfo(Map map);

    Map getOneBorbitById(String myid);
}
