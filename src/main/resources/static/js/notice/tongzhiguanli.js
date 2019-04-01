$(function () {

    //获取权限生成操作按钮
    var menuAction =parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);
    $('#notice').datagrid({
        url:'/api/bnotice/findByParamEzui',
        columns:[[
            {field:'ck',checkbox:true},
            {field: 'num', title: '序号', width: '5%', align: 'center', resizable: true, sortable: true,
                formatter: function (value,row,index) {
                    var op = $('#notice').datagrid('options');
                    var num=(index*1+1)+op.pageSize * (op.pageNumber - 1);
                    return '<span class="row_num">'+num+'</span>'
                }},
            {field:'bnteType',title:'通知类型',width:'10%',align:'center'},
            {field:'bnteDesc',title:'通知内容',width:'35%',align:'center'},
            {field:'bnteAtpcreatedatetime',title:'添加时间',width:'19%',align:'center'},
            {field:'bnteTimelong',title:'显示时长(小时)',width:'10%',align:'center'},
            {field:'handle',title:'操作',width:'20%',align:'center',formatter: function(value,row,index){
                    var updatabtn = '<a style="cursor: pointer" id="edit" class="update"  userid="'+row.bnteAtpid+'">编辑</a>';
                    var delbtn= '<a style="cursor: pointer" id="del" class="delete" userid="'+row.bnteAtpid+'">删除</a>';
                    var buttons=[];
                    if(menuAction.indexOf("5")!=-1) buttons.push(updatabtn);
                    if(menuAction.indexOf("6")!=-1) buttons.push(delbtn);
                    return buttons.join("<span style='margin:0 5px'>|</span>");
                }
            }
        ]],
        pagination:true,
        rownumbers:false,
        showFooter:true,
        fitColumns:true,
     /*   pageNumber:1,
        pageSize:7,
        pageList:[7,20,30,40,50],*/
        height:'100%',
        width:'100%',
        onLoadSuccess:function(d){
        }
    });

    //添加
    $("#add").on("click",function(){
        openDialog("添加通知",function(){
            var data = getInputData("get");
            if(!data) return;
            ht.data.getData("/api/bnotice/addBnotice","POST",data,function(d){
                if(d["status"]=="1"){
                    parent.showAlert("恭喜","添加成功","info",function(){
                        $("#notice").datagrid("reload");
                        closeDialog();
                    });
                    parent.noticeFn();
                }else{
                    parent.showAlert("错误",d.msg,"error");
                }
            })
        });
        getInputData("clear");
    });
    //点击toolbar按钮修改
    $("#update").on("click",function(){
        var selectRows = $("#notice").datagrid("getSelections");
        if(selectRows.length==1){
            var userId = selectRows[0].bnteAtpid ;
            doUpdate(userId);
        }else{
            parent.showAlert("提示","请选择1条数据进行修改","warning");
        }
    });
    //点击操作栏修改
    $("body").undelegate(".update","click").delegate(".update","click",function(){
        var userId = $(this).attr("userid");
        doUpdate(userId);
    });
    //点击toolbar按钮删除
    $("#delete").on("click",function(){
        var selectRows = $("#notice").datagrid("getSelections");
        if(selectRows.length!=0){
            var userIds = [];
            for(var k in selectRows){
                userIds.push(selectRows[k]["bnteAtpid"]);
            }
            doDelete(userIds);

        }else{

            parent.showAlert("提示","未选择任何数据","warning");
        }

    });

    //点击操作栏删除
    $("body").undelegate(".delete","click").delegate(".delete","click",function(){
        var userIds = [$(this).attr("userid")];
        doDelete(userIds);
    });
    //查询
    $("#query").on("click",function(){
        var keyword = $("#keyword").val();
        if(keyword){
            $("#notice").datagrid("load",{keyword:keyword});
        }else{
            $("#notice").datagrid("load",{});
        }
    });
    //打开弹窗
    function openDialog(title,okBtnFunc){
        parent.createDialog({
            element:$("#notice_dialog"),
            elementId:"notice_dialog",
            title:title,
            icon:'',
            ok_button_handler:function(){
                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }
    //关闭弹窗
    function closeDialog(){
        parent.$("#notice_dialog").dialog("close");
    }
    //获取用户输入数据
    function getInputData(type,data){
        var noticeType = parent.$("#noticeType");
        var describe = parent.$("#describe");
        var showTime = parent.$("#showTime");
        if(type=="clear"){
            noticeType.val("");
            describe.val("");
            showTime.val("");
        }
        if(type=="get"){
            if(noticeType.val()==""){
                parent.showAlert("提示","请输入通知类型","error");
                return false;
            }
            if(describe.val()==""){
                parent.showAlert("提示","请输入通知描述","error");
                return false;
            }
            if(showTime.val()==""){
                parent.showAlert("提示","请输入显示时长","error");
                return false;
            }else{
                var reg=/^[1-9]\d*$/;
                if(!reg.test(showTime.val())){
                    parent.showAlert("提示","时长必须为整数","error");
                    return false;
                }
            }
            var data = {bnteType:noticeType.val(),bnteDesc:describe.val(),bnteTimelong:showTime.val()};
            return data;
        }
        if(type=="set"){
            noticeType.val(data.bnteType);
            describe.val(data.bnteDesc);
            showTime.val(data.bnteTimelong);
        }
    }
    //更新数据
    function doUpdate(userId){
        getInputData("clear");
        ht.data.getData("/api/bnotice/getOneById","POST",{atpId :userId},function (d) {
            if(d["status"]=="1"){
                openDialog("修改通知",function(){
                    var data = getInputData("get");
                    if(!data) return;
                    data.bnteAtpid = userId;
                    ht.data.getData("/api/bnotice/updateBnotice","POST",data,function(re){
                        if(re["status"]=="1"){
                            parent.showAlert("恭喜","修改成功","info",function(){
                                $("#notice").datagrid("reload");
                                closeDialog();
                                parent.noticeFn();
                            })
                        }else{
                            parent.showAlert("错误",re.msg,"error");
                        }
                    })
                });
                getInputData("set",d.data);
            }else{
                parent.showAlert("错误",d.msg,"error");
            }
        })
    }
    //删除数据
    function doDelete(userIds){
        parent.showConfirm("提示","确定删除"+userIds.length+"条记录？",function(r){
            if(r){
                ht.data.getData("/api/bnotice/removeBnotice","POST",{BnoticeAtpid :userIds.join(",")},function (d) {
                    if(d["status"]=="1"){
                        parent.showAlert("恭喜","删除成功","info",function(){
                            $("#notice").datagrid("reload");
                        });
                        parent.noticeFn();
                    }else{
                        parent.showAlert("错误",d.msg,"error");
                    }
                })
            }
        })
    }
});