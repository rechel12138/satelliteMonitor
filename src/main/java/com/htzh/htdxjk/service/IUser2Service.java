package com.htzh.htdxjk.service;

import com.htzh.htdxjk.entity.User2;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-21
 */
public interface IUser2Service extends IService<User2> {

    List<User2> getUser2Diy();

    void saveExcelList(List<User2> typeLists);

    List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception;
}
