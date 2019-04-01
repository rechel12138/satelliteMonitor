package com.htzh.htdxjk.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.AlarmModel;
import com.htzh.htdxjk.entity.AlarmStatisticalModel;
import com.htzh.htdxjk.entity.Balarmfx;
import com.htzh.htdxjk.entity.DutyAlarmStatisticalModel;
import com.htzh.htdxjk.mapper.BalarmfxMapper;
import com.htzh.htdxjk.service.IBalarmfxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htzh.htdxjk.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@Service
public class BalarmfxServiceImpl extends ServiceImpl<BalarmfxMapper, Balarmfx> implements IBalarmfxService {

    @Autowired
    private BalarmfxMapper balarmfxMapper;


    @Override
    public Map getBalarmfxByParma(String alarmlevel) {
        Map map = new HashMap();
        map.put("alarmlevel",alarmlevel);
        List<Map> balarmfxByParma = balarmfxMapper.getBalarmfxByParma(map);
        map.put("count",balarmfxByParma.size());
        map.put("list", balarmfxByParma);
        return map;
    }

    @Override
    public Page<Map> getBalarmfxUnion(Page page ,Map map) {
        String showQr=(String)map.get("showQr");

        if(showQr.equals("0")){
            return page.setRecords(balarmfxMapper.getBalarmfxUnion1(page,(String) map.get("keyword"),(String)map.get("startTime"),(String)map.get("endTime"),(String)map.get("levels"),(String)map.get("bsatCodes")));

        }else{
            return page.setRecords(balarmfxMapper.getBalarmfxUnion2(page,(String) map.get("keyword"),(String)map.get("startTime"),(String)map.get("endTime"),(String)map.get("levels"),(String)map.get("bsatCodes")));

        }

    }

    @Override
    public List<AlarmStatisticalModel> getStatistical(String userId, int dayNum) {
        Map hashMap = new HashMap();
        hashMap.put("userId",userId);
        hashMap.put("dayNum",dayNum);
        List<AlarmStatisticalModel> statistical = balarmfxMapper.getStatistical(hashMap);
        int count = balarmfxMapper.countBalarm(hashMap);
        Map map = new HashMap();
        //创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        List<AlarmStatisticalModel> list = new ArrayList<>();
        numberFormat.setMaximumFractionDigits(0);
        for (AlarmStatisticalModel a:statistical) {
            String result = numberFormat.format((float) Integer.parseInt(a.getCount()) / (float) count * 100);
            a.setCount(result+"%");
            list.add(a);
        }

        return list;
    }

    @Override
    public Map<String,Object> getBySatIdParamEzui(String satId, String beginTime, String endTime, String alarmType,String alarmSource) {
        Map<String,Object> map = new HashMap<>();
        map.put("satId",satId);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("alarmType",alarmType);
        map.put("alarmSource",alarmSource);
        List<Map<String, Object>> resultList = balarmfxMapper.getBySatIdParamEzui(map);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("rows",resultList);
        return resultMap;
    }

    @Override
    public Map<String, Object> findAlarmListByParam(int page, int rows, String keyWord, String userId,String alarmlevel,String startTime,String endTime,String sort,String order,String showQr,int dayNum,String param) {
        int offset = page*rows - rows;
        Map<String,Object> map = new HashMap<>();
        ArrayList<String> arrayList = new ArrayList<>();
        if(!StringUtils.isEmpty(alarmlevel)){
            String[] strArray = alarmlevel.split(",");
            arrayList =new ArrayList<String>(Arrays.asList(strArray));
        }
        map.put("alarmlevel",arrayList);
        map.put("offset",offset);
        map.put("rows",rows);
        map.put("keyWord",keyWord);
        map.put("userId",userId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("sort",sort);
        map.put("order",order);
        //1->显示未处理的报警 0->都显示
        map.put("showQr",showQr);
        map.put("dayNum",dayNum);
        if(!StrUtil.hasEmpty(param)){
            String[] strArray = param.split(",");
            Set<String> staffsSet = new HashSet<>(Arrays.asList(strArray));
            List<String> ayyayList = new ArrayList<>(staffsSet);
            map.put("param",ayyayList);
        }
        int count = balarmfxMapper.getCount(map);
        List<AlarmModel> list = balarmfxMapper.findAlarmListByParam(map);
        /*for (AlarmModel model:list) {
            if(model.equals()){

            }
        }*/
        Map<String,Object> result = new HashMap<>();
        result.put("total",count);
        result.put("rows",list);
        return result;
    }

    @Override
    public List<AlarmModel> findBySatIdAndSource(String satId, String source) {
        return null;
    }

    @Override
    public Balarmfx findByAlarmId(String alarmId) {
        return balarmfxMapper.findByAlarmId(alarmId);
    }

    @Override
    public Map<String, Object> alarmStatistical(int dayNum) {
        Map<String,Object> map = new HashMap<>();
        List<String> bsatCode = new ArrayList<>();
        List<Integer> alarmCount = new ArrayList<>();
        List<DutyAlarmStatisticalModel> alarmStatisticalModels = balarmfxMapper.alarmStatistical(dayNum);
        for (DutyAlarmStatisticalModel duty:alarmStatisticalModels) {
            bsatCode.add(duty.getBsatCode());
            alarmCount.add(duty.getNum());
        }
        map.put("bsatCode",bsatCode);
        map.put("count",alarmCount);

        return map;
    }

    @Override
    public List<Map> countZrrIndexType(Map map) {
        return balarmfxMapper.countZrrIndexType(map);
    }

    @Override
    public List<Map> getBjFlfxCount(Map map) {
        return balarmfxMapper.getBjFlfxCount(map);
    }
}
