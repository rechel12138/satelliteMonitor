package com.htzh.htdxjk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.*;
import com.htzh.htdxjk.service.IBusualfileService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-02
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"常用文件"})
@RequestMapping("/api/busualfile")
public class BusualfileController {


    @Autowired
    private IBusualfileService iBusualfileService;

    @Autowired
    private IBfileshareService iBfileshareService;

    @Autowired
    private ISuserroleService iSuserroleService;

    @Autowired
    private IBusualFileTopService iBusualFileTopService;


    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${role.adminId}")
    private String roleAdminId;

    private static String IS_ADMIN = "是";

    private static String IS_NO_ADMIN = "否";


    /**
     * 查询
     *
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @ApiOperation(value = "查询文件名称(完成)", notes = "查询文件名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @PostMapping(value = "listBusualfile", produces = "application/json;charset=UTF-8")
    public String listBusualfile(@RequestParam(value = "page", defaultValue = "1") String page,
                                 @RequestParam(value = "rows", defaultValue = "10") String rows,
                                 @RequestParam(value = "sort", defaultValue = "buf_atplastmodifydatetime") String sort,
                                 @RequestParam(value = "order", defaultValue = "desc") String order) {
        try {
            IPage<Busualfile> busualfilePage = null;
            Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
            String asc = "asc";
            String desc = "desc";
            if (order.equals(asc)) {
                modelPage.setAsc(sort);
            } else if (order.equals(desc)) {
                modelPage.setDesc(sort);
            } else {
                Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
            }
            busualfilePage = iBusualfileService.page(modelPage);
            if (busualfilePage.getRecords().size() > 0) {
                List<Busualfile> list = new ArrayList<>();
                for (Busualfile busualfile : busualfilePage.getRecords()) {
                    busualfile.setBufFilelocation(uploadFolder + busualfile.getBufFilelocation());
                    list.add(busualfile);
                }
                busualfilePage.setRecords(list);
            }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rows", busualfilePage.getRecords());
            resultMap.put("total", busualfilePage.getTotal());
            return JSON.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "查询失败", null);
        }
    }

    /**
     * 下载文件
     *
     * @param res
     * @param atpId
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "下载文件(完成)", notes = "下载文件")
    @ApiImplicitParam(name = "atpId", value = "主键Id", dataType = "String")
    @GetMapping(value = "downBusualfile", produces = "application/json;charset=UTF-8")
    public String downBusualfile(HttpServletResponse res, @RequestParam("atpId") String atpId) throws UnsupportedEncodingException {
        // 通过查找文件信息
        Busualfile busualfile = iBusualfileService.getById(atpId);
        log.info("busualfile-->{}", busualfile);
        if (busualfile == null) {
            return Utils.makeJSONResponseMsg(0, "文件名不存在", null);
        }
        InputStream fis = null;
        try {
            // path是指欲下载的文件的路径。
            File file = new File(uploadFolder + busualfile.getBufFilelocation());
            if (file.exists()) {
                // 取得文件名。
                String filename = file.getName();

                // 以流的形式下载文件。
                fis = new BufferedInputStream(new FileInputStream(uploadFolder + busualfile.getBufFilelocation()));
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
            return Utils.makeJSONResponseMsg(0, "文件不存在", null);

        } catch (IOException ex) {
            ex.printStackTrace();
            return Utils.makeJSONResponseMsg(0, "下载失败", null);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 上传
     *
     * @param multipartFile
     * @param httpSession
     * @return
     */
    @ApiOperation(value = "上传文件(完成)", notes = "上传")
    @PostMapping(value = "uploadBusualfile", consumes = "multipart/*", headers = "content-type=multipart/form-data", produces = "application/json;charset=UTF-8")
    public String uploadBusualfile(@RequestParam("multipartFile") MultipartFile multipartFile, HttpSession httpSession) {


        if (multipartFile.isEmpty()) {
            return Utils.makeJSONResponseMsg(0, "文件为空", null);
        }
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        log.info("上传文件名为：" + fileName);
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("文件的后缀名为：" + suffixName);
        //重命名文件名
        String newFileName = fileName.substring(0, fileName.indexOf(".")) + System.currentTimeMillis();
        String fileNewName = newFileName + suffixName;
        //创建文件目录
        File src = new File(uploadFolder + fileNewName);
        //检查文件目录是否存在
        if (!src.getParentFile().exists()) {
            src.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(src);
            Busualfile busualfile = new Busualfile();
            busualfile.setBufAtplastmodifydatetime(Utils.getNow());
            busualfile.setBufAtpcreatedatetime(Utils.getNow());
            busualfile.setBufAtpid(UUID.randomUUID().toString());
            busualfile.setBufFilelocation(fileNewName);
            busualfile.setBufFilepostfix(suffixName);
            busualfile.setBufName(fileName.substring(0, fileName.indexOf(".")));
            busualfile.setBufUpdatetime(Utils.getNow());
            iBusualfileService.save(busualfile);
            return Utils.makeJSONResponseMsg(1, "上传成功", null);
        } catch (IOException e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "上传失败", null);
        }
    }

    /**
     * 添加到分享区
     *
     * @param atpIds
     * @param httpSession
     * @return
     */
    @ApiOperation(value = "常用文件添加到分享区(完成)", notes = "常用文件添加到分享区")
    @ApiImplicitParam(name = "atpIds", value = "atpIds ，多个用英文逗号分割", required = true, dataType = "String")
    @PostMapping(value = "addBusualfile2Share", produces = "application/json;charset=UTF-8")
    public String addBusualfile(@RequestParam("atpIds") String atpIds, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String[] idsArray = atpIds.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            List<Busualfile> listByIds = iBusualfileService.findListByIds(arrayList);
            List<Bfileshare> bfileshareList = new ArrayList<>();
            for (Busualfile busualfile : listByIds) {
                Bfileshare bfileshare = new Bfileshare();
                bfileshare.setBfsAtpid(UUID.randomUUID().toString());
                bfileshare.setBfsAtplastmodifydatetime(Utils.getNow());
                bfileshare.setBfsAtpcreatedatetime(Utils.getNow());
                bfileshare.setBfsAtpcreateuser(ls.getSUser().getSuAccount());
                bfileshare.setBfsFilelocation("/" + busualfile.getBufFilelocation());
                bfileshare.setBfsFilepostfix(busualfile.getBufFilepostfix());
                bfileshare.setBfsName(busualfile.getBufName());
                bfileshare.setBfsUpdatetime(Utils.getNow());
                bfileshareList.add(bfileshare);
            }
            boolean b = iBfileshareService.saveBatch(bfileshareList);
            if (b) {
                return Utils.makeJSONResponseMsg(1, "添加分享区成功", b);
            }
            return Utils.makeJSONResponseMsg(0, "添加分享区失败", b);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "添加分享区失败", null);
        }
    }

    /**
     * 修改
     */
    @PostMapping(value = "updateBusualfile", produces = "application/json;charset=UTF-8")
    public String updateFileshare(Busualfile inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBufAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBufAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBusualfileService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }


    /**
     * 删除
     */
    @PostMapping(value = "removeBusualfile", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除（完成）", notes = "批量删除")
    @ApiImplicitParam(name = "busualfileAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBusualfile(@RequestParam("busualfileAtpid") String busualfileAtpid) {
        try {
            String[] idsArray = busualfileAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            boolean b = iBusualfileService.removeByIds(arrayList);
            if (b) {
                return Utils.makeJSONResponseMsg(1, "删除成功", b);
            }
            return Utils.makeJSONResponseMsg(0, "删除失败", b);

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
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


        Busualfile obj = new Busualfile();
        //筛选字段list
        List list = new ArrayList();

        list.add("buf_atpcreateuser");
        list.add("buf_atpstatus");
        list.add("buf_atpsort");
        list.add("buf_atpdotype");
        list.add("buf_atpremark");
        list.add("buf_name");
        list.add("buf_filepostfix");
        list.add("buf_filelocation");
        list.add("buf_updatetime");

        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBusualfileService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }


    /**
     * 根据atpid获取一条信息
     *
     * @param atpId
     * @return
     */
    @ApiOperation(value = "根据atpid获取一条信息(完成)", notes = "根据atpid获取一条信息")
    @PostMapping(value = "/getOneById", produces = "application/json;charset=UTF-8")
    public String getOneById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            Busualfile busualfile = iBusualfileService.getById(atpId);
            if (busualfile == null) {
                return Utils.makeJSONResponseMsg(0, "该文件不存在了", null);
            }
            busualfile.setBufFilelocation(uploadFolder + busualfile.getBufFilelocation());
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, busualfile);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 常用文件置顶
     *
     * @param atpIds
     * @return
     */
    @ApiOperation(value = "常用文件置顶，根据所选atpId(完成)", notes = "常用文件置顶，根据所选atpId")
    @PostMapping(value = "/topBusualfile", produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "atpIds", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String topBusualfile(@RequestParam(value = "atpIds") String atpIds, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            //验证登陆用户所属角色
            List<String> stringByUserId = iSuserroleService.getUserRoleIdStringByUserId(ls.getSUser().getSuAtpid());
            String[] idsArray = atpIds.split(",");
            List<BusualFileTop> busualFileTopList = new ArrayList<>();
            for (String str : idsArray) {
                BusualFileTop busualFileTop = new BusualFileTop();
                busualFileTop.setBuftAtpid(UUID.randomUUID().toString());
                busualFileTop.setBuftAtpcreatedatetime(Utils.getNow());
                busualFileTop.setBuftAtplastmodifydatetime(Utils.getNow());
                busualFileTop.setBuftAtpcreateuser(ls.getSUser().getSuAccount());
                busualFileTop.setBuftAtplastmodifyuser(ls.getSUser().getSuAccount());
                busualFileTop.setBuftFksuserid(ls.getSUser().getSuAtpid());
                busualFileTop.setBuftFkbusualfileid(str);
                busualFileTop.setBuftSeq(0);
                //判断 所属角色中是否包含管理员角色ID
                if (stringByUserId.contains(roleAdminId)) {
                    busualFileTop.setBuftAdmin(IS_ADMIN);
                } else {
                    busualFileTop.setBuftAdmin(IS_NO_ADMIN);
                }
                busualFileTopList.add(busualFileTop);
            }
            boolean b = iBusualFileTopService.saveBatch(busualFileTopList);
            if (b) {
                return Utils.makeJSONResponseMsg(1, "置顶成功", b);
            }
            return Utils.makeJSONResponseMsg(1, "置顶失败", b);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "置顶失败", null);
        }
    }
}
