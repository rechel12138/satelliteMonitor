(function($){
    //加载卫星
    $.ajax({
        type: 'post',
        async: false,
        url:'/api/bsat/listBsatInfo',
        data: {'satelliteType':'遥感卫星'},
        success: function(data) {
            if(data.searchCount==true){
                var satlite_data=data.rows;
                var str='';
                for(var i=0;i<satlite_data.length;i++){
                    str+='<div class="satellite" style="position:relative"><input type="checkbox"  class="select_box"/><img class="img_hover satellite_pic" src="/images/big_pic.png"/><h3>型号代号（26）：<span class="model_code">'+satlite_data[i].bsatCode+'</span></h3><p>对外名称：'+satlite_data[i].bsatOutsideName+'</p><p>发射时间：'+satlite_data[i].bsatLaunchTime+'</p><p>状态：'+satlite_data[i].bsatActive+'</p><div><span class="detail_btn">详情</span><span class="download_btn" satId="'+satlite_data[i].bsatId+'">回放数据</span></div><input class="satlite_id" type="hidden" value="'+satlite_data[i].bsatAtpid+'"><div class="qk_pic" style="display: none;position:absolute;top:168px;left:26px;background-color:#fff;color:rgb(136, 136, 136)"><ul><li>型号代号（五院）：<span>'+satlite_data[i].bsatName+
                        '</span></li><li>对内名称：<span>'+satlite_data[i].bsatFullName+'</span></li><li>责任人：<span>'+satlite_data[i].bsatAdminSubsId+'</span></li><li>责任人电话：<span>'+satlite_data[i].zrrdh+'</span></li><li>替代人：<span>'+satlite_data[i].bsatAdminId+'</span></li><li>替代人电话：<span>'+satlite_data[i].tdrdh+'</span></li></ul></div></div>'
                }
                $("#all_satellites").html(str)
               /* //列表切换数据table
                $("#dg").datagrid({
                    data:satlite_data,
                    nowrap: true,
                    fitColumns:true,
                    striped: true,
                    rownumbers:true,
                    pagination:true,
                    singleSelect:false,
                    pageNumber:1,
                    pageSize:7,
                    pageList:[7,20,30,40,50],
                    width:'100%',
                    hieght:'100%',
                    columns:[[
                        {field:'bsatCode',title:'型号代号',align:'center'},
                        {field:'bsatName',title:'型号代号（五院）',align:'center'},
                        {field:'bsatFull_name',title:'对内名称',align:'center'},
                        {field:'bsatOutsideName',title:'对外名称',align:'center'},
                        {field:'bsatLaunchTime',title:'发射时间',align:'center'},
                        {field:'bsatLifeyear',title:'考核寿命',align:'center'},
                        {field:'bsatActive',title:'状态',align:'center'},
                        {field:'zrrname',title:'责任人',align:'center'},
                        {field:'zrrdh',title:'责任人电话',align:'center'},
                        {field:'tdrname',title:'替代人',align:'center'},
                        {field:'tdrdh',title:'替代人电话',align:'center'},
                        {field:'bsatBxxrjlId',title:'在轨型号责任联系人',align:'center'},
                        {field:'bsatBxhzcdwId',title:'在轨技术支持队伍',align:'center'},
                        {field:'op',title:'操作',align:'center',formatter:function(v,r){
                                var btnopen = "<a style='cursor: pointer' class='btnopen' userid='"+r.suAtpid+"'>打开</a>";
                                var btnload = "<a style='cursor: pointer' class='btnload' userid='"+r.suAtpid+"'>回放数据下载</a>";
                                var buttons = [];
                                buttons.push(btnopen);
                                buttons.push(btnload);
                                return buttons.join("<span style='margin:0 5px'>|</span>");
                            }}
                    ]],
                    rowStyler:function(index,row){
                        var valuestatus=row.valuestatus;
                        switch(valuestatus){
                            case '待发射':
                                return 'background-color:green';
                                break;
                            case '未超寿':
                                return 'background-color:blue';
                                break;
                            default:
                                return 'background-color:yellow';
                        }
                    },
                    onLoadSuccess:function(data){
                    }
                })*/
            }
        },
        error: function(request) {
            parent.showAlert("提示","加载数据失败","warning");
        }
    });
    //置顶
    $("#set_top").click(function(){
        var topPic='';
        $(".select_box").each(function(){
            if($(this).prop('checked')==true){
                $(this).removeAttr("checked");
                var img_url=$(this).siblings('.satellite_pic').attr('src');
                var img_name=$(this).siblings('h3').find('.model_code').text();
                topPic+='<div class="top_pic"><img src="'+img_url+'"/><p class="top_pic_title">'+img_name+'</p></div>'
            }
        });
        parent.$(".top_area").append(topPic)
    });
    //下载列表
  /*  $("#download_list").click(function(){
        $("#download_list_item").slideToggle()
    });*/
    //快照
    $(".img_hover").hover(function(){
        $(this).siblings().last().show();
    },function(){
        $(this).siblings().last().hide();
    });
    //详情
    $(".detail_btn").on("click",function() {
        var title_data=$(this).parents().siblings('h3').find(".model_code").text();
        detailsData('clear');
        var data_id=$(this).parents().siblings('.satlite_id').val();
        detailsFn(title_data,data_id)
    });
    function detailsFn(title_data,data_id){
        parent.createDialogAlert({
            element: $("#detail_box"),
            elementId: "detail_box",
            title:title_data,
            icon: '',
        });
        $.ajax({
            type: 'post',
            async: false,
            url: '/api/bsat/getOneBsatById',
            data: {'atpId': data_id},
            success: function(data) {
                if(data.status==1){
                    detailsData('set',data.data);
                }
            },
            error: function(request) {
                parent.showAlert("提示","加载数据失败","warning");
            }
        });
    }
    function detailsData(type,data){
        var wrap_Name = parent.$("#wrap_Name");
        var start_Time = parent.$("#start_Time");
        var end_Time = parent.$("#end_Time");
        var life_Time = parent.$("#life_Time");
        var status_Time = parent.$("#status_Time");
        var duty_Name = parent.$("#duty_Name");
        var duty_Tel = parent.$("#duty_Tel");
        var replace_Name = parent.$("#replace_Name");
        var replace_Tel = parent.$("#replace_Tel");
        if(type=='clear'){
            wrap_Name.text("");
            start_Time.text("");
            end_Time.text("");
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
            status_Time.text(data.bsatActive);
            duty_Name.text(data.zrrname);
            duty_Tel.text(data.zrrdh);
            replace_Name.text(data.tdrname);
            replace_Tel.text(data.tdrdh);
        }
    }
    //回放数据
    $(".download_btn").on("click",function() {
        var $satId=$(this).attr('satId');
        opendwnFn($satId);
        bindClick($satId);

    });
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
            columns:[[
                {field:'fileName',title:'文件名',width:'45%',align:'center',formatter:function(v,r){
                        return "<a href='#' class='dn_file' filename='"+v+"'>"+v+"</a>";
                    }},
                {field:'fileSize',title:'文件大小',width:'10%',align:'center'},
                {field:'fileType',title:'文件类型',width:'10%',align:'center'},
                {field:'fileModifyTime',title:'最近修改时间',width:$(this).width() * 0.2,align:'center'}
            ]],
            onLoadSuccess:function(data){
                console.log(data)

            }
        })
    };
    function bindClick(id){
        parent.$("body").undelegate(".dn_file","click").delegate(".dn_file","click",function(){
            var fileName = $(this).attr("filename");
            //parent.document.location.href="/api/file/downloadRemoteFile?fileName="+fileName+"satId="+
            alert(fileName+"\n"+id);
        });
    }
// 列表切换
    $('.select_list').change(function(){
        var data_value=$(this).val();
        switch (data_value){
            case 'big':
                $('#all_satellites').show();
                $('#satellite_list_box').hide();
                break;
            case 'small':
                $('#all_satellites').hide();
                $('.satellite_list_box').hide();
                // $('#all_satellites').show();
                break;
            case 'list':
                // alert('此静态页面不全');
                $('#all_satellites').hide();
                $('#satellite_list_box').show();
                // $("#dg").datagrid("reload");
                $.ajax({
                    type: 'post',
                    async: false,
                    url: '/api/bsat/listBsatInfo',
                    data: {'satelliteType': '1'},
                    success: function(data) {
                        if(data.searchCount==true){
                            var satlite_data=data.rows;
                            //列表切换数据table
                            $("#dg").datagrid({
                                data:satlite_data,
                                nowrap: true,
                                fitColumns:true,
                                striped: true,
                                rownumbers:true,
                                pagination:true,
                                singleSelect:false,
                                pageNumber:1,
                                pageSize:7,
                                pageList:[7,20,30,40,50],
                                width:'100%',
                                // hieght:'100%',
                                columns:[[
                                    {field:'bsatCode',title:'型号代号',align:'center',width:'7%'},
                                    {field:'bsatName',title:'型号代号（五院）',align:'center',width:'7%'},
                                    {field:'bsatFull_name',title:'对内名称',align:'center',width:'7%'},
                                    {field:'bsatOutsideName',title:'对外名称',align:'center',width:'7%'},
                                    {field:'bsatLaunchTime',title:'发射时间',align:'center',width:'7%'},
                                    {field:'bsatLifeyear',title:'考核寿命',align:'center',width:'5%'},
                                    {field:'bsatActive',title:'状态',align:'center',width:'7%'},
                                    {field:'zrrname',title:'责任人',align:'center',width:'7%'},
                                    {field:'zrrdh',title:'责任人电话',align:'center',width:'7%'},
                                    {field:'tdrname',title:'替代人',align:'center',width:'7%'},
                                    {field:'tdrdh',title:'替代人电话',align:'center',width:'7%'},
                                    {field:'bsatBxxrjlId',title:'在轨型号责任联系人',align:'center',width:'7%',formatter:function(v,r){
                                            /*  if(v=="0") return "<a style='cursor: pointer' class='tel_xq' downid='"+r.bsatAtpid+"'>无</a>";
                                              if(v=="1") return "<a style='cursor: pointer' class='tel_xq' downid='"+r.bsatAtpid+"'>详情</a>";*/
                                            return "<a style='cursor: pointer' class='tel_xq' downid='"+v+"'>详情</a>"
                                        }},
                                    {field:'bsatBxhzcdwId',title:'在轨技术支持队伍',align:'center',width:'7%',formatter:function(v,r){
                                            /*    if(v=="0") return "<a style='cursor: pointer' class='ranks_xq' downid='"+r.bsatAtpid+"'>无</a>";
                                                if(v=="1") return "<a style='cursor: pointer' class='ranks_xq' downid='"+r.bsatAtpid+"'>详情</a>";*/
                                            return "<a style='cursor: pointer' class='ranks_xq' downid='"+v+"'>详情</a>"
                                        }},
                                    {field:'op',title:'操作',align:'center',width:'10%',formatter:function(v,r){
                                            var btnopen = "<a style='cursor: pointer' class='btnopen' detail_id='"+r.bsatAtpid+"' detail_title='"+r.bsatCode+"'>打开</a>";
                                            var btnload = "<a style='cursor: pointer' class='btnload' downid='"+r.bsatAtpid+"'>回放数据下载</a>";
                                            var buttons = [];
                                            buttons.push(btnopen);
                                            buttons.push(btnload);
                                            return buttons.join("<span style='margin:0 5px'>|</span>");
                                        }}
                                ]],
                                rowStyler:function(index,row){
                                    var valuestatus=row.valuestatus;
                                    switch(valuestatus){
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
                                }
                            });
                            $(".btnopen").off("click").click(function(){
                                var title_data=$(this).attr('detail_title');
                                var data_id=$(this).attr('detail_id');
                                detailsFn(title_data,data_id)
                            });
                            $(".btnload").off("click").click(function(){
                                var $satId=$(this).attr('downid');
                                opendwnFn($satId);
                                bindClick($satId);
                            })
                        }
                    },
                    error: function(request) {
                        parent.showAlert("提示","加载数据失败","warning");
                    }
                });
                break;
        }
    })
})(jQuery)