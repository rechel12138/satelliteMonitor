$(function () {
    //获取权限生成操作按钮
    var menuAction = parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);
    $(".toolbar").find(".input_box").remove();
    $(".toolbar").find("#query").remove();

    //轨道根数表格：
    $('#gdgs').datagrid({
        url: '/api/borbit/findByParamEzui',
        nowrap: true,
        fitColumns: true,
        scrollbarSize: 0,
        striped: true,
        rownumbers: true,
        pagination: true,
        singleSelect: false,
        checkOnSelect: false,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        height: '100%',
        width: '100%',
        columns: [[
            {field: 'check_box', checkbox: true},
            {field: 'bsat_code', title: '型号代号', sortable: true, width: 100, id: 'gdgs_type', align: 'center'},
            {
                field: 'borbit_time_stamp', title: '测量时间', sortable: true, width: 100, align: 'center',
                // formatter:function (value) {
                //     return formatDate(value);
                // }
            },
            {field: 'borbit_type', title: '类别', width: 50, align: 'center'},
            {field: 'borbit_a', title: '半长轴（m）', width: 60, align: 'center'},
            {field: 'borbit_e', title: '偏心率', width: 50, align: 'center'},
            {field: 'borbit_i', title: '倾角（Deg）', width: 60, align: 'center'},
            {field: 'borbit_o', title: '升交点赤经（Deg）', width: 80, align: 'center'},
            {field: 'borbit_w', title: '近地点俯角（Deg）', width: 80, align: 'center'},
            {field: 'borbit_m', title: '平近点角（Deg）', width: 80, align: 'center'},
            {
                field: 'erweima', title: '二维码 ', width: 100, align: 'center',
                formatter: function (value, rowData, rowIndex) {
                    //return "<img src='/images/qrcode.png' alt='' class='qrcode' custom_prop='rowData.borbit_atpid'>"
                    //ht.data.getData('/api/borbit/makeQRCodeString','POST',{borbitAtpid:rowData.borbit_atpid})
                    return "<img style='width:35px;height:35px;' class='qrcode' src='/api/borbit/makeQRCodeString?borbitAtpid=" + rowData.borbit_atpid + "'>";
                }
            }
        ]],
        onLoadSuccess: function () {
            getList();

            //全选框点击时：
            $(".baseCode_select").click(function () {
                if ($(this).prop('checked') == true) {
                    $("#selectUl").find('input[type=checkbox]').prop('checked', true)
                } else {
                    $("#selectUl").find('input[type=checkbox]').prop('checked', false)
                }
            });

            //型号代号筛选框的显示和隐藏：
            $('body').on("click","#gdgs_type",function () {
                $("#ck_tp_sel").toggle()
            });
        }
    });

    //点击二维码放大：
    $('body').undelegate(".qrcode", 'click').delegate(".qrcode", 'click', function () {
        var imgSrc = $(this).attr("src");
        $("#erweima").find("img").attr('src',imgSrc);
        openDialogEwm('微信扫码', function () {
            closeDialogEwm();
        });
    });


    //选择时间下拉菜单的日期，日历跳到相应时间：
    var calendar_begin_time;
    var calendar_end_time;
    $(".ckjhSelect").change(function () {
        if ($(this).val() == "2") {
            $(".calendar_box_back").css("display", "block");
            calendar_begin_time = formatDate(new Date().getTime() - 12 * 60 * 60 * 1000);
            calendar_end_time = formatDate(new Date().getTime() + 12 * 60 * 60 * 1000);
        } else if ($(this).val() == "3") {
            $(".calendar_box_back").css("display", "block");
            calendar_begin_time = formatDate(new Date().getTime() - 48 * 60 * 60 * 1000);
            calendar_end_time = formatDate(new Date().getTime() + 24 * 60 * 60 * 1000);
        } else if ($(this).val() == "4") {
            $(".calendar_box_back").css("display", "block");
            calendar_begin_time = formatDate(new Date().getTime() - 96 * 60 * 60 * 1000);
            calendar_end_time = formatDate(new Date().getTime() + 72 * 60 * 60 * 1000);
        } else {
            $(".calendar_box_back").css("display", "none");
            return;
        }
        $(".validatebox-text:eq(0)").val(calendar_begin_time);
        $(".validatebox-text:eq(1)").val(calendar_end_time);
    });

    //点击查询按钮时：
    $("#ck_query_btn").click(function (e) {
        var starttime = new Date($(".validatebox-text:eq(0)").val()).getTime();
        var endtime = new Date($(".validatebox-text:eq(1)").val()).getTime();
        if (starttime >= endtime) {
            alert("结束时间不能小于开始时间");
            return;
        }
        var keyword = $(".ckkeywords").val();
        var stime = $(".validatebox-text:eq(0)").val();
        var etime = $(".validatebox-text:eq(1)").val();
        $("#gdgs").datagrid("load",
            {
                keyword: keyword,
                startTime: stime,
                endTime: etime
            });
    });

    // 打开弹窗：
    function openDialog(title, okBtnFunc) {
        parent.createDialog({
            element: $("#gdgstc"),
            elementId: "gdgstc",
            title: title,
            icon: '',
            ok_button_handler: function () {
                if (typeof okBtnFunc == "function") okBtnFunc();
            }
        })
    }

    function openDialogEwm(title, okBtnFunc) {
        parent.createDialog({
            element: $("#erweima"),
            elementId: "erweima",
            title: title,
            icon: '',
            ok_button_handler: function () {
                if (typeof okBtnFunc == "function") okBtnFunc();
            }
        })
    }

    function closeDialogEwm() {
        parent.$("#erweima").dialog("close");
    }

    //关闭弹窗
    function closeDialog() {
        parent.$("#gdgstc").dialog("close");
    }

    //点击添加按钮:
    $("#add").on("click", function () {
        openDialog("添加轨道根数", function () {
            var data = getInputData("get");
            if (!data) return;
            ht.data.getData("/api/borbit/addBorbit", "POST", data, function (d) {
                // console.log(data);
                if (d["status"] == "1") {
                    parent.showAlert("恭喜", "添加成功", "info", function () {
                        $("#gdgs").datagrid("reload");
                        closeDialog();
                    })
                } else {
                    parent.showAlert("错误", "添加失败", "error");
                }
            })
        });
        getList();
        parent.$("#measuretime").datetimebox({showSeconds: true});
        parent.$('#measuretime').next().find('a').css("right","2px");

        getInputData("clear");
    });

    //点击修改按钮:
    $("#update").on("click", function () {
        var selectRows = $("#gdgs").datagrid("getSelections");
        // console.log(selectRows);
        if (selectRows.length == 1) {
            var gdgsId = selectRows[0]["borbit_atpid"];
            doUpdate(gdgsId);
        } else {
            parent.showAlert("提示", "请选择1条数据进行修改", "warning");
        }
    });

    //点击删除按钮：
    $("#delete").on("click", function () {
        var selectRows = $("#gdgs").datagrid("getSelections");
        if (selectRows.length != 0) {
            var gdgsIds = [];
            for (var k in selectRows) {
                gdgsIds.push(selectRows[k]["borbit_atpid"]);
            }
            doDelete(gdgsIds);
        } else {
            parent.showAlert("提示", "未选择任何数据", "warning");
        }
    });

    //点击导出excel按钮:
    $("#exportExcel").on("click", function () {
        // var url="/api/borbit/exportExcel";
        // parent.document.location.href=url;
        var keyword = $(".ckkeywords").val();
        var stime = $(".validatebox-text:eq(0)").val();
        var etime = $(".validatebox-text:eq(1)").val();
        var url = "/api/borbit/exportExcel?keyword=" + keyword + "&startTime=" + stime + "&endTime=" + etime;
        parent.document.location.href = url;
    });


    //筛选框中输入型号代号时：
    $("#sort_search").keyup(function(){
        var strkey=$(this).val();
        if(strkey){
            $("#selectUl").find('input[type=checkbox]').each(function(){
                if($(this).attr('id').indexOf(strkey)<0){
                    $(this).parent().parent().hide()
                }else{
                    $(this).parent().parent().show()
                }
            });
        }else{
            $("#selectUl").find('input[type=checkbox]').each(function(){
                $(this).parent().parent().show()
            });
        }
    });

    //点击确定时：
    $("#sorbtn_true").click(function(){
        var selectArr=[];
        $("#selectUl").find('input[type=checkbox]').each(function(){
            if($(this).prop('checked')){
                selectArr.push($(this).attr('id'))
            }
        });

        if(selectArr.length!=0){
            var selectStr=selectArr.toString();
            ht.data.getData("/api/borbit/findByParamEzui","POST",{bsatCodes:selectStr},function (data) {
                // console.log(data);
                if(data.total>0) {
                    $('#gdgs').datagrid('loadData', data.rows);
                    $("#ck_tp_sel").hide();
                }else{
                    parent.showAlert('提示','没有这个型号代号','error');
                }
            })
        }else{
            ht.data.getData("/api/borbit/findByParamEzui", "post",null,function (data) {
                // console.log(data);
                if(data.total>0){
                    $('#gdgs').datagrid('loadData', data.rows);
                    $("#ck_tp_sel").hide();
                }else{
                    parent.showAlert('提示','加载数据错误','error');
                    $("#ck_tp_sel").hide();
                }
            })
        }
    });
    //点击取消时：
    $("#sorbtn_cel").click(function () {
        $("#ck_tp_sel").hide();
    });
    //全选
    $("#btn_all").click(function () {
        $("#selectUl").find('input[type=checkbox]').prop('checked', true)
    });
    //反选
    $("#btn_reverse").click(function () {
        $("#selectUl").find('input[type=checkbox]').each(function () {
            if ($(this).prop('checked')) {
                $(this).prop('checked', false)
            } else {
                $(this).prop('checked', true)
            }
        })
    });

    //升序
    $("#sort_ace").click(function () {
        $("#gdgs").datagrid("load", {
            sort: '',
            order: 'asc'
        });
        $("#ck_tp_sel").hide();
    });
    //降序
    $("#sort_desc").click(function () {
        $("#gdgs").datagrid("load", {
            sort: '',
            order: 'desc'
        });
        $("#ck_tp_sel").hide();
    });


    //增加和修改时获取用户输入数据
    function getInputData(type, data) {
        var xhdh = parent.$("#xhdh");
        var option_id =xhdh.find("option:selected").attr("hiddenData");
        var measuretime = parent.$("#gdgstc .validatebox-text");
        var gdgs_type = parent.$("#gdgs_type");
        var bcz = parent.$("#bcz");
        var pxl = parent.$("#pxl");
        var qj = parent.$("#qj");
        var sjdcj = parent.$("#sjdcj");
        var jdifj = parent.$("#jdifj");
        var pjdj = parent.$("#pjdj");

        if (type == "clear") {
            xhdh.val("");
            measuretime.val("");
            gdgs_type.val("");
            bcz.val("");
            pxl.val("");
            qj.val("");
            sjdcj.val("");
            jdifj.val("");
            pjdj.val("");
        }
        if (type == "get") {
            if (xhdh.val() ==null) {
                parent.showAlert("提示", "请输入型号代号", "error");
                return false;
            }
            if (measuretime.val() == "") {
                parent.showAlert("提示", "请输入测量时间", "error");
                return false;
            }
            if (gdgs_type.val() == "") {
                parent.showAlert("提示", "请输入类别", "error");
                return false;
            }
            if (bcz.val() == "") {
                parent.showAlert("提示", "请输入半长轴（m）", "error");
                return false;
            }
            if (pxl.val() == "") {
                parent.showAlert("提示", "请输入偏心率", "error");
                return false;
            }
            if (qj.val() == "") {
                parent.showAlert("提示", "请输入倾角（Deg）", "error");
                return false;
            }
            if (sjdcj.val() == "") {
                parent.showAlert("提示", "请输入升交点赤经（Deg）", "error");
                return false;
            }
            if (jdifj.val() == "") {
                parent.showAlert("提示", "请输入近地点俯角（Deg）", "error");
                return false;
            }
            if (pjdj.val() == "") {
                parent.showAlert("提示", "请输入平近点角（Deg）", "error");
                return false;
            }
            var data = {
                borbitSatid: option_id,
                borbitTimeStamp: measuretime.val(),
                borbitType: gdgs_type.val(),
                borbitA: bcz.val(),
                borbitE: pxl.val(),
                borbitI: qj.val(),
                borbitO: sjdcj.val(),
                borbitW: jdifj.val(),
                borbitM: pjdj.val()
            };
            // console.log(data);
            return data;
        }
        if (type == "set") {
            xhdh.val(data.bsat_code);
            measuretime.val(data.borbit_time_stamp);
            gdgs_type.val(data.borbit_type);
            bcz.val(data.borbit_a);
            pxl.val(data.borbit_e);
            qj.val(data.borbit_i);
            sjdcj.val(data.borbit_o);
            jdifj.val(data.borbit_w);
            pjdj.val(data.borbit_m);
        }
    }

    //更新封装:
    function doUpdate(gdgsId) {
        getInputData("clear");
        ht.data.getData("/api/borbit/getOneById", "POST", {atpId: gdgsId}, function (d) {
            if (d["status"] == "1") {
                openDialog("修改轨道根数", function () {
                    var data = getInputData("get");
                    if (!data) return;
                    data.borbitAtpid = gdgsId;
                    ht.data.getData("/api/borbit/updateBorbit", "POST", data, function (re) {
                        if (re["status"] == "1") {
                            parent.showAlert("恭喜", "修改成功", "info", function () {
                                $("#gdgs").datagrid("reload");
                                parent.$("#cover").css("display","none");
                                closeDialog();
                            })
                        } else {
                            parent.showAlert("错误", re.msg, "error");
                        }
                    })
                });
                parent.$("#measuretime").datetimebox({showSeconds: true});
                parent.$('#measuretime').next().find('a').css("right","2px");
                parent.$("#cover").css({display:"block", left:"151px",top:"54px",width:"187px",height:"27px"});
                getInputData("set", d["data"]);
            } else {
                parent.showAlert("错误", "获取信息失败", "error");
            }
        })
    }

    //删除封装：
    function doDelete(gdgsIds) {
        parent.showConfirm("提示", "确定删除" + gdgsIds.length + "条记录？", function (r) {
            if (r) {
                ht.data.getData("/api/borbit/removeBorbit", "POST", {borbitAtpid: gdgsIds.join(",")}, function (d) {
                    if (d["status"] == "1") {
                        parent.showAlert("恭喜", "删除成功", "info", function () {
                            $("#gdgs").datagrid("reload");
                        })
                    } else {
                        parent.showAlert("错误", "删除失败", "error");
                    }
                })
            }
        })
    }

    //获取所有卫星的型号代号列表：
    function getList() {
        ht.data.getData("/api/bsat/getAllSatIds", "post",null,function (checkD) {
            // console.log(checkD);
            if(checkD.status==1){
                var str='';//型号代号筛选框列表,多选
                var addStr = '';//点击增加时的型号代号列表，单选
                for(var i=0;i<checkD.data.length;i++){
                    str+='<li style="padding:5px 0;width:100%"><label for="'+checkD.data[i].code+'"><input type="checkbox" id="'+checkD.data[i].code+'">&nbsp&nbsp&nbsp'+checkD.data[i].code+'</label></li>';
                    addStr+='<option value='+checkD.data[i].code+' hiddenData='+checkD.data[i].id+'>'+checkD.data[i].code+'</option>'
                }
                $("#selectUl").html(str);//将型号代号列表放进筛选框中
                $("#xhdh").append($(addStr));
            }
        })
    }

    //把当前的毫秒数转为年月日时分秒格式：
    var formatDate = function (datetime) {
        var datetime = new Date(datetime);
        var year = datetime.getFullYear();
        var month = ("0" + (datetime.getMonth() + 1)).slice(-2);
        var date = ("0" + datetime.getDate()).slice(-2);
        var hour = ("0" + datetime.getHours()).slice(-2);
        var minute = ("0" + datetime.getMinutes()).slice(-2);
        var second = ("0" + datetime.getSeconds()).slice(-2);
        return (year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second);
    }
});

