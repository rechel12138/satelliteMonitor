package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.Bsat;
import com.htzh.htdxjk.service.IBsatService;
import com.htzh.htdxjk.service.IFtpService;
import com.htzh.htdxjk.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: Rechel
 * @Date: 2019/2/27 下午1:07
 */
@Service
public class FtpServiceImpl implements IFtpService {


    @Value("${myftp.ip}")
    private String ip;

    @Value("${myftp.port}")
    private int port;

    @Value("${myftp.userName}")
    private String userName;

    @Value("${myftp.password}")
    private String password;

    @Value("${myftp.localPath}")
    private String localPath;

    @Autowired
    IBsatService iBsatService;

    @Override
    public String downloadFile(String remotePath,String fileName,String satId) {
        try {
            FtpUtil ftpUitl = new FtpUtil();

            Bsat bsat=iBsatService.getById(satId);
            String port =bsat.getBsatFtpport();
            String userName=bsat.getBsatFtpaccount();
            String password=bsat.getBsatFtppasswod();
            String ftpPath=bsat.getBsatFtppath();
            //连接服务器
            ftpUitl.connectFtp(ip, Integer.parseInt(port), userName, password);

            //下载文件
            ftpUitl.download(localPath,fileName, remotePath);
            ftpUitl.ftpClient.logout();
            ftpUitl.ftpClient.disconnect();
        } catch (IOException e) {

            e.printStackTrace();
            return "1";
        }

        return "0";
    }
}
