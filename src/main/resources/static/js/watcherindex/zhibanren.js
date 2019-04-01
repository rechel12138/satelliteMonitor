$(function() {
    //获取权限生成操作按钮
    // var menuAction = parent.getMenuInfo().menuAction;
    // var menuActionHTML = ht.ui.createMenuButton(menuAction);
    // $("#toolbar").html(menuActionHTML);

    //今日备忘事项表：
    $("#todotable").datagrid({
        url: '/api/bzbbwl/findBzByParamEzui',
        nowrap: true,
        fitColumns: true,
        striped: true,
        rownumbers: true,
        singleSelect: false,
        height:'117px',
        width:'100%',
        columns: [[
            {field: 'bzbbwl_satid', title: '型号', width: '8%', align: 'center'},
            {field: 'aaaaa', title: '异常等级', width: '6%', align: 'center'},
            {field: 'bzbbwl_showtitle', title: '标题/现象', width: '20%', align: 'center'},
            {field: 'bzbbwl_addtime', title: '发生时间', width: '15%', align: 'center'},
            {field: 'bzbbwl_showdetail', title: '详情/处理结果', width: '24%', align: 'center'},
            {field: 'su_chinesename', title: '添加人', width: '12%', align: 'center'},
            {field: 'bzbbwl_confirm', title: '请确认', width: '12%', align: 'center',
                formatter:function (value,row,index) {
                    // console.log(value);
                    if(value){
                        return value;
                    }else{
                        return '<a class="confirm_btn" href="javascript:;" row_id="'+row.bzbbwl_atpid+'">未确认</a>'
                    }

                }
            }
        ]]
    });

    //打开弹窗
    function openDialog(title,okBtnFunc){

        parent.createDialog({

            element:$("#"),
            elementId:"",
            title:title,
            icon:'',
            ok_button_handler:function(){

                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }

    //关闭弹窗
    function closeDialog(){
        parent.$("#addMemo").dialog("close");
        parent.$("#add_duty_panel").dialog("close");
        parent.$("#watcher_add_panel").dialog("close");
        parent.$("#sel_handle_res").dialog("close");
        parent.$("#search_panel").dialog("close");
    }

    //添加备忘弹窗：
    $("#btn_Add").on("click", function () {
        parent.createDialog({
            element: $("#addMemo"),
            elementId: "addMemo",
            title: '添加备忘',
            icon: '',
            display_cancel_button:false,
            ok_button_text:"提交",
            ok_button_handler: function () {
                var data = getInputDataBwl("get");
                if(!data) return;
                ht.data.getData("/api/bzbbwl/addBzbbwl","POST",data,function (d) {
                    console.log(data);
                    if(d["status"]=="1"){
                        parent.showAlert("恭喜","添加成功","info",function(){
                            $("#todotable").datagrid("reload");
                            closeDialog();
                        })
                    }
                })
            }
        });
        parent.$('#show_date').datetimebox({showSeconds: true});

        //获取型号代号列表：
        getAllType(parent.$("#addMemo_tl"));
        parent.$('#show_date').next().find('a').css("right","2px");

    });

    $("body").delegate(".confirm_btn","click",function(){
        var row_param = $(this).attr("row_id");
        var that = this;
        ht.data.getData("/api/bzbbwl/confirmBzbbwlByAtpId",'GET',{atpId:row_param},function (d) {
            console.log(d);
            if(d.status==1){
                $(that).text("确认");
            }
        });
    });

    //搜索弹窗：
    $("#btn_search").on("click", function () {
        parent.createDialog({
            element: $("#search_panel"),
            elementId: "search_panel",
            title: '搜索',
            icon: '',
            display_cancel_button:false,
            ok_button_text:"提交",
            ok_button_handler: function () {
                var sel_type = parent.$("#w_s_list").val();
                var sel_adder = parent.$("#sel_adder");
                if(sel_type==1){

                    function getParamForBw() {
                        var data = {};
                        parent.$("#search_panel input[type=checkbox]").each(function () {
                            if($(this).prop("checked")){
                                // console.log($(this).next());
                                data[$(this).attr("cus_prop")]=$(this).next().val()
                            }
                        });
                        return data;
                    }

                    var paramObj = getParamForBw ();
                    // console.log(paramObj);

                    ht.data.getData("/api/bzbbwl/findBzByParamEzui","POST",paramObj,function (d){
                        if(d.total>0){
                            $("#todotable").datagrid("loadData",d.rows);
                            closeDialog();
                        }else{
                            parent.showAlert("提示","没有搜到对应结果","warning");
                        }
                    })
                }else if(sel_type==2){

                    function getParamForZb() {
                        var data = {};
                        parent.$("#search_panel input[type=checkbox]").each(function () {
                            if($(this).prop("checked")){
                                // console.log($(this).next());
                                data[$(this).attr("cus_prop")]=$(this).next().val()
                            }
                        });
                        return data;
                    }

                    var par_Obj = getParamForZb ();

                    ht.data.getData("/api/bzbjl/listBzbjl","POST",par_Obj,function (d){
                        if(d.total>0){
                            $("#watcher").datagrid("loadData",d.rows);
                            closeDialog();
                        }else{
                            parent.showAlert("提示","没有搜到对应结果","warning");
                        }
                    })
                }
            }
        });
        //根据搜索选项切换搜说面板：
        parent.$("#w_s_list").change(function () {
            // console.log($(this).val());
            if($(this).val()=="1"){
                parent.$(".forWatchRecord").css("display","none");
                parent.$(".forMemo").css("display","block");
            }else{
                parent.$(".forWatchRecord").css("display","block");
                parent.$(".forMemo").css("display","none");
                getWcoList(parent.$("#watcher_sel_first"));
                getWctList(parent.$("#watcher_sel_second"));

            }
        });
        //值班日期切换时日历跳变：
        parent.$("#date_option").change(function(){
            if ($(this).val()=="1") {
                parent.$(".calendar_box_back").css("display","block");
                calendar_begin_time = formatDate(new Date().getTime()-24*60*60*1000);
            } else if($(this).val()=="3") {
                parent.$(".calendar_box_back").css("display","block");
                calendar_begin_time = formatDate(new Date().getTime()-72*60*60*1000);
            } else if($(this).val()=="7") {
                parent. $(".calendar_box_back").css("display","block");
                calendar_begin_time = formatDate(new Date().getTime()-168*60*60*1000);
            } else {
                parent.$(".calendar_box_back").css("display","none");
                return;
            }

            calendar_end_time = formatDate(new Date().getTime());
            parent.$(".forWatchRecord .validatebox-text:eq(0)").val(calendar_begin_time);
            parent.$(".forWatchRecord .validatebox-text:eq(1)").val(calendar_end_time);
        });

        //获取标题/详述/添加人的列表：
        if(parent.$("#w_s_list").val()=='1'){
            getTtList(parent.$("#bwl_title"));
            getDtList(parent.$("#bwl_msg"));
        }else{//获取值班人1/值班人2的列表：
            getWcoList(parent.$("#watcher_sel_first"));
            getWctList(parent.$("#watcher_sel_second"));
        }
        //获取添加人的列表：
        getAdList(parent.$("#sel_adder"));

        parent.$('#duty_date_start').datetimebox({showSeconds: true});
        parent.$('#duty_date_end').datetimebox({showSeconds: true});
        parent.$("#bwsx_datetime").datetimebox({showSeconds: true});
        getAllType(parent.$("#bw_satellite_type"));
        parent.$('#bwsx_datetime').next().find('a').css("right","2px");
    });

    //值班列表：
    $("#watcher").datagrid({
        url: '/api/bzbjl/listBzbjl',
        nowrap: true,
        fitColumns: true,
        striped: true,
        rownumbers: true,
        pagination:true,
        pageNumber: 1,
        pageSize: 2,
        pageList: [2],
        singleSelect: false,
        height:'154px',
        width:'100%',
        columns: [[
            {field: 'bzbjl_dutydate', title: '值班日期', width: 200, sortable:true,align: 'center'},
            {field: 'dutyName1', title: '值班员1', width: 100, align: 'center'},
            {field: 'dutyName2', title: '值班员2', width: 100, align: 'center'},
            {field: 'bzbjl_week', title: '星期', width: 100,align: 'center'},
            {field: 'addName', title: '添加人', width: 100, align: 'center'},
            {field: 'bzbjlAddtime', title: '创建时间', width: 200,sortable:true, align: 'center'},
            {field: 'bzbjl_atplastmodifydatetime', title: '修改时间', width: 200,sortable:true, align: 'center'}
        ]],

        //打开值班记录弹窗：
        onClickRow:function (rowIndex, rowData) {
            var bzbjlAtpid = rowData.bzbjl_atpid;
            // console.log(bzbjlAtpid);
            parent.createDialog({
                element: $("#watchRecords"),
                elementId: "watchRecords",
                title: '值班记录',
                icon: '',
                display_cancel_button:false,
                ok_button_text:"添加处理结果",
                ok_button_handler: function () {//添加处理结果弹窗
                    parent.createDialog({
                        element: $("#watcher_add_panel"),
                        elementId: "watcher_add_panel",
                        title: '添加处理结果',
                        icon: '',
                        display_cancel_button: false,
                        ok_button_text: "提交",
                        ok_button_handler: function () {
                            var data = getInputDataAddResult("get");
                            if(!data) return;
                            data.bzbswInfoid = bzbjlAtpid;
                            ht.data.getData("/api/bzbsw/addBzbsw","POST",data,function (d) {
                                console.log(data);
                                console.log(d);
                                if(d["status"]=="1"){
                                    parent.showAlert("恭喜","添加成功","info",function(){
                                        parent.$("#watchRecordList").datagrid("reload");
                                        closeDialog();
                                    })
                                }
                            })
                        }
                    });
                    getAllType(parent.$("#watcher_add_type_list"));
                    parent.$('#occurrence_time,#hangdle_time,#tck_stime,#tck_etime').datetimebox({showSeconds: true});
                    parent.$('#occurrence_time,#hangdle_time,#tck_stime,#tck_etime').next().find('a').css("right","2px");
                    getInputDataAddResult("clear");
                }
            });
            var select_btn = '<span id="sel_btn" style="padding:7px 14px;background-color:dodgerblue;color:#fff;vertical-align:middle;cursor:pointer;margin-right:10px;">搜索处理结果</span>';
            parent.$("a.l-btn-small").before($(select_btn));
            parent.$('body').delegate("#sel_btn","click",function () {
                parent.createDialog({
                    element: $("#sel_handle_res"),
                    elementId: "sel_handle_res",
                    title: '搜索处理结果',
                    icon: '',
                    display_cancel_button:false,
                    ok_button_text:"提交",
                    ok_button_handler: function () {
                        function getParamObj() {
                            var data = {};
                            parent.$("#sel_handle_res li input[type=checkbox]").each(function () {
                                if($(this).prop("checked")){

                                    // console.log($(this).attr("custom_prop"));
                                    data[$(this).attr("custom_prop")]=$(this).next().next().val()
                                }
                            });
                           return data;
                        }

                        var paramObj = getParamObj ();
                        // console.log(paramObj);
                        ht.data.getData("/api/bzbsw/getByAtpId ","POST",paramObj,function (d) {
                            // console.log(d);
                            parent.$('#watchRecordList').datagrid('loadData', d.rows);
                            closeDialog();
                        })

                    }
                });
                getAllType(parent.$("#sel_sat_type"));
                getPhList(parent.$("#selPhemList"));
                getResList(parent.$("#selHandleRes"));
                getAdList(parent.$("#selAdder"));
                getStList(parent.$("#selState"));

            });
            //值班记录表：
            parent.$("#watchRecordList").datagrid({
                url: '/api/bzbsw/getByAtpId',
                queryParams:{atpId:bzbjlAtpid},
                nowrap: true,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: false,
                singleSelect: false,
               /* pageNumber: 1,
                pageSize: 7,
                pageList: [7, 20, 30, 40, 50],*/
                height:'100%',
                width:'100%',
                columns: [[
                    {field: 'bzbsw_state', title: '状态', width: '6%', align: 'center'},
                    {field: 'bzbsw_satcode', title: '型号', width: '10%', align: 'center'},
                    {field: 'bzbsw_level', title: '异常等级', width: '10%', align: 'center'},
                    {field: 'bzbsw_infotype', title: '异常类型', width: '10%', align: 'center'},
                    {field: 'bzbsw_phemtime', title: '现象发生时间', width: '13%', align: 'center'},
                    {field: 'bzbsw_phemdesc', title: '现象', width: '10%', align: 'center'},
                    {field: 'bzbsw_reslttime', title: '处理完成时间', width: '13%', align: 'center'},
                    {field: 'bzbsw_sendto', title: '发送给', width: '5%', align: 'center'},
                    {field: 'addUserName', title: '添加人', width: '15%', align: 'center'},
                    {field: 'bzbsw_addtime', title: '添加时间', width: '13%', align: 'center'},
                    {field: 'bzbsw_modifytime', title: '修改时间', width: '13%', align: 'center'}
                ]]
            });

            //搜索处理结果时获取现象的列表：
            function getPhList(ph_obj) {
                ht.data.getData("/api/bzbsw/getByAtpId", "POST", {atpId: bzbjlAtpid}, function (d) {
                    // console.log(d);
                    var phemListStr = '';//现象列表
                    for (var i = 0; i < d.rows.length; i++) {
                        phemListStr += '<option value=' + d.rows[i].bzbsw_phemdesc + '>' + d.rows[i].bzbsw_phemdesc + '</option>';
                    }
                    ph_obj.html(phemListStr);
                });
            }
             //搜索处理结果时获取处理结果的列表：
            function getResList(res_obj) {
                ht.data.getData("/api/bzbsw/getByAtpId", "POST", {atpId: bzbjlAtpid}, function (d) {
                    // console.log(d);

                    var resListStr = '';//处理结果列表

                    for (var i = 0; i < d.rows.length; i++) {
                        resListStr += '<option value=' + d.rows[i].bzbsw_resltdesc + '>' + d.rows[i].bzbsw_resltdesc + '</option>';
                    }
                    res_obj.html(resListStr);
                });
            }
             //搜索处理结果时获取添加人的列表：
            function getAdList(ad_obj) {
                ht.data.getData("/api/bzbsw/getByAtpId", "POST", {atpId: bzbjlAtpid}, function (d) {
                    // console.log(d);
                    var adderListStr = '';//添加人列表
                    for (var i = 0; i < d.rows.length; i++) {
                        adderListStr += '<option value=' + d.rows[i].bzbsw_addperson + '>' + d.rows[i].bzbsw_addperson + '</option>';
                    }
                    ad_obj.html(adderListStr);
                });
            }
            //搜索处理结果时获取状态的列表：
            function getStList(st_obj) {
                ht.data.getData("/api/bzbsw/getByAtpId", "POST", {atpId: bzbjlAtpid}, function (d) {
                    // console.log(d);
                    var stateListStr = '';//状态列表
                    for (var i = 0; i < d.rows.length; i++) {
                        stateListStr += '<option value=' + d.rows[i].bzbsw_state + '>' + d.rows[i].bzbsw_state + '</option>';
                    }
                    st_obj.html(stateListStr);
                });
            }

        }
    });

    //新建值班列表：
    $("#new_btn").on("click", function () {
        parent.createDialog({
            element: $("#add_duty_panel"),
            elementId: "add_duty_panel",
            title: '新建值班列表',
            icon: '',
            display_cancel_button:false,
            ok_button_text:"提交",
            ok_button_handler: function () {
                // console.log(123);
                var data = getInputDataZblb("get");
                // console.log(data);
                if(!data) return;
                ht.data.getData("/api/bzbjl/addBzbjl","POST",data,function (d) {
                    if(d["status"]=="1"){
                        parent.showAlert("恭喜","添加成功","info",function(){
                            $("#watcher").datagrid("reload");
                            closeDialog();
                        })
                    }
                })
            }
        });
        parent.$("#add_duty_date").datetimebox({showSeconds: true});
        parent.$('#add_duty_date').next().find('a').css("right","2px");
    });

    //添加处理结果的三个面板切换：
    $("#handle_type").change(function () {
        if ($(this).val() == '1') {
            parent.$(".else").css("display", "none");
            parent.$(".cksj").css("display", "none");
            parent.$(".zgyc").css("display", "block")
        } else if ($(this).val() == '2') {
            parent.$(".else").css("display", "block");
            parent.$(".cksj").css("display", "none");
            parent.$(".zgyc").css("display", "none")
        } else {
            parent.$(".else").css("display", "none");
            parent.$(".cksj").css("display", "block");
            parent.$(".zgyc").css("display", "none")
        }
    });

    //是否需要追踪时切换下一条：
    $('input[name="w_a_blean"]').click(function () {
        if(this.value=='是'){
            parent.$(".isTaraceToggle").css("display","block")
        }else{
            parent.$(".isTaraceToggle").css("display","none")
        }
    });

    //搜索备忘录时获取用户输入：
    // function getInputSelBwl(type) {
    //     var bwl_date = parent.$(".forMemo .validatebox-text");
    //     var bwl_title = parent.$("#bwl_title");
    //     var bwl_msg = parent.$("#bwl_msg");
    //     var bw_satellite_type = parent.$("#bw_satellite_type");
    //     var bwl_state = parent.$("#bwl_state");
    //     if (type == "get") {
    //         if (bwl_date.val() == "") {
    //             parent.showAlert("提示", "请输入日期", "error");
    //             return false;
    //         }
    //         if (bwl_title.val() == "") {
    //             parent.showAlert("提示", "请输入标题", "error");
    //             return false;
    //         }
    //         if (bwl_msg.val() == "") {
    //             parent.showAlert("提示", "请输入详述", "error");
    //             return false;
    //         }
    //         var data = {
    //             fssj: bwl_date.val(),//日期
    //             title: bwl_title.val(),//标题
    //             detail: bwl_msg.val(),//详述
    //             satcode:bw_satellite_type.val(),//型号
    //             state:bwl_state.val(),//状态
    //             addperson:sel_adder.val()//添加人
    //         };
    //         // console.log(data);
    //         return data;
    //     }
    //     if (type == "clear") {
    //         bwl_date.val() == "";
    //         bwl_title.val() == "";
    //         bwl_msg.val() == "";
    //     }
    // }

    // //搜索值班记录时获取用户输入：
    // function getInputSelFwr(type) {
    //     var date_option = parent.$("#date_option");
    //     var sel_time = parent.$(".forWatchRecord .validatebox-text");
    //     var watcher_sel_first = parent.$("#watcher_sel_first");
    //     var watcher_sel_second = parent.$("#watcher_sel_second");
    //     if (type == "get") {
    //         if (sel_time.eq(0).val() == "") {
    //             parent.showAlert("提示", "请输入开始时间", "error");
    //             return false;
    //         }
    //         if (sel_time.eq(1).val() == "") {
    //             parent.showAlert("提示", "请输入结束时间", "error");
    //             return false;
    //         }
    //
    //         var data = {
    //             // :sel_time.eq(0).val(),//开始时间
    //             // :sel_time.eq(1).val(),//结束时间
    //             // :watcher_sel_first.val(),//值班人1
    //             // :watcher_sel_second.val(),//值班人2
    //             addperson:sel_adder.val()//添加人
    //         };
    //         // console.log(data);
    //         return data;
    //     }
    //     if (type == "clear") {
    //         sel_time.eq(0).val() == "";
    //         sel_time.eq(1).val() == "";
    //     }
    // }

    //添加备忘录时获取用户输入数据
    function getInputDataBwl(type){
        //备忘录：
        var show_date = parent.$(".validatebox-text");
        var show_period = parent.$(".validatebox-text");
        var addMemo_tl = parent.$("#addMemo_tl");
        var addMemo_title = parent.$("#addMemo_title");
        var addMemo_detail = parent.$("#addMemo_detail");

        // console.log(show_period.val());


        if(type=="clear"){
            //备忘录:
            show_date.val("");
            show_period.val("");
            addMemo_tl.val("");
            addMemo_title.val("");
            addMemo_detail.val("");
        }
        if(type=="get"){
            if(show_date.val()==""){
                parent.showAlert("提示","请输入显示日期","error");
                return false;
            }
            if(show_period.val()==null){
                parent.showAlert("提示","请输入显示周期","error");
                return false;
            }
            if(addMemo_tl.val()==""){
                parent.showAlert("提示","请输入型号","error");
                return false;
            }
            if(addMemo_title.val()==""){
                parent.showAlert("提示","请输入标题","error");
                return false;
            }
            if(addMemo_detail.val()==""){
                parent.showAlert("提示","请输入详情","error");
                return false;
            }

            var data = {
                bzbbwlShowdate:show_date.val(),
                bzbbwlShowdays:show_period.val(),
                bzbbwlSatid:addMemo_tl.val(),
                bzbbwlShowtitle:addMemo_title.val(),
                bzbbwlShowdetail:addMemo_detail.val()
            };
            // console.log(data);
            return data;
        }
        if(type=="set"){
            show_date.val(data.bzbbwlShowdate);
            show_period.val(data.bzbbwlShowdays);
            addMemo_tl.val(data.bzbbwlSatid);
            addMemo_title.val(data.bzbbwlShowtitle);
            addMemo_detail.val(data.bzbbwlShowdetail);
        }
    }

    //新建值班列表时获取用户输入数据
    function getInputDataZblb(type){
        var add_duty_date = parent.$("#add_duty_panel .validatebox-text");
        var watcher_first_list = parent.$("#watcher_first_list");
        var watcher_second_list = parent.$("#watcher_second_list");
        var add_week_list = parent.$("#add_week_list");

        if(type=="clear"){
            add_duty_date.val("");

        }
        if(type=="get"){
            if(add_duty_date.val()==""){
                parent.showAlert("提示","请输入值班日期","error");
                return false;
            }
            var data = {
                bzbjlDutydate:add_duty_date.val(),
                bzbjlPerson1:watcher_first_list.val(),
                bzbjlPerson2:watcher_second_list.val(),
                bzbjlWeek:'星期'+add_week_list.val()
            };
            console.log(data);
            return data;
        }
    }

    //添加处理结果时获取用户输入数据
    function getInputDataAddResult(type){
        var watcher_add_type_list = parent.$("#watcher_add_type_list");
        var handle_type = parent.$("#handle_type");
        var abnormal_type = handle_type.find("option:selected").text();

        //报警处理/链路中断/来电记录...：
        var occurrence_time = parent.$(".occ_time .validatebox-text");//发生时间
        var ph_txt = parent.$("#ph_txt");//现象详述
        var hangdle_time = parent.$(".stop_time .validatebox-text");//结束时间
        var re_txt = parent.$("#re_txt");//结果详述
        //测控事件：
        var tck_stime = parent.$('.cj_stime .validatebox-text');//测控计划开始时间
        var tck_etime = parent.$('.cj_etime .validatebox-text');//结束时间
        var tck_msxx = parent.$('#tck_msxx');//描述信息
        //在轨异常:
        var abnormal_level = parent.$('#abnormal_level');//异常等级

        var isTrace = parent.$('input[name="w_a_blean"]:checked');
        var sendTo = parent.$('input[name="w_a_sel"]:checked');

        if(type=="clear"){
            watcher_add_type_list.val("");
            handle_type.val("");
            //报警处理/链路中断/来电记录...：
            occurrence_time.val("");
            ph_txt.val("");
            hangdle_time.val("");
            re_txt.val("");
            //测控事件：
            tck_stime.val("");
            tck_etime.val("");
            tck_msxx.val("");
            //在轨异常:
            abnormal_level.val(null);

            isTrace.prop("checked", false);
            sendTo.val(null);
        }
        if(type=="get"){
            if(watcher_add_type_list.val()==null){
                parent.showAlert("提示","请选择型号","error");
                return false;
            }
            if(handle_type.val()==null){
                parent.showAlert("提示","请选择异常类型","error");
                return false;
            }
            if(handle_type.val()=="2"){//报警处理，链路中断，来电记录...
                if(occurrence_time.val()==""){
                    parent.showAlert("提示","请选择现象发生时间","error");
                    return false;
                }
                if(ph_txt.val()==""){
                    parent.showAlert("提示","请输入现象详述","error");
                    return false;
                }
                if(hangdle_time.val()==""){
                    parent.showAlert("提示","请选择结束时间","error");
                    return false;
                }
                if(re_txt.val()==""){
                    parent.showAlert("提示","请输入结果详述","error");
                    return false;
                }
                if(isTrace.val()==null){
                    parent.showAlert("提示","请选择是否需要追踪","error");
                    return false;
                }
                if(isTrace.val()=='是'&& sendTo.val()==null){
                    parent.showAlert("提示","请选择发送给谁","error");
                    return false;
                }

                var data = {
                    bzbswSatcode:watcher_add_type_list.val(),//型号
                    bzbswInfotype:abnormal_type,//类型
                    bzbswPhemtime:occurrence_time.val(),//发生时间
                    bzbswPhemdesc:ph_txt.val(),//详述
                    bzbswReslttime:hangdle_time.val(),//结束时间
                    bzbswResltdesc:re_txt.val(),//详述
                    isTrace:isTrace.val(),//是否追踪
                    bzbswSendto:sendTo.val()//发送给谁
                };
                return data;
            }
            if(handle_type.val()=="1"){//在轨异常
                console.log(abnormal_level.val());
                if(abnormal_level.val()==null){
                    parent.showAlert("提示","请选择异常等级","error");
                    return false;
                }
                if(isTrace.val()==null){
                    parent.showAlert("提示","请选择是否需要追踪","error");
                    return false;
                }
                if(isTrace.val()=='是'&& sendTo.val()==null){
                    parent.showAlert("提示","请选择发送给谁","error");
                    return false;
                }
                var data = {
                    bzbswSatcode:watcher_add_type_list.val(),//型号
                    bzbswInfotype:abnormal_type,//类型
                    bzbswLevel:abnormal_level.val(),//异常等级
                    isTrace:isTrace.val(),//是否追踪
                    bzbswSendto:sendTo.val()//发送给谁
                };
                return data;
            }
            if(handle_type.val()=="3"){//测控事件
                if(tck_stime.val()==""){
                    parent.showAlert("提示","请选择测控计划开始时间","error");
                    return false;
                }
                if(tck_etime.val()==""){
                    parent.showAlert("提示","请选择测控计划结束时间","error");
                    return false;
                }
                if(tck_msxx.val()==""){
                    parent.showAlert("提示","请输入描述信息","error");
                    return false;
                }
                if(isTrace.val()==null){
                    parent.showAlert("提示","请选择是否需要追踪","error");
                    return false;
                }
                if(isTrace.val()=='是'&& sendTo.val()==null){
                    parent.showAlert("提示","请选择发送给谁","error");
                    return false;
                }
                var data = {
                    bzbswSatcode:watcher_add_type_list.val(),//型号
                    bzbswInfotype:abnormal_type,//类型
                    bzbswPhemtime:tck_stime.val(),//测控计划开始时间
                    bzbswReslttime:tck_etime.val(),//测控计划结束时间
                    bzbswPhemdesc:tck_msxx.val(),//描述信息
                    isTrace:isTrace.val(),//是否追踪
                    bzbswSendto:sendTo.val()//发送给谁
                };
                return data;
            }
        }
    }


    //近期卫星报警信息图表：
    var a_d_list = $("#alarm_date_list").val();
    ht.data.getData("/api/balarmfx/findListByParam", "POST", {numDays:a_d_list}, function (d) {
        if (d["status"] == "1") {
            var data = d.data;
            // console.log(data["bsatCode"]);
            // console.log(data["count"]);
            var ccc = ['CE-001','CE-0002','CE-0003','CE-0004','CE-0005','CE-0006','CE-0007','CE-0008','CE-0009','CE-00010','BD-001','BD-002','BD-003','BD-004','BD-005','BD-006','BD-007','BD-008','BD-009','BD-010'];
            var ddd = [7,10,30,23,17,29,41,34,28,19,43,23,23,15,25,34,45,34,29,39];

            var myChart = echarts.init(document.getElementById("recentAlarmChart"));
            var option = {
                title: {
                    text: ''
                },
                legend: {
                    data: ['报警次数']
                },
                grid:{//坐标系离容器的距离
                    x:30,
                    y:20
                },
                xAxis: {
                    // data: data["bsatCode"],
                    data:ccc,
                    axisLabel: {
                        interval: 0,// 0 强制显示所有，1为隔一个标签显示一个标签，2为隔两个
                        rotate: 45,//标签旋转角度，在标签显示不下的时可通过旋转防止重叠
                        textStyle:{fontSize:14}//x轴下面文本样式
                    }
                },
                yAxis: {
                    min: 0,//y轴数值域
                    max: 60
                },
                series: [{
                    name: '',
                    type: 'bar',
                    // data: data["count"],
                    data:ddd,
                    itemStyle: {
                        normal: {
                            color: 'rgb(58,160,255)',//柱图的颜色
                            label: {
                                show: true,		//开启显示
                                position: 'top',    //在上方显示
                                textStyle: {
                                    color: 'black',//柱图顶部的文本颜色
                                    fontSize: 14
                                }
                            }
                        }
                    },
                    barWidth: '40px',//柱子宽度
                    // barCategoryGap:'同一系列的柱间距离，默认为类目间距的20%，可设固定值',
                    barCategoryGap: '200px'
                }],

                axisLabel: {
                    interval: 0// 0 强制显示所有，1为隔一个标签显示一个标签，2为隔两个
                }
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option)

        } else {
            parent.showAlert("提示", d.msg, "warning");
        }
    });
    $("#alarm_date_list").change(function () {
        var a_d_list = $("#alarm_date_list").val();
        ht.data.getData("/api/balarmfx/findListByParam", "POST", {numDays:a_d_list}, function (d) {
            if (d["status"] == "1") {
                var data = d.data;
                var myChart = echarts.init(document.getElementById("recentAlarmChart"));
                var option = {
                    title: {
                        text: ''
                    },
                    legend: {
                        data: ['报警次数']
                    },
                    grid:{
                        x:30,
                        y:20
                    },
                    xAxis: {
                        data: data["bsatCode"],
                        axisLabel: {
                            interval: 0,// 0 强制显示所有，1为隔一个标签显示一个标签，2为隔两个
                            rotate: 45//标签旋转角度，在标签显示不下的时可通过旋转防止重叠
                        }
                    },
                    yAxis: {
                        min: 0,
                        max: 60
                    },
                    series: [{
                        name: '',
                        type: 'bar',
                        data: data["count"],
                        itemStyle: {
                            normal: {
                                color: 'rgb(58,160,255)',
                                label: {
                                    show: true,		//开启显示
                                    position: 'top',	//在上方显示
                                    textStyle: {	    //数值样式
                                        color: 'black',
                                        fontSize: 12
                                    }
                                }
                            }
                        },
                        barWidth: '100px',
                        // barCategoryGap:'同一系列的柱间距离，默认为类目间距的20%，可设固定值',
                        barCategoryGap: '80%'
                    }],

                    axisLabel: {
                        interval: 0// 0 强制显示所有，1为隔一个标签显示一个标签，2为隔两个
                    }
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option)

            } else {
                parent.showAlert("提示", d.msg, "warning");
            }
        })
    });

    //获取所有卫星的型号代号：
    function getAllType(obj) {
        ht.data.getData("/api/bsat/getAllSatIds","POST",null,function (d){
            if(d.status == 1){
                var typeListStr = '';//型号代号列表，单选
                for(var i=0;i<d.data.length;i++){
                    typeListStr+='<option value='+d.data[i].code+'>'+d.data[i].code+'</option>'
                }
                obj.html(typeListStr);//将型号代号列表放进列表中
            }
        });
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
    };


    //搜索备忘事项时获取标题/详述的列表：
    function getTtList(tt_obj) {
        ht.data.getData("/api/bzbbwl/findBzByParamEzui", "POST", null, function (d) {
            var titleListStr = '';
            for (var i = 0; i < d.rows.length; i++) {
                titleListStr += '<option value=' + d.rows[i].bzbbwl_showtitle + '>' + d.rows[i].bzbbwl_showtitle + '</option>';
            }
            tt_obj.html(titleListStr);
        });
    }
    //搜索备忘事项时获取详述的列表：
    function getDtList(dt_obj) {
        ht.data.getData("/api/bzbbwl/findBzByParamEzui", "POST",null, function (d) {
            var detailListStr = '';
            for (var i = 0; i < d.rows.length; i++) {
                detailListStr += '<option value=' + d.rows[i].bzbbwl_showdetail + '>' + d.rows[i].bzbbwl_showdetail + '</option>';

            }
            dt_obj.html(detailListStr);
        });
    }


    //搜索值班记录时获取值班人1的列表：
    function getWcoList(st_obj) {
        ht.data.getData("/api/bzbjl/listBzbjl", "POST",null, function (d) {
            console.log(d);
            var stateListStr = '';//状态列表
            for (var i = 0; i < d.rows.length; i++) {
                stateListStr += '<option value=' + d.rows[i].dutyName1 + '>' + d.rows[i].dutyName1 + '</option>';
            }
            st_obj.html(stateListStr);
        });
    }
    //搜索值班记录时获取值班人2的列表：
    function getWctList(st_obj) {
        ht.data.getData("/api/bzbjl/listBzbjl", "POST",null, function (d) {
            // console.log(d);
            var stateListStr = '';//状态列表
            for (var i = 0; i < d.rows.length; i++) {
                stateListStr += '<option value=' + d.rows[i].dutyName2 + '>' + d.rows[i].dutyName2 + '</option>';
            }
            st_obj.html(stateListStr);
        });
    }

    //搜索值班记录时获取添加人的列表：
    function getAdList(ad_obj) {
        ht.data.getData("/api/bzbsw/getByAtpId", "POST",null, function (d) {
            // console.log(d);
            var adderListStr = '';//添加人列表
            for (var i = 0; i < d.rows.length; i++) {
                adderListStr += '<option value=' + d.rows[i].bzbsw_atpcreateuser + '>' + d.rows[i].bzbsw_atpcreateuser + '</option>';
            }
            ad_obj.html(adderListStr);
        });
    }

});
