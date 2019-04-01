package com.htzh.htdxjk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.mapper.SuserMapper;
import com.htzh.htdxjk.service.ISmoduleService;
import com.htzh.htdxjk.service.ISuserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@Slf4j
@Service
public class SuserServiceImpl extends ServiceImpl<SuserMapper, Suser> implements ISuserService {
    @Autowired
    SuserMapper suserMapper;

    @Autowired
    ISmoduleService smoduleService;


    @Override
    public Suser findModulesByUserId(String userId) {
        Suser user = suserMapper.selectById(userId);


        List<Srole> roles = suserMapper.getUserRoles(userId);
        for (Srole role : roles) {
            role.setPermissions(suserMapper.getRoleSmodules(role.getSrAtpid()));
        }
        user.setRoles(roles);
        return user;
    }

    @Override
    public List<Smodule> listMenuByUserid(String userid) {
        List<Smodule> menuList;
        List<Smodule> newMenuList = new ArrayList<>();
        try {
            //1、根据角色获得所有的菜单（包括一级和二级）
            menuList = suserMapper.getRoleSmodules(userid);
//            menuList=suserMapper.getMenusByUserid(userid);
            for (int i = 0; i < menuList.size(); i++) {
                Smodule menu = menuList.get(i);
                List<Smodule> childMenuList = new ArrayList<>();
                //2、拼装二级菜单
                if (menu.getSmFksmoduleid().equals("")) {
                    for (int j = 0; j < menuList.size(); j++) {

                        if (menu.getSmAtpid().equals(menuList.get(j).getSmFksmoduleid())) {
                            childMenuList.add(menuList.get(j));
                        }
                    }
                    menu.setChildSmodule(childMenuList);
                    newMenuList.add(menu);
                }
            }
        } catch (Exception e) {
            log.error("【后台菜单获取失败】，cause:{}", e);
        }
        return newMenuList;
    }

    @Override
    public List<Smodule> listDefaultMenu() {

        List<Smodule> menuList;
        List<Smodule> newMenuList = new ArrayList<>();
        Smodule smodule = new Smodule();
        smodule.setSmIslogineddisplay(0);
        try {
            //1、根据角色获得所有的菜单（包括一级和二级）
//            menuList = smoduleService.list();
            QueryWrapper<Smodule> smoduleQueryWrapperw = new QueryWrapper<Smodule>(smodule);
            menuList = smoduleService.list(smoduleQueryWrapperw);
            for (int i = 0; i < menuList.size(); i++) {
                Smodule menu = menuList.get(i);
                List<Smodule> childMenuList = new ArrayList<>();
                //2、拼装二级菜单
                if (menu.getSmFksmoduleid().equals("")) {
                    for (int j = 0; j < menuList.size(); j++) {

                        if (menu.getSmAtpid().equals(menuList.get(j).getSmFksmoduleid())) {
                            childMenuList.add(menuList.get(j));
                        }
                    }
                    menu.setChildSmodule(childMenuList);
                    newMenuList.add(menu);
                }
            }
        } catch (Exception e) {
            log.error("【后台菜单获取失败】，cause:{}", e);
        }
        return newMenuList;

    }

    @Override
    public List<Map<String, Object>> getUnLoginedMenu() {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Smodule> menuList = new ArrayList<Smodule>();
        Smodule smodule = new Smodule();
        //查询不需要登录显示的菜单
        //TODO :1 不需要登录 0需要登录
        smodule.setSmIslogineddisplay(1);
        QueryWrapper<Smodule> smoduleQueryWrapperw = new QueryWrapper<Smodule>(smodule);
        menuList = smoduleService.list(smoduleQueryWrapperw);
        //获取一级菜单
        for (Smodule item : menuList) {

            if (StringUtils.isEmpty(item.getSmFksmoduleid())) {

                Map<String, Object> menu = new HashMap<>();
                menu.put("title", item.getSmName());
                menu.put("icon", item.getSmIcon() == null ? "" : item.getSmIcon());
                menu.put("isCurrent", "0".equals(item.getSmIscurrent()) ? false : true);
               /* menu.put("action",item.getSmAction());
                menu.put("id",item.getSmAtpid());
                menu.put("islogineddisplay",item.getSmIslogineddisplay());*/

                Smodule sm = new Smodule();
                sm.setSmFksmoduleid(item.getSmAtpid());
                sm.setSmIslogineddisplay(1);
                QueryWrapper<Smodule> qw = new QueryWrapper<Smodule>(sm);
                List<Smodule> secondMenu = smoduleService.list(qw);
                List<Map<String, Object>> semenuList = new ArrayList<Map<String, Object>>();
                for (Smodule seitem : secondMenu) {

                    Map<String, Object> semenu = new HashMap<>();
                    semenu.put("title", seitem.getSmName());
                    semenu.put("isCurrent", "0".equals(seitem.getSmIscurrent()) ? false : true);
                    semenu.put("href", seitem.getSmWebpath());
                    semenu.put("action", seitem.getSmAction());
                    semenu.put("id", seitem.getSmAtpid());
                    semenu.put("islogineddisplay", seitem.getSmIslogineddisplay());
                    semenu.put("icon", seitem.getSmIcon());
                    semenuList.add(semenu);
                }

                menu.put("children", semenuList);

                result.add(menu);
            }
        }

        return result;
    }


    @Override
    public List<Map<String, Object>> getLoginedMenu(String userid) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Smodule> menuList;
        //1、根据userid获取一级菜单
        menuList = suserMapper.getFatherMenusByUserid(userid);
        //根据一级菜单 查二级菜单
        for (Smodule item : menuList) {

            if (StringUtils.isEmpty(item.getSmFksmoduleid())) {

                Map<String, Object> menu = new HashMap<>();
                menu.put("title", item.getSmName());
                menu.put("icon", item.getSmIcon() == null ? "" : item.getSmIcon());
                menu.put("isCurrent", "0".equals(item.getSmIscurrent()) ? false : true);
                //查询子菜单
                List<SonMenuListModel> secondMenu = suserMapper.getSonMenusByUseridFkid(userid, item.getSmAtpid());
                List<Map<String, Object>> semenuList = new ArrayList<Map<String, Object>>();
                for (SonMenuListModel seitem : secondMenu) {

                    Map<String, Object> semenu = new HashMap<>();
                    semenu.put("title", seitem.getSmName());
                    semenu.put("isCurrent", "0".equals(seitem.getSmIscurrent()) ? false : true);
                    semenu.put("href", seitem.getSmWebpath());

                    if (StringUtils.isNotEmpty(seitem.getSmAction())) {

                        String[] actionsArray = seitem.getSmAction().split(",");
                        Set set = new HashSet();
                        for (int i = 0; i < actionsArray.length; i++) {
                            set.add(actionsArray[i]);
                        }
                        Object[] actionsArray1 = set.toArray();
                        StringBuffer action = new StringBuffer("");
                        for (int j = 0; j < actionsArray1.length; j++) {
                            if (j == actionsArray1.length - 1) {
                                action.append(actionsArray1[j]);
                            } else {
                                action.append(actionsArray1[j]).append(",");
                            }
                        }

                        seitem.setSmAction(action.toString());

                    }

                    semenu.put("action", seitem.getSmAction());
                    semenu.put("id", seitem.getSmAtpid());
                    semenu.put("islogineddisplay", seitem.getSmIslogineddisplay());
                    semenu.put("icon", seitem.getSmIcon());
                    semenuList.add(semenu);
                }

                menu.put("children", semenuList);

                result.add(menu);
            }
        }

        return result;
    }

//    @Override
//    public List<UserListModel> selectUserListPage(String keyword) {
//
//        List<UserListModel> result =suserMapper.selectUserListPage( keyword);
//        return result;
//    }


    @Override
    public Page<UserListModel> getUserListPage(Page<UserListModel> page, String keyword) {


        return page.setRecords(suserMapper.selectUserListPage(page, keyword));
    }


    @Override
    public String getUserRoleNamesBySuserid(String userid) {
        return suserMapper.getUserRoleNamesBySuserid(userid);
    }

    public static void main(String[] args) throws Exception {
        String str = "1,2,2,3,4";
        String[] array = str.split(str);

        Set set = new HashSet();
        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }

        Object[] objects = set.toArray();


    }
    @Override
    public HashSet getDefultApi(){
        return suserMapper.getDefultAPI();
    }

    @Override
    public HashSet getApiByUserid(Map map) {
        return suserMapper.getApiByUserid(map);
    }

    @Override
    public List<String> findUserIdList() {
        return suserMapper.findUserIdList();
    }

    @Override
    public List<Map> getUserInfo(Map map) {
        return suserMapper.getUserInfo(map);
    }
}
