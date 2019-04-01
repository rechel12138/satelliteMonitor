$(function(){
    //获取权限生成操作按钮
    var menuAction =parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);
    $('#category').datagrid({
        url:"",
        // data: [
        //     {name:'value11',time:'2014-01-05', people:'和消化'},
        //     {name:'value11',time:'2014-11-15', people:'的说法都是'},
        //     {name:'value11',time:'2018-05-25', people:'胜多负少'}
        // ],
        columns:[[
            {field:'check_box',checkbox:true,width:10,align:'center'},
            {field:'name',title:'分类名称',width:80,align:'center'},
            {field:'time',title:'修改时间',width:180,align:'center'},
            {field:'people',title:'创建人',width:120,align:'center'},
            {field:'handle',title:'操作',width:80,align:'center',formatter: function(value,row,index){
                    var button = '<a id="edit">启用/禁用    |     </a>';
                    button += '<a id="del" class=\'update\'>编辑</a>';
                    return button;

                }
            }
        ]],
        pagination:true,
        rownumbers:true,
        showFooter:true,
        fitColumns:true,
    });
    //添加
    $("#add").on("click",function(){
        openDialog("添加数据分类",function(){
            var data = getInputData("get");
            if(!data) return;
            ht.data.getData("","POST",data,function(d){
                if(d["status"]=="1"){
                    parent.showAlert("恭喜","添加成功","info",function(){
                        $("#category").datagrid("reload");
                        closeDialog();
                    })
                }else{
                    parent.showAlert("错误","添加失败","error");
                }
            })
        });
        getInputData("clear");
    });
    //点击toolbar按钮修改
    $("#update").on("click",function(){
        var selectRows = $("#category").datagrid("getSelections");
        if(selectRows.length==1){
            var userId = selectRows[0]["suAtpid"];                                   //唯一标识？？？？？？
            doUpdate(userId);
        }else{
            parent.showAlert("提示","请选择1条数据进行修改","warning");
        }
    });
    //点击操作栏修改
    $("body").undelegate(".update","click").delegate(".update","click",function(){
        var userId = $(this).attr("userid");                                         //字段唯一标识？？？？？？
        doUpdate(userId);
    });
    //点击toolbar按钮删除
    $("#delete").on("click",function(){
        var selectRows = $("#category").datagrid("getSelections");
        if(selectRows.length!=0){
            var userIds = [];
            for(var k in selectRows){
                userIds.push(selectRows[k]["suAtpid"]);                                   //字段？？？？？？
            }
            doDelete(userIds);

        }else{

            parent.showAlert("提示","未选择任何数据","warning");
        }

    })

    //点击操作栏删除
    $("body").undelegate(".delete","click").delegate(".delete","click",function(){

        var userIds = [$(this).attr("userid")];                                //userid   html里面定义？？？？？
        doDelete(userIds);
    })
    //打开弹窗
    function openDialog(title,okBtnFunc){
        parent.createDialog({
            element:$("#basicData_dialog"),
            elementId:"basicData_dialog",
            title:title,
            icon:'',
            ok_button_handler:function(){
                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }
    //关闭弹窗
    function closeDialog(){
        parent.$("#basicData_dialog").dialog("close");
    }
    //获取用户输入数据
    function getInputData(type,data){
        var name = parent.$("#name");
        var time = parent.$("#time");
        var people = parent.$("#people");
        if(type=="clear"){
            name.val("");
            time.val("");
            people.val("");
        }
        if(type=="get"){
            if(name.val()==""){
                parent.showAlert("提示","请输入分类名称","error");
                return false;
            }
            if(time.val()==""){
                parent.showAlert("提示","请输入修改时间","error");
                return false;
            }
            if(people.val()==""){
                parent.showAlert("提示","请输入创建人","error");
                return false;
            }
            var data = {suAccount:name.val(),suChinesename:time.val(),suChinesename:people.val()};            //接口字段？？？？？？
            return data;
        }
        if(type=="set"){
            name.val(data.suChinesename);                                         //接口字段？？？？？？
            time.val(data.suAccount);
            people.val(data.suAccount);
        }
    }
    //更新数据
    function doUpdate(userId){
        getInputData("clear");
        ht.data.getData("","POST",{suAtpid:userId},function (d) {                   //接口？？？？？？？
            if(d["status"]=="1"){
                openDialog("修改分类",function(){
                    var data = getInputData("get");
                    if(!data) return;
                    data.suAtpid = userId;                                                                   //字段
                    ht.data.getData("","POST",data,function(re){                         //保存数据接口？？？？？？
                        if(re["status"]=="1"){
                            parent.showAlert("恭喜","修改成功","info",function(){
                                $("#category").datagrid("reload");
                                closeDialog();
                            })
                        }else{
                            parent.showAlert("错误",re.msg,"error");
                        }
                    })
                });
                getInputData("set",d["data"]);

            }else{
                parent.showAlert("错误","获取信息失败","error");
            }
        })
    }
    //删除数据
    function doDelete(userIds){
        parent.showConfirm("提示","确定删除"+userIds.length+"条记录？",function(r){
            if(r){
                ht.data.getData("","POST",{suAtpid:userIds.join(",")},function (d) {             //接口？？？？？
                    if(d["status"]=="1"){
                        parent.showAlert("恭喜","删除成功","info",function(){
                            $("#category").datagrid("reload");
                        })
                    }else{
                        parent.showAlert("错误","删除失败","error");
                    }
                })
            }
        })
    }


})