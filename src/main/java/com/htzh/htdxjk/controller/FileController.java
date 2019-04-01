package com.htzh.htdxjk.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htzh.htdxjk.entity.Bsat;
import com.htzh.htdxjk.entity.Bsatdatamsg;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.service.IBsatService;
import com.htzh.htdxjk.service.IBsatdatamsgService;
import com.htzh.htdxjk.service.IFtpService;
import com.htzh.htdxjk.utils.FtpUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;


/**
 * @author Rechel
 * @date 2019/2/26
 */
@Api(value = "user-api", tags = {"文件上传下载"})
@RequestMapping("/api/file")
@RestController
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);


    @Value("${myftp.localPath}")
    private String localPath;


    @Autowired
    IFtpService ftpService;

    @Autowired
    IBsatService iBsatService;

    @Autowired
    IBsatdatamsgService iBsatdatamsgService;

    @Value("${myftp.remotePath}")
    private String remotePath;


    /**
     * 文件上传
     *
     * @param file     上传的文件
     * @param filePath 上传到哪个路径
     * @return
     */
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping(value = "/upload", produces = "application/json;charset=UTF-8")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("filePath") String filePath) {
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
            String path = filePath + File.separatorChar + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                // 新建文件夹
                dest.getParentFile().mkdirs();
            }
            // 文件写入
            file.transferTo(dest);
            return Utils.makeJSONResponseMsg(1, "上传成功", null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Utils.makeJSONResponseMsg(0, "上传失败", null);

    }


    /**
     * @param request
     * @return
     */
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            String filePath = "/Users/dalaoyang/Downloads/";
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    //设置文件路径及名字
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(filePath + file.getOriginalFilename())));
                    // 写入
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "第 " + i + " 个文件上传失败 ==> "
                            + e.getMessage();
                }
            } else {
                return "第 " + i
                        + " 个文件上传失败因为文件为空";
            }
        }
        return "上传成功";
    }

    /**
     * @param response
     * @param fileName 文件名
     * @return
     */
    @ApiOperation(value = "回放数据：根据文件名 下载文件(完成) ", notes = "回放数据：根据文件名、路径 下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "satId", value = "卫星id", required = true, dataType = "String")
    })
    @GetMapping(value = "/downloadRemoteFile", produces = "application/json;charset=UTF-8")
    public String downloadRemoteFile(HttpSession httpSession, HttpServletResponse response,
                                     @RequestParam("fileName") String fileName,
                                     @RequestParam("satId") String satId) {
        if (fileName != null) {
            //先从客户服务器将指定的文件 通过ftp下载到自己的服务器指定目录

            String downloadStatus = ftpService.downloadFile(remotePath, fileName, satId);

            if (downloadStatus.equals("1")) {
                return Utils.makeJSONResponseMsg(0, "下载失败", null);
            }

            //设置文件路径
            File file = new File(localPath + File.separatorChar + fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }


                    return Utils.makeJSONResponseMsg(1, "下载成功", null);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
                    Bsatdatamsg bsatdatamsg = new Bsatdatamsg();
                    bsatdatamsg.setBsdmAtpid(UUID.randomUUID().toString());
                    bsatdatamsg.setBsdmAtpcreatedatetime(Utils.getNow());
                    ;
                    bsatdatamsg.setBsdmAtpcreateuser(ls.getSUser().getSuAccount());
                    bsatdatamsg.setBsdmAtplastmodifydatetime(Utils.getNow());
                    bsatdatamsg.setBsdmAtplastmodifyuser(ls.getSUser().getSuAccount());
                    bsatdatamsg.setBsdmFksuserid(ls.getSUser().getSuAtpid());
                    bsatdatamsg.setBsdmFilename(fileName);
                    bsatdatamsg.setBsdmDownloadtime(Utils.getNow());
                    bsatdatamsg.setBsdmStatus("已下载");
                    iBsatdatamsgService.save(bsatdatamsg);
                }
            }
        }
        return Utils.makeJSONResponseMsg(0, "下载失败", null);
    }


    /**
     * 查看远程ftp文件列表
     *
     * @param satId 查看的目录
     * @return
     */
    @ApiOperation(value = "回放数据:查看远程ftp文件列表 ", notes = "查看远程ftp文件列表,每个卫星有不同的ftp路径")
    @ApiImplicitParam(name = "satId", value = "卫星型号id", required = true, dataType = "String")
    @PostMapping(value = "/listFtpFiles", produces = "application/json;charset=UTF-8")
    public String listFtpFiles(@RequestParam("satId") String satId) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("bsat_id", satId);
        Bsat bsat = iBsatService.getOne(queryWrapper);
        String port = bsat.getBsatFtpport();
        String userName = bsat.getBsatFtpaccount();
        String password = bsat.getBsatFtppasswod();
        String ftpPath = bsat.getBsatFtppath();

        String ipPath = ftpPath.replace("ftp://", "");
        String ip = ipPath.split("/")[0];
        ftpPath = "/" + ipPath.split("/")[1];
        List<Map> mapList = new ArrayList<Map>();
        try {


            FtpUtil ftpUitl = new FtpUtil();
            System.out.println(ip);
            System.out.println(port);
            //连接服务器
            ftpUitl.connectFtp(ip, Integer.parseInt(port), userName, password);
            //跳转目录至/htdocs目录下，并列 出目录下所有文件
            List<Map<String, Object>> fileList = ftpUitl.getFileList(ftpPath);
            for (Map<String, Object> file : fileList) {

                mapList.add(file);
            }

            ftpUitl.ftpClient.logout();
            ftpUitl.ftpClient.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("rows", mapList);
        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);

    }


    /**
     * 查看远程ftp文件列表(new)
     *
     * @param satId 查看的目录
     * @return
     */
    @ApiOperation(value = "回放数据:根据path查看远程ftp文件列表 ", notes = "查看远程ftp文件列表,每个卫星有不同的ftp路径")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "satId", value = "卫星型号id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPath", value = "路径", required = false, dataType = "String")
    })
    @PostMapping(value = "/listFtpFilesByParams", produces = "application/json;charset=UTF-8")
    public String listFtpFilesByParams(@RequestParam("satId") String satId, @RequestParam(value = "newPath", defaultValue = "") String newPath) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("bsat_id", satId);
        Bsat bsat = iBsatService.getOne(queryWrapper);
        String port = bsat.getBsatFtpport();
        String userName = bsat.getBsatFtpaccount();
        String password = bsat.getBsatFtppasswod();
        String ftpPath = bsat.getBsatFtppath();

        String ipPath = ftpPath.replace("ftp://", "");
        String ip = ipPath.split("/")[0];


        if (StrUtil.hasBlank(newPath) && StrUtil.hasEmpty(newPath)) {
            ftpPath = "/" + ipPath.split("/")[1];
        } else {
            ftpPath = newPath;
        }


        List<Map> mapList = new ArrayList<Map>();
        try {


            FtpUtil ftpUitl = new FtpUtil();
            //连接服务器
            ftpUitl.connectFtp(ip, Integer.parseInt(port), userName, password);
            //跳转目录至目录下，并列 出目录下所有文件
            List<Map<String, Object>> fileList = ftpUitl.getFileList(ftpPath);
            for (Map<String, Object> file : fileList) {

                mapList.add(file);
            }

            ftpUitl.ftpClient.logout();
            ftpUitl.ftpClient.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("total", mapList.size());
        result.put("rows", mapList);
        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);

    }


}
