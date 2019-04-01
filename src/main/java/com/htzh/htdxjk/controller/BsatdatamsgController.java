package com.htzh.htdxjk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htzh.htdxjk.entity.Bsatdatamsg;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.service.IBsatdatamsgService;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-06
 */
@RestController
@Api(value = "user-api", tags = {"卫星回放数据"})
@RequestMapping("/htdxjk/bsatdatamsg")
@Slf4j
public class BsatdatamsgController {

    @Autowired
    IBsatdatamsgService iBsatdatamsgService;

    /**
     * 根据登录用户查找下载过的文件列表
     * @author guoconglin
     * @DATE 2019/3/10 15:43
     */
    @ApiOperation(value = "查看已下载列表(完成)", notes = "查看已下载列表")
    @PostMapping(value = "getDownloadedFileList", produces = "application/json;charset=UTF-8")
    public String getDownloadedFileList(HttpSession httpSession){
        if(!Utils.checkLoginStatus(httpSession)){
            return Utils.makeJSONResponseMsg(0,"未登录,请登录",null);
        }
        //具体过程
        LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
        List<Bsatdatamsg> bsatdatamsg =iBsatdatamsgService.findByUserId(ls.getSUser().getSuAtpid());
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, bsatdatamsg);
    }

    /**
     * 删除回放数据下载记录
     * @param atpIds
     * @return
     */
    @ApiOperation(value = "删除回放数据下载记录(完成)",notes = "删除回放数据下载记录")
    @ApiImplicitParam(name = "atpIds",value = "atpId，多个用英文逗号隔开",required = true,dataType = "String")
    @GetMapping(value = "getDownloadedFileList",produces = "application/json;charset=UTF-8")
    public String removeById(@RequestParam(value = "atpIds",required = true) String atpIds){
        try {
            String[] idsStr = atpIds.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsStr));
            boolean b = iBsatdatamsgService.removeByIds(arrayList);
            if(b){
                return Utils.makeJSONResponseMsg(1,"删除成功",b);
            }else{
                return Utils.makeJSONResponseMsg(0,"删除失败",b);
            }
        }catch (Exception e){
           log.error(e.getMessage());
           log.error(e.getLocalizedMessage());
           return Utils.makeJSONResponseMsg(0,"删除失败",null);
        }
    }


}
