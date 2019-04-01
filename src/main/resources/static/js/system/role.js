$(function(){
    //获取权限生成操作按钮
    var menuAction =parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);
    //加载datagrid数据表
    $('#myTable').datagrid({
        url:'/api/srole/findByParamEzui',
        nowrap: true,
        fitColumns:true,
        striped: true,
        rownumbers:true,
        pagination:true,
        singleSelect:false,
        pageNumber:1,
        pageSize:7,
        pageList:[7,20,30,40,50],
        height:'100%',
        width:'100%',
        columns:[[
            {field:'check_box',checkbox:true,align:'center'},
            {field:'srName',title:'角色名称',width:'15%',align:'center'},
            {field:'srRemark',title:'描述',width:"25%",align:'center'},
            {field:'srIndexurl',title:'主页链接',width:"30%",align:'center'},
            {field:'srAtpcreatedatetime',title:'创建时间',width:"15%",align:'center'},
            {field:'handle',title:'操作',width:"11%",align:'center',formatter:function(v,r){
                    //5-修改 6-删除  27-配置权限
                    var update = "<a style='cursor: pointer' class='update' roleid='"+r.srAtpid+"'>修改</a>";
                    var del = "<a style='cursor: pointer' class='delete' roleid='"+r.srAtpid+"'>删除</a>";
                    var configMenu = "<a style='cursor: pointer' class='configMenu' roleid='"+r.srAtpid+"'>配置权限</a>";
                    var buttons = [];
                    if(menuAction.indexOf("5")!=-1) buttons.push(update);
                    if(menuAction.indexOf("6")!=-1) buttons.push(del);
                    if(menuAction.indexOf("27")!=-1) buttons.push(configMenu);
                    return buttons.join("<span style='margin:0 5px'>|</span>");
                }}
        ]]
    });

    //打开弹窗
    function openDialog(title,okBtnFunc){
        parent.createDialog({
            element:$("#role_config_dialog"),
            elementId:"role_config_dialog",
            title:title,
            icon:'',
            ok_button_handler:function(){
                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }
    //关闭弹窗
    function closeDialog(){
        parent.$("#role_config_dialog").dialog("close");
    }
    //获取用户输入数据
    function getInputData(type,data){
        var roleName = parent.$("#roleName");
        var indexUrl = parent.$("#indexUrl");
        var remark = parent.$("#remark");
        if(type=="clear"){
            roleName.val("");
            indexUrl.val("");
            remark.val("");
        }
        if(type=="get"){
            if(roleName.val()==""){
                parent.showAlert("提示","请输入角色名称","error");
                return false;
            }
            if(indexUrl.val()==""){
                parent.showAlert("提示","请输入主页链接","error");
                return false;
            }
            var data = {srName:roleName.val(),srIndexurl:indexUrl.val(),srRemark:remark.val()};
            return data;
        }
        if(type=="set"){
            roleName.val(data.srName);
            indexUrl.val(data.srIndexurl);
            remark.val(data.srRemark);
        }
    }

    //点击toolbar配置权限
    $("#configMenu").on("click",function(){
        var selectRows = $("#myTable").datagrid("getSelections");
        if(selectRows.length==1){
            var roleId = selectRows[0]["srAtpid"];
            configMenu(roleId);
        }else{
            parent.showAlert("提示","请选择1条数据进行修改","warning");
        }

    });
    //点击toolbar按钮添加
    $("#add").on("click",function(){
        openDialog("添加角色",function(){
            var data = getInputData("get");
            if(!data) return;
            ht.data.getData("/api/srole/addSrole","POST",data,function(d){
                if(d["status"]=="1"){
                    parent.showAlert("恭喜","添加成功","info",function(){
                        $("#myTable").datagrid("reload");
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
        var selectRows = $("#myTable").datagrid("getSelections");
        if(selectRows.length==1){
            var roleId = selectRows[0]["srAtpid"];
            // console.log(roleId);
            doUpdate(roleId);
        }else{
            parent.showAlert("提示","请选择1条数据进行修改","warning");
        }
    });

    //点击toolbar按钮删除
    $("#delete").on("click",function(){

        var selectRows = $("#myTable").datagrid("getSelections");
        if(selectRows.length!=0){
            var roleIds = [];
            for(var k in selectRows){
                roleIds.push(selectRows[k]["srAtpid"]);
                console.log(selectRows[k]);
            }

            doDelete(roleIds);

        }else{

            parent.showAlert("提示","未选择任何数据","warning");
        }

    });

    //点击toolbar按钮查询
    $("#query").on("click",function(){

        var keyword = $("#keyword").val();
        if(keyword){
            $("#myTable").datagrid("load",{keyword:keyword});
        }else{
            $("#myTable").datagrid("load",{keyword:''});
            // parent.showAlert("提示","请输入查询关键字","warning");
        }
    });

    //点击toolbar按钮导出excel
    $("#exportExcel").on("click",function(){
        var keyword = $("#keyword").val();
        var export_url="/api/srole/exportExcel?keyword="+keyword;
        parent.document.location.href=export_url;
    });


    //点击操作栏修改
    $("body").undelegate(".update","click").delegate(".update","click",function(){
        var roleId = $(this).attr("roleid");
        doUpdate(roleId);
    });

    //点击操作栏删除
    $("body").undelegate(".delete","click").delegate(".delete","click",function(){

        var roleIds = [$(this).attr("roleid")];
        doDelete(roleIds);
    });

    //点击操作栏配置权限
    $("body").undelegate(".configMenu","click").delegate(".configMenu","click",function(){

        var roleId = $(this).attr("roleid");
        configMenu(roleId);
    });


    function doDelete(roleIds){
        parent.showConfirm("提示","确定删除"+roleIds.length+"条记录？",function(r){

            if(r){

                ht.data.getData("/api/srole/removeSrole","POST",{srAtpid:roleIds.join(",")},function (d) {
                    if(d["status"]=="1"){

                        parent.showAlert("恭喜","删除成功","info",function(){

                            $("#myTable").datagrid("reload");

                        })

                    }else{

                        parent.showAlert("错误","删除失败","error");
                    }
                })
            }
        })
    }

    function doUpdate(roleId){

        getInputData("clear");
        ht.data.getData("/api/srole/getOneById","POST",{atpId:roleId},function (d) {
           if(d["status"]=="1"){

                openDialog("修改角色",function(){

                    var data = getInputData("get");
                    if(!data) return;
                    data.srAtpid = roleId;
                    ht.data.getData("/api/srole/updateSrole","POST",data,function(re){
                        // console.log(re);
                        if(re["status"]=="1"){

                            parent.showAlert("恭喜","修改成功","info",function(){

                                $("#myTable").datagrid("reload");
                                closeDialog();
                            })

                        }else{

                            parent.showAlert("错误",re.msg,"error");
                        }
                    })
                })
                getInputData("set",d["data"]);

            }else{

                parent.showAlert("错误","获取信息失败","error");
            }
        })
    }

    //配置权限封装：
    function configMenu(roleId){

        parent.createDialog({

            element:$("#configTree"),
            elementId:"configTree",
            title:"配置权限",
            icon:'',
            ok_button_handler:function(){

                var node = parent.$("#configTree").tree("getChecked",["checked","indeterminate"]);
                var result = [];
                for(var k in node){

                    if(node[k].level==2 ){

                            var sNode = node[k];
                            var sId = sNode["id"];
                            var sAction =[];
                            var children = sNode.children;
                            if(children.length>0){

                                for(var i in children){

                                    if(children[i]["level"]==3 && children[i]["checkState"]=="checked"){

                                        sAction.push(children[i]["id"]);
                                    }
                                }

                            }else{

                                sAction.push("null");
                            }

                            result.push(sId+"_"+sAction.join(","));

                            var parents =  parent.$("#configTree").tree("getParent",sNode.target);
                            var str = parents.id+"_null";
                            if(result.indexOf(str)==-1) result.push(str);

                    }
                }

                var data ={roleId:roleId,action:result.join("|")};
                // console.log(data);
                ht.data.getData("/api/srole/saveRoleMenu","POST",data,function(re){

                    if(re["status"]=="1"){

                        parent.showAlert("提示",re.msg,"info",function(){

                            parent.$("#configTree").dialog("close");
                            $("#myTable").datagrid("reload");
                        });


                    }else{

                        parent.showAlert("提示",re.msg,"warning");
                    }
                })
            }
        })

        //加载所有的菜单
        parent.$("#configTree").empty();
        parent.$("#configTree").append("<div class='loading' id='menu_loading'>正在加载...</div>");
        parent.$('#configTree').tree({

            url:'/api/srole/getAllMenu',
            animate:true,
            lines:true,
            checkbox:function(node){
                return true;
            },
            onLoadSuccess:function(){

                parent.$("#menu_loading").hide('fast');
                setMenu();

            }
        });
        function setMenu(){


            ht.data.getData("/api/srole/getMenuByRoleId","POST",{roleId:roleId},function(d){

                var thisRoleMenu = d;
                for(var k in thisRoleMenu){

                    var node = parent.$('#configTree').tree('find',thisRoleMenu[k]["moduleId"]);
                    if(node.level=="2"){

                        var children = parent.$('#configTree').tree('getChildren',node.target);
                        var action = thisRoleMenu[k]["action"];
                        if(action.length>0){

                            for(var i in action){

                                for (var n in children){

                                    if(children[n].id==action[i]){

                                        parent.$('#configTree').tree('check', children[n].target);

                                    }
                                }
                            }

                        }else{

                            parent.$('#configTree').tree('check', node.target);
                        }

                    }

                }

            });

        }

    }

});