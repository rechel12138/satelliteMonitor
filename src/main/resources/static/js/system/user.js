$(function(){

    //获取权限生成操作按钮
    var menuAction = parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);
    //加载datagrid数据表
    $('#myTable').datagrid({
        url:'/api/suser/findByParamEzuiForUsers',
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
        columns:[[
            {field:'ck',checkbox:true},
            {field:'suChinesename',title:'姓名',width:'11%',align:'center'},
            {field:'suAccount',title:'登录账号',width:'12%',align:'center'},
            {field:'srName',title:'所属角色',width:'10%',align:'center'},
            {field:'suAtpcreatedatetime',title:'创建时间',width:'18%',align:'center'},
            {field:'suLastlogindatetime',title:'最后登录时间',width:'18%',align:'center'},
            {field:'suAtpstatus',title:'状态',width:'7%',align:'center',formatter:function(v){

                if(v=="0") return "启用";
                if(v=="1") return "禁用";

                }},
            {field:'op',title:'操作',width:'18%',align:'center',formatter:function(v,r){
                    //5-修改 6-删除  7-禁用  9-重置密码
                    var enable_text=r.suAtpstatus=="0"?"禁用":"启用";
                    var update = "<a style='cursor: pointer' class='update' userid='"+r.suAtpid+"'>修改</a>";
                    var del = "<a style='cursor: pointer' class='delete' userid='"+r.suAtpid+"'>删除</a>";
                    var enable = "<a style='cursor: pointer' class='enable' userid='"+r.suAtpid+"'>"+enable_text+"</a>";
                    var resetPassword = "<a style='cursor: pointer' class='resetPassword' userid='"+r.suAtpid+"'>重置密码</a>";
                    var buttons = [];
                    if(menuAction.indexOf("5")!=-1) buttons.push(update);
                    if(menuAction.indexOf("6")!=-1) buttons.push(del);
                    if(menuAction.indexOf("7")!=-1) buttons.push(enable);
                    if(menuAction.indexOf("9")!=-1) buttons.push(resetPassword);

                    return buttons.join("<span style='margin:0 5px'>|</span>");
                }}
        ]],
        onLoadSuccess:function(data){
            // console.log(data)
            if (data.total==0){

                $(this).datagrid('appendRow', { suChinesename: "<div style='text-align:center;'>没有相关记录</div>" }).datagrid('mergeCells', { index: 0, field: 'suChinesename', colspan: 7 })
            }
        }

    });

    //添加
    $("#add").on("click",function(){

        openDialog("添加用户",function(){

            var data = getInputData("get");
            if(!data) return;
            ht.data.getData("/api/suser/addSUser","POST",data,function(d){

                if(d["status"]=="1"){

                    parent.showAlert("恭喜","添加成功","info",function(){

                        $("#myTable").datagrid("reload");
                        closeDialog();
                    })

                }else{

                    parent.showAlert("错误","添加失败","error");
                }
            })
        })
        getInputData("clear");
    });

    //点击toolbar按钮修改
    $("#update").on("click",function(){

        var selectRows = $("#myTable").datagrid("getSelections");
        if(selectRows.length==1){
            var userId = selectRows[0]["suAtpid"];
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

        var selectRows = $("#myTable").datagrid("getSelections");
        if(selectRows.length!=0){

           var userIds = [];
           for(var k in selectRows){

               userIds.push(selectRows[k]["suAtpid"]);
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


            $("#myTable").datagrid("load",{keyword:keyword});


        }else{
            $("#myTable").datagrid("load",{keyword:''});

            // parent.showAlert("提示","请输入查询关键字","warning");
        }
    });

    //导出excel
    $("#exportExcel").on("click",function(){

        var keyword = $("#keyword").val();
        var export_url="/api/suser/userToExcel?keyword="+keyword;
        parent.document.location.href=export_url;

    });

    //点击toolbar重置密码
    $("#resetPassword").on("click",function(){

        var selectRows = $("#myTable").datagrid("getSelections");
        if(selectRows.length!=0){

            var userIds = [];
            for(var k in selectRows){

                userIds.push(selectRows[k]["suAtpid"]);
            }

            doResetPassword(userIds);

        }else{

            parent.showAlert("提示","未选择任何数据","warning");
        }
    });

    //点击操作栏重置密码
    $("body").undelegate(".resetPassword","click").delegate(".resetPassword","click",function(){

        var userIds = [$(this).attr("userid")];
        doResetPassword(userIds);
    });


    //点击toolbar启用/禁用
    $("#enable").on("click",function(){

        var selectRows = $("#myTable").datagrid("getSelections");
        if(selectRows.length!=0){

            var userIds = [];
            for(var k in selectRows){

                userIds.push(selectRows[k]["suAtpid"]);
            }

            doEnable(userIds);

        }else{

            parent.showAlert("提示","未选择任何数据","warning");
        }
    });

    //点击操作栏重置密码
    $("body").undelegate(".enable","click").delegate(".enable","click",function(){

        var userIds = [$(this).attr("userid")];
        doEnable(userIds);
    });

    function doDelete(userIds){

        parent.showConfirm("提示","确定删除"+userIds.length+"条记录？",function(r){

            if(r){

                ht.data.getData("/api/suser/removeSUser","POST",{suAtpid:userIds.join(",")},function (d) {

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

    function doUpdate(userId){

        getInputData("clear");
        ht.data.getData("/api/suser/getSUserById","POST",{suAtpid:userId},function (d) {
            // console.log(d);
            if(d["status"]=="1"){

                openDialog("修改用户",function(){

                    var data = getInputData("get");
                    if(!data) return;
                    data.suAtpid = userId;
                    ht.data.getData("/api/suser/updateSUser","POST",data,function(re){

                        if(re["status"]=="1"){

                            parent.showAlert("恭喜","修改成功","info",function(){

                                $("#myTable").datagrid("reload");
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

    function doResetPassword(userIds) {

        parent.showConfirm("提示","确定重置密码吗？",function(r){

            if(r){

                ht.data.getData("/api/suser/resetPassword","POST",{userIds:userIds.join(",")},function(d){

                    if(d["status"]=="1"){

                        parent.showAlert("提示",d.msg+"<br>"+"新密码为123456","info");
                        $("#myTable").datagrid("reload");

                    }else{

                        parent.showAlert("提示",d.msg,"error");
                    }

                })
            }
        })
    }

    function doEnable(userIds){

        parent.showConfirm("提示","确定操作吗？",function(r){

            if(r){

                ht.data.getData("/api/suser/updateUserStatus","POST",{userIds:userIds.join(",")},function(d){

                    if(d["status"]=="1"){

                        parent.showAlert("提示",d.msg,"info");
                        $("#myTable").datagrid("reload");

                    }else{

                        parent.showAlert("提示",d.msg,"error");
                    }

                })
            }
        })
    }

    //打开弹窗
    function openDialog(title,okBtnFunc){

        parent.createDialog({

            element:$("#user_dialog"),
            elementId:"user_dialog",
            title:title,
            icon:'',
            ok_button_handler:function(){

                if(typeof okBtnFunc=="function") okBtnFunc();
            }
        })
    }

    //关闭弹窗
    function closeDialog(){

        parent.$("#user_dialog").dialog("close");
    }

    //获取用户输入数据
    function getInputData(type,data){

        var name = parent.$("#name");
        var userName = parent.$("#userName");
        var password = parent.$("#password");
        if(type=="clear"){

            password.parent().show();
            name.val("");
            userName.val("");
            password.val("");

        }
        if(type=="get"){

            if(name.val()==""){

                parent.showAlert("提示","请输入用户姓名","error");
                return false;
            }
            if(userName.val()==""){

                parent.showAlert("提示","请输入登录帐号","error");
                return false;
            }

            var data = {suAccount:userName.val(),suChinesename:name.val()};

            if(password.parent().css("display")!="none"){


                if(password.val()==""){

                    parent.showAlert("提示","请输入登录密码","error");
                    return false;
                }

                data.suPassword=password.val();
            }

            return data;

        }
        if(type=="set"){

            name.val(data.suChinesename);
            userName.val(data.suAccount);
            password.parent().hide();
        }
    }

    //配置角色
    $("#configRole").on("click",function(){

        var selectRows = $("#myTable").datagrid("getSelections");
        if(selectRows.length==1){
            var userId = selectRows[0]["suAtpid"];

            parent.createDialog({

                element:$("#role_dialog"),
                elementId:"role_dialog",
                title:"配置角色",
                icon:'',
                ok_button_handler:function(){

                    //保存结果
                    var roleId=[];
                    parent.$("#selectedItem option").each(function(){

                        roleId.push($(this).val());
                    })
                    if(roleId.length!=0){

                        var _d = {

                            userId:userId,
                            roleIds:roleId.join(",")
                        };


                        ht.data.getData("/api/suser/saveUserRole","POST",_d,function(res){

                            if(res["status"]=="1"){

                                $("#myTable").datagrid("reload");
                                parent.$("#role_dialog").dialog("close");

                            }else{

                                parent.showAlert("提示",res.msg,"warning");
                            }
                        })

                    }else{

                        parent.showAlert("提示","请至少为用户选择一个角色","warning");
                    }


                }
            })

        }else{

            parent.showAlert("提示","请选择1条数据进行配置","warning");
        }

        //获取所有角色
        ht.data.getData("/api/suser/getAllRolesList","POST",null,function(d){

            if(d["status"]=="1"){

                var roleList = d["data"];
                var allRoles = parent.$("#allItem");
                allRoles.empty();
                if(roleList.length>0){

                    for(var k in roleList){

                        allRoles.append("<option value='"+roleList[k]["srAtpid"]+"'>"+roleList[k]["srName"]+"</option>");
                    }
                }

            }else{

                parent.showAlert("提示",d.msg,'warning');
            }
        });

        //根据用户id获取该用户已经配置的角色
        ht.data.getData("/api/suser/getRoleListByUserId","POST",{userId:userId},function(re){

            if(re["status"]=="1"){

                var roleList = re["data"];
                var allRoles = parent.$("#selectedItem");
                allRoles.empty();
                if(roleList.length>0){

                    for(var k in roleList){

                        allRoles.append("<option value='"+roleList[k]["surFksroleid"]+"'>"+roleList[k]["srName"]+"</option>");
                    }
                }

            }else{

                parent.showAlert("提示",d.msg,'warning');
            }
        });

        //从全部角色双击加入已选角色列表
        parent.$("#allItem").on("dblclick",function(){

            var value = $(this).find("option:selected").val();
            var text = $(this).find("option:selected").text();
            var ele="<option value='"+value+"'>"+text+"</option>";
            if(parent.$("#selectedItem option[value="+value+"]").length==0){

                parent.$("#selectedItem").append(ele);
            }

        });

        //已选角色双击删除
        parent.$("#selectedItem").on("dblclick",function(){

            var selectedOption = $(this).find("option:selected");
            $(this).find(selectedOption).remove();
        })

    })
});