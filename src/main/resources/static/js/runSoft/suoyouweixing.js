(function($){
    //获取权限生成操作按钮
    var menuAction = parent.getMenuInfo().menuAction;
    var btn_top='<span class="set_top" id="set_top" style="cursor: pointer">置顶</span>';
    var btn_download_list='<span class="download_list" id="download_list" ishow=0 style="cursor: pointer">下载列表</span>';
    var btn_select_list='<select class="select_list"><option value="big">大图标</option><option value="list">列表</option></select>';
    var buttons = [];
    if(menuAction.indexOf("1")!=-1){
         buttons.push(btn_top)
    }
    if(menuAction.indexOf("2")!=-1){
        buttons.push(btn_download_list)
    }
    if(menuAction.indexOf("3")!=-1){
        buttons.push(btn_select_list)
    }
    var btnStr=buttons.join(' ');
    $(".top_btn").append(btnStr);
    //获取卫星型号
    var satelliteType= $("#satelliteType").val();
    ht.data.getData("/api/bsat/findByParamEzuiForBsat","POST",{domain:satelliteType},function (data) {
        // if(data.current==1){
        console.log(data)
            var satlite_data=data.rows;
            var str='';
            for(var i=0;i<satlite_data.length;i++){
                for(okey in satlite_data[i]){
                    if(satlite_data[i][okey]==null){
                        satlite_data[i][okey]='';
                    }
                }
                str+='<div class="satellite" style="position:relative"><input type="checkbox"  class="select_box"/><input type="hidden"  satId="'+satlite_data[i].bsatAtpid+'"/><img class="img_hover satellite_pic" src="'+satlite_data[i].bsatMinipic+'"/><h3>型号代号（26）：<span class="model_code">'+satlite_data[i].bsatCode+'</span></h3><p>对外名称：'+satlite_data[i].bsatOutsideName+'</p><p>发射时间：'+satlite_data[i].bsatLaunchTime+'</p>'
                if(satlite_data[i].valuestatus=='待发射'||satlite_data[i].valuestatus=="未超寿"||satlite_data[i].valuestatus==null){
                    str+='<p>状态：<span>'+satlite_data[i].valuestatus+'</span></p>'
                }else{
                    str+='<p>状态：<span style="color:#ff0000">'+satlite_data[i].valuestatus+'</span></p>'
                }
                    str+='<div><span class="detail_btn" style="display: none">详情</span><span class="download_btn" satId="'+satlite_data[i].bsatAtpid+'" style="display: none">回放数据</span></div><input class="satlite_id" type="hidden" value="'+satlite_data[i].bsatAtpid+'"><div class="qk_pic"><ul><li>型号代号（五院）：<span>'+satlite_data[i].bsatName+
                    '</span></li><li>对内名称：<span>'+satlite_data[i].bsatFullName+'</span></li><li>责任人：<span>'+satlite_data[i].zrrname+'</span></li><li>责任人电话：<span>'+satlite_data[i].zrrdh+'</span></li><li>替代人：<span>'+satlite_data[i].tdrname+'</span></li><li>替代人电话：<span>'+satlite_data[i].tdrdh+'</span></li></ul></div></div>'
            }
            $("#all_satellites").html(str);
            //大图标自适应：
            fitSize();
            function fitSize() {
                var wrapWidth = $(".all_satellites").width()-12;
                var sWidth = $(".satellite").width()+2;
                var sNum = Math.floor(wrapWidth/sWidth);
                var smarginl = (wrapWidth-sWidth*sNum)/(sNum+1);
                $(".satellite").css("margin-left",smarginl);
            }
            $(window).resize(function () {
                fitSize();
            });


            if(menuAction.indexOf("11")!=-1){
                $(".detail_btn").show();
                //详情
                $("body").delegate('.detail_btn',"click",function() {
                    var title_data=$(this).parents().siblings('h3').find(".model_code").text();
                    detailsData('clear');
                    var data_id=$(this).parents().siblings('.satlite_id').val();
                    detailsFn(title_data,data_id)
                });
            }
            if(menuAction.indexOf("12")!=-1){
                $(".download_btn").show();
                //回放数据
                $("body").delegate('.download_btn',"click",function() {
                    var $satId=$(this).attr('satId');
                    opendwnFn($satId);
                    bindClick($satId);
                })
            }
     /*   }else{
            parent.showAlert("错误",data.searchCount,"error");
        }*/
    });



    //置顶
    $("#set_top").click(function(){
        var topPic='';
        var satIdArr=[];
        $(".select_box").each(function(){
            if($(this).prop('checked')==true){
                $(this).removeAttr("checked");
                var _satid=$(this).siblings('input[type=hidden]').attr('satId');
                satIdArr.push(_satid);
                // var img_url=$(this).siblings('.satellite_pic').attr('src');
                // var img_name=$(this).siblings('h3').find('.model_code').text();
                // topPic+='<div class="top_pic" style="position:relative"><span style="position:absolute;top:5px;right:5px;color:#FF0000;border:0" class="top_listSatelite_del" atpid="'+_satid+'">X</span><img src="'+img_url+'"/><p class="top_pic_title">'+img_name+'</p></div>'
            }
        });
        var satIdStr=satIdArr.toString();
          ht.data.getData("/api/bsat/topBsat","POST",{atpIds:satIdStr},function (d) {
              if(d.status==1){
                  // parent.$(".top_area").append(topPic);
                  parent.top_getlist();
                  parent.showAlert('提示',d.msg,"info")
              }else{
                  parent.showAlert('提示',d.msg,"error")
              }
            });
    });
    //下载列表展示
    $("#download_list").click(function(){
        var is_show=$(this).attr("ishow");
          if(is_show==0){
              ht.data.getData("/htdxjk/bsatdatamsg/getDownloadedFileList", "POST",{},function (d) {
                  if(d.status==1){
                      var str='';
                      $("#download_list_item").find('p').text('共传输完成'+d.data.length+'个文件');
                      d.data.forEach(function(value,index,arr){
                          str+='<li style="background-color:#fff3f3;padding:5px 5px;font-size: 15px"><span style="padding-right:15px;">'+value.bsdmFilename+'</span><span style="padding-right:15px;">'+value.bsdmAtpcreatedatetime+'</span><span  style="padding-right:15px;">'+value.bsdmStatus+'</span><a href="#" class="down_lis_del" style="cursor:pointer;font-size: 15px" appid="'+value.bsdmAtpid+'">删除</a></li>'
                      })
                  }else{
                      parent.showAlert("提示",d.msg,"error");
                  }
                  $('#download_list_ul').html(str);
                  $(".down_lis_del").off('click').click(function(){
                      var appid=$(this).attr('appid');
                      var $ode=$(this);
                      ht.data.getData("/htdxjk/bsatdatamsg/getDownloadedFileList", "get",{atpIds:appid},function (d) {
                          if(d.status==1){
                              $ode.parent().remove();
                          }
                      })
                  });
              });
              $(this).attr("ishow",1);
              $("#download_list_item").slideDown();
          }else{
              $(this).attr("ishow",0);
              $("#download_list_item").slideUp();
          }

    });
    //快照
    $("body").delegate('.img_hover',"mouseover",function() {
        $(this).siblings().last().show();
    });
    $("body").delegate('.img_hover',"mouseout",function() {
        $(this).siblings().last().hide();
    });
    function detailsFn(title_data,data_id){
        parent.createDialogAlert({
            element: $("#detail_box"),
            elementId: "detail_box",
            title:title_data,
            icon: ''
        });
        ht.data.getData("/api/bsat/getOneBsatById", "post",{atpId:data_id},function (data) {
            if(data.status==1){
                for(okey in data.data){
                    if(data.data[okey]==null){
                        data.data[okey]='';
                    }
                }
                detailsData('set',data.data);
            }else{
                parent.showAlert("提示",data.msg,"error");
            }
        })
    }
    function detailsData(type,data){
        var wrap_Name = parent.$("#wrap_Name");
        var start_Time = parent.$("#start_Time");
        var life_Time = parent.$("#life_Time");
        var status_Time = parent.$("#status_Time");
        var duty_Name = parent.$("#duty_Name");
        var duty_Tel = parent.$("#duty_Tel");
        var replace_Name = parent.$("#replace_Name");
        var replace_Tel = parent.$("#replace_Tel");
        if(type=='clear'){
            wrap_Name.text("");
            start_Time.text("");
            life_Time.text("");
            status_Time.text("");
            duty_Name.text("");
            duty_Tel.text("");
            replace_Name.text("");
            replace_Tel.text("");
        }
        if(type=='set'){
            wrap_Name.text(data.bsatOutsideName);
            start_Time.text(data.bsatLaunchTime);
            life_Time.text(data.bsatOutsideName);
            status_Time.text(data.valuestatus);
            duty_Name.text(data.zrrname);
            duty_Tel.text(data.zrrdh);
            replace_Name.text(data.tdrname);
            replace_Tel.text(data.tdrdh);
        }
    }

function opendwnFn($satId){
    parent.createDialog({
        element: $("#pre_download"),
        elementId: "pre_download",
        title: '回放数据下载',
        icon: '',
        ok_button_handler:function(){
            alert('回调函数事件：字接口还未传参')
        },
        display_ok_button:false,
        cancel_button_text:'关闭'
    });
    parent.$("#predata").datagrid({
        url:'/api/file/listFtpFiles',
        queryParams:{satId:$satId},
        nowrap: true,
        fitColumns:true,
        striped: true,
        rownumbers:false,
        singleSelect:true,
        scrollbarSize:0,
        height:'450px',
        columns:[[
            {field:'fileName',title:'文件名',width:'45%',align:'center',formatter:function(v,r){
                    return "<a href='#' class='dn_file' filename='"+v+"'>"+v+"</a>";
                }},
            {field:'fileSize',title:'文件大小',width:'10%',align:'center'},
            {field:'fileType',title:'文件类型',width:'10%',align:'center'},
            {field:'fileModifyTime',title:'最近修改时间',width:'35%',align:'center'}
        ]],
        onLoadSuccess:function(data){

        }
    })
}
function bindClick(id){
    parent.$("body").undelegate(".dn_file","click").delegate(".dn_file","click",function(){
        var fileName = $(this).attr("filename");
        parent.document.location.href="/api/file/downloadRemoteFile?fileName="+fileName+"&satId="+id;
        });
    }

// 列表切换
    $('.select_list').change(function(){
        var data_value=$(this).val();
        switch (data_value){
            case 'big':
                $('#all_satellites').show();
                $('.satellite_list_box').hide();
                break;
         /*   case 'small':
                $('#all_satellites').hide();
                $('.satellite_list_box').hide();
                break;*/
            case 'list':
                $('#all_satellites').hide();
                $('.satellite_list_box').show();
                ht.data.getData("/api/bsat/findByParamEzuiForBsat", "post",{domain:satelliteType},function (data) {
                    // if(data.current==1){
                        var satlite_data=data.rows;
                        // console.log(satlite_data)
                        //列表切换数据table

                   for(var i=0; i<satlite_data.length;i++){
                       for(okey in satlite_data[i]){
                           if(satlite_data[i][okey]==null){
                               satlite_data[i][okey]='';
                           }
                       }
                   }
                        $("#dg").datagrid({
                            data:satlite_data,
                            nowrap: true,
                            fitColumns:true,
                            striped: true,
                            rownumbers:true,
                            pagination:false,
                            singleSelect:false,
                            /*    pageNumber:1,
                                  pageSize:7,
                                  pageList:[7,20,30,40,50],*/
                         /*   height:'350px',
                            width:'100%',*/
                            columns:[[
                                {field:'bsatCode',title:'型号代号&nbsp▼',sortable:false,align:'center',width:'10%',id:'btn_code'
                                /*    sorter:function(a,b){
                                                      a = a.split('/');
                                                      b = b.split('/');
                                                      if (a[2] == b[2]){
                                                          if (a[0] == b[0]){
                                                              return (a[1]>b[1]?1:-1);
                                                          } else {
                                                              return (a[0]>b[0]?1:-1);
                                                          }
                                                      } else {
                                                          return (a[2]>b[2]?1:-1);
                                                      }}*/
                                    },
                                {field:'bsatName',title:'型号代号（五院）',align:'center',width:'10%'},
                                {field:'bsatFull_name',title:'对内名称',align:'center',width:'10%'},
                                {field:'bsatOutsideName',title:'对外名称',align:'center',width:'10%'},
                                {field:'bsatLaunchTime',title:'发射时间',align:'center',width:'20%'},
                                {field:'bsatLifeyear',title:'考核寿命',align:'center',width:'8%'},
                                {field:'valuestatus',title:'状态',align:'center',width:'10%',
                                    formatter:function(v,rowData,rowIndex){
                                     if(v=='未超寿'||v=="待发射"||v==null){
                                         return '<span>'+v+'</span>'
                                     }else{
                                         return '<span style="color: #ff0000">'+v+'</span>'
                                     }
                                    }},
                                {field:'zrrname',title:'责任人',align:'center',width:'10%'},
                                {field:'zrrdh',title:'责任人电话',align:'center',width:'20%'},
                                {field:'tdrname',title:'替代人',align:'center',width:'10%'},
                                {field:'tdrdh',title:'替代人电话',align:'center',width:'20%'},
                                {field:'bsatBxxrjlId',title:'在轨型号责任联系人',align:'center',width:'10%',
                                    formatter:function(v,rowData,rowIndex){
                                        /*  if(v=="0") return "<a style='cursor: pointer' class='tel_xq' downid='"+r.bsatAtpid+"'>无</a>";
                                             if(v=="1") return "<a style='cursor: pointer' class='tel_xq' downid='"+r.bsatAtpid+"'>详情</a>";*/
                                        if(menuAction.indexOf("11")!=-1){
                                            if(v){
                                                return "<a style='cursor: pointer' class='tel_xq' downid='"+rowData.bsatAtpid+"'>详情</a>"
                                            }else{
                                                return "<a style='cursor: pointer' class='tel_xq' downid='"+rowData.bsatAtpid+"'>无</a>"
                                            }

                                        }
                                    }},
                                {field:'bsatBxhzcdwId',title:'在轨技术支持队伍',align:'center',width:'10%',
                                    formatter:function(v,rowData,rowIndex){
                                        /*    if(v=="0") return "<a style='cursor: pointer' class='ranks_xq' downid='"+r.bsatAtpid+"'>无</a>";
                                               if(v=="1") return "<a style='cursor: pointer' class='ranks_xq' downid='"+r.bsatAtpid+"'>详情</a>";*/
                                        if(menuAction.indexOf("11")!=-1){
                                            if(v){
                                                return "<a style='cursor: pointer' class='ranks_xq' downid='"+rowData.bsatAtpid+"'>详情</a>"
                                            }else{
                                                return "<a style='cursor: pointer' class='ranks_xq' downid='"+rowData.bsatAtpid+"'>无</a>"
                                            }

                                        }
                                    }},
                                {field:'op',title:'操作',align:'center',width:'10%',formatter:function(v,r){
                                    console.log(r)
                                        // var btnopen = "<a style='cursor: pointer' class='btnopen' urldirc='"+r.bsatLocalDirc+"' urlname='"+r.bsatAppName+"'>打开</a>";
                                        var btnopen='<a style="cursor: pointer" href="openLocalApplication:'+r.bsatLocalDirc+r.bsatAppName+'">打开</a>'
                                        var btnload = "<a style='cursor: pointer' class='btnload' downid='"+r.bsatAtpid+"'>回放数据下载</a>";
                                        var buttons = [];
                                        if(menuAction.indexOf("28")!=-1) buttons.push(btnopen);
                                        if(menuAction.indexOf("12")!=-1) buttons.push(btnload);
                                        return buttons.join("<span style='margin:0 5px'>|</span>");
                                    }}
                            ]],
                            rowStyler:function(index,row){
                                var valuestatu=row.valuestatus;
                                switch(valuestatu){
                                    case '待发射':
                                        return 'background-color:#d4b7c9';
                                        break;
                                    case '未超寿':
                                        return 'background-color:#88d4bc';
                                        break;
                                    default:
                                        return 'background-color:#F2E64D';
                                }
                            },
                            onLoadSuccess:function(data){
                                console.log(data)
                             var bxxrjlIddata="";
                             if(satelliteType!=""){
                                 bxxrjlIddata=data.rows[0].bsatBxxrjlId
                             }
                                ht.data.getData("/api/bsat/getAllSatIds", "post",{bxxrjlId:bxxrjlIddata},function (checkD) {

                                    if(checkD.status==1){
                                        var str="";
                                        for(var i=0;i<checkD.data.length;i++){
                                            str+='<li style="padding:5px 0;width:100%"><label for="'+checkD.data[i].code+'"><input type="checkbox" id="'+checkD.data[i].code+'">&nbsp&nbsp&nbsp'+checkD.data[i].code+'</label></li>';
                                        }
                                        $("#selectUl").html(str);
                                    }
                                });
                                $(".baseCode_select").click(function(){
                                    if($(this).prop('checked')==true){
                                        $("#selectUl").find('input[type=checkbox]').prop('checked',true)
                                    }else{
                                        $("#selectUl").find('input[type=checkbox]').prop('checked',false)
                                    }
                                });
                             //筛选
                                $('body').undelegate("#btn_code","click").delegate('#btn_code','click',function(){
                                    $("#table_sort").toggle()
                                });
                             // $("#btn_code").find(".datagrid-sort-icon").show();
                            },
                            onSortColumn:function(){
                            }
                        });
                       /* $("body").undelegate(".btnopen","click").delegate(".btnopen","click",function(){
                            var _url=$(this).attr('urldirc')+$(this).attr('urlname')
                            parent.showConfirm("提示","确定打开此程序么吗?",function(r){
                                if(r){
                                   try{
                                        //var path = 'C:\\windows\\notepad.exe';
                                        var path=_url;
                                        path = path.replace(/\\/g,"\\\\");
                                        var shell= new ActiveXObject("WScript.Shell");
                                        shell.run(path);

                                   }catch (e) {

                                       alert("打开失败，请在IE下运行，且将域名添加至可信站点");
                                   }
                                   
                                }
                            })

                        });*/
                         $("body").undelegate(".btnload","click").delegate(".btnload","click",function(){
                            var $satId=$(this).attr('downid');
                            console.log($satId);
                            opendwnFn($satId);
                            bindClick($satId);
                        });

                   /* }else{
                        parent.showAlert('提示',data.searchCount,'error')
                    }*/
                });
         break;
        }
    });
    //联系人详情
    $('body').delegate('.tel_xq','click',function(){
        var str=$(this).attr('downid');
        parent.createDialogAlert({
            element: $("#contacts_qx"),
            elementId: "contacts_qx",
            title:'在轨型号联系人详情',
            icon: ''
        });
        parent.$('#contacts_table').datagrid({
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
                atpid:str
            },
            // pageSize:7,
            // pageList:[7,20,30,40,50],
            height:'100%',
            width:'100%',
            columns:[[
                {field:'bxhzcdwPost',title:'岗位',width:'40%',align:'center'},
                {field:'bxhzcdwName',title:'姓名',width:"20%",align:'center'},
                {field:'bxhzcdwPhone',title:'手机',width:"20%",align:'center'},
                {field:'bxhzcdwTelephone',title:'电话',width:"20%",align:'center'},
            ]],
            onLoadSuccess:function(dd){
            }
        });
    });
    //队伍详情
    $('body').delegate('.ranks_xq','click',function(){
        var str=$(this).attr('downid');
        console.log()
        parent.createDialogAlert({
            element: $("#ranks_qx"),
            elementId: "ranks_qx",
            title:'在轨技术支持队伍详情',
            icon: ''
        });
        parent.$('#ranks_table').datagrid({
            url:'/api/bxhzcdw/getZcdwInfoByBsatAtpId',
            nowrap: true,
            fitColumns:true,
            striped: true,
            rownumbers:false,
            pagination:false,
            singleSelect:false,
            scrollbarSize: '0',
            // pageNumber:1,
            queryParams: {
                atpid:str
            },
            // pageSize:7,
            // pageList:[7,20,30,40,50],
            height:'100%',
            width:'100%',
            columns:[[
                {field:'bxhzcdwPost',title:'岗位',width:'25%',align:'center'},
                {field:'bxhzcdwName',title:'姓名',width:"15%",align:'center'},
                {field:'bxhzcdwUnit',title:'单位',width:"25%",align:'center'},
                {field:'bxhzcdwPhone',title:'手机',width:"20%",align:'center'},
                {field:'bxhzcdwTelephone',title:'电话',width:"20%",align:'center'},
                {field:'bxhzcdwRemark',title:'备注',width:"25%",align:'center'}
            ]],
            onLoadSuccess:function(dd){
            }
        });
    });
    //查询
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
    //close筛选
    $("#sorbtn_true").click(function(){
        var selectArr=[];
        $("#selectUl").find('input[type=checkbox]').each(function(){
              if($(this).prop('checked')){
                  selectArr.push($(this).attr('id'))
              }
        });
        if(selectArr.length!=0){
            var selectStr=selectArr.toString();
            ht.data.getData("/api/bsat/findByParamEzuiForBsat","POST",{domain:satelliteType,satCodes:selectStr},function (data) {
            // ht.data.getData("/api/bsat/listBsatInfoBySatids","POST",{satIds :selectStr},function (data) {
                $('#dg').datagrid('loadData', data.rows);
                $("#table_sort").hide();
            })
        }else{
            // ht.data.getData("/api/bsat/listBsatInfo", "post",{satelliteType:satelliteType},function (data) {
            ht.data.getData("/api/bsat/findByParamEzuiForBsat", "post",{domain:satelliteType},function (data) {
                $('#dg').datagrid('loadData', data.rows);
                $("#table_sort").hide();
            /*    if(data.status==1){
                    $('#dg').datagrid('loadData', data.rows);
                    $("#table_sort").hide();
                }else{
                    parent.showAlert('提示',data.msg,'error');
                    $("#table_sort").hide();
                }*/
            })
        }
    });
    $("#sorbtn_cel").click(function(){
        $("#table_sort").hide();
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
        ht.data.getData("/api/bsat/findByParamEzuiForBsat","POST",{domain:satelliteType,sort:'bsat_code',order:'asc'},function (data) {
            $('#dg').datagrid('loadData', data.rows);
            $("#table_sort").hide();
        });
    });
   /* //降序
    $("#sort_desc").click(function(){
        $("#dg").datagrid("load",{domain:satelliteType,sort:'bsat_code',order:'desc'});
        $("#table_sort").hide();
    })*/
    //降序
    $("#sort_desc").click(function(){
        ht.data.getData("/api/bsat/findByParamEzuiForBsat","POST",{domain:satelliteType,sort:'bsat_code',order:'desc'},function (data) {
            $('#dg').datagrid('loadData', data.rows);
            $("#table_sort").hide();
        });
    })
})(jQuery)