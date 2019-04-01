package com.htzh.htdxjk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htzh.htdxjk.entity.Smodule;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.entity.UserListModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
public interface ISuserService extends IService<Suser> {

    public Suser findModulesByUserId(String userId);

    public List<Smodule> listMenuByUserid(String roleid);

    public List<Smodule> listDefaultMenu();

    public List<Map<String,Object>> getUnLoginedMenu();
    public List<Map<String,Object>> getLoginedMenu(String userid);

//    public List<UserListModel> selectUserListPage(String keyword);

    Page<UserListModel> getUserListPage(Page<UserListModel> page,String keyword);

    String getUserRoleNamesBySuserid(String userid);

     HashSet getDefultApi();

    HashSet getApiByUserid(Map map);

    List<String> findUserIdList();

    List<Map> getUserInfo(Map map);
}
