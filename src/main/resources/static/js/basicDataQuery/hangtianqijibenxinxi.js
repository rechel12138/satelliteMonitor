$(function () {

    //获取权限生成操作按钮
    var menuAction =parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);
    $(".toolbar").find(".input_box").remove();
    $(".toolbar").find("#query").remove();

    ht.data.getData("/api/bsat/findByParamEzuiForBsat", "post",null,function (data) {
        var satlite_data = data.rows;
        for (var i = 0; i < satlite_data.length; i++) {
            for (okey in satlite_data[i]) {
                if (satlite_data[i][okey] == null) {
                    satlite_data[i][okey] = '';
                }
            }
        }

        //航天器信息表格：
        $('#htqjbxx').datagrid({
            // url:'/api/bsat/findByParamEzuiForBsat',
            data:satlite_data,
            nowrap: true,
            fitColumns: true,
            scrollbarSize:0,
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: false,
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            height: '100%',
            width: '100%',
            columns: [[
                {field: 'check_box', checkbox: true},
                {field: 'bsatCode', title: '型号代号',width: 100,sortable:true,id:'htqSelPanel', align: 'center'},
                {field: 'bsatName', title: '型号代号(五院)', width: 100, align: 'center'},
                {field: 'bsatOutsideName', title: '对外名称',width: 100, align: 'center'},
                {field: 'bsatLaunchTime', title: '发射时间',width: 100, align: 'center'},
                {field: 'bsatLifeyear', title: '考核寿命', width:100,align: 'center'},
                {field: 'bsatActive', title: '状态', width:100, align: 'center'},
                {field: 'zrrname', title: '责任人', width:100, align: 'center'},
                {field: 'zrrdh', title: '责任人电话', width:100, align: 'center'},
                {field: 'tdrname', title: '代替人', width:100, align: 'center'},
                {field: 'tdrdh', title: '代替人电话', width:100, align: 'center'},
                {field: 'bsatBxxrjlId', title: '在轨型号责任联系人', width:100, align: 'center'},//！！！没有这个字段，暂填下一字段
                {field: 'bsatBxhzcdwId', title: '在轨技术/支持队伍', width:100, align: 'center'}
            ]],
            onLoadSuccess:function(){
                //全选框点击时：
                $(".baseCode_select").click(function(){
                    if($(this).prop('checked')==true){
                        $("#selectUl").find('input[type=checkbox]').prop('checked',true)
                    }else{
                        $("#selectUl").find('input[type=checkbox]').prop('checked',false)
                    }
                });

                //型号代号筛选框的显示和隐藏：
                $('body').undelegate("#htqSelPanel","click").delegate('#htqSelPanel','click',function () {
                $("#ck_tp_sel").toggle();
                if($("#ck_tp_sel").css("display")=="block"){
                    getList();
                }
            });
            }
        });
    });


    //选择时间下拉菜单的日期，日历跳到相应时间：
    var calendar_begin_time;
    var calendar_end_time;
    $(".ckjhSelect").change(function(){
        if ($(this).val()=="2") {
            $(".calendar_box_back").css("display","block");
            calendar_begin_time = formatDate(new Date().getTime()-12*60*60*1000);
            calendar_end_time = formatDate(new Date().getTime()+12*60*60*1000);
        } else if($(this).val()=="3") {
            $(".calendar_box_back").css("display","block");
            calendar_begin_time = formatDate(new Date().getTime()-48*60*60*1000);
            calendar_end_time = formatDate(new Date().getTime()+24*60*60*1000);
        } else if($(this).val()=="4") {
            $(".calendar_box_back").css("display","block");
            calendar_begin_time = formatDate(new Date().getTime()-96*60*60*1000);
            calendar_end_time = formatDate(new Date().getTime()+72*60*60*1000);
        } else {
            $(".calendar_box_back").css("display","none");
            return;
        }
        $(".validatebox-text:eq(0)").val(calendar_begin_time);
        $(".validatebox-text:eq(1)").val(calendar_end_time);
    });

    //点击查询按钮时：
    $("#ck_query_btn").click(function() {
        //获取两个日历的输入时间：
        var starttime = new Date($(".validatebox-text:eq(0)").val()).getTime();
        var endtime = new Date($(".validatebox-text:eq(1)").val()).getTime();
        if (starttime >= endtime) {
            parent.showAlert("提示","开始时间不能小于结束时间","warning");
            return;
        }

        var keyword = $(".ckkeywords").val();
        // console.log(keyword);
        var stime = $(".validatebox-text:eq(0)").val();
        var etime = $(".validatebox-text:eq(1)").val();
        $("#htqjbxx").datagrid("load",
            {
                keyword:keyword,
                startTime:stime,
                endTime:etime
            });
    });

    // 打开弹窗：
    function openDialog(title,okBtnFunc){
        parent.createDialog({
            element:$("#htqxxtc"),
            elementId:"htqxxtc",
            title:title,
            icon:'',
            ok_button_handler:function(){
                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }

    //关闭弹窗
    function closeDialog(){
        parent.$("#htqxxtc").dialog("close");
    }

    //点击添加按钮:
    $("#add").on("click",function(){
        openDialog("添加航天器信息",function(){
            var data = getInputData("get");
            // console.log(data);
            if(!data) return;
            ht.data.getData("/api/bsat/addBsat","POST",data,function(d){
                if(d["status"]=="1"){
                    parent.showAlert("恭喜","添加成功","info",function(){
                        $("#htqjbxx").datagrid("reload");
                        closeDialog();
                    })
                }else{
                    parent.showAlert("错误","添加失败","error");
                }
            })
        });
        parent.$("#launch_time").datetimebox({showSeconds: true});
        parent.$('#launch_time').next().find('a').css("right","2px");
        getZrrList(parent.$("#zrr"));
        getZrrList(parent.$("#dtr"));
        getZdXlList(parent.$("#zglxr"));
        getZdXlList(parent.$("#zgzcdw"));
        getInputData("clear");
    });

    //点击修改按钮:
    $("#update").on("click",function() {
        var selectRows = $("#htqjbxx").datagrid("getSelections");
        if (selectRows.length == 1) {
            var ckjhId = selectRows[0]["bsatAtpid"];
            doUpdate(ckjhId);
        } else {
            parent.showAlert("提示", "请选择1条数据进行修改", "warning");
        }
    });

    //点击删除按钮：
    $("#delete").on("click",function(){
        var selectRows = $("#htqjbxx").datagrid("getSelections");
        if(selectRows.length!=0){
            var ckjhIds = [];
            for(var k in selectRows){
                ckjhIds.push(selectRows[k]["bsatAtpid"]);
            }
            doDelete(ckjhIds);
        }else{
            parent.showAlert("提示","未选择任何数据","warning");
        }
    });

    //点击导出excel按钮:
    $("#exportExcel").on("click",function(){
        // var url="/api/bckplan/exportExcel";
        // parent.document.location.href=url;
        var keyword = $(".ckkeywords").val();
        var stime = $(".validatebox-text:eq(0)").val();
        var etime = $(".validatebox-text:eq(1)").val();
        var export_url="/api/bsat/exportExcel?keyword="+keyword+"&startTime="+stime+"&endTime="+etime;
        parent.document.location.href=export_url;
    });

    //把当前的毫秒数转为年月日时分秒格式：
    var formatDate = function(datetime) {
        var dateTime = new Date(datetime);
        var year = dateTime.getFullYear();
        var month = ("0" + (datetime.getMonth() + 1)).slice(-2);
        var date = ("0" + datetime.getDate()).slice(-2);
        var hour = ("0" + datetime.getHours()).slice(-2);
        var minute = ("0" + datetime.getMinutes()).slice(-2);
        var second = ("0" + datetime.getSeconds()).slice(-2);
        return (year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second);
    };

    //获取用户输入数据
    function getInputData(type,data){
        var xhdh = parent.$("#xhdh");
        var xhdhwy = parent.$("#xhdhwy");
        var out_name = parent.$("#out_name");
        var launch_time = parent.$("#htqxxtc .validatebox-text");
        var khsm = parent.$("#khsm");
        var zrr = parent.$("#zrr");
        var dtr = parent.$("#dtr");
        var zglxr = parent.$("#zglxr");
        var zgzcdw = parent.$("#zgzcdw");

        if(type=="clear"){
            xhdh.val("");
            xhdhwy.val("");
            out_name.val("");
            launch_time.val("");
            khsm.val("");
            zrr.val(null);
            dtr.val(null);
            zglxr.val(null);
            zgzcdw.val(null);
        }
        if(type=="get"){
            if(xhdh.val()==""){
                parent.showAlert("提示","请输入型号代号","error");
                return false;
            }
            if(xhdhwy.val()==""){
                parent.showAlert("提示","请输入型号代号（五院）","error");
                return false;
            }
            if(out_name.val()==""){
                parent.showAlert("提示","请输入对外名称","error");
                return false;
            }
            if(launch_time.val()==""){
                parent.showAlert("提示","请输入发射时间","error");
                return false;
            }
            if(khsm.val()==""){
                parent.showAlert("提示","请输入考核寿命","error");
                return false;
            }
            if(zrr.val()==null){
                parent.showAlert("提示","请选择责任人","error");
                return false;
            }
            if(dtr.val()==null){
                parent.showAlert("提示","请选择代替人","error");
                return false;
            }
            if(zglxr.val()==null){
                parent.showAlert("提示","请选择在轨型号责任联系人","error");
                return false;
            }
            if(zgzcdw.val()==null){
                parent.showAlert("提示","请选择在轨技术支持队伍","error");
                return false;
            }
            var data = {
                bsatCode:xhdh.val(),//型号代号
                bsatName:xhdhwy.val(),//型号代号（五院）
                bsatOutsideName:out_name.val(),//对外名称
                bsatLaunchTime:launch_time.val(),//发射时间
                bsatLifeyear:khsm.val(),//考核寿命
                zrrAtpid:zrr.val(),//责任人
                tdrid:dtr.val(),//代替人
                bsatBxxrjlId:zglxr.val(),//在轨型号责任联系人
                bsatBxhzcdwId:zgzcdw.val()//在轨技术支持队伍
            };
            return data;
        }
        if(type=="set"){
            xhdh.val(data.bsatCode),
            xhdhwy.val(data.bsatName),
            out_name.val(data.bsatOutsideName),
            launch_time.val(data.bsatLaunchTime),
            khsm.val(data.bsatLifeyear),
            zrr.val(data.bsatAdminSubsId),
            dtr.val(data.bsatAdminId),
            bsatBxxrjlId.val(data.bsatZglxr),
            zgzcdw.val(data.bsatBxhzcdwId)
        }
    }

    //更新封装:
    function doUpdate(ckjhId){
        getInputData("clear");
        ht.data.getData("/api/bsat/getOneBsatById","POST",{atpId:ckjhId},function (d) {
            if(d["status"]=="1"){
                openDialog("修改测控计划",function(){
                    var data = getInputData("get");
                    if(!data) return;
                    data.bsatAtpid = ckjhId;
                    ht.data.getData("/api/bsat/updateBsat","POST",data,function(re){
                        // console.log(re);
                        if(re["status"]=="1"){
                            parent.showAlert("恭喜","修改成功","info",function(){
                                $("#htqjbxx").datagrid("reload");
                                closeDialog();
                            })
                        }else{
                            parent.showAlert("错误",re.msg,"error");
                        }
                    })
                });
                parent.$("#launch_time").datetimebox({showSeconds: true});
                parent.$('#launch_time').next().find('a').css("right","2px");
                getInputData("set",d["data"]);
            }else{
                parent.showAlert("错误","获取信息失败","error");
            }
        })
    }

    //删除封装：
    function doDelete(ckjhIds){
        parent.showConfirm("提示","确定删除"+ckjhIds.length+"条记录？",function(r){
            if(r){
                ht.data.getData("/api/bsat/removeBsat","POST",{bsatAtpid :ckjhIds.join(",")},function (d) {
                    if(d["status"]=="1"){
                        parent.showAlert("恭喜","删除成功","info",function(){
                            $("#htqjbxx").datagrid("reload");
                        })
                    }else{
                        parent.showAlert("错误","删除失败","error");
                    }
                })
            }
        })
    }


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
            ht.data.getData("/api/bsat/findByParamEzuiForBsat","POST",{satCodes:selectStr},function (data) {
                console.log(data);
                if(data.total>0) {
                    $('#htqjbxx').datagrid('loadData', data.rows);
                    $("#ck_tp_sel").hide();
                }else{
                    parent.showAlert('提示','没有这个型号代号','error');
                }
            })
        }else{
            ht.data.getData("/api/bsat/findByParamEzuiForBsat", "post",null,function (data) {
                // console.log(data);
                if(data.total>0){
                    $('#htqjbxx').datagrid('loadData', data.rows);
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
        ht.data.getData("/api/bsat/findByParamEzuiForBsat","POST",{sort:'bsat_code',order:'asc'},function (data) {
            $('#htqjbxx').datagrid('loadData', data.rows);
            $("#ck_tp_sel").hide();
        });
    });
    //降序
    $("#sort_desc").click(function () {
        ht.data.getData("/api/bsat/findByParamEzuiForBsat","POST",{sort:'bsat_code',order:'desc'},function (data) {
            $('#htqjbxx').datagrid('loadData', data.rows);
            $("#ck_tp_sel").hide();
        });
    });



    //获取所有卫星的型号代号列表：
    function getList() {
        ht.data.getData("/api/bsat/getAllSatIds", "post",null,function (checkD) {
            // console.log(checkD);
            if(checkD.status==1){
                var str='';//型号代号筛选框列表,多选
                // var addStr = '';//点击增加时的型号代号列表，单选
                for(var i=0;i<checkD.data.length;i++){
                    str+='<li style="padding:5px 0;width:100%"><label for="'+checkD.data[i].code+'"><input type="checkbox" id="'+checkD.data[i].code+'">&nbsp&nbsp&nbsp'+checkD.data[i].code+'</label></li>';
                    // addStr+='<option value='+checkD.data[i].code+' hiddenData='+checkD.data[i].id+'>'+checkD.data[i].code+'</option>'
                }
                $("#selectUl").html(str);//将型号代号列表放进筛选框中
            }
        })
    }

    //增加时获取责任人/代替人信息：
    function getZrrList(zrr_obj) {
        ht.data.getData("/api/suser/findByParamEzuiForUsers", "POST", null, function (d) {
            // console.log(d);
            var zrrListStr = '';
            for (var i = 0; i < d.rows.length; i++) {
                zrrListStr += '<option value=' + d.rows[i].suChinesename + '>' + d.rows[i].suChinesename + '</option>';
            }
            zrr_obj.html(zrrListStr);//将责任人列表放进列表中
        })
    }

    //增加时获取型号责任联系人/型号支持队伍信息：
    function getZdXlList(zd_obj) {
        ht.data.getData("/api/bxhzcdw/listBxhzcdw", "post", null, function (d) {
            console.log(d);
            if (d.status == 1) {
                var zdListStr = '';
                for (var i = 0; i < d.data.length; i++) {
                    zdListStr += '<option value=' + d.data[i].bxhzcdwName + '>' + d.data[i].bxhzcdwName + '</option>';
                }
                zd_obj.html(zdListStr);
            }
        })
    }
});
