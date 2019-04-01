package com.htzh.htdxjk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Bmonthpre;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htzh.htdxjk.entity.BmonthpreSatModel;
import com.htzh.htdxjk.entity.UserListModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
public interface IBmonthpreService extends IService<Bmonthpre> {
    Page<BmonthpreSatModel> selectBmonthpreSatModelList(Page<BmonthpreSatModel> page, String satid);


    List<BmonthpreSatModel> getBySatIdAndSatCode(String satId,String satCode);
}
