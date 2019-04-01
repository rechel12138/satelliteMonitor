package com.htzh.htdxjk.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.htzh.htdxjk.entity.AlarmInfoModel;
import com.htzh.htdxjk.entity.AlarmModel;
import com.htzh.htdxjk.entity.Bsjmxss;
import com.htzh.htdxjk.mapper.BdxznzdxtssbjMapper;
import com.htzh.htdxjk.mapper.BsjmxssMapper;
import com.htzh.htdxjk.mapper.BsjmxysMapper;
import com.htzh.htdxjk.service.IBsjmxssService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BsjmxssServiceImpl extends ServiceImpl<BsjmxssMapper, Bsjmxss> implements IBsjmxssService {

    @Autowired
    private BsjmxssMapper bsjmxssMapper;

    @Autowired
    private BsjmxysMapper bsjmxysMapper;

    @Autowired
    private BdxznzdxtssbjMapper bdxznzdxtssbjMapper;

    @Override
    public Map<String, Object> getBsjmxssBySatId(String satId) {
        Map<String,Object> map = new HashMap<>();
        map.put("satId",satId);
        List<Map<String, Object>> bsjmxssBySatId = bsjmxssMapper.getBsjmxssBySatId(map);
        map.put("list",bsjmxssBySatId);
        return map;
    }

    @Override
    public Map<String, Object> getBalarmfxByParma(Map<String,Object> map) {
        List<AlarmModel> bsjmxssByParam = bsjmxssMapper.getBsjmxssByParam(map);

        Map m = new HashMap();
        //如果当前页数和每页显示数据不等于0则进行分页了
        if((int)map.get("offset") != 0 && (int)map.get("rows") != 0){
            int count = bsjmxssMapper.getCount(map);
            m.put("total",count);
        }
        //m.put("total",bsjmxssByParam.size());
        m.put("rows",bsjmxssByParam);
        return m;
    }

    @Override
    public Map<String, Object> getAlarmByAtpId(String atpId, String beginTime, String endTime, String alarmType) {
        Map<String,Object> map = new HashMap<>();
        map.put("atpId",atpId);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("alarmType",alarmType);

        return null;
    }

    @Override
    public Map<String, Object> incrementReturnData(String alarmlevel, String satIds, String source, String satId, String alarmStartTime, String timeStamp) {
        Map<String,Object> map = new HashMap<>();
        map.put("alarmlevel",alarmlevel);
        map.put("source",source);
        if(!StrUtil.hasEmpty(satIds)){
            String[] strArray = satIds.split(",");
            Set<String> staffsSet = new HashSet<>(Arrays.asList(strArray));
            List<String> ayyayList = new ArrayList<>(staffsSet);
            map.put("satIds",ayyayList);
        }
        map.put("satId",satId);
        map.put("alarmStartTime",alarmStartTime);
        Date date = DateUtil.parse(timeStamp, "yyyyMMddHHmmss");
        timeStamp = DateUtil.formatDateTime(date);
        map.put("timeStamp",timeStamp);
        List<AlarmModel> bsjmxssByParam = bsjmxssMapper.incrementReturnData(map);
        Map<String,Object> resultMap = new HashMap();
        //m.put("total",bsjmxssByParam.size());
        resultMap.put("rows",bsjmxssByParam);

        return resultMap;
    }

    @Override
    public AlarmInfoModel getByAlarmIdAndSource(String alarmId, String source) {
        AlarmInfoModel alarmInfoModel = null;
        if("多星智能诊断系统实时报警".equals(source)){
            alarmInfoModel = bdxznzdxtssbjMapper.getByAtpId(alarmId);
        }else if("三级门限延时报警".equals(source)){
            alarmInfoModel = bsjmxysMapper.getByAtpId(alarmId);
        }else if("三级门限实时报警".equals(source)){
            alarmInfoModel = bsjmxssMapper.getByAtpId(alarmId);
        }else{
            //TODO
        }
        return alarmInfoModel;
    }
}
