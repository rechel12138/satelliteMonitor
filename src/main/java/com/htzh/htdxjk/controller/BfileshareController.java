package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Bfileshare;
import com.htzh.htdxjk.entity.Busualfile;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.service.IBfileshareService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-02
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"文件分享"})
@RequestMapping("/api/bfileshare")
public class BfileshareController {


    @Autowired
    private IBfileshareService iBfileshareService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;


    //查询
    @ApiOperation(value = "查询文件名称(完成)", notes = "查询")
    @PostMapping(value = "listBfileshare",produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    public String listBfileshare(@RequestParam(value = "page", defaultValue = "1") String page,
                                 @RequestParam(value = "rows", defaultValue = "10") String rows,
                                 @RequestParam(value = "sort", defaultValue = "bfs_atplastmodifydatetime") String sort,
                                 @RequestParam(value = "order", defaultValue = "desc") String order) {
        try {
            IPage<Bfileshare> busualfilePage = null;
            Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
            if(order.equals("asc")){
                modelPage.setAsc(sort);
            }else if(order.equals("desc")){
                modelPage.setDesc(sort);
            }else {
                Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
            }
            busualfilePage = iBfileshareService.page(modelPage);
            if(busualfilePage.getRecords().size()>0){
                List<Bfileshare> list = new ArrayList<>();
                for (Bfileshare bfileshare:busualfilePage.getRecords()){
                    bfileshare.setBfsFilelocation(uploadFolder+bfileshare.getBfsFilelocation());
                    list.add(bfileshare);
                }
                busualfilePage.setRecords(list);
            }
            return JSON.toJSONString(busualfilePage, SerializerFeature.WriteMapNullValue).replace("records", "rows");
        }catch (Exception e){
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0,"查询失败",null);
        }
    }

    //下载文件
    @ApiOperation(value = "下载文件(完成)", notes = "下载文件")
    @ApiImplicitParam(name = "atpId",value = "主键Id",dataType = "String")
    @GetMapping(value = "downBfileshare", produces = "application/json;charset=UTF-8")
    public String downBfileshare(HttpServletResponse res, @RequestParam("atpId") String atpId) throws UnsupportedEncodingException {
        // 通过查找文件信息
        Bfileshare bfileshare = iBfileshareService.getById(atpId);
        log.info("bfileshare-->{}", bfileshare);
        if (StringUtils.isEmpty(bfileshare)) {
            return Utils.makeJSONResponseMsg(0, "文件名不存在", null);
        }
        InputStream fis = null;
        try{
            //判断文件是否存在
            File file = new File(uploadFolder+ bfileshare.getBfsFilelocation());
            if(file.exists()){
                // 取得文件名。
                String filename = file.getName();

                // 以流的形式下载文件。
                fis = new BufferedInputStream(new FileInputStream(uploadFolder+bfileshare.getBfsFilelocation()));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                res.reset();
                // 设置response的Header
                res.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
                res.addHeader("Content-Length", "" + file.length());
                OutputStream toClient = new BufferedOutputStream(res.getOutputStream());
                res.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
                return Utils.makeJSONResponseMsg(1, "下载成功", res);
            }
            return Utils.makeJSONResponseMsg(0,"文件不存在",null);

      } catch (IOException ex) {
        ex.printStackTrace();
        return Utils.makeJSONResponseMsg(0, "下载失败", null);
     }finally {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       }
    }

    //查询
    @ApiOperation(value = "上传文件(完成)", notes = "上传")
    @PostMapping(value = "uploadBfileshare",consumes = "multipart/*",headers = "content-type=multipart/form-data",produces = "application/json;charset=UTF-8")
    public String uploadFileshare(@RequestParam("multipartFile") MultipartFile multipartFile, HttpSession httpSession) {

        //检查权限
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        String userId = ls.getSUser().getSuAtpid();
        if(multipartFile.isEmpty()){
            return Utils.makeJSONResponseMsg(0,"文件为空",null);
        }
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        log.info("上传文件名为："+fileName);
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("文件的后缀名为："+suffixName);
        //重命名文件名
        String newFileName=fileName.substring(0, fileName.indexOf("."))+System.currentTimeMillis();
        String fileNewName = newFileName+suffixName;
        //创建文件目录
        File src = new File(uploadFolder+fileNewName);
        //检查文件目录是否存在
        if(!src.getParentFile().exists()){
            src.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(src);
            Bfileshare bfileshare = new Bfileshare();
            bfileshare.setBfsAtpid(UUID.randomUUID().toString());
            bfileshare.setBfsUpdatetime(Utils.getNow());
            bfileshare.setBfsAtpcreateuser(userId);
            bfileshare.setBfsAtplastmodifyuser(userId);
            bfileshare.setBfsAtpcreatedatetime(Utils.getNow());
            bfileshare.setBfsAtplastmodifydatetime(Utils.getNow());
            bfileshare.setBfsName(fileName.substring(0, fileName.indexOf(".")));
            bfileshare.setBfsFilepostfix(suffixName);
            bfileshare.setBfsFilelocation(fileNewName);
            boolean save = iBfileshareService.save(bfileshare);
            if(save){
                return Utils.makeJSONResponseMsg(1, "上传成功", save);
            }
            return Utils.makeJSONResponseMsg(0, "上传失败", save);
        } catch (IOException e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "上传失败", null);
        }
    }

    /**      * 增加     */
    @PostMapping(value = "addBfileshare",produces = "application/json;charset=UTF-8")
    public String addFileshare(Bfileshare inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBfsAtpid(UUID.randomUUID().toString());
            inputEntity.setBfsAtpcreatedatetime(Utils.getNow());
            inputEntity.setBfsAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBfsAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBfsAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBfileshareService.save(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**      * 修改      */
    @PostMapping(value = "updateBfileshare",produces = "application/json;charset=UTF-8")
    public String updateFileshare(Bfileshare inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBfsAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBfsAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBfileshareService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }



    /**      * 删除      */
    @PostMapping(value = "removeBfileshare",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除(完成)", notes = "批量删除")
    @ApiImplicitParam(name = "bfileshareAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBfileshare(@RequestParam("bfileshareAtpid") String bfileshareAtpid) {
        try {
            String[] idsArray=bfileshareAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            boolean b = iBfileshareService.removeByIds(arrayList);
            if(b){
                return Utils.makeJSONResponseMsg(1, "删除成功", b);
            }
            return Utils.makeJSONResponseMsg(0, "删除失败", b);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.DEL_FAIL_STATUS, ResultTo.DEL_FAIL_STATUS_MSG, null);
        }
    }

    @ApiOperation(value = "根据关键字查询信息", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })

    @PostMapping(value = "findByParamEzui", produces = "application/json;charset=UTF-8")
    public String findByParamEzui(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "page", defaultValue = "1") String page,
                                  @RequestParam(value = "rows", defaultValue = "10") String rows,
                                  @RequestParam(value = "sort", defaultValue = "bfs_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bfileshare obj = new Bfileshare();
        //筛选字段list
        List list = new ArrayList();

        list.add("bfs_atpcreateuser");
        list.add("bfs_atpstatus");
        list.add("bfs_atpsort");
        list.add("bfs_atpdotype");
        list.add("bfs_atpremark");
        list.add("bfs_name");
        list.add("bfs_filepostfix");
        list.add("bfs_filelocation");
        list.add("bfs_updatetime");



        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBfileshareService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }

    /**
     * 根据atpid获取一条信息
     * @param atpId
     * @return
     */
    @ApiOperation(value = "根据atpid获取一条信息(文件分享预览)", notes = "根据atpid获取一条信息(文件分享预览)")
    @PostMapping(value = "/getOneById", produces = "application/json;charset=UTF-8")
    public String getOneById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            Bfileshare bfileshare = iBfileshareService.getById(atpId);
            if(bfileshare == null){
                return Utils.makeJSONResponseMsg(1, "该文件不存在了", null);
            }
            bfileshare.setBfsFilelocation(uploadFolder+bfileshare.getBfsFilelocation());
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, bfileshare);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

}
