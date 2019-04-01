package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bsatset;
import com.htzh.htdxjk.mapper.BsatsetMapper;
import com.htzh.htdxjk.service.IBsatsetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-11
 */
@Service
public class BsatsetServiceImpl extends ServiceImpl<BsatsetMapper, Bsatset> implements IBsatsetService {

    @Autowired
    BsatsetMapper bsatsetMapper;

    @Override
    public List<Map<String,Object>> findByParam(String keyWord,String userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("keyWord",keyWord);
        List<Map<String,Object>> list = bsatsetMapper.findByParam(map);
        //不包含该用户，则该用户看不到该数据
        Iterator<Map<String,Object>> it = list.iterator();
        while(it.hasNext()){
            Map<String,Object> stringMap = it.next();
            Iterator<Map.Entry<String, Object>> it1 = stringMap.entrySet().iterator();
            while(it1.hasNext()){
                Map.Entry<String, Object> entry = it1.next();
                if(entry.getKey().equals("userIds") && !entry.getValue().toString().contains(userId))
                    it.remove();//使用迭代器的remove()方法删除元素
            }
            //it.remove();
        }
        return list;
    }

    @Override
    public List<Bsatset> findAllBsatSet() {
        return bsatsetMapper.findAllBsatSet();
    }

    @Override
    public Map<String, Object> getOneById(String atpId) {
        return bsatsetMapper.getOneById(atpId);
    }
}
