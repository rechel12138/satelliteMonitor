package com.htzh.htdxjk.requestParam;

import lombok.Data;

import java.util.List;

/**
 * @Author: Rechel
 * @Date: 2019/3/29 下午12:29
 */
@Data
public class SpageconfigRequest {

    private String name;
    private List<SpageConfigParam> list;
}
