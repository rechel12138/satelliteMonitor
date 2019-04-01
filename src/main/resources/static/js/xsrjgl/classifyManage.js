$(function () {
    //获取权限生成操作按钮
    var menuAction =parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);

    $('#classify').datagrid({
        url:'/api/bxxrjl/findByParamEzui',
        nowrap: true,
     /* queryParams:{
            domain : ''
        },*/
        fitColumns:true,
        striped: true,
        rownumbers:true,
        pagination:true,
        singleSelect:false,
        // pageSize:10,
        pageNumber:1,
        pageSize:7,
        pageList:[7,20,30,40,50],
        height:'98%',
        width:'100%',
        columns:[[
            {field:'check_box',checkbox:true,align:'center'},
     /*       {field: 'num', title: '序号', width: '7%', align: 'center', resizable: true, sortable: true,
                formatter: function (value,row,index) {
                    var op = $('#classify').datagrid('options');
                    var num=(index*1+1)+op.pageSize * (op.pageNumber - 1);
                    return '<span class="row_num">'+num+'</span>'
                }},*/
            {field:'domainName',title:'分类名称',width:'20%',align:'center'},
            {field:'bsatCodes',title:'所属卫星',width:"21%",align:'center'},
            {field:'lastupdatetime',title:'修改时间',width:"23%",align:'center'},
            {field:'createuser',title:'创建人',width:"12%",align:'center'},
            {field:'handle',title:'操作',width:"20%",align:'center',formatter: function(value,row,index){
                console.log(row);
                    var button = '<a id="edit" class="update" style="cursor:pointer" domainParam="'+row.bxxrjl_atpid+'">编辑    |    </a>';
                    button += '<a id="del" class="del" style="cursor:pointer" bsatCodesParam="'+row.bxxrjl_atpid+'">删除</a>';
                    return button;
                }
            }
        ]]
    });


    //获取增加面板里面的所属卫星列表：
    $.ajax({
        url:'/api/bsat/getAllSatIds',
        type:'post',
        datatype:"json",
        cache: false,
        async: false,
        crossDomain: true,
        success: function (d) {
            var data=d.data;
            var sli='';
            if(d.status==1){
                for(var i=0;i<data.length;i++){
                    sli+='<li><label for="'+data[i].id+'"  style="vertical-align:top"><input type="checkbox" id="'+data[i].id+'" style="margin-right:5px">'+data[i].code+'</label></li>'
                }
          $("#satelit_class").html(sli)
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    function getSateliteIds(){
        $.ajax({
            url:'/api/bsat/getAllSatIds',
            type:'post',
            datatype:"json",
            cache: false,
            async: false,
            crossDomain: true,
            success: function (d) {
                var data=d.data;
                console.log(d)
                var sli='';
                if(d.status==1){
                    for(var i=0;i<data.length;i++){
                        sli+='<li><label for="'+data[i].id+'"  style="vertical-align:top"><input type="checkbox" id="'+data[i].id+'" style="margin-right:5px">'+data[i].code+'</label></li>'
                    }
                    parent.$("#satelit_class").html(sli)
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }


    //点击toolbar增加按钮：
    $("#add").on("click",function(){
        getSateliteIds();
        getInputData("clear");
        openDialog("添加分类",function(){
            var data = getInputData("get");
            if(!data) return;
            ht.data.getData("/api/bxxrjl/addBxxrjl","POST",data,function(d){
                if(d["status"]=="1"){
                    parent.showAlert("恭喜","添加成功","info",function(){
                        $("#classify").datagrid("reload");
                        closeDialog();
                    })
                }else{
                    parent.showAlert("错误","添加失败","error");
                }
            })
        });
    });

    //点击toolbar按钮修改
    $("#update").on("click",function(){
        var selectRows = $("#classify").datagrid("getSelections");
        if(selectRows.length==1){
            var userId = selectRows[0].bxxrjl_atpid;
            doUpdate(userId);
        }else{
            parent.showAlert("提示","请选择1条数据进行修改","warning");
        }
    });

    //点击操作栏修改
    $("body").undelegate(".update","click").delegate(".update","click",function(){

        var userId=$(this).attr("domainParam");
        doUpdate(userId);
    });
    function doUpdate(userId){
        getInputData("clear");
        ht.data.getData("/api/bxxrjl/findByParamEzui","POST",{bxxrjlAtpid:userId},function (d) {
                openDialog("修改分类",function(){
                    var data = getInputData("get");
                    if(!data) return;
                    data.bxxrjlAtpid=userId;
                    console.log(data);
                    ht.data.getData("/api/bxxrjl/updateBxxrjl","POST",data,function(re){
                        console.log(re)
                        if(re["status"]=="1"){
                            parent.showAlert("恭喜","修改成功","info",function(){
                                $("#classify").datagrid("reload");
                                closeDialog();
                            })
                        }else{
                            parent.showAlert("错误",re.msg,"warning");
                        }
                    })
                });
                getInputData("set",d.rows[0]);
        })
    }

    //点击toolbar按钮删除
    $("#delete").on("click",function(){
        var selectRows = $("#classify").datagrid("getSelections");
        console.log(selectRows)
        if(selectRows.length!=0){
            var userIds = [];
            for(var k in selectRows){
                userIds.push(selectRows[k]["bxxrjl_atpid"]);
            }
            doDelete(userIds);
        }else{
            parent.showAlert("提示","未选择任何数据","warning");
        }
    });

    //点击操作栏删除
    $("body").undelegate("#del","click").delegate("#del","click",function(){
        var userIds = [$(this).attr("bsatCodesParam")];
        doDelete(userIds);
    });
    function doDelete(userIds){
        parent.showConfirm("提示","确定删除"+userIds.length+"条记录？",function(r){
            if(r){
                ht.data.getData("/api/bxxrjl/deleteBxxrjl","POST",{bxxrjlAtpid:userIds.join(",")},function (d) {
                    if(d["status"]=="1"){
                        parent.showAlert("恭喜","删除成功","info",function(){
                            $("#classify").datagrid("reload");
                        });
                    }else{
                        parent.showAlert("错误","删除失败","error");
                    }
                })
            }
        })
    }

    //查询
    $("#query").click(function(){
        var keyword = $("#keyword").val();
        if(keyword){
            $("#classify").datagrid("load",{keyWord:keyword});
        }else{
            // parent.showAlert("提示","请输入查询关键字","warning");
            $("#classify").datagrid("load",{keyWord:''});
        }
    });

    //打开弹窗
    function openDialog(title,okBtnFunc){
        parent.createDialog({
            element:$("#classify_dialog"),
            elementId:"classify_dialog",
            title:title,
            icon:'',
            ok_button_handler:function(){
                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }
    //关闭弹窗
    function closeDialog(){
        parent.$("#classify_dialog").dialog("close");
    }

    //获取用户输入数据
    function getInputData(type,data){
        var classifyName = parent.$("#classifyName");
        var satelit_class = parent.$("#satelit_class");
        if(type=="clear"){
            classifyName.val("");
            satelit_class.find('input').attr("checked",false);
        }
        if(type=="get"){
            if(classifyName.val()==""){
                parent.showAlert("提示","请输入分类名称","error");
                return false;
            }
            var select_box=satelit_class.find('input[type=checkbox]');
            var sSlece=[];
            for(var i=0;i<select_box.size();i++){
                if(select_box.eq(i).prop("checked")){
                    sSlece.push(select_box.eq(i).attr("id"));
                }
            };
            var data = {appGroup:classifyName.val(),bsatAtpids:sSlece.join(',')};
            return data;
        }
        if(type=="set"){
            classifyName.val(data.domainName);
            var codeArr=data.atpids.split(",");
            var select_box=satelit_class.find('input[type=checkbox]');
            select_box.prop('checked',false);
            for(var i=0;i<codeArr.length;i++){
                for(var j=0;j<select_box.size();j++){
                    if(codeArr[i]==select_box.eq(j).attr("id")){
                        satelit_class.find('input[type=checkbox]').eq(j).prop("checked",true)
                    }
                }
            }
        }
    }

});