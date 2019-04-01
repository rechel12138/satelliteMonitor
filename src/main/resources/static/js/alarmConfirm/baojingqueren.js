$(function () {
    // var $nowId;
    // $(".header_list").children("li").each(function(){
    //     $(this).click(function(e){
    //         e.stopPropagation();
    //         var $left=-parseInt($(this).data("choose"));
    //         $(".table_list").css("left",$left+"00%");
    //     })
    // })
    // $(".alert_box_close").click(function(e){
    //     e.stopPropagation();
    //     $(".alert_box").hide();
    // })
    // $(".add_over").click(function(e){
    //     //e.stopPropagation();
    //     // $(".add_panel_box").show();
    //     $.ajax({
    //         url:'updateRepeatById',
    //         method:'post',
    //         data:{'id':$nowId},
    //         success: function(data){
    //             console.log(data);
    //             $(".over_id").val(data.rows[0].code);
    //             $(".over_time").val(data.rows[0].occurredtime);
    //             $(".over_man").val(data.rows[0].owner);
    //         }
    //     })
    // })
    //
    // $(".submit_btn").click(function(e){
    //     e.stopPropagation();
    //     if ($(".over_id").val()=="") {
    //         alert("型号代号不能为空");
    //         return;
    //     } else if ($(".over_name").val()=="") {
    //         alert("异常名称不能为空");
    //         return;
    //     } else if ($(".over_man").val()=="") {
    //         alert("卫星责任人不能为空");
    //         return;
    //     } else {
    //         $.ajax({
    //             url:'updateRepeat',
    //             method:'post',
    //             data:{
    //                 'id':$nowId,
    //                 'code':$(".over_id").val(),
    //                 'satelliteCode':$(".over_num").val(),
    //                 'exceptionName':$(".over_name").val(),
    //                 'occurredtime':$(".over_time").val(),
    //                 'appearance':$(".over_info").val(),
    //                 'owner':$(".over_man").val(),
    //                 'status':$(".over_state").val(),
    //                 'result':$(".over_end").val()
    //             },
    //             success:function(data){
    //                 console.log(1);
    //             }
    //         })
    //         $(".add_panel_box").hide();
    //     }
    // })

    // $(".close").click(function(e){
    //     $(".add_panel_box").hide();
    // })
    parent.$(".back_btn").click();
    parent.$(".sider_fold_btn").click();
    //时间戳转为年月日时分秒：
    $('.time_num').text(getTime);
    setInterval(function(){
        $('.time_num').text(getTime);
    }, 1000);
    function getTime() {
        var datetime = new Date();
        var year = datetime.getFullYear();
        var month = ("0" + (datetime.getMonth() + 1)).slice(-2);
        var date = ("0" + datetime.getDate()).slice(-2);
        var hour = ("0" + datetime.getHours()).slice(-2);
        var minute = ("0" + datetime.getMinutes()).slice(-2);
        var second = ("0" + datetime.getSeconds()).slice(-2);
        return (year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second);
    }
    //获取请求参数
    function getPara(){
        var keyword=$('.show_light_alarm').prop("ids");
             if(keyword==null||keyword==""||keyword==undefined){
                 keyword="";
             }
        var alarmlevel ="";
            if($('.show_light_alarm').prop("checked")){
                alarmlevel="轻度"
            }
        return {
            satIds:keyword,
            alarmlevel:alarmlevel
        }
    }
    //实时刷新
    //实时刷新时间戳；
    function createTime(min){
        var min=min||0;
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


   //报警详情单条数据值全局
    var xqdataArr=[];

    //初始化报警确认表格：
    $('#dg').datagrid({
        url:'/api/bsjmxss/listBsjmxss',
        rownumbers:true,
        autoRowHeight: true,
        sortable:true,
        pagination: false,
        fitColumns:true,
        // fit:false,
        // scrollbarSize:0,
        // striped: true,
        checkOnSelect: false,
        selectOnCheck: true,
        // collapsible:true,
        nowrap:true,
        height:"100%",
        // pageNumber:1,
        // pageSize:7,
        // pageList:[7,20,30,40,50],
        columns: [[
            {field: 'check', checkbox: true,resizable:false},
          /*  {field: 'num', title: '序号', width: '5%', align: 'center',resizable:false,sortable: true,
                formatter: function (value,row,index) {
                    return '<span class="row_num" style="display:inline-block;width:25px;height:28px">'+(index*1+1)+'</span><span class="sign" style="display:inline-block;width:25px;height:28px"><img src="/images/redflag.png" class="sign_img"/></span>'
                }
            },*/
            {field: 'bsatCode', title: '型号代号&nbsp▼', align: 'center',  width: '8%', resizable:false,id:"btn_code",
                formatter: function (value,row,index) {
                          var str='<span class="row_num" style="display:inline-block;width:80px;height:28px">'+value+'</span><span class="sign" style="display:inline-block;width:25px;height:28px"  isshow="0" source="'+row.alarmSource+'" atp="'+row.atpid+'" sufid="'+row.sufid+'">';
                              if(row.sufid){
                              str+='<img src="/images/redflag.png" class="sign_img"/></span>'
                              }else{
                                  str+='</span>'
                              }
                              return str;
                    // return '<span class="row_num" style="display:inline-block;width:80px;height:28px">'+value+'</span><span class="sign" style="display:inline-block;width:25px;height:28px"  isshow="0" source="'+row.alarmSource+'" atp="'+row.atpid+'"><img src="/images/redflag.png" class="sign_img"/></span>'
                },
                sorter:function(){


                }
            },
            {field: 'alarmBeginTime', title: '报警开始时间', align: 'center',  width: '15%',resizable:false},
            {field: 'paramCode', title: '参数代号' , align: 'center',width: '8%',resizable:false, sortable: true},
            {field: 'paramLevel', title: '参数级别' , align: 'center',width: '6%',resizable:false, sortable: true},
            {field: 'alarmLevel', title: '报警级别',  align: 'center',width: '6%',resizable:false, sortable: true},
            {field: 'currentVal', title: '当前值',  align: 'center', width: '6%', resizable:false,sortable: true},
            {field: 'alarmVal', title: '报警值',  align: 'center', width: '6%', resizable:false,sortable: true},
            {field: 'alarmMsg', title: '报警信息',  align: 'center', width: '20%', resizable:false,sortable: true},
            {field: 'alarmSource', title: '报警来源&nbsp▼', align: 'center',width: '15%',resizable:false,id:"btn_source"},
            {
                field: 'confirmTime', title: '确认时间', width:'15%', align: 'center',resizable:false,
                sortable: true, formatter: function (value,row,index) {
                    if (value) {
                        return value
                    } else {
                        var $a="<a href='javascript:;' class='unconfirmed' id="+row.atpid+">等待确认</a>";
                        return $a;
                    }
                }
            },
            {
                field: 'modify_time1', title: '报警分析',width: '8%',  align: 'center',resizable:false,
                formatter: function (v,r,index) {
                    // xqdataArr.length=0;
                    // xqdataArr.push(r);
                    return '<a href="#" class="detail" satpid="'+r.atpid+'" said="'+r.satId+'" alarmcode="'+r.bsatCode+'" btnConfirm="'+r.confirmTime+'" alarsource="'+r.alarmSource+'">详情</a>'
                }
            },
            {
                field: 'trend', title: '遥测趋势',  align: 'center',width: '15%',resizable:false,
                formatter: function (v,r,index) {

                    return '<a href="javascript:;" parecode="'+r.paramCode+'" bascode="'+r.bsatCode+'" class="telemetering">趋势图</a>'
                }
            }
        ]],
        onLoadSuccess:function(data){
            setInterval(function(){
                var time=createTime();
                var param=getPara();
                param.timeStamp=time;
             /*   var tt=$('#dg').datagrid('getData');
                console.log(tt);*/
                        ht.data.getData("/api/bsjmxss/incrementReturnData","post",param,function (d) {
                            if(d.status!=0){
                                if(d.rows.length>0){
                                    var getdata=$('#dg').datagrid('getData');
                                    for(var i=0;i<d.rows.length;i++){
                                        var hitted = false;
                                        for(var j=0;j<getdata.rows.length;j++){
                                            // var hitted = false;
                                            if(d.rows[i].atpid==getdata.rows[j].atpid){
                                                hitted = true;
                                          /*      $('#dg').datagrid('updateRow',{
                                                    index:j,
                                                    row:d.rows[i]
                                                });*/
                                                break;
                                            }
                                        }
                                        if(hitted !== true){
                                            $('#dg').datagrid('insertRow',{
                                                index: 0,
                                                row:d.rows[i]
                                            });
                                        }
                                    }

                                    $("#alarm_num").text(getdata.rows.length)
                                }
                    }else{
                        parent.showAlert("提示",d.msg,"warning")
                    }

                })

                // $('#dg').datagrid('load',getPara());
            }, 5000);
          $("#alarm_num").text(data.rows.length)
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
                $("#table_sort").css("left","1071px");
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
        },
        //根据报警级别改变对应行颜色：
        rowStyler: function(index,row){
            if (row.alarmLevel=="重度"){
                return 'background-color:#ccc';
            }if (row.alarmLevel=="中度"){
                return 'background-color:#999';
            }if (row.alarmLevel=="轻度"){
                return 'background-color:#666';
            }
        },
        freezeRow:0
    });
    //小红旗
    $("body").delegate(".sign","click",function(){
           var _source=$(this).attr("source");
           var _atp=$(this).attr("atp");
           var _sufid=$(this).attr("sufid");
           var _this=$(this);
          if(_sufid=="null"||_sufid==""){
              ht.data.getData("/htdxjk/suserflag/addSuserflag", "post",{from:_source,bjAtpid:_atp},function (data) {
                  if(data.status==1){
                      _this.attr("sufid",data.data.sufAtpid);
                      _this.html('<img src="/images/redflag.png" class="sign_img"/>')
                  }else{
                      parent.showAlert("提示",data.msg,"warning")
                  }

              })
          }else{
              ht.data.getData("/htdxjk/suserflag/removeSuserflag", "post",{sufid :_sufid},function (data) {
                  if(data.status==1){
                      _this.html("")
                      _this.attr("sufid","");
                  }else{
                      parent.showAlert("提示",data.msg,"warning")
                  }

              })
          }
    });
    //确认筛选
    $("#sorbtn_true").click(function(){
        var sorparam=$("#table_sort").attr("sorparam");
        var selectArr=[];
        $("#selectUl").find('input[type=checkbox]').each(function(){
            if($(this).prop('checked')){
                selectArr.push($(this).attr('id'))
            }
        });
        var paramter=getPara();
        if(selectArr.length!=0){
            var selectStr=selectArr.toString();
            paramter.keyWords=selectStr;
            paramter.sort=sorparam;
            paramter.order="asc";
            $("#table_sort").hide();
            ht.data.getData("/api/bsjmxss/listBsjmxss", "post",paramter,function (data) {
                $('#dg').datagrid('loadData', data.rows);
            });
            $("#table_sort").attr("istrue","0");
        }else{
            paramter.keyWords="";
            paramter.sort=sorparam;
            paramter.order="asc";
            ht.data.getData("/api/balarmfx/findAlarmListByParam", "post",paramter,function (data) {
                $('#dg').datagrid('loadData', data.rows);
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
        paramter.keyWords=selectArr.toString();
        ht.data.getData("/api/bsjmxss/listBsjmxss", "post",paramter,function (data) {
            $('#dg').datagrid('loadData', data.rows);
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
        paramter.keyWords=selectArr.toString();
        paramter.order="desc";
        ht.data.getData("/api/bsjmxss/listBsjmxss", "post",paramter,function (data) {
            $('#dg').datagrid('loadData', data.rows);
        });
        $("#table_sort").hide();
        $("#table_sort").attr("istrue","0");
    });
    //趋势图
    $("body").delegate(".telemetering","click",function(){
        var parecode=$(this).prop("parecode");
        var bascode=$(this).prop("bascode");
         var local_time=new Date();
        var local_time_str=settimeString(local_time);
        var conut_time=local_time.getTime()-30*60*1000;
        var star_time=new Date(conut_time);
        var star_time_str=settimeString(star_time);
        parent.location.href="http://10.64.4.86/db/index.html?T1="+star_time_str+"&T2="+local_time_str+"&TID="+parecode+"&SID="+bascode;

    });
    function settimeString(local_time){
        var lyear=local_time.getFullYear();
        var lmoth=local_time.getMonth()+1;
        if(lmoth<10){
            lmoth="0"+lmoth
        }
        var lday=local_time.getDate();
        if(lday<10){
            lday="0"+lday
        }
        var lhour=local_time.getHours();
        if(lhour<10){
            lhour="0"+lhour
        }
        var lminutes=local_time.getMinutes();
        if(lminutes<10){
            lminutes="0"+lminutes
        }
        var lSecond=local_time.getSeconds();
        if(lSecond<10){
            lSecond="0"+lSecond
        }
        var str=lyear+lmoth+lday+lhour+lminutes+lSecond;
      return str
    }
    // 10.64.4.86/db/index.html?T1=YYYYMMDDhhmmss&T2=YYYYMMDDhhmmss&TID=TX181&SID=AA1234
    //确认时间
    $("body").delegate(".unconfirmed","click",function(){
        var confirm_time=$('.time_num').text();
        var confirm_atpid=$(this).attr("id");
        var othis=$(this);
        ht.data.getData("/api/bsjmxss/confirmBsjmxss","get",{atpId:confirm_atpid},function (d) {
            if(d.status==1){
                othis.parent().text(confirm_time);
            }else{
                parent.showAlert('提示',d.msg,"error")
            }
        })
    });

//页面加载显示型号集分类弹
    parent.createDialog({
        element: $("#xhjfl"),
        elementId: "xhjfl",
        title: '型号集分类',
        icon: '',
        ok_button_handler: function () {

        },
        display_ok_button:false,
        display_cancel_button:false
    });
    creatXhjfl();
    //型号集分类弹窗渲染
    function creatXhjfl(){
        ht.data.getData("/api/bsatset/listBsatSet","POST",{},function (data) {
            if(data.status==0){
                parent.showAlert("提示",data.msg,"error")
            }else{
                var strLi='<li class="xhjflList" style="padding:5px 12px;width:17%"><span style="padding-right:6px "><img src="/images/satelite_icon.png" alt="" style="height:30px;width:30px"></span><span class="satelliteName" style="cursor:pointer" ids="">所有卫星</span></li>';
                if(data.rows.length!=0){
                    for(var i=0;i<data.rows.length;i++){
                        strLi+='<li class="xhjflList"  style="padding:5px 12px;width:17%"><span style="padding-right:6px "><img src="/images/satelite_icon.png" alt="" style="height:30px;width:30px"></span><span class="satelliteName" style="cursor:pointer" ids="'+data.rows[i].satids+'">'+data.rows[i].bss_name+'</span></li>'
                    }
                }
                parent.$("#categoryList").html(strLi)
            }

        });
    }
       //点击型号集分类显示弹窗
$("#xhjflBtn").click(function(){
    parent.createDialog({
        element: $("#xhjfl"),
        elementId: "xhjfl",
        title: '型号集分类',
        icon: '',
        ok_button_handler: function () {
        },
        display_ok_button:false,
        display_cancel_button:false,
    });
    creatXhjfl();
});
//型号集分类选择
parent.$("body").undelegate('.satelliteName','click').delegate('.satelliteName','click',function(){
    var sid=$(this).attr("ids");
    $(".show_light_alarm").prop("ids",sid);
    $("#dg").datagrid("load",{satIds:sid});
    parent.$("#xhjfl").dialog("close");
});
    //点击配置按钮跳到配置表：
    parent.$("body").undelegate('.configure','click').delegate('.configure','click',function(){
        parent.createDialog({
            element: $("#xhjconfig-panel"),
            elementId: "xhjconfig-panel",
            title: '型号集分类配置',
            icon: '',
            ok_button_handler: function () {
                parent.$("#xhjconfig-panel").dialog("close");
            }
        });
        ht.data.getData("/api/bsat/getAllSatIds", "post",{bxxrjlId:""},function (dataCode) {
            if(dataCode.status==1){
                var strcode='';
                for(var i=0;i<dataCode.data.length;i++){
                    strcode+='<li style="margin: 4px 0"><label for="'+dataCode.data[i].id+'"><input type="checkbox" id="'+dataCode.data[i].id+'">&nbsp&nbsp&nbsp'+dataCode.data[i].code+'</label></li>';
                }
              $("#typeNameList").html(strcode)
            }
        });
        ht.data.getData("/api/suser/listSUser", "post",{},function (userdata) {
            if(userdata.status==1){
                var struserdata='';
                for(var i=0;i<userdata.data.length;i++){
                    struserdata+='<li style="margin: 4px 0"><label for="'+userdata.data[i].suAtpid+'"><input type="checkbox" id="'+userdata.data[i].suAtpid+'">&nbsp&nbsp&nbsp'+userdata.data[i].suChinesename+'</label></li>';
                }
                $("#dutyManList").html(struserdata)
            }
        });
        //型号集分类配置弹窗内表格：
        parent.$("#xhjconfig").datagrid({
            url:'/api/bsatset/listBsatSet',
            fitColumns:true,
            striped: true,
            rownumbers:true,
            pagination:false,
            singleSelect:false,
            scrollbarSize:0,
      /*      pageNumber:1,
            pageSize:7,
            pageList:[7,20,30,40,50],*/
            height:'350px',
            width:'100%',
            columns:[[
                {field:'check',checkbox:true},
                {field:'bss_name',title:'分类名称',width:'10%',align:'center'},
                {field:'bss_desc',title:'分类描述',width:'14%',align:'center'},
                {field:'bsatCode',title:'型号代号',width:'8%',align:'center'},
                {field:'bss_type',title:'类型',width:'8%',align:'center'},
                {field:'creatUserName',title:'创建人',width:'8%',align:'center'},
                {field:'su_atpcreatedatetime',title:'创建时间',width:'15%',align:'center'},
                {field:'userName',title:'指派人',width:'11%',align:'center'},
                {field:'op',title:'操作',width:'19%',align:'center',
                    formatter:function(v,r){
                        return "<span class='Xhjfl_line_update' fljid='"+r.bss_atpid+"' style='cursor: pointer'>编辑</span><span style='padding:0 10px'>|</span><span style='cursor: pointer' class='Xhjfl_line_delete' fljid='"+r.bss_atpid+"'>删除</span>"
                    }
                }
            ]],
            onBeforeLoad:function() {
            },
            onLoadSuccess:function(d){
             parent.$("body").undelegate("#createXhjfl","click").delegate("#createXhjfl","click",function(){
                 getInputData("clear");
                 parent.createDialog({
                     element: $("#createType"),
                     elementId: "createType",
                     title: '创建新型号集',
                     icon: '',
                     ok_button_handler: function () {
                         var obj=getInputData("get");
                         if(obj!=false){
                             ht.data.getData("/api/bsatset/addBsatSet","POST",obj,function (d) {
                                 if(d.status==1){
                                     parent.showAlert("提示",d.msg,"success");
                                     parent.$("#createType").dialog("close");
                                     parent.$("#xhjconfig").datagrid("load",{keyword:''});
                                     creatXhjfl();
                                 }else{
                                     parent.showAlert("提示",d.msg,"error");
                                 }
                             })
                         }
                     }
                 });
                 parent.$(".radio").find('input[name="singleSelection"]').change(function(){
                     if($(this).val()=="共有"){
                       parent.$("#radioControll").show()
                     }else{
                         parent.$("#radioControll").hide()
                     }
                 })
             });
                parent.$("body").undelegate("#Xhjfl_delete","click").delegate("#Xhjfl_delete","click",function(){
                        var selectRows = parent.$("#xhjconfig").datagrid("getSelections");
                        if(selectRows.length!=0){
                            var aBssAtpid = [];
                            for(var k in selectRows){
                                aBssAtpid.push(selectRows[k]["bss_atpid"]);
                            }
                            ht.data.getData("/api/bsatset/removeBsatSet","POST",{bssAtpid:aBssAtpid.toString()},function (d) {
                                if(d.status==1){
                                    parent.showAlert("提示",d.msg,"success");
                                    parent.$("#createType").dialog("close");
                                    parent.$("#xhjconfig").datagrid("load",{keyword:''});
                                    creatXhjfl();
                                }else{
                                    parent.showAlert("提示",d.msg,"error");
                                }
                            })
                        }else{
                            parent.showAlert("提示","未选择任何数据","warning");
                        }
                });
                parent.$("body").undelegate(".Xhjfl_line_delete","click").delegate(".Xhjfl_line_delete","click",function(){
                    var fljid = $(this).attr("fljid");
                        ht.data.getData("/api/bsatset/removeBsatSet","POST",{bssAtpid:fljid},function (d) {
                            if(d.status==1){
                                parent.showAlert("提示",d.msg,"success");
                                parent.$("#createType").dialog("close");
                                parent.$("#xhjconfig").datagrid("load",{keyword:''});
                                creatXhjfl();
                            }else{
                                parent.$("#createType").dialog("close");
                                parent.showAlert("提示",d.msg,"error");
                            }
                        })
                });
                parent.$("body").undelegate(".Xhjfl_line_update","click").delegate(".Xhjfl_line_update","click",function(){
                    var fljid = $(this).attr("fljid");
                    ht.data.getData("/api/bsatset/getOneById","POST",{atpId:fljid},function (d) {
                        if(d.status==1){
                            getInputData("clear");
                            parent.createDialog({
                                element: $("#createType"),
                                elementId: "createType",
                                title: '修改型号集',
                                icon: '',
                                ok_button_handler: function () {
                                    var data=getInputData("get");
                                    if(data!=false){
                                        data.bssAtpid=fljid;
                                        ht.data.getData("/api/bsatset/updateBsatSet","POST",data,function (dd) {
                                            if(dd.status==1){
                                                parent.showAlert("提示",d.msg,"success");
                                                parent.$("#createType").dialog("close");
                                                parent.$("#xhjconfig").datagrid("load",{keyword:''});
                                                creatXhjfl();
                                            }else{
                                                parent.$("#createType").dialog("close");
                                                parent.showAlert("提示",dd.msg,"error");
                                            }
                                        })
                                    }
                                }
                            });
                         /*   ht.data.getData("/api/bsat/getAllSatIds", "post",{bxxrjlId:""},function (dataCode) {
                                if(dataCode.status==1){
                                    var strcode='';
                                    for(var i=0;i<dataCode.data.length;i++){
                                        strcode+='<option  value="'+dataCode.data[i].id+'">'+dataCode.data[i].code+'</option>'
                                    }
                                    parent.$("#typeNameList").html(strcode)
                                }
                            });*/
                               parent.$(".radio").find('input[name="singleSelection"]').change(function(){
                                   if($(this).val()=="共有"){
                                       parent.$("#radioControll").show()
                                   }else{
                                       parent.$("#radioControll").hide()
                                   }
                               });
                            getInputData("set",d.data);
                        }
                    });
                });
                parent.$("body").undelegate("#Xhjfl_search","click").delegate("#Xhjfl_search","click",function(){
                    var keyword = parent.$("#Xhjfl_keyword").val();
                    if(keyword){
                        parent.$("#xhjconfig").datagrid("load",{keyWord:keyword});
                    }else{
                        parent.$("#xhjconfig").datagrid("load",{keyWord:''});
                    }
                });
            }
        })
    });
    //显示轻度报警
    $('.show_light_alarm').click(function(){
        $('#dg').datagrid('load',getPara());
    });
    //导出信息
    $("#excel_port").click(function(){
        var sid=$('.show_light_alarm').prop("ids");
        if($('.show_light_alarm').prop('checked')){
            var url="/api/bsjmxss/exportExcel?alarmlevel='轻度'&satIds="+sid;
            parent.document.location.href=url;
        }else{
            var url="/api/bsjmxss/exportExcel?satIds="+sid;
            parent.document.location.href=url;
        }
    });
//值班人预判
    $(".watcher_judge").on("click",function () {
        var selectRows = $("#dg").datagrid("getSelections");
        zbrFn(selectRows)

    });
    function zbrFn(selectRows){
        if(selectRows.length==1){
            var zatpid=selectRows[0].atpid;
            var zsource=selectRows[0].alarmSource;
            var zsatId=selectRows[0].satId;
            var zbascode=selectRows[0].bsatCode;
            parent.createDialog({
                element: $("#watcherJudge"),
                elementId: "watcherJudge",
                title: '值班人预判',
                icon: '',
                ok_button_handler: function () {
                    var select_type=parent.$("#ff").val();
                    var select_alarmtype=parent.$("#cc").val();
                    var resultV=parent.$("#resultV").val();
                    var endtime=parent.$("#end_time").datetimebox("getValue");
                    var followingV=parent.$("#folowing_V").find('input[name="to_trace"]:checked').val();
                    // var watcher_zrrV=parent.$("#watcher_zrr").find('input[name="next_watcher"]:checked').val();
                    // console.log(watcher_zrrV);
                    var data={};
                    if(followingV=="on"){
                        data={atpId:zatpid,source:zsource,preJudgeType:select_alarmtype,preJudgeRemark:resultV,preJudgeTime:endtime,satId:zsatId,bsatCode:zbascode,isTrace:"是"};
                    }else if(followingV=="off"){
                        data={atpId:zatpid,source:zsource,preJudgeType:select_alarmtype,preJudgeRemark:resultV,preJudgeTime:endtime,satId:zsatId,bsatCode:zbascode,isTrace:"否"};
                    }
                    ht.data.getData("/api/bsjmxss/preJudgAlarm","POST",data,function (d) {
                        if(d.status==1){
                            parent.$("#watcherJudge").dialog("close");
                            parent.showAlert("提示",d.msg,"success");
                            var sid=$(".show_light_alarm").prop("ids");
                            $("#dg").datagrid("load",{satIds:sid});
                        }else{
                            parent.showAlert("提示",d.msg,"error")
                        }
                    });
                    parent.$("#resultV").val("");
                }
            });
            parent.$('#end_time').datetimebox({
                value: '2018-12-12 12:03:03',
                required: true,
                showSeconds: true
            });
            parent.$(".textbox-icon").css("right","0px");
                 parent.$("#folowing_V").find('input[name="to_trace"]').change(function(){
                     if(parent.$("#folowing_V").find('input[name="to_trace"]:checked').val()=='off'){
                         parent.$("#watcher_zrr").hide();
                     }else if(parent.$("#folowing_V").find('input[name="to_trace"]:checked').val()=='on'){
                         parent.$("#watcher_zrr").show();
                     }
                 });
            /*  parent.$("#dd").off('change').on('change',(function(){
                  parent.$("#watcher_zrr").find('input[name="next_watcher"]:checked').val($(this).val())
              }))*/
        }else{
            parent.showAlert("提示","请选择1条数据进行进行预判","error");
        }
    }
    //报警分析详情
$("body").delegate(".detail","click",function(){
    var alarm_code=$(this).attr("alarmcode");
    var satapid=$(this).attr("said");
    var atpid=$(this).attr("satpid");
    var Confirm=$(this).attr("btnConfirm");
    var _alarsource=$(this).attr("alarsource");
    $("#tt").attr("satid",satapid);
    $("#tt").attr("atpid",atpid);
    $("#tt").attr("alarsource",_alarsource);
 if(satapid!='null'){
     parent.createDialog({
         element: $("#alarmConfirmDetail"),
         elementId: "alarmConfirmDetail",
         title: alarm_code,
         icon: '',
         ok_button_handler: function () {

         },
         display_ok_button:false,
         display_cancel_button:false,
         // cancel_button_text:'关闭'
     });
 }else{
     parent.showAlert('提示','型号代号为空','error')
 }
 //预判
    parent.$("#zbr_btn_call").off("click").on("click",function(){
      var abr_arr=[{atpid:atpid,alarmSource:_alarsource,satId:satapid,bsatCode:alarm_code}];
        zbrFn(abr_arr)
});
    //时间插件
    parent.$('#flfx_begin_tme').datetimebox({
        value: '2017-12-12 12:12:12',
        required: true,
        showSeconds: true
    });
    parent.$('#flfx_end_time').datetimebox({
        value: '2017-12-12 12:12:12',
        required: true,
        showSeconds: true
    });
    // 确认时间
    if(Confirm=="null"){
        parent.$("#alarm_confirm").css('background-color','#3A87E4');
        parent.$("body").undelegate("#alarm_confirm","click").delegate("#alarm_confirm","click",function(){
            ht.data.getData("/api/bsjmxss/confirmBsjmxss","get",{atpId:atpid},function (d) {
            })
        });
    }else{
        parent.$("#alarm_confirm").css('background-color','#cacaca');
        parent.$("body").undelegate("#alarm_confirm","click");
    }
    parent.$("#tt").attr("satid",satapid);
    parent.$("#tt").attr("atpid",atpid);
    parent.$("#tt").attr("alarsource",_alarsource);
    //分类分析
    parent.$('#detail_dg').datagrid({
        url:'/api/balarmfx/getBySatIdParamEzui',
        singleSelect: true,
        autoRowHeight: false,
        fitColumns: true,
        scrollbarSize: '0',
        striped: true,
        checkOnSelect: true,
        selectOnCheck: false,
        queryParams:{
            satId:satapid
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
                parent.$("#duty_text").text(strDuty);
                parent.$("#replace_text").text(strReplace);
                //添加超寿异常
                parent.$("body").undelegate("#add_over","click").delegate(".add_over","click",function(){
                    var str='<li><span style="display: inline-block;width:100px;text-align: right">型号代号：</span><input class="cscf_satellitecode" type="text" satid="'+satapid+'" value=""></li>\n' +
                        '        <li><span style="display: inline-block;width:100px;text-align: right">异常记录编号：</span><input  class="cscf_code" type="text" placeholder="20190102-YG1234-001"></li>\n' +
                        '        <li><span style="display: inline-block;width:100px;text-align: right">异常名称：</span><input  class="cscf_exceptionname" type="text"></li>\n' +
                        '        <li><span style="display: inline-block;width:100px;text-align: right">发生时间：</span><input  class="cscf_occurredtime" type="text" value=""></li>\n' +
                        '        <li><span style="display: inline-block;width:100px;text-align: right">异常现象描述：</span><input  class="cscf_appearance" type="text"></li>\n' +
                        '        <li><span style="display: inline-block;width:100px;text-align: right">卫星责任人：</span><input  class="cscf_owner" type="text" subsid="'+data.rows[0].bsat_admin_subs_id+'" value=""></li>\n' +
                        '        <li><span style="display: inline-block;width:100px;text-align: right">状态：</span><input  class="cscf_status" type="text"></li>\n' +
                        '        <li><span style="display: inline-block;width:100px;text-align: right">异常处理结果：</span><input  class="cscf_result" type="text"></li>'
                    parent.createDialog({
                        element: $("#add_csyc"),
                        elementId: "add_csyc",
                        title:"添加超寿异常",
                        icon: '',
                        ok_button_handler: function () {
                            var obj={};
                            obj.bcscfycSatellitecode=parent.$(".cscf_satellitecode").val();
                            obj.bcscfycSatid=satapid;
                            obj.bcscfycCode=parent.$(".cscf_code").val();
                            obj.bcscfycExceptionname=parent.$(".cscf_exceptionname").val();
                            obj.bcscfycOccurredtime=parent.$(".cscf_occurredtime").val();
                            obj.bcscfycAppearance=parent.$(".cscf_appearance").val();
                            obj.bcscfycOwner=parent.$(".cscf_owner").attr("subsid");
                            obj.bcscfycStatus=parent.$(".cscf_status").val();
                            obj.bcscfycResult=parent.$(".cscf_result").val();
                            obj.bcscfycAtpid="";
                            obj.bcscfycAtpcreatedatetime="";
                            obj.bcscfycAtpcreateuser="";
                            obj.bcscfycAtplastmodifydatetime="";
                            obj.bcscfycAtplastmodifyuser="";
                            obj.bcscfycAtpstatus="";
                            obj.bcscfycAtpsort="";
                            obj.bcscfycAtpdotype="";
                            obj.bcscfycAtpremark="";
                            ht.data.getData("/api/bcscfyc/addBcscfyc","POST",obj,function (d) {
                                if(d.status==1){
                                    parent.showAlert("提示",d.msg,"success");
                                    parent.$("#add_csyc").dialog("close");
                                }else{
                                    parent.showAlert("提示",d.msg,"error")
                                }
                            })
                        },
                        // display_ok_button:false,
                        // display_cancel_button:false,
                    });
                    parent.$("#add_csyc_ul").html(str)
                    ht.data.getData("/api/bsjmxss/getByAlarmIdAndSource", "post",{"alarmId":atpid,"alarmSourc":_alarsource},function (_data) {
                        if(_data.status==1){
                            parent.$(".cscf_satellitecode").val(_data.data.bsatCode);
                            parent.$(".cscf_occurredtime").val(_data.data.beginTime);
                            parent.$(".cscf_owner").val(_data.data.dutyName);
                            // parent.$(".cscf_satellitecode").val(_data.data.bsatCode);
                        }else{
                            parent.showAlert("提示",_data.msg,"warning")
                        }
                    })
                });
                //查询
                parent.$("body").undelegate("#flfx_searchbtn","click").delegate("#flfx_searchbtn","click",function(){
                    var begin_time=parent.$("#flfx_begin_tme").datetimebox("getValue");
                    var end_time=parent.$("#flfx_end_time").datetimebox("getValue");
                    var flfx_type=parent.$("#ee").val();
                    parent.$('#detail_dg').datagrid("load",{satId:satapid,beginTime:begin_time,endTime:end_time,alarmType:flfx_type})

                })
            }else{
                parent.$("#duty_text").text("无");
                parent.$("#replace_text").text("无");
            }
        }
    });
    //航天器在轨基本信息
    ht.data.getData("/api/bsat/getOneBsatBySatId","POST",{satId:satapid.toString()},function (d) {
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
                parent.$("#base_msg").html(str)
            }else{
                parent.$("#base_msg").html("<p style='width:1000px;height:60px;text-align:center;padding:20px 20px;font-size:20px'>无数据</p>")
            }

        }
    });
    parent.$('#contacts_xq_table').datagrid({
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
            atpid:satapid
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
    parent.$('#abnormal_dg').datagrid({
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
            satId:satapid
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
                    return '<span style="cursor: pointer" class="bzgyc_detail" rowindex="'+index+'">详情</span>'
                }
            }
        ]],
        onLoadSuccess:function(data){
            //查询
            parent.$("body").undelegate("#abnormal_query","click").delegate("#abnormal_query","click",function(){
                var keyword = parent.$("#abnormal_keyword").val();
                if(keyword){
                    parent.$("#abnormal_dg").datagrid("load",{satId:satapid,bzgycName:keyword});
                }else{
                    parent.$("#abnormal_dg").datagrid("load",{satId:satapid});

                }
            });
            // 详情
            parent.$("body").undelegate(".bzgyc_detail","click").delegate(".bzgyc_detail","click",function(){
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
                    element: $("#bzgyc_detail"),
                    elementId: "bzgyc_detail",
                    title: '详情',
                    icon: '',
                    ok_button_handler: function () {
                    },
                    display_ok_button:false,
                    cancel_button_text:'关闭'
                });
                parent.$("#bzgyc_detail").html(str)
            });
        }

    });
    //地月影预报
    parent.$('#moon_dg').datagrid({
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
            satId:satapid
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
            parent.$("body").undelegate("#dyyquery","click").delegate("#dyyquery","click",function(){
                var keyword = parent.$("#dyykeyword").val();
                if(keyword){
                    parent.$("#moon_dg").datagrid("load",{satId:satapid,keyWord:keyword});
                }else{
                    parent.$("#moon_dg").datagrid("load",{satId:satapid});
                }
            });
        }
    });
    //测控计划
    parent.$('#cekong_dg').datagrid({
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
            satId:satapid
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
    parent.$('#team_dg').datagrid({
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
            satId:satapid
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
            parent.$("body").undelegate("#zgdwquery","click").delegate("#zgdwquery","click",function(){
                var keyword = parent.$("#zgdwkeyword").val();
                if(keyword){
                    parent.$("#team_dg").datagrid("load",{satId:satapid,keyWord:keyword});
                }else{
                    parent.$("#team_dg").datagrid("load",{satId:satapid});
                }
            });
        }
    });
});
    bindTabs();
    function bindTabs(){
        $('#tt').tabs({
            border:false,
            onSelect:function(title){
                var ssatid=$(this).attr("satid");
                var satpid= $(this).attr("atpid");
                var _alarsource= $(this).attr("alarsource");
                switch(title){
                    case '历史报警分类分析':
                        parent.$('#detail_dg').datagrid({
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
                                    parent.$("#duty_text").text(strDuty);
                                    parent.$("#replace_text").text(strReplace);
                                    //添加超寿异常
                                    parent.$("body").undelegate(".add_over","click").delegate(".add_over","click",function(){

                                        var str='<li><span style="display: inline-block;width:100px;text-align: right">型号代号：</span><input class="cscf_satellitecode" type="text" value=""></li>\n' +
                                            '        <li><span style="display: inline-block;width:100px;text-align: right">异常记录编号：</span><input  class="cscf_code" type="text" placeholder="20190102-YG1234-001"></li>\n' +
                                            '        <li><span style="display: inline-block;width:100px;text-align: right">异常名称：</span><input  class="cscf_exceptionname" type="text"></li>\n' +
                                            '        <li><span style="display: inline-block;width:100px;text-align: right">发生时间：</span><input  class="cscf_occurredtime" type="text" value=""></li>\n' +
                                            '        <li><span style="display: inline-block;width:100px;text-align: right">异常现象描述：</span><input  class="cscf_appearance" type="text"></li>\n' +
                                            '        <li><span style="display: inline-block;width:100px;text-align: right">卫星责任人：</span><input  class="cscf_owner" type="text" subsid="'+data.rows[0].bsat_admin_subs_id+'" value=""></li>\n' +
                                            '        <li><span style="display: inline-block;width:100px;text-align: right">状态：</span><input  class="cscf_status" type="text"></li>\n' +
                                            '        <li><span style="display: inline-block;width:100px;text-align: right">异常处理结果：</span><input  class="cscf_result" type="text"></li>'
                                        parent.createDialog({
                                            element: $("#add_csyc"),
                                            elementId: "add_csyc",
                                            title:"添加超寿异常",
                                            icon: '',
                                            ok_button_handler: function () {
                                                var obj={};
                                                obj.bcscfycSatellitecode=parent.$(".cscf_satellitecode").val();
                                                obj.bcscfycSatid=ssatid;
                                                obj.bcscfycCode=parent.$(".cscf_code").val();
                                                obj.bcscfycExceptionname=parent.$(".cscf_exceptionname").val();
                                                obj.bcscfycOccurredtime=parent.$(".cscf_occurredtime").val();
                                                obj.bcscfycAppearance=parent.$(".cscf_appearance").val();
                                                obj.bcscfycOwner=parent.$(".cscf_owner").attr("subsid");
                                                obj.bcscfycStatus=parent.$(".cscf_status").val();
                                                obj.bcscfycResult=parent.$(".cscf_result").val();
                                                obj.bcscfycAtpid="";
                                                obj.bcscfycAtpcreatedatetime="";
                                                obj.bcscfycAtpcreateuser="";
                                                obj.bcscfycAtplastmodifydatetime="";
                                                obj.bcscfycAtplastmodifyuser="";
                                                obj.bcscfycAtpstatus="";
                                                obj.bcscfycAtpsort="";
                                                obj.bcscfycAtpdotype="";
                                                obj.bcscfycAtpremark="";
                                                ht.data.getData("/api/bcscfyc/addBcscfyc","POST",obj,function (d) {
                                                    if(d.status==1){
                                                        parent.$("#add_csyc").dialog("close");
                                                        parent.showAlert("提示",d.msg,"success");
                                                    }else{
                                                        parent.showAlert("提示",d.msg,"error")
                                                    }
                                                })
                                            },
                                            // display_ok_button:false,
                                            // display_cancel_button:false,
                                        });
                                        parent.$("#add_csyc_ul").html(str);
                                        ht.data.getData("/api/bsjmxss/getByAlarmIdAndSource", "post",{"alarmId":satpid,"alarmSourc":_alarsource},function (_data) {
                                            if(_data.status==1){
                                                parent.$(".cscf_satellitecode").val(_data.data.bsatCode);
                                                parent.$(".cscf_occurredtime").val(_data.data.beginTime);
                                                parent.$(".cscf_owner").val(_data.data.dutyName);
                                                // parent.$(".cscf_satellitecode").val(_data.data.bsatCode);
                                            }else{
                                                parent.showAlert("提示",_data.msg,"warning")
                                            }
                                        })
                                    });
                                    //查询
                                    parent.$("body").undelegate("#flfx_searchbtn","click").delegate("#flfx_searchbtn","click",function(){
                                        var begin_time=parent.$("#flfx_begin_tme").datetimebox("getValue");
                                        var end_time=parent.$("#flfx_end_time").datetimebox("getValue");
                                        var flfx_type=parent.$("#ee").val();
                                        parent.$('#detail_dg').datagrid("load",{satId:ssatid,beginTime:begin_time,endTime:end_time,alarmType:flfx_type})
                                    })
                                }else{
                                    parent.$("#duty_text").text("无");
                                    parent.$("#replace_text").text("无");
                                }
                            }
                        });
                        break;
                    case '航天器在轨基本信息':
                        ht.data.getData("/api/bsat/getOneBsatBySatId","POST",{satId:ssatid.toString()},function (d) {
                            if(d.status==1){
                                if(d.data!=undefined&&d.data!=""&&d.data!=null) {
                                    for(okey in d.data){
                                        if(d.data[okey]==null){
                                            d.data[okey]='';
                                        }
                                    }
                                    var str = '<tr class="base_msg_one">\n' +
                                        '<td class="name">对外名称</td><td colspan="3" class="name_value">' + d.data.bsat_outside_name + '</td>\n' +
                                        '<td class="satellite_name">卫星代号</td><td colspan="2" class="satellite_name_value">' + d.data.bsat_code + '</td></tr>';
                                    str += '<tr>\n' +
                                        '<td>发射时间</td><td class="shoot_time" colspan="2">' + d.data.launch_time + '</td>\n' +
                                        '<td>考核寿命(年)</td><td class="life_time">' + d.data.lifeyear + '</td>\n' +
                                        '<td class="is_over_time">是否超寿</td><td class="life_state">' + d.data.valuestatus + '</td></tr>';
                                    str += '<tr class="base_msg_three">\n' +
                                        '<td>责任人</td><td class="zpoe">' + d.data.dutyname + '</td>\n' +
                                        '<td class="phone">电话</td><td class="zphone">' + d.data.dutyworkphone + '</td>\n' +
                                        '<td class="mobile">手机</td><td class="ztel" colspan="2">' + d.data.dutytel + '</td></tr>';
                                    str += '<tr>\n' +
                                        '<td>替代人</td><td  class="tpeo">' + d.data.replacename + '</td>\n' +
                                        '<td>电话</td><td  class="tphone">' + d.data.replcaeworkphone + '</td>\n' +
                                        '<td >手机</td><td class="ttel" colspan="2">' + d.data.replacetel + '</td></tr>';
                                    parent.$("#base_msg").html(str)
                                }else{
                                    parent.$("#base_msg").html("<p style='width:1000px;height:60px;text-align:center;padding:20px 20px;font-size:20px'>无数据</p>")
                                }
                            }
                        });
                        parent.$('#contacts_xq_table').datagrid({
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
                        parent.$('#abnormal_dg').datagrid({
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
                                {field:'bzgycC8788',title:'卫星名称',width:'15%', align: 'center'},
                                {field:'bzgycC512',title:'异常名称',width:'15%', align: 'center'},
                                {field:'bzgycC9117',title:'发生次数',width:'5%',align: 'center'},
                                {field:'bzgycC9064', title: '发生时间',width:'15%', align: 'center'},
                                { field:'bzgycSubmitTime', title: '上报时间',width:'15%', align: 'center'},
                                { field:'bzgyc_detail', title: '详情',width:'30%', align: 'center',
                                       formatter: function (value,row,index) {
                                           return '<span style="cursor: pointer" class="bzgyc_detail" rowindex="'+index+'">详情</span>'
                                       }
                                }
                            ]],
                            onLoadSuccess:function(data){
                            if(data.rows.length>0){
                                //查询
                                parent.$("body").undelegate("#abnormal_query","click").delegate("#abnormal_query","click",function(){
                                    var keyword = parent.$("#abnormal_keyword").val();
                                    if(keyword){
                                        parent.$("#abnormal_dg").datagrid("load",{satId:ssatid,bzgycName:keyword});
                                    }else{
                                        parent.$("#abnormal_dg").datagrid("load",{satId:ssatid});

                                    }
                                });
                                // 详情
                                parent.$("body").undelegate(".bzgyc_detail","click").delegate(".bzgyc_detail","click",function(){
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
                                        element: $("#bzgyc_detail"),
                                        elementId: "bzgyc_detail",
                                        title: '详情',
                                        icon: '',
                                        ok_button_handler: function () {
                                        },
                                        display_ok_button:false,
                                        cancel_button_text:'关闭'
                                    });
                                    parent.$("#bzgyc_detail").html(str)
                                });
                            }
                            }
                        });
                        break;
                    case '地月影预报':
                        parent.$('#moon_dg').datagrid({
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
                                parent.$("body").undelegate("#dyyquery","click").delegate("#dyyquery","click",function(){
                                    var keyword = parent.$("#dyykeyword").val();
                                    if(keyword){
                                        parent.$("#moon_dg").datagrid("load",{satId:ssatid,keyWord:keyword});
                                    }else{
                                        parent.$("#moon_dg").datagrid("load",{satId:ssatid});
                                    }
                                });
                            }
                        });

                        break;
                    case '测控计划':
                        parent.$('#cekong_dg').datagrid({
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
                        parent.$('#team_dg').datagrid({
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
                                parent.$("body").undelegate("#zgdwquery","click").delegate("#zgdwquery","click",function(){
                                    var keyword = parent.$("#zgdwkeyword").val();
                                    if(keyword){
                                        parent.$("#team_dg").datagrid("load",{satId:ssatid,keyWord:keyword});
                                    }else{
                                        parent.$("#team_dg").datagrid("load",{satId:ssatid});
                                    }
                                });
                            }
                        });
                        break;
                }
            },
        });
    }
    //获取型号集分类创建型号输入数据
    function getInputData(type,data){
        var bssName=parent.$("#createType_name");
        var bssDesc=parent.$("#createType_desc");
        var bssSatlist=parent.$("#typeNameList");
        // var bssType=parent.$(".radio").find('input[name="singleSelection"]');
        var bssUserlist=parent.$("#dutyManList");
        if(type=="clear"){
            bssName.val("");
            bssDesc.val("");
            parent.$("#typeNameList").find('input[type=checkbox]').prop('checked',false);
            parent.$(".radio").find('input[name="singleSelection"]').prop('checked',false);
            parent.$("#dutyManList").find('input[type=checkbox]').prop('checked',false)
        }
        if(type=="get"){
            if(bssName.val()==""){
                parent.showAlert("提示","请输入分类名称","error");
                return false;
            }
            if(bssDesc.val()==""){
                parent.showAlert("提示","请输入分类描述","error");
                return false;
            }
            var obj={};
            obj.bssName=parent.$("#createType_name").val();
            obj.bssDesc=parent.$("#createType_desc").val();
            var nameArr=[];
            parent.$("#typeNameList").find('input[type=checkbox]').each(function(){
                if($(this).prop('checked')){
                    nameArr.push($(this).attr("id"))
                }
            });
            if(nameArr.length==0){
                obj.satids=''
            }else{
                obj.satids=nameArr.toString();
            }
            obj.bssType=parent.$(".radio").find('input[name="singleSelection"]:checked').val();
            if(obj.bssType==""||obj.bssType==null||obj.bssType==undefined){
                parent.showAlert("提示","请选择类型","error");
                return false;
            }
            if(parent.$(".radio").find('input[name="singleSelection"]:checked').val()=="共有"){
                var dutyArr=[];
                parent.$("#dutyManList").find('input[type=checkbox]').each(function(){
                    if($(this).prop('checked')){
                        dutyArr.push($(this).attr("id"))
                    }
                });
                if(dutyArr.length==0){
                    obj.userIds=''
                }else{
                    obj.userIds=dutyArr.toString();
                }
            }
            return obj;
        }
        if(type=="set"){
            bssName.val(data.bss_name);
            bssDesc.val(data.bss_desc);
            var bsatCode=data.satids.split(",");
            parent.$("#typeNameList").find('input[type=checkbox]').each(function(){
                var othis=$(this);
                for(var i=0;i<bsatCode.length;i++){
                    if(othis.prop("id")==bsatCode[i]){
                        othis.prop("checked",true)
                    }
                }
            });
            parent.$("[name='singleSelection'][value="+data.bss_type+"]").prop("checked", true);
            if(data.bss_type=='共有'){
                var userarr=data.userIds.split(",");
                parent.$("#dutyManList").find('input[type=checkbox]').each(function(){
                    var othis=$(this);
                    for(var j=0;j<userarr.length;j++){
                        if(othis.prop("id")==userarr[j]){
                            othis.prop("checked",true)
                        }
                    }
                });
                parent.$("#radioControll").show();
            }else{
                parent.$("#radioControll").hide();
            }
        }
    }



})