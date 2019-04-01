package com.htzh.htdxjk.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htzh.htdxjk.entity.Bnotice;
import com.htzh.htdxjk.service.IBnoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    IBnoticeService iBnoticeService;

    /**
     * 每天凌晨4点检查通知是否过期
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void checkTimeOutEveryDay() {
        QueryWrapper<Bnotice> queryWrapper = new QueryWrapper<>();
        List<Bnotice> bnoticeList = iBnoticeService.list(queryWrapper.ne("bnte_atpstatus", "DEL"));


        for (Bnotice bnotice : bnoticeList) {
            try {
                //初始时间
                String bnteAtplastmodifydatetime = bnotice.getBnteAtplastmodifydatetime();
                //显示时长
                String bnteTimelong = bnotice.getBnteTimelong();

                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");

                int bnteTimelongForLong =  Integer.parseInt(bnteTimelong);

                if (TimeUtil.calculateTimeDifferenceByCalendar(bnteAtplastmodifydatetime) <= bnteTimelongForLong) {
                    bnotice.setBnteAtpstatus("DEL");
                    iBnoticeService.updateById(bnotice);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

}