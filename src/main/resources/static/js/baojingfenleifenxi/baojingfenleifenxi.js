$(function(){

    //获取权限生成操作按钮
    //数据库未配置权限TODO...
    var menuAction = parent.getMenuInfo().menuAction;

    //给报警开始和结束时间赋值，默认为当前时间
    setQueryTime(0);

    //自定义时间选择
    $("#dateSelect").on("change",function(){

        var v = $(this).val();

        switch (v){

            case 'yesterday':
                setQueryTime(1);
                break;
            case 'lasted_two':
                setQueryTime(2);
                break;
            case 'lasted_week':
                setQueryTime(7);
                break;
            case 'lasted_month':
                setQueryTime(30);
                break;
            case 'lasted_threemonth':
                setQueryTime(90);
                break;
            case 'lasted_halfyear':
                setQueryTime(183);
                break;

        }

    });

    //加载数据表
    var star_param=getPara();
    ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",star_param,function (data) {
        var _data=data.rows;
        $('#bjflfx').datagrid({

            data:_data,
            nowrap: true,
            fitColumns:true,
            striped: true,
            rownumbers:true,
            pagination:true,
            singleSelect:false,
            pageNumber:1,
            pageSize:10,
            pageList:[10,20,30,40,50],
            height:'100%',
            width:'100%',
            // queryParams:getPara(),
            columns:[[
                {field:'check_col',checkbox: true,width: '50',rowspan:2},
                {field:'bsatCode',title: '型号代号&nbsp▼', width: '100',align: 'center', resizable:false,sortable:true,rowspan:2,id:'btn_code'},
                {field:'alarmBeginTime',title: '开始时间', width: '135',align: 'center', resizable:true,rowspan:2},
                {field:'alarmSource',title: '报警来源&nbsp▼', width: '130',align: 'center', resizable:true,sortable:true,rowspan:2,id:"btn_source"},
                {field:'confirmTime',title: '确认时间', width: '135',align: 'center', resizable:true,rowspan:2},
                {field:'alarmEndTime',title: '结束时间', width: '135',align: 'center', resizable:true,rowspan:2},
                {field:'alarmMsg',title: '报警信息', width: '130',align: 'center', resizable:true,rowspan:2},
                {field:'currentVal',title: '当前值', width: '100',align: 'center', resizable:true,rowspan:2},
                {field:'alarmVal',title: '报警值', width: '100',align: 'center', resizable:true,rowspan:2},
                {field:'alarmLevel',title: '报警级别', width: '70',align: 'center', resizable:true,rowspan:2},
                {field:'dd',title: '值班人预判',width: '',align: 'center', resizable:true,colspan:2},
                {field:'ccc',title: '责任人分析', width: '165',align: 'center', resizable:true,colspan:2},
                {field:'confirmUser',title: '确认人',width: '80',align: 'center', resizable:true,rowspan:2},
                {field:"aaaaaaaa",title: '报警分析', width: '80',align: 'center', resizable:true,rowspan:2,
                    formatter:function (v,r) {
                        return '<a href="#" class="flfx_detail" said="'+r.satId+'" atpid="'+r.atpid+'" alarmcode="'+r.bsatCode+'">详情</a>';
                    }
                },
                {field:'bbbb',title: '遥测趋势', width: '80',align: 'center', resizable:true,rowspan:2,formatter:function(v,r){

                        function createTime(min){

                            var myDate = new Date();

                            if(min!=0){

                                myDate = new Date(myDate.getTime()-min*60*1000);
                            }

                            var y = myDate.getFullYear();
                            var m = myDate.getMonth()+1;
                            m = m<10?"0"+m:m;
                            var d = myDate.getDate();
                            d = d<10?"0"+d:d;
                            var h = myDate.getHours();
                            h = h<10?"0"+h:h;
                            var M = myDate.getMinutes();
                            M = M<10?"0"+M:M;
                            var s = myDate.getSeconds();
                            s = s<10?"0"+s:s;

                            return y.toString()+m.toString()+d.toString()+h.toString()+M.toString()+s.toString();
                        }

                        var s = createTime(0);
                        var e = createTime(30);
                        var url = "http://10.64.4.86/db/index.html?";
                        url+="T1="+s+"&T2="+e+"&TID="+r.paramCode+"&SID="+r.bsatCode;
                        return "<a href='"+url+"' target='_parent'>查看趋势图</a>";
                    }}
            ],[
                {field:'confirmType',title: '报警类别', width: '100',align: 'center', resizable:true,rowspan:1},
                {field:'remark',title: '处理结果', width: '100',align: 'center', resizable:true,rowspan:1},
                {field:'realConfirmType',title: '确认类别', width: '120',align: 'center', resizable:true,rowspan:1,
                    formatter:function (v,r) {

                        var options = ["地面异常报警","星上异常报警","测控事件","乱码和野值","门限设置不当","其他","知识错误"];
                        var options_final = [];
                        for(var i=0;i<options.length;i++){

                            if(options[i]==v){

                                options_final.unshift("<option value='"+options[i]+"'>"+options[i]+"</option>");

                            }else{

                                options_final.push("<option value='"+options[i]+"'>"+options[i]+"</option>");
                            }
                        }
                        var s = $("<select class='confirm_type_"+r.atpid+"'></select>");
                        s.append(options_final.join(""));
                        return s.prop("outerHTML");
                    }
                },
                {field:'relRemark',title: '情况说明',width: '120',align: 'center', resizable:true,rowspan:1,formatter:function(v,r){

                        if(v==null) v="";
                        return '<input class="_relRemark  _re_'+r.atpid+'" value="'+v+'" style="width:100px;border:1px solid #dadada" />';
                    }}
            ]
            ],
            onLoadSuccess:function(data){
                console.log(data)
                // 取消选择
                $("#selectUl").find('input[type=checkbox]').prop('checked',false);
                $("body").undelegate("#btn_code","click").delegate("#btn_code","click",function(){
                    $("#table_sort").attr("sorparam","bsatCode");
                    $("#table_sort").css("left","67px");
                    $("#table_sort .search_name").text("型号代号;");
                     var isShow=$("#table_sort").attr("istrue");
                     if(isShow==0){
                         // 初始化筛选
                         ht.data.getData("/api/bsat/getAllSatIds", "post",{},function (checkD) {
                             if(checkD.status==1){
                                 var str="";
                                 for(var i=0;i<checkD.data.length;i++){
                                     str+='<li style="padding:5px 0;width:100%"><label for="'+checkD.data[i].code+'"><input type="checkbox" id="'+checkD.data[i].code+'">&nbsp&nbsp&nbsp'+checkD.data[i].code+'</label></li>';
                                 }
                                 $("#selectUl").html(str);
                             }
                         });
                         $("#table_sort").show();
                         $("#table_sort").attr("istrue","1");
                     }else{
                         $("#table_sort").hide();
                         $("#table_sort").attr("istrue","0");
                     }
                });
                $("body").undelegate("#btn_source","click").delegate("#btn_source","click",function(){
                    $("#table_sort").attr("sorparam","alarmSource");
                    $("#table_sort").css("left","294px");
                    $("#table_sort .search_name").text("报警来源:");
                    var isShow=$("#table_sort").attr("istrue");
                    if(isShow==0){
                        // 初始化筛选
                        var str="";
                        str+='<li style="padding:5px 0;width:100%"><label for="三级门限延时报警"><input type="checkbox" id="三级门限延时报警">&nbsp&nbsp&nbsp三级门限延时报警</label></li>';
                        str+='<li style="padding:5px 0;width:100%"><label for="三级门限实时报警"><input type="checkbox" id="三级门限实时报警">&nbsp&nbsp&nbsp三级门限实时报警</label></li>';
                        str+='<li style="padding:5px 0;width:100%"><label for="多星智能诊断系统实时报警"><input type="checkbox" id="多星智能诊断系统实时报警">&nbsp&nbsp&nbsp多星智能诊断系统实时报警</label></li>';
                        $("#selectUl").html(str);
                        $("#table_sort").attr("istrue","1");
                        $("#table_sort").show();
                    }else{
                        $("#table_sort").attr("istrue","0");
                        $("#table_sort").hide();
                    }
                });

            }
        });
    });
    /*$('#bjflfx').datagrid({

            url: '/api/balarmfx/findAlarmListByParam',
            nowrap: true,
            fitColumns:true,
            striped: true,
            rownumbers:true,
            pagination:true,
            singleSelect:false,
            pageNumber:1,
            pageSize:10,
            pageList:[10,20,30,40,50],
            height:'100%',
            width:'100%',
            queryParams:getPara(),
            columns:[[
                {field:'check_col',checkbox: true,width: '50',rowspan:2},
                {field:'bsatCode',title: '型号代号', width: '100',align: 'center', resizable:false,sortable:true,rowspan:2,id:'btn_code'},
                {field:'alarmBeginTime',title: '开始时间', width: '135',align: 'center', resizable:true,sortable: true,rowspan:2},
                {field:'alarmSource',title: '报警来源', width: '130',align: 'center', resizable:true,rowspan:2},
                {field:'confirmTime',title: '确认时间', width: '135',align: 'center', resizable:true,sortable: true,rowspan:2},
                {field:'alarmEndTime',title: '结束时间', width: '135',align: 'center', resizable:true,sortable: true,rowspan:2},
                {field:'alarmMsg',title: '报警信息', width: '130',align: 'center', resizable:true,rowspan:2},
                {field:'currentVal',title: '当前值', width: '100',align: 'center', resizable:true,sortable: true,rowspan:2},
                {field:'alarmVal',title: '报警值', width: '100',align: 'center', resizable:true,sortable: true,rowspan:2},
                {field:'alarmLevel',title: '报警级别', width: '70',align: 'center', resizable:true,rowspan:2},
                {field:'dd',title: '值班人预判',width: '',align: 'center', resizable:true,colspan:2},
                {field:'ccc',title: '责任人分析', width: '165',align: 'center', resizable:true,colspan:2},
                {field:'confirmUser',title: '确认人',width: '80',align: 'center', resizable:true,rowspan:2},
                // {field:"aaaaaaaa",title: '报警分析', width: '80',align: 'center', resizable:true,rowspan:2,
                //     formatter:function () {
                //         return '<a href="#">详情</a>';
                //     }
                // },
                {field:'bbbb',title: '遥测趋势', width: '80',align: 'center', resizable:true,rowspan:2,formatter:function(v,r){

                        function createTime(min){

                            var myDate = new Date();

                            if(min!=0){

                                myDate = new Date(myDate.getTime()-min*60*1000);
                            }

                            var y = myDate.getFullYear();
                            var m = myDate.getMonth()+1;
                            m = m<10?"0"+m:m;
                            var d = myDate.getDate();
                            d = d<10?"0"+d:d;
                            var h = myDate.getHours();
                            h = h<10?"0"+h:h;
                            var M = myDate.getMinutes();
                            M = M<10?"0"+M:M;
                            var s = myDate.getSeconds();
                            s = s<10?"0"+s:s;

                            return y.toString()+m.toString()+d.toString()+h.toString()+M.toString()+s.toString();
                        }

                        var s = createTime(0);
                        var e = createTime(30);
                        var url = "http://10.64.4.86/db/index.html?";
                        url+="T1="+s+"&T2="+e+"&TID="+r.paramCode+"&SID="+r.bsatCode;
                        return "<a href='"+url+"' target='_parent'>查看趋势图</a>";
                    }}
            ],[
                {field:'confirmType',title: '报警类别', width: '100',align: 'center', resizable:true,rowspan:1},
                {field:'remark',title: '处理结果', width: '100',align: 'center', resizable:true,rowspan:1},
                {field:'realConfirmType',title: '确认类别', width: '120',align: 'center', resizable:true,rowspan:1,
                    formatter:function (v,r) {

                        var options = ["地面异常报警","星上异常报警","测控事件","乱码和野值","门限设置不当","其他","知识错误"];
                        var options_final = [];
                        for(var i=0;i<options.length;i++){

                            if(options[i]==v){

                                options_final.unshift("<option value='"+options[i]+"'>"+options[i]+"</option>");

                            }else{

                                options_final.push("<option value='"+options[i]+"'>"+options[i]+"</option>");
                            }
                        }
                        var s = $("<select class='confirm_type_"+r.atpid+"'></select>");
                        s.append(options_final.join(""));
                        return s.prop("outerHTML");
                    }
                },
                {field:'relRemark',title: '情况说明',width: '120',align: 'center', resizable:true,rowspan:1,formatter:function(v,r){

                       if(v==null) v="";
                       return '<input class="_relRemark  _re_'+r.atpid+'" value="'+v+'" style="width:100px;border:1px solid #dadada" />';
                    }}
            ]
            ],
        onLoadSuccess:function(){
                // 初始化筛选
            ht.data.getData("/api/bsat/getAllSatIds", "post",{},function (checkD) {
                if(checkD.status==1){
                    var str="";
                    for(var i=0;i<checkD.data.length;i++){
                        str+='<li style="padding:5px 0;width:100%"><label for="'+checkD.data[i].code+'"><input type="checkbox" id="'+checkD.data[i].code+'">&nbsp&nbsp&nbsp'+checkD.data[i].code+'</label></li>';
                    }
                    $("#selectUl").html(str);
                }
            });
            $("body").undelegate("#btn_code","click").delegate("#btn_code","click",function(){
                $("#table_sort").toggle()
            })
        }
        });*/

    //选择重度、中度、轻度、仅显示未处理、查询 重新加载datagrid
    $(".alarm_level input[type=checkbox],#seeOnlyUnHander,#query").on("click",function(){
        var param=getPara();
        ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",param,function (data) {
            $('#bjflfx').datagrid('loadData', data.rows);
        })
        // $("#bjflfx").datagrid("load",getPara());
    })

 /*   //实时刷新
    setInterval(function(){
        $("#bjflfx").datagrid("load",getPara());
    },5000)*/
    //情况说明点击弹窗
    $("body").undelegate("._relRemark","click").delegate("._relRemark","click",function(){

        var v = $(this).val();
        var that = $(this);
        parent.createDialog({

            element:$("#relRemark_dialog"),
            elementId:"relRemark_dialog",
            title:"情况说明",
            icon:'',
            ok_button_handler:function(){

                that.val(parent.$("#_relRemark").val());
                parent.$("#relRemark_dialog").dialog("close");
            }
        })

        parent.$("#_relRemark").val(v);

    });

    //保存
    $("#_save").on("click",function(){

        var selectRows = $("#bjflfx").datagrid("getSelections");
        if(selectRows.length!=0){
            var _data = [];
             for(var i=0;i<selectRows.length;i++){

                var obj = new Object();

                obj.confirmMsg = $("._re_"+selectRows[i]["atpid"]).val();
                obj.confirmType = $(".confirm_type_"+selectRows[i]["atpid"]).val();
                obj.alarmSource = selectRows[i]["alarmSource"];
                obj.atpId = selectRows[i]["atpid"];
                _data.push(obj);
                obj=null;
            }
            ht.data.getData("/api/balarmfx/batchAnalysis","POST",JSON.stringify(_data),function(_d){

                if(_d["status"]==1){

                    parent.showAlert("提示",_d.msg,"info",function(){
                        var param=getPara();
                        ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",param,function (data) {
                            $('#bjflfx').datagrid('loadData', data.rows);
                        })
                        // $("#bjflfx").datagrid("load",getPara());
                    });


                }else{

                    parent.showAlert("提示",_d.msg,"warning");
                }

            },null,true,'application/json')

        }else{

            parent.showAlert("提示","未选择任何数据","warning");
        }
    });
//批量分析
    $("#much_save").click(function(){
        var selectRows = $("#bjflfx").datagrid("getSelections");
        if(selectRows.length!=0){
            parent.createDialog({
                element: $("#much_flfx"),
                elementId: "much_flfx",
                title: '型号集分类',
                icon: '',
                ok_button_handler: function () {
                    var much_confirm_type=parent.$("#much_confirm_type_").val();
                    var much_relRemark=parent.$("#much_relRemark").val();
                    var _data = [];
                    for(var i=0;i<selectRows.length;i++){
                        var obj = new Object();
                        obj.confirmMsg =much_relRemark;
                        obj.confirmType = much_confirm_type;
                        obj.alarmSource = selectRows[i]["alarmSource"];
                        obj.atpId = selectRows[i]["atpid"];
                        _data.push(obj);
                        obj=null;
                    }
                    ht.data.getData("/api/balarmfx/batchAnalysis","POST",JSON.stringify(_data),function(_d){
                        if(_d["status"]==1){
                            parent.$("#much_flfx").dialog("close");
                            parent.showAlert("提示",_d.msg,"info",function(){
                                var param=getPara();
                                ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",param,function (data) {
                                    $('#bjflfx').datagrid('loadData', data.rows);
                                })
                                // $("#bjflfx").datagrid("load",getPara());
                            });
                        }else{
                            parent.showAlert("提示",_d.msg,"warning");
                        }

                    },null,true,'application/json')

                },
                display_cancel_button:false
            });
        }else{
            parent.showAlert("提示","请选择分析数据","error")
        }
    })


    //给开始时间和结束时间赋值
    function setQueryTime(interval){

        var dom_s = $(".start_time");
        var dom_e = $(".end_time");
        var time_s = null;
        var time_e = null;
        if(interval==0){

            time_s = createTime("00:00:00");
            time_e = createTime("23:59:59");

        }else{

            var cal_time = calTime(interval);
            time_s = cal_time.s;
            //time_e = cal_time.e;
            time_e = createTime("23:59:59");
        }
        dom_s.datetimebox('setValue',time_s);
        dom_e.datetimebox('setValue',time_e);
    }

    //初始化时间
    function createTime(t){

        var myDate = new Date();
        var year =  myDate.getFullYear();
        var month =  myDate.getMonth()+1;
        var day =  myDate.getDate();
        var time = year+'-'+month+'-'+day;
        return time+" "+t;

    }

    //计算时间 d:天数
    function calTime(d){

        if(d==0) return false;
        var now = new Date().getTime();
        var final = new Date(now-d*24*60*60*1000);
        var year =  final.getFullYear();
        var month =  final.getMonth()+1;
        var day =  final.getDate();
        var time = year+'-'+month+'-'+day;
        return {s:time+" 00:00:00",e:time+" 23:59:59"};
    }

    //获取请求参数
    function getPara(){
        var keyword = $("#keyword").val();
        var alarmlevel = [];
        $(".alarm_level input[type=checkbox]").each(function(){
            if($(this).prop("checked")){
                alarmlevel.push($(this).val());
            }
        });
        var startTime = $(".start_time").val();
        var endTime = $(".end_time").val();
        var showQr = $("#seeOnlyUnHander").prop("checked")?1:0;

        return {

            keyword:keyword,
            alarmlevel:alarmlevel.join(","),
            startTime:$(".start_time").datetimebox('getValue'),
            endTime:$(".end_time").datetimebox('getValue'),
            showQr:showQr
        }
    }

    //确认筛选
    $("#sorbtn_true").click(function(){
        var selectArr=[];
        $("#selectUl").find('input[type=checkbox]').each(function(){
            if($(this).prop('checked')){
                selectArr.push($(this).attr('id'))
            }
        });
        var paramter=getPara();
        if(selectArr.length!=0){
            var selectStr=selectArr.toString();
            paramter.keyword=selectStr;
            console.log(paramter);
            $("#table_sort").hide();
            ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",paramter,function (data) {
                $('#bjflfx').datagrid('loadData', data.rows);
            })
            $("#table_sort").attr("istrue","0");
        }else{
            ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",paramter,function (data) {
                $('#bjflfx').datagrid('loadData', data.rows);
            });
            $("#table_sort").hide();
            $("#table_sort").attr("istrue","0");
        }
    });
    //取消筛选
    $("#sorbtn_cel").click(function(){
        $("#table_sort").hide();
        $("#table_sort").attr("istrue","0");
    });
    $(".baseCode_select").click(function(){
        if($(this).prop('checked')==true){
            $("#selectUl").find('input[type=checkbox]').prop('checked',true)
        }else{
            $("#selectUl").find('input[type=checkbox]').prop('checked',false)
        }
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
        var sorparam=$("#table_sort").attr("sorparam");
        var selectArr=[];
        $("#selectUl").find('input[type=checkbox]').each(function(){
            if($(this).prop('checked')){
                selectArr.push($(this).attr('id'))
            }
        });
        var paramter=getPara();
        paramter.sort=sorparam;
        paramter.order="asc";
        paramter.param=selectArr.toString();
        ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",paramter,function (data) {
            $('#bjflfx').datagrid('loadData', data.rows);
        });
        $("#table_sort").hide();
        $("#table_sort").attr("istrue","0");
    });
    //降序
    $("#sort_desc").click(function(){
        var sorparam=$("#table_sort").attr("sorparam");
        var selectArr=[];
        $("#selectUl").find('input[type=checkbox]').each(function(){
            if($(this).prop('checked')){
                selectArr.push($(this).attr('id'))
            }
        });
        var paramter=getPara();
        paramter.sort=sorparam;
        paramter.order="asc";
        paramter.param=selectArr.toString();
        paramter.order="desc";
        ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",paramter,function (data) {
            $('#bjflfx').datagrid('loadData', data.rows);
        });
        $("#table_sort").hide();
        $("#table_sort").attr("istrue","0");
    });
//分类分析详情
    $("body").delegate(".flfx_detail","click",function(){
        var alarm_code=$(this).attr("alarmcode");
        var satid=$(this).attr("said");
        var atpid=$(this).attr("atpid");
        $("#flfx_tabs").attr("satid",satid);
        $("#flfx_tabs").attr("atpid",atpid);
        if(satid!='null'){
            parent.createDialog({
                element: $("#alarmFlfxDetail"),
                elementId: "alarmFlfxDetail",
                title: alarm_code,
                icon: '',
                ok_button_handler: function () {

                },
                display_ok_button:false,
                display_cancel_button:true,
                cancel_button_text:'关闭'
            });
        }else{
            parent.showAlert('提示','型号代号为空','error')
        }
        //时间插件
        parent.$('#flfx_begintime').datetimebox({
            value: '2017-12-12 12:12:12',
            required: true,
            showSeconds: true
        });
        parent.$('#flfx_endtime').datetimebox({
            value: '2017-12-12 12:12:12',
            required: true,
            showSeconds: true
        });
        parent.$("#flfx_tabs").attr("satid",satid);
        parent.$("#flfx_tabs").attr("atpid",atpid);
        //分类分析
        parent.$('#flfx_detail_dg').datagrid({
            url:'/api/balarmfx/getBySatIdParamEzui',
            singleSelect: true,
            autoRowHeight: false,
            fitColumns: true,
            scrollbarSize: '0',
            striped: true,
            checkOnSelect: true,
            selectOnCheck: false,
            queryParams:{
                satId:satid
            },
            height:"320px",
            width:"100%",
            columns: [[
                {field: 'num', title: '序号', width: '5%', align: 'center', resizable: true, sortable: true,
                    formatter: function (value,row,index) {
                        return '<span class="row_num">'+(index*1+1)+'</span>'
                    }},
                {field: 'balarmfx_begintime', title: '开始时间', width: '15%', align: 'center', resizable: true, sortable: true},
                {field: 'balarmfx_endtime', title: '结束时间', width: '15%', align: 'center', resizable: true},
                {field: 'balarmfx_alarmvalue', title: '报警值', width: '5%', align: 'center', sortable: true},
                {field: 'balarmfx_alarmmsg', title: '报警信息', width: '20%', align: 'center', resizable: true},
                {field: 'balarmfx_type', title: '类别', width: '10%', align: 'center', resizable: true, sortable: false},
                {field: 'balarmfx_remark', title: '说明', width: '30%', align: 'center', resizable: true, sortable: false}
            ]],
            onLoadSuccess:function(data){
                if(data.rows.length!=0){
                    var strDuty='';
                    if(data.rows[0].dutyName!=null){
                        strDuty=data.rows[0].dutyName;
                        if(data.rows[0].dutyTel!=null){
                            strDuty+="("+data.rows[0].dutyTel+")";
                        };
                        if(data.rows[0].dutyWorkTel!=null){
                            strDuty+="("+data.rows[0].dutyWorkTel+")";
                        };
                    }
                    var strReplace='';
                    if(data.rows[0].replaceName!=null){
                        strReplace=data.rows[0].replaceName;
                        if(data.rows[0].replaceTel!=null){
                            strReplace+="("+data.rows[0].replaceTel+")";
                        };
                        if(data.rows[0].replaceWorkTel!=null){
                            strReplace+="("+data.rows[0].replaceWorkTel+")";
                        };
                    }
                    parent.$("#flfx_duty_text").text(strDuty);
                    parent.$("#flfx_replace_text").text(strReplace);
                    //查询
                    parent.$("body").undelegate("#flfx_search","click").delegate("#flfx_search","click",function(){
                        var begin_time=parent.$("#flfx_begintime").datetimebox("getValue");
                        var end_time=parent.$("#flfx_endtime").datetimebox("getValue");
                        var flfx_type=parent.$("#flfx_select").val();
                        parent.$('#flfx_detail_dg').datagrid("load",{satId:satid,beginTime:begin_time,endTime:end_time,alarmType:flfx_type})

                    })
                }else{
                    parent.$("#flfx_duty_text").text("无");
                    parent.$("#flfx_replace_text").text("无");
                }
            }
        });
        //航天器在轨基本信息
        ht.data.getData("/api/bsat/getOneBsatBySatId","POST",{satId:satid},function (d) {
            if(d.status==1){
                if(d.data!=undefined&&d.data!=""&&d.data!=null) {
                    for(okey in d.data){
                        if(d.data[okey]==null){
                            d.data[okey]='';
                        }
                    }
                    var str='<tr class="base_msg_one">\n' +
                        '<td class="name">对外名称</td><td colspan="3" class="name_value">'+d.data.bsat_outside_name+'</td>\n' +
                        '<td class="satellite_name">卫星代号</td><td colspan="2" class="satellite_name_value">'+d.data.bsat_code+'</td></tr>';
                    str+='<tr>\n' +
                        '<td>发射时间</td><td class="shoot_time" colspan="2">'+d.data.launch_time+'</td>\n' +
                        '<td>考核寿命(年)</td><td class="life_time">'+d.data.lifeyear+'</td>\n' +
                        '<td class="is_over_time">是否超寿</td><td class="life_state">'+d.data.valuestatus+'</td></tr>';
                    str+='<tr class="base_msg_three">\n' +
                        '<td>责任人</td><td class="zpoe">'+d.data.dutyname+'</td>\n' +
                        '<td class="phone">电话</td><td class="zphone">'+d.data.dutyworkphone+'</td>\n' +
                        '<td class="mobile">手机</td><td class="ztel" colspan="2">'+d.data.dutytel+'</td></tr>';
                    str+='<tr>\n' +
                        '<td>替代人</td><td  class="tpeo">'+d.data.replacename+'</td>\n' +
                        '<td>电话</td><td  class="tphone">'+d.data.replcaeworkphone+'</td>\n' +
                        '<td >手机</td><td class="ttel" colspan="2">'+d.data.replacetel+'</td></tr>';
                    parent.$("#flfx_base_msg").html(str)
                }else{
                    parent.$("#flfx_base_msg").html("<p style='width:1000px;height:60px;text-align:center;padding:20px 20px;font-size:20px'>无数据</p>")
                }

            }
        });
        parent.$('#flfx_contacts_xq_table').datagrid({
            url:'/api/bxhzcdw/getZrrInfoByBsatAtpId',
            nowrap: true,
            fitColumns:true,
            striped: true,
            rownumbers:false,
            pagination:false,
            singleSelect:false,
            scrollbarSize: '0',
            // pageNumber:1,
            queryParams: {
                atpid:satid
            },
            // pageSize:7,
            // pageList:[7,20,30,40,50],
            columns:[[
                {field:'bxhzcdwPost',title:'岗位',width:'40%',align:'center'},
                {field:'bxhzcdwName',title:'姓名',width:"20%",align:'center'},
                {field:'bxhzcdwPhone',title:'手机',width:"20%",align:'center'},
                {field:'bxhzcdwTelephone',title:'电话',width:"20%",align:'center'},
            ]],
            onLoadSuccess:function(dd){
            }
        });
        //在轨异常
        parent.$('#flfx_abnormal_dg').datagrid({
            url:'/api/bzgyc/listBcscfycBySatId',
            autoRowHeight: false,
            pagination: false,
            fitColumns: true,
            scrollbarSize: '0',
            striped: true,
            checkOnSelect: false,
            selectOnCheck: true,
            nowrap:false,
            queryParams:{
                satId:satid
            },
            columns: [[
                {field: 'num', title:'序号',align:'center',width:'5%',
                    formatter: function (value,row,index) {
                        return '<span class="row_num">'+(index*1+1)+'</span>'
                    }},
                {field:'bzgycAstelliteId',title:'卫星名称',width:'15%', align: 'center'},
                {field:'bzgycC512',title:'异常名称',width:'15%', align: 'center'},
                {field:'bzgycC9117',title:'发生次数',width:'5%',align: 'center'},
                {field:'bzgycC9996', title: '发生时间',width:'15%', align: 'center'},
                { field:'bzgycImportTime', title: '上报时间',width:'15%', align: 'center'},
                { field:'bzgyc_detail', title: '详情',width:'30%', align: 'center',
                    formatter: function (value,row,index) {
                        return '<span style="cursor: pointer" class="flfx_bzgyc_detail" rowindex="'+index+'">详情</span>'
                    }
                }
            ]],
            onLoadSuccess:function(data){
                //查询
                parent.$("body").undelegate("#flfx_abnormal_query","click").delegate("#flfx_abnormal_query","click",function(){
                    var keyword = parent.$("#flfx_abnormal_keyword").val();
                    if(keyword){
                        parent.$("#flfx_abnormal_dg").datagrid("load",{satId:satid,bzgycName:keyword});
                    }else{
                        parent.$("#flfx_abnormal_dg").datagrid("load",{satId:satid});

                    }
                });
                // 详情
                parent.$("body").undelegate(".flfx_bzgyc_detail","click").delegate(".flfx_bzgyc_detail","click",function(){
                    var rowindex=parent.$(this).attr("rowindex");
                    for(okey in data.rows[rowindex]){
                        if(data.rows[rowindex][okey]==null){
                            data.rows[rowindex][okey]='';
                        }
                    }
                    var str='<div style="width:100%;height:100%;font-size: 14px">\n' +
                        '            <div style="display: inline-block;width:250px">\n' +
                        '                <p style="padding:5px">异常编号：'+data.rows[rowindex].bzgycC8812+'</p>\n' +
                        '                <p style="padding:5px">型号代号：'+data.rows[rowindex].bzgycC8788+'</p>\n' +
                        '                <p style="padding:5px">异常名称：'+data.rows[rowindex].bzgycC512+'</p>\n' +
                        '                <p style="padding:5px">所属分系统：'+data.rows[rowindex].bzgycC8813+'</p>\n' +
                        '                <p style="padding:5px">所属单机：'+data.rows[rowindex].bzgycC9739+'</p>\n' +
                        '                <p style="padding:5px">发现时间：'+data.rows[rowindex].bzgycC9996+'</p>\n' +
                        '                <p style="padding:5px">异常涉及单位：</p>\n' +
                        '            </div>\n' +
                        '            <div style="display: inline-block;width:250px">\n' +
                        '                <p style="padding:5px">型号名称：'+data.rows[rowindex].bzgycC8787+'</p>\n' +
                        '                <p style="padding:5px">发生次数：'+data.rows[rowindex].bzgycC9117+'</p>\n' +
                        '                <p style="padding:5px">型号总体单位：'+data.rows[rowindex].bzgycC8817+'</p>\n' +
                        '                <p style="padding:5px">所属关键单位：'+data.rows[rowindex].bzgycC8871+'</p>\n' +
                        '                <p style="padding:5px">发生时间：'+data.rows[rowindex].bzgycC9064+'</p>\n' +
                        '                <p style="padding:5px">是否为常驻故障：</p>\n' +
                        '                <p style="padding:5px">异常等级：'+data.rows[rowindex].bzgycC9998+'</p>\n' +
                        '            </div>\n' +
                        '            <p style="padding:5px">异常现象描述:'+data.rows[rowindex].bzgycC520+'</p>\n' +
                        '            <p style="padding:5px">异常原因分析:'+data.rows[rowindex].bzgycC521+'</p>\n' +
                        '            <p style="padding:5px">异常处理过程:'+data.rows[rowindex].bzgycC522+'</p>\n' +
                        '            <p style="padding:5px">异常处理结果:'+data.rows[rowindex].bzgycC9999+'</p>\n' +
                        '            <p style="padding:5px">备注:'+data.rows[rowindex].bzgycC9060+'</p>\n' +
                        '            <p style="padding:5px">举一反三措施：</p>\n' +
                        '            <div style="display: inline-block;width:250px">\n' +
                        '                <p style="padding:5px">设计寿命：'+data.rows[rowindex].bzgycC8081+'</p>\n' +
                        '                <p style="padding:5px">是否归零：</p>\n' +
                        '                <p style="padding:5px">计划归零时间：</p>\n' +
                        '                <p style="padding:5px">单机失效日期：</p>\n' +
                        '            </div>\n' +
                        '            <div style="display: inline-block;width:250px">\n' +
                        '                <p style="padding:5px">已运行时间：</p>\n' +
                        '                <p style="padding:5px">原因分类：</p>\n' +
                        '                <p style="padding:5px">完成归零时间：</p>\n' +
                        '                <p style="padding:5px">补偿措施：</p>\n' +
                        '            </div>\n' +
                        '        </div>';
                    parent.createDialog({
                        element: $("#flfx_bzgyc_detail"),
                        elementId: "flfx_bzgyc_detail",
                        title: '详情',
                        icon: '',
                        ok_button_handler: function () {
                        },
                        display_ok_button:false,
                        cancel_button_text:'关闭'
                    });
                    parent.$("#flfx_bzgyc_detail").html(str)
                });
            }

        });
        //地月影预报
        parent.$('#flfx_moon_dg').datagrid({
            url:'/api/bmonthpre/getBySatId',
            autoRowHeight: false,
            pagination: false,
            fitColumns: false,
            scrollbarSize: '0',
            striped: true,
            checkOnSelect: false,
            selectOnCheck: true,
            nowrap:false,
            height:"100%",
            width:"100%",
            queryParams:{
                satId:satid
            },
            columns: [[
                {field: 'num', title: '序号', align: 'center', width: '7%',resizable:false,sortable: true,
                    formatter: function (value,row,index) {
                        return '<span class="row_num">'+(index*1+1)+'</span>'
                    }},
                {field: 'bmonthpreAtpcreatedatetime', title: '开始时间',  width: '26%', align: 'center',resizable:false, sortable: true},
                {field: 'bmonthpreAtplastmodifydatetime', title: '结束时间',  width: '26%', align: 'center',resizable:false, sortable: true},
                {field: 'bmonthpreSpan', title: '时长',  width: '20%', align: 'center', resizable:false,sortable: true},
                { field: 'bmonthpreMstype', title: '阴影',  width: '21%', align: 'center',resizable:false,}
            ]],
            onLoadSuccess:function(data){
                //查询
                parent.$("body").undelegate("#flfx_dyyquery","click").delegate("#flfx_dyyquery","click",function(){
                    var keyword = parent.$("#flfx_dyykeyword").val();
                    if(keyword){
                        parent.$("#flfx_moon_dg").datagrid("load",{satId:satid,keyWord:keyword});
                    }else{
                        parent.$("#flfx_moon_dg").datagrid("load",{satId:satid});
                    }
                });
            }
        });
        //测控计划
        parent.$('#flfx_cekong_dg').datagrid({
            url:'/api/bckplan/getBySatId',
            autoRowHeight: false,
            pagination: false,
            fitColumns: false,
            scrollbarSize: '0',
            striped: true,
            checkOnSelect: false,
            selectOnCheck: true,
            nowrap:false,
            queryParams:{
                satId:satid
            },
            columns: [[
                {field: 'num', title: '序号', align: 'center',width: '7%', resizable:false,sortable: true,
                    formatter: function (value,row,index) {
                        return '<span class="row_num">'+(index*1+1)+'</span>'
                    }},
                {field: 'bckplanMadetime', title: '文件生成时间',  width: '15%', align: 'center',resizable:false, sortable: false},
                {field: 'bckplanStartTime', title: '开始时间',  width: '15%', align: 'center',resizable:false, sortable: true},
                {field: 'bckplanEndTime', title: '结束时间',  width: '15%', align: 'center',resizable:false, sortable: true},
                {field: 'bckplanCnt', title: '圈次',  width: '5%', align: 'center', resizable:false,sortable: true},
                { field: 'bckplanDevname', title: '测站',  width: '20%', align: 'center',resizable:false,},
                {field: 'bckplanIsDelete', title: '是否已删除',  width: '10%', align: 'center', resizable:false,sortable: true},
                {field: 'bckplanAtpstatus', title: '来源',  width: '13%', align: 'center', sortable: true},
            ]],
            onLoadSuccess:function(data){
            }
        });
        //在轨技术支持队伍
        parent.$('#flfx_team_dg').datagrid({
            url:'/api/bxhzcdw/getBySatId',
            autoRowHeight: false,
            pagination: false,
            fitColumns: false,
            scrollbarSize: '0',
            striped: true,
            checkOnSelect: false,
            selectOnCheck: true,
            nowrap:false,
            queryParams:{
                satId:satid
            },
            columns: [[
                {field: 'num', title: '序号',align: 'center',width: '8%', resizable:false,sortable: true,
                    formatter: function (value,row,index) {
                        return '<span class="row_num">'+(index*1+1)+'</span>'
                    }},
                {field: 'bxhzcdwPost', title: '岗位',  width: '25%', align: 'center',resizable:false, sortable: false},
                {field: 'bxhzcdwName', title: '姓名',  width: '15%', align: 'center',resizable:false, sortable: true},
                {field: 'bxhzcdwUnit', title: '单位',  width: '25%', align: 'center',resizable:false, sortable: true},
                {field: 'bxhzcdwTelephone', title: '办公电话',  width: '20%', align: 'center', resizable:false,sortable: true},
                {field: 'bxhzcdwPhone', title: '手机',  width: '20%', align: 'center', resizable:false,sortable: true},
                {field: 'bxhzcdwRemark', title: '备注',  width: '25%', align: 'center', sortable: true},
            ]],
            onLoadSuccess:function(data){
                //查询
                parent.$("body").undelegate("#flfx_zgdwquery","click").delegate("#flfx_zgdwquery","click",function(){
                    var keyword = parent.$("#flfx_zgdwkeyword").val();
                    if(keyword){
                        parent.$("#flfx_team_dg").datagrid("load",{satId:satid,keyWord:keyword});
                    }else{
                        parent.$("#flfx_team_dg").datagrid("load",{satId:satid});
                    }
                });
            }
        });
    });

});
bindTabs();
function bindTabs(){
    $('#flfx_tabs').tabs({
        border:false,
        onSelect:function(title){
            var ssatid=$(this).attr("satid");
            var satpid= $(this).attr("atpid");
            switch(title){
                case '历史报警分类分析':
                    parent.$('#flfx_detail_dg').datagrid({
                        url:'/api/balarmfx/getBySatIdParamEzui',
                        singleSelect: true,
                        autoRowHeight: false,
                        fitColumns: true,
                        scrollbarSize: '0',
                        striped: true,
                        checkOnSelect: true,
                        selectOnCheck: false,
                        queryParams:{
                            satId:ssatid
                        },
                        height:"320px",
                        width:"100%",
                        columns: [[
                            {field: 'num', title: '序号', width: '5%', align: 'center', resizable: true, sortable: true,
                                formatter: function (value,row,index) {
                                    return '<span class="row_num">'+(index*1+1)+'</span>'
                                }},
                            {field: 'balarmfx_begintime', title: '开始时间', width: '15%', align: 'center', resizable: true, sortable: true},
                            {field: 'balarmfx_endtime', title: '结束时间', width: '15%', align: 'center', resizable: true},
                            {field: 'balarmfx_alarmvalue', title: '报警值', width: '5%', align: 'center', sortable: true},
                            {field: 'balarmfx_alarmmsg', title: '报警信息', width: '20%', align: 'center', resizable: true},
                            {field: 'balarmfx_type', title: '类别', width: '10%', align: 'center', resizable: true, sortable: false},
                            {field: 'balarmfx_remark', title: '说明', width: '30%', align: 'center', resizable: true, sortable: false}
                        ]],
                        onLoadSuccess:function(data){
                            if(data.rows.length!=0){
                                var strDuty='';
                                if(data.rows[0].dutyName!=null){
                                    strDuty=data.rows[0].dutyName;
                                    if(data.rows[0].dutyTel!=null){
                                        strDuty+="("+data.rows[0].dutyTel+")";
                                    };
                                    if(data.rows[0].dutyWorkTel!=null){
                                        strDuty+="("+data.rows[0].dutyWorkTel+")";
                                    };
                                }
                                var strReplace='';
                                if(data.rows[0].replaceName!=null){
                                    strReplace=data.rows[0].replaceName;
                                    if(data.rows[0].replaceTel!=null){
                                        strReplace+="("+data.rows[0].replaceTel+")";
                                    };
                                    if(data.rows[0].replaceWorkTel!=null){
                                        strReplace+="("+data.rows[0].replaceWorkTel+")";
                                    };
                                }
                                parent.$("#flfx_duty_text").text(strDuty);
                                parent.$("#flfx_replace_text").text(strReplace);
                                //查询
                                parent.$("body").undelegate("#flfx_search","click").delegate("#flfx_search","click",function(){
                                    var begin_time=parent.$("#flfx_begintime").datetimebox("getValue");
                                    var end_time=parent.$("#flfx_endtime").datetimebox("getValue");
                                    var flfx_type=parent.$("#flfx_select").val();
                                    parent.$('#flfx_detail_dg').datagrid("load",{satId:ssatid,beginTime:begin_time,endTime:end_time,alarmType:flfx_type})

                                })
                            }else{
                                parent.$("#flfx_duty_text").text("无");
                                parent.$("#flfx_replace_text").text("无");
                            }
                        }
                    });
                    break;
                case '航天器在轨基本信息':
                    ht.data.getData("/api/bsat/getOneBsatBySatId","POST",{satId:ssatid},function (d) {
                        if(d.status==1){
                            if(d.data!=undefined&&d.data!=""&&d.data!=null) {
                                for(okey in d.data){
                                    if(d.data[okey]==null){
                                        d.data[okey]='';
                                    }
                                }
                                var str='<tr class="base_msg_one">\n' +
                                    '<td class="name">对外名称</td><td colspan="3" class="name_value">'+d.data.bsat_outside_name+'</td>\n' +
                                    '<td class="satellite_name">卫星代号</td><td colspan="2" class="satellite_name_value">'+d.data.bsat_code+'</td></tr>';
                                str+='<tr>\n' +
                                    '<td>发射时间</td><td class="shoot_time" colspan="2">'+d.data.launch_time+'</td>\n' +
                                    '<td>考核寿命(年)</td><td class="life_time">'+d.data.lifeyear+'</td>\n' +
                                    '<td class="is_over_time">是否超寿</td><td class="life_state">'+d.data.valuestatus+'</td></tr>';
                                str+='<tr class="base_msg_three">\n' +
                                    '<td>责任人</td><td class="zpoe">'+d.data.dutyname+'</td>\n' +
                                    '<td class="phone">电话</td><td class="zphone">'+d.data.dutyworkphone+'</td>\n' +
                                    '<td class="mobile">手机</td><td class="ztel" colspan="2">'+d.data.dutytel+'</td></tr>';
                                str+='<tr>\n' +
                                    '<td>替代人</td><td  class="tpeo">'+d.data.replacename+'</td>\n' +
                                    '<td>电话</td><td  class="tphone">'+d.data.replcaeworkphone+'</td>\n' +
                                    '<td >手机</td><td class="ttel" colspan="2">'+d.data.replacetel+'</td></tr>';
                                parent.$("#flfx_base_msg").html(str)
                            }else{
                                parent.$("#flfx_base_msg").html("<p style='width:1000px;height:60px;text-align:center;padding:20px 20px;font-size:20px'>无数据</p>")
                            }

                        }
                    });
                    parent.$('#flfx_contacts_xq_table').datagrid({
                        url:'/api/bxhzcdw/getZrrInfoByBsatAtpId',
                        nowrap: true,
                        fitColumns:true,
                        striped: true,
                        rownumbers:false,
                        pagination:false,
                        singleSelect:false,
                        scrollbarSize: '0',
                        // pageNumber:1,
                        queryParams: {
                            atpid:ssatid
                        },
                        // pageSize:7,
                        // pageList:[7,20,30,40,50],
                        columns:[[
                            {field:'bxhzcdwPost',title:'岗位',width:'40%',align:'center'},
                            {field:'bxhzcdwName',title:'姓名',width:"20%",align:'center'},
                            {field:'bxhzcdwPhone',title:'手机',width:"20%",align:'center'},
                            {field:'bxhzcdwTelephone',title:'电话',width:"20%",align:'center'},
                        ]],
                        onLoadSuccess:function(dd){
                        }
                    });
                    break;
                case '在轨异常':
                    parent.$('#flfx_abnormal_dg').datagrid({
                        url:'/api/bzgyc/listBcscfycBySatId',
                        autoRowHeight: false,
                        pagination: false,
                        fitColumns: true,
                        scrollbarSize: '0',
                        striped: true,
                        checkOnSelect: false,
                        selectOnCheck: true,
                        nowrap:false,
                        queryParams:{
                            satId:ssatid
                        },
                        columns: [[
                            {field: 'num', title:'序号',align:'center',width:'5%',
                                formatter: function (value,row,index) {
                                    return '<span class="row_num">'+(index*1+1)+'</span>'
                                }},
                            {field:'bzgycAstelliteId',title:'卫星名称',width:'15%', align: 'center'},
                            {field:'bzgycC512',title:'异常名称',width:'15%', align: 'center'},
                            {field:'bzgycC9117',title:'发生次数',width:'5%',align: 'center'},
                            {field:'bzgycC9996', title: '发生时间',width:'15%', align: 'center'},
                            { field:'bzgycImportTime', title: '上报时间',width:'15%', align: 'center'},
                            { field:'bzgyc_detail', title: '详情',width:'30%', align: 'center',
                                formatter: function (value,row,index) {
                                    return '<span style="cursor: pointer" class="flfx_bzgyc_detail" rowindex="'+index+'">详情</span>'
                                }
                            }
                        ]],
                        onLoadSuccess:function(data){
                            //查询
                            parent.$("body").undelegate("#flfx_abnormal_query","click").delegate("#flfx_abnormal_query","click",function(){
                                var keyword = parent.$("#flfx_abnormal_keyword").val();
                                if(keyword){
                                    parent.$("#flfx_abnormal_dg").datagrid("load",{satId:ssatid,bzgycName:keyword});
                                }else{
                                    parent.$("#flfx_abnormal_dg").datagrid("load",{satId:ssatid});

                                }
                            });
                            // 详情
                            parent.$("body").undelegate(".flfx_bzgyc_detail","click").delegate(".flfx_bzgyc_detail","click",function(){
                                var rowindex=parent.$(this).attr("rowindex");
                                for(okey in data.rows[rowindex]){
                                    if(data.rows[rowindex][okey]==null){
                                        data.rows[rowindex][okey]='';
                                    }
                                }
                                var str='<div style="width:100%;height:100%;font-size: 14px">\n' +
                                    '            <div style="display: inline-block;width:250px">\n' +
                                    '                <p style="padding:5px">异常编号：'+data.rows[rowindex].bzgycC8812+'</p>\n' +
                                    '                <p style="padding:5px">型号代号：'+data.rows[rowindex].bzgycC8788+'</p>\n' +
                                    '                <p style="padding:5px">异常名称：'+data.rows[rowindex].bzgycC512+'</p>\n' +
                                    '                <p style="padding:5px">所属分系统：'+data.rows[rowindex].bzgycC8813+'</p>\n' +
                                    '                <p style="padding:5px">所属单机：'+data.rows[rowindex].bzgycC9739+'</p>\n' +
                                    '                <p style="padding:5px">发现时间：'+data.rows[rowindex].bzgycC9996+'</p>\n' +
                                    '                <p style="padding:5px">异常涉及单位：</p>\n' +
                                    '            </div>\n' +
                                    '            <div style="display: inline-block;width:250px">\n' +
                                    '                <p style="padding:5px">型号名称：'+data.rows[rowindex].bzgycC8787+'</p>\n' +
                                    '                <p style="padding:5px">发生次数：'+data.rows[rowindex].bzgycC9117+'</p>\n' +
                                    '                <p style="padding:5px">型号总体单位：'+data.rows[rowindex].bzgycC8817+'</p>\n' +
                                    '                <p style="padding:5px">所属关键单位：'+data.rows[rowindex].bzgycC8871+'</p>\n' +
                                    '                <p style="padding:5px">发生时间：'+data.rows[rowindex].bzgycC9064+'</p>\n' +
                                    '                <p style="padding:5px">是否为常驻故障：</p>\n' +
                                    '                <p style="padding:5px">异常等级：'+data.rows[rowindex].bzgycC9998+'</p>\n' +
                                    '            </div>\n' +
                                    '            <p style="padding:5px">异常现象描述:'+data.rows[rowindex].bzgycC520+'</p>\n' +
                                    '            <p style="padding:5px">异常原因分析:'+data.rows[rowindex].bzgycC521+'</p>\n' +
                                    '            <p style="padding:5px">异常处理过程:'+data.rows[rowindex].bzgycC522+'</p>\n' +
                                    '            <p style="padding:5px">异常处理结果:'+data.rows[rowindex].bzgycC9999+'</p>\n' +
                                    '            <p style="padding:5px">备注:'+data.rows[rowindex].bzgycC9060+'</p>\n' +
                                    '            <p style="padding:5px">举一反三措施：</p>\n' +
                                    '            <div style="display: inline-block;width:250px">\n' +
                                    '                <p style="padding:5px">设计寿命：'+data.rows[rowindex].bzgycC8081+'</p>\n' +
                                    '                <p style="padding:5px">是否归零：</p>\n' +
                                    '                <p style="padding:5px">计划归零时间：</p>\n' +
                                    '                <p style="padding:5px">单机失效日期：</p>\n' +
                                    '            </div>\n' +
                                    '            <div style="display: inline-block;width:250px">\n' +
                                    '                <p style="padding:5px">已运行时间：</p>\n' +
                                    '                <p style="padding:5px">原因分类：</p>\n' +
                                    '                <p style="padding:5px">完成归零时间：</p>\n' +
                                    '                <p style="padding:5px">补偿措施：</p>\n' +
                                    '            </div>\n' +
                                    '        </div>';
                                parent.createDialog({
                                    element: $("#flfx_bzgyc_detail"),
                                    elementId: "flfx_bzgyc_detail",
                                    title: '详情',
                                    icon: '',
                                    ok_button_handler: function () {
                                    },
                                    display_ok_button:false,
                                    cancel_button_text:'关闭'
                                });
                                parent.$("#flfx_bzgyc_detail").html(str)
                            });
                        }

                    });
                    break;
                case '地月影预报':
                    parent.$('#flfx_moon_dg').datagrid({
                        url:'/api/bmonthpre/getBySatId',
                        autoRowHeight: false,
                        pagination: false,
                        fitColumns: false,
                        scrollbarSize: '0',
                        striped: true,
                        checkOnSelect: false,
                        selectOnCheck: true,
                        nowrap:false,
                        height:"100%",
                        width:"100%",
                        queryParams:{
                            satId:ssatid
                        },
                        columns: [[
                            {field: 'num', title: '序号', align: 'center', width: '7%',resizable:false,sortable: true,
                                formatter: function (value,row,index) {
                                    return '<span class="row_num">'+(index*1+1)+'</span>'
                                }},
                            {field: 'bmonthpreAtpcreatedatetime', title: '开始时间',  width: '26%', align: 'center',resizable:false, sortable: true},
                            {field: 'bmonthpreAtplastmodifydatetime', title: '结束时间',  width: '26%', align: 'center',resizable:false, sortable: true},
                            {field: 'bmonthpreSpan', title: '时长',  width: '20%', align: 'center', resizable:false,sortable: true},
                            { field: 'bmonthpreMstype', title: '阴影',  width: '21%', align: 'center',resizable:false,}
                        ]],
                        onLoadSuccess:function(data){
                            //查询
                            parent.$("body").undelegate("#flfx_dyyquery","click").delegate("#flfx_dyyquery","click",function(){
                                var keyword = parent.$("#flfx_dyykeyword").val();
                                if(keyword){
                                    parent.$("#flfx_moon_dg").datagrid("load",{satId:ssatid,keyWord:keyword});
                                }else{
                                    parent.$("#flfx_moon_dg").datagrid("load",{satId:ssatid});
                                }
                            });
                        }
                    });
                    break;
                case '测控计划':
                    parent.$('#flfx_cekong_dg').datagrid({
                        url:'/api/bckplan/getBySatId',
                        autoRowHeight: false,
                        pagination: false,
                        fitColumns: false,
                        scrollbarSize: '0',
                        striped: true,
                        checkOnSelect: false,
                        selectOnCheck: true,
                        nowrap:false,
                        queryParams:{
                            satId:ssatid
                        },
                        columns: [[
                            {field: 'num', title: '序号', align: 'center',width: '7%', resizable:false,sortable: true,
                                formatter: function (value,row,index) {
                                    return '<span class="row_num">'+(index*1+1)+'</span>'
                                }},
                            {field: 'bckplanMadetime', title: '文件生成时间',  width: '15%', align: 'center',resizable:false, sortable: false},
                            {field: 'bckplanStartTime', title: '开始时间',  width: '15%', align: 'center',resizable:false, sortable: true},
                            {field: 'bckplanEndTime', title: '结束时间',  width: '15%', align: 'center',resizable:false, sortable: true},
                            {field: 'bckplanCnt', title: '圈次',  width: '5%', align: 'center', resizable:false,sortable: true},
                            { field: 'bckplanDevname', title: '测站',  width: '20%', align: 'center',resizable:false,},
                            {field: 'bckplanIsDelete', title: '是否已删除',  width: '10%', align: 'center', resizable:false,sortable: true},
                            {field: 'bckplanAtpstatus', title: '来源',  width: '13%', align: 'center', sortable: true},
                        ]],
                        onLoadSuccess:function(data){
                        }
                    });
                    break;
                case '在轨技术支持队伍':
                    parent.$('#flfx_team_dg').datagrid({
                        url:'/api/bxhzcdw/getBySatId',
                        autoRowHeight: false,
                        pagination: false,
                        fitColumns: false,
                        scrollbarSize: '0',
                        striped: true,
                        checkOnSelect: false,
                        selectOnCheck: true,
                        nowrap:false,
                        queryParams:{
                            satId:ssatid
                        },
                        columns: [[
                            {field: 'num', title: '序号',align: 'center',width: '8%', resizable:false,sortable: true,
                                formatter: function (value,row,index) {
                                    return '<span class="row_num">'+(index*1+1)+'</span>'
                                }},
                            {field: 'bxhzcdwPost', title: '岗位',  width: '25%', align: 'center',resizable:false, sortable: false},
                            {field: 'bxhzcdwName', title: '姓名',  width: '15%', align: 'center',resizable:false, sortable: true},
                            {field: 'bxhzcdwUnit', title: '单位',  width: '25%', align: 'center',resizable:false, sortable: true},
                            {field: 'bxhzcdwTelephone', title: '办公电话',  width: '20%', align: 'center', resizable:false,sortable: true},
                            {field: 'bxhzcdwPhone', title: '手机',  width: '20%', align: 'center', resizable:false,sortable: true},
                            {field: 'bxhzcdwRemark', title: '备注',  width: '25%', align: 'center', sortable: true},
                        ]],
                        onLoadSuccess:function(data){
                            //查询
                            parent.$("body").undelegate("#flfx_zgdwquery","click").delegate("#flfx_zgdwquery","click",function(){
                                var keyword = parent.$("#flfx_zgdwkeyword").val();
                                if(keyword){
                                    parent.$("#flfx_team_dg").datagrid("load",{satId:ssatid,keyWord:keyword});
                                }else{
                                    parent.$("#flfx_team_dg").datagrid("load",{satId:ssatid});
                                }
                            });
                        }
                    });
                    break;
            }
        },
    });
}