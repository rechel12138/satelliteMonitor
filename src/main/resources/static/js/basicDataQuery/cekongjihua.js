$(function(){

    //获取权限生成操作按钮
    var menuAction =parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);
    $(".toolbar").find(".input_box").remove();
    $(".toolbar").find("#query").remove();

    //测控计划表格：
    $('#dg').datagrid({
        url:'/api/bckplan/findByParamEzui',
        nowrap: true,
        fitColumns: true,
        scrollbarSize:0,
        striped: true,
        rownumbers: true,
        pagination: true,
        remoteSort:false,
        singleSelect: false,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        height: '100%',
        width: '100%',
        columns: [[
            {field: 'check_box', checkbox: true},
            {field: 'bsat_code', title: '型号代号', width:200,sortable:true,id:'ck_tp',align: 'center'},
            {field: 'bckplan_start_time', title: '开始时间', width: 250,sortable:true, align: 'center'},
            {field: 'bckplan_end_time', title: '结束时间', width: 250, sortable:true,align: 'center'},
            {field: 'bckplan_cnt', title: '圈次', width: 100, sortable:true,align: 'center'},
            {field: 'bckplan_devname', title: '测站', width: 200, align: 'center'},
            {field: 'bckplan_is_delete', title: '是否删除', width: 100, align: 'center'},
            {field: 'bckplan_state', title: '状态', width: 100, align: 'center'},
            {field: 'bckplan_modify_time', title: '修改时间', width: 250,sortable:true, align: 'center'},
            {field: 'bckplan_madetime', title: '文件生成时间', width: 250,sortable:true, align: 'center'}
        ]],
        onLoadSuccess:function(){
            getList();

            //全选框点击时：
            $(".baseCode_select").click(function(){
                if($(this).prop('checked')==true){
                    $("#selectUl").find('input[type=checkbox]').prop('checked',true)
                }else{
                    $("#selectUl").find('input[type=checkbox]').prop('checked',false)
                }
            });

            //型号代号筛选框的显示和隐藏：
            $('body').on("click","#ck_tp",function () {
                $("#ck_tp_sel").toggle()
            });
        }
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
        var stime = $(".validatebox-text:eq(0)").val();
        var etime = $(".validatebox-text:eq(1)").val();
        $("#dg").datagrid("load",
            {
                keyword:keyword,
                startTime:stime,
                endTime:etime
            });
    });

    // 打开弹窗：
    function openDialog(title,okBtnFunc){
        parent.createDialog({
            element:$("#ckjhtc"),
            elementId:"ckjhtc",
            title:title,
            icon:'',
            ok_button_handler:function(){
                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }

    //关闭弹窗
    function closeDialog(){
        parent.$("#ckjhtc").dialog("close");
    }

    //点击添加按钮:
    $("#add").on("click",function(){
        openDialog("添加测控计划",function(){
            var data = getInputData("get");
            if(!data) return;
            ht.data.getData("/api/bckplan/addBckplan","POST",data,function(d){
                // console.log(data);
                // console.log(d);
                if(d["status"]=="1"){
                    parent.showAlert("恭喜","添加成功","info",function(){
                        $("#dg").datagrid("reload");
                        closeDialog();
                    })
                }else{
                    parent.showAlert("错误","添加失败","error");
                }
            })
        });
        getList();
        parent.$("#starttime,#endtime").datetimebox({showSeconds: true});
        parent.$('#starttime,#endtime').next().find('a').css("right","2px");
        getInputData("clear");
    });

    //点击修改按钮:
    $("#update").on("click",function() {
        var selectRows = $("#dg").datagrid("getSelections");
        // console.log(selectRows);
        if (selectRows.length == 1) {
            var ckjhId = selectRows[0]["bckplan_atpid"];
            doUpdate(ckjhId);
        } else {
            parent.showAlert("提示", "请选择1条数据进行修改", "warning");
        }
    });

    //点击删除按钮：
    $("#delete").on("click",function(){
        var selectRows = $("#dg").datagrid("getSelections");
        if(selectRows.length!=0){
            var ckjhIds = [];
            for(var k in selectRows){
                ckjhIds.push(selectRows[k]["bckplan_atpid"]);
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
        var export_url="/api/bckplan/exportExcel?keyword="+keyword+"&startTime="+stime+"&endTime="+etime;
        parent.document.location.href=export_url;
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
            ht.data.getData("/api/bckplan/findByParamEzui","POST",{bsatCodes:selectStr},function (data) {
                if(data.total>0) {
                    $('#dg').datagrid('loadData', data.rows);
                    $("#ck_tp_sel").hide();
                }else{
                    parent.showAlert('提示','没有这个型号代号','error');
                }
            })
        }else{
            ht.data.getData("/api/bckplan/findByParamEzui", "post",null,function (data) {
                // console.log(data);
                if(data.total>0){
                    $('#dg').datagrid('loadData', data.rows);
                    $("#ck_tp_sel").hide();
                }else{
                    parent.showAlert('提示','加载数据错误','error');
                    $("#ck_tp_sel").hide();
                }
            })
        }
    });
    //点击取消时：
    $("#sorbtn_cel").click(function(){
        $("#ck_tp_sel").hide();
    });
    //全选
    $("#btn_all").click(function(){
        $("#selectUl").find('input[type=checkbox]').prop('checked',true)
    });
    //反选
    $("#btn_reverse").click(function(){
        $("#selectUl").find('input[type=checkbox]').each(function(){
            if($(this).prop('checked')){
                $(this).prop('checked',false)
            }else{
                $(this).prop('checked',true)
            }
        })
    });

    //升序
    $("#sort_ace").click(function(){
        $("#dg").datagrid("load",{
            sort:'bsat_code',
            order:'asc'
        });
        $("#ck_tp_sel").hide();
    });
    //降序
    $("#sort_desc").click(function(){
        $("#dg").datagrid("load",{
            sort:'bsat_code',
            order:'desc'
        });
        $("#ck_tp_sel").hide();
    });

    //把当前的毫秒数转为年月日时分秒格式：
    var formatDate = function(datetime) {
       var datetime = new Date(datetime);
       var year = datetime.getFullYear();
       var month = ("0" + (datetime.getMonth() + 1)).slice(-2);
       var date = ("0" + datetime.getDate()).slice(-2);
       var hour = ("0" + datetime.getHours()).slice(-2);
       var minute = ("0" + datetime.getMinutes()).slice(-2);
       var second = ("0" + datetime.getSeconds()).slice(-2);
       return (year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second);
    };

    //获取用户输入数据
    function getInputData(type,data){
        // console.log(data);
        var xhdh = parent.$("#xhdh");
        var option_id =xhdh.find("option:selected").attr("hiddenData");

        var starttime = parent.$("#ckjhtc .validatebox-text").eq(0);
        var endtime = parent.$("#ckjhtc .validatebox-text").eq(1);
        var quanci = parent.$("#quanci");
        var cezhan = parent.$("#cezhan");
        var state = parent.$("#state");
        // console.log(starttime.val());
        if(type=="clear"){
            xhdh.val(null);
            starttime.val("");
            endtime.val("");
            quanci.val("");
            cezhan.val("");
            state.val("");
        }
        if(type=="get"){
            if(xhdh.val()==null){
                parent.showAlert("提示","请输入型号代号","error");
                return false;
            }
            if(starttime.val()==""){
                parent.showAlert("提示","请输入开始时间","error");
                return false;
            }
            if(endtime.val()==""){
                parent.showAlert("提示","请输入结束时间","error");
                return false;
            }
            if(quanci.val()==""){
                parent.showAlert("提示","请输入圈次","error");
                return false;
            }
            if(cezhan.val()==""){
                parent.showAlert("提示","请输入测站","error");
                return false;
            }
            if(state.val()==""){
                parent.showAlert("提示","请输入状态","error");
                return false;
            }
            var data = {
                bckplanSatid:option_id,
                bckplanStartTime:starttime.val(),
                bckplanEndTime:endtime.val(),
                bckplanCnt:quanci.val(),
                bckplanDevname:cezhan.val(),
                bckplanState:state.val()
            };
            // console.log(data);
            return data;
        }
        if(type=="set"){
            // console.log(data);
            xhdh.val(data.bsat_code);
            starttime.val(data.bckplan_start_time);
            endtime.val(data.bckplan_end_time);
            quanci.val(data.bckplan_cnt);
            cezhan.val(data.bckplan_devname);
            state.val(data.bckplan_state);
        }
    }

    //更新封装:
    function doUpdate(ckjhId){
        getInputData("clear");
        ht.data.getData("/api/bckplan/getOneById","POST",{atpId:ckjhId},function (d) {
            console.log(d);
            if(d["status"]=="1"){
                openDialog("修改测控计划",function(){

                    var data = getInputData("get");
                    // console.log(data);
                    if(!data) return;
                    data.bckplanAtpid = ckjhId;
                    ht.data.getData("/api/bckplan/updateBckplan","POST",data,function(re){
                        // console.log(re);
                        if(re["status"]=="1"){
                            parent.showAlert("恭喜","修改成功","info",function(){
                                $("#dg").datagrid("reload");
                                parent.$("#cover").css("display","none");
                                closeDialog();
                            })
                        }else{
                            parent.showAlert("错误",re.msg,"error");
                        }
                    })
                });
                parent.$("#cover").css("display","block");
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
                ht.data.getData("/api/bckplan/removeBckplan","POST",{bckplanAtpid:ckjhIds.join(",")},function (d) {
                    if(d["status"]=="1"){
                        parent.showAlert("恭喜","删除成功","info",function(){
                            $("#dg").datagrid("reload");
                        })
                    }else{
                        parent.showAlert("错误","删除失败","error");
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

});