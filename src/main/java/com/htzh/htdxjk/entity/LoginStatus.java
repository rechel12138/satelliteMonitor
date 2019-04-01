package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginStatus implements Serializable {

    private Suser sUser;

    private List auth;

    private String bsatCodes;

    private HashSet apiPermissionSet;

}
