package com.htzh.htdxjk.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResponseMsg implements Serializable {

    private int status;
    private String msg;
    private Object data;

}
