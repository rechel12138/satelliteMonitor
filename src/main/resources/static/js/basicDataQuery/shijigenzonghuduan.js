$(function () {

    //获取权限生成操作按钮
    var menuAction =parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);
    $(".toolbar").find(".input_box").remove();
    $(".toolbar").find("#query").remove();

    //实际跟踪弧段表格：
    $('#sjgzhd').datagrid({
        url:'/api/bsjckgzhd/findByParamEzui',
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
            {field: 'bsat_code', title: '型号代号', sortable:true, width: 100,id:"sjgzhdSelPanel",align: 'center'},
            {field: 'bsjckgzhd_start_time', title: '开始时间',sortable:true,  width: 200, align: 'center'},
            {field: 'bsjckgzhd_end_time', title: '结束时间',sortable:true,  width:200, align: 'center'},
            {field: 'bsjckgzhd_devname', title: '测站信息', width: 150, align: 'center'}
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
            $('body').on("click","#sjgzhdSelPanel",function () {
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
    $("#ck_query_btn").click(function(e) {
        var starttime = new Date($(".validatebox-text:eq(0)").val()).getTime();
        var endtime = new Date($(".validatebox-text:eq(1)").val()).getTime();
        if (starttime>=endtime) {
            alert("结束时间不能小于开始时间");
            return;
        }
        var keyword = $(".ckkeywords").val();
        var stime = $(".validatebox-text:eq(0)").val();
        var etime = $(".validatebox-text:eq(1)").val();
        $("#sjgzhd").datagrid("load",
            {
                keyword:keyword,
                startTime:stime,
                endTime:etime
            });

    });

    // 打开弹窗：
    function openDialog(title,okBtnFunc){
        parent.createDialog({
            element:$("#sjgzhdtc"),
            elementId:"sjgzhdtc",
            title:title,
            icon:'',
            ok_button_handler:function(){
                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }

    //关闭弹窗
    function closeDialog(){
        parent.$("#sjgzhdtc").dialog("close");
    }

    //点击添加按钮:
    $("#add").on("click",function(){
        openDialog("添加实际跟踪弧段",function(){
            var data = getInputData("get");
            if(!data) return;
            ht.data.getData("/api/bsjckgzhd/addBsjckgzhd","POST",data,function(d){

                if(d["status"]=="1"){
                    parent.showAlert("恭喜","添加成功","info",function(){
                        $("#sjgzhd").datagrid("reload");
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
        var selectRows = $("#sjgzhd").datagrid("getSelections");
        // console.log(selectRows);
        if (selectRows.length == 1) {
            var sjgzhdId = selectRows[0]["bsjckgzhd_atpid"];
            doUpdate(sjgzhdId);
        } else {
            parent.showAlert("提示", "请选择1条数据进行修改", "warning");
        }
    });

    //点击删除按钮：
    $("#delete").on("click",function(){
        var selectRows = $("#sjgzhd").datagrid("getSelections");
        if(selectRows.length!=0){
            var sjgzhdIds = [];
            for(var k in selectRows){
                sjgzhdIds.push(selectRows[k]["bsjckgzhd_atpid"]);
            }
            doDelete(sjgzhdIds);
        }else{
            parent.showAlert("提示","未选择任何数据","warning");
        }
    });

    //点击导出excel按钮:
    $("#exportExcel").on("click",function(){
        // var url="/api/bsjckgzhd/exportExcel";
        // parent.document.location.href=url;
        var keyword = $(".ckkeywords").val();
        var stime = $(".validatebox-text:eq(0)").val();
        var etime = $(".validatebox-text:eq(1)").val();
        var url="/api/bsjckgzhd/exportExcel?keyword="+keyword+"&startTime="+stime+"&endTime="+etime;
        parent.document.location.href=url;

    });

    //获取用户输入数据
    function getInputData(type,data){
        var xhdh = parent.$("#xhdh");
        var option_id =xhdh.find("option:selected").attr("hiddenData");
        var starttime = parent.$("#sjgzhdtc .validatebox-text").eq(0);
        var endtime = parent.$("#sjgzhdtc .validatebox-text").eq(1);
        var czxx = parent.$("#czxx");

        if(type=="clear"){
            xhdh.val(null);
            starttime.val("");
            endtime.val("");
            czxx.val("");
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
            if(czxx.val()==""){
                parent.showAlert("提示","请输入测站信息","error");
                return false;
            }
            var data = {
                bsjckgzhdSatid:option_id,
                bsjckgzhdStartTime:starttime.val(),
                bsjckgzhdEndTime:endtime.val(),
                bsjckgzhdDevname:czxx.val()
            };
            // console.log(data);
            return data;
        }
        if(type=="set"){
            xhdh.val(data.bsat_code);
            starttime.val(data.bsjckgzhd_start_time);
            endtime.val(data.bsjckgzhd_end_time);
            czxx.val(data.bsjckgzhd_devname);
        }
    }

    //更新封装:
    function doUpdate(sjgzhdId){
        ht.data.getData("/api/bsjckgzhd/getOneById","POST",{atpId:sjgzhdId},function (d) {
            // console.log(d);
            if(d["status"]=="1"){
                openDialog("修改实际跟踪弧段",function(){
                    var data = getInputData("get");
                    // console.log(data);
                    if(!data) return;
                    data.bsjckgzhdAtpid = sjgzhdId;
                    ht.data.getData("/api/bsjckgzhd/updateBsjckgzhd","POST",data,function(re){
                        if(re["status"]=="1"){
                            parent.showAlert("恭喜","修改成功","info",function(){
                                $("#sjgzhd").datagrid("reload");
                                parent.$("#cover").css("display","none");
                                closeDialog();
                            })
                        }else{
                            parent.showAlert("错误",re.msg,"error");
                        }
                    })
                });

                parent.$("#starttime,#endtime").datetimebox({showSeconds: true});
                parent.$('#starttime,#endtime').next().find('a').css("right","2px");
                parent.$("#cover").css({display:"block", left:"79px"});
                getInputData("set",d["data"]);
            }else{
                parent.showAlert("错误","获取信息失败","error");
            }
        })
    }

    //删除封装：
    function doDelete(sjgzhdIds){
        parent.showConfirm("提示","确定删除"+sjgzhdIds.length+"条记录？",function(r){
            if(r){
                ht.data.getData("/api/bsjckgzhd/removeBsjckgzhd","POST",{bsjckgzhdAtpid :sjgzhdIds.join(",")},function (d) {
                    if(d["status"]=="1"){
                        parent.showAlert("恭喜","删除成功","info",function(){
                            $("#sjgzhd").datagrid("reload");
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
            ht.data.getData("/api/bsjckgzhd/findByParamEzui","POST",{bsatCodes:selectStr},function (data) {
                console.log(data);
                if(data.total>0) {
                    $('#sjgzhd').datagrid('loadData', data.rows);
                    $("#ck_tp_sel").hide();
                }else{
                    parent.showAlert('提示','没有这个型号代号','error');
                }
            })
        }else{
            ht.data.getData("/api/bsjckgzhd/findByParamEzui", "post",null,function (data) {
                // console.log(data);
                if(data.total>0){
                    $('#sjgzhd').datagrid('loadData', data.rows);
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
    var formatDate = function(datetime) {
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
