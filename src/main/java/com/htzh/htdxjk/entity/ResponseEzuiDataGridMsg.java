package com.htzh.htdxjk.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResponseEzuiDataGridMsg implements Serializable {

    private List rows ;
    private String total;

}
