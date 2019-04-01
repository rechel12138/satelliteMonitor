$(function(){

    //获取权限生成操作按钮
    var menuAction =parent.getMenuInfo().menuAction;

    //生成一级菜单
    ht.data.getData("/api/smodule/getFirstMenuList","POST",null,function(d){

        if(d["status"]=="1"){

            var selectDOM = $("#firstMenu");
            selectDOM.empty();
            var list = d["data"];
            for(var k in list){

                var item = $("<span sid='"+list[k]["smAtpid"]+"' class='menu-items'>"+list[k]["smName"]+"</span>");
                var button = $("<span bid='"+list[k]["smAtpid"]+"' class='menu-button'>[修改]</span>");
                var li = $("<li></li>");
                li.append(item);
                if(menuAction.indexOf("5")!=-1){

                    li.append(button);
                };

                selectDOM.append(li);

                item.on("click",_menuItemClick);
                button.on("click",_menuButtonClick);

            }
            var firstId = selectDOM.find(".menu-items").eq(0).attr("sid");
            createTable(firstId);

        }else{

            parent.showAlert("提示","获取一级菜单失败","warning");
        }
    })


    function _menuItemClick(){

        var value = $(this).attr("sid");
        $("#myTable").datagrid("load",{id:value});
        $(this).parent().addClass("selectedMenu");
        $(this).parent().siblings().removeClass("selectedMenu");
    }


    function _menuButtonClick(){

        var value = $(this).attr("bid");
        updateMenu(value);
    }

    //加载datagrid数据表
    function createTable(id){

        $('#myTable').datagrid({
            url:'/api/smodule/getSecondMenuListByFId',
            nowrap: true,
            fitColumns:true,
            striped: true,
            rownumbers:true,
            pagination:true,
            singleSelect:false,
            pageNumber:1,
            pageSize:3,
            pageList:[3,7,20,30,40,50],
            height:'100%',
            width:'100%',
            queryParams:{id:id},
            columns:[[

                {field:'sm_name',title:'二级菜单名称',width:'15%',align:'center'},
                {field:'sm_webpath',title:'链接',width:'21%',align:'center'},
                {field:'sm_isdisplay',title:'是否在权限配置中显示',width:'16%',align:'center',formatter:function(v){

                    if(v=="0") return "显示";
                    if(v=="1") return "不显示";
                    }},
                {field:'sm_islogineddisplay',title:'是否需要登录访问',width:'15%',align:'center',formatter:function(v){

                        if(v=="0") return "需要";
                        if(v=="1") return "不需要";
                    }},
                {field:'sm_atplastmodifydatetime',title:'最后更新时间',width:'20%',align:'center'},
                {field:'op',title:'操作',width:'10%',align:'center',formatter:function(v,r){

                    if(menuAction.indexOf("5")!=-1){

                        return "<a style='cursor: pointer' class='update' id='"+r.sm_atpid+"'>修改</a>";
                    }
                        return "<span style='color:#999'>修改</span>";
                    }}

            ]]

        });
    }

    $("body").delegate(".update","click",function () {

        var id = $(this).attr("id");
        updateMenu(id);
    })

    function updateMenu(id){

        parent.createDialog({

            element:$("#module_dialog"),
            elementId:"module_dialog",
            title:"编辑菜单",
            icon:'',
            ok_button_handler:function(){

                var name = parent.$("#moduleName").val();
                var isDisp = parent.$("input[type=radio][name=aa]:checked").val();
                var isLoginedDisp = parent.$("input[type=radio][name=bb]:checked").val();
                if(!name){

                    parent.showAlert("提示","菜单名称不能为空","warning");
                    return;
                }

                var data={

                    smAtpid:id,
                    smIsdisplay:isDisp,
                    smIslogineddisplay:isLoginedDisp,
                    smName:name
                }

                ht.data.getData("/api/smodule/updateSmodule","POST",data,function(d){

                    if(d["status"]=="1"){

                        $('#myTable').datagrid("reload");
                        parent.$("#module_dialog").dialog("close");
                        window.parent.location.reload();

                    }else{

                        parent.showAlert("提示",d.msg,"warning");
                    }
                })
            }

        });

        ht.data.getData("/api/smodule/getMenuInfoById","POST",{id:id},function(d){

            if(d["status"]=="1"){

                parent.$("#moduleName").val(d.data.smName);
                parent.$("input[name=aa][value='"+d.data.smIsdisplay+"']").prop("checked",true);
                parent.$("input[name=bb][value='"+d.data.smIslogineddisplay+"']").prop("checked",true);


            }else{

                parent.showAlert("提示",d.msg,"warning");
            }
        })

    }
})