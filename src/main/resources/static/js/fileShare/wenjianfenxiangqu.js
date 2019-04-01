$(function () {

    //获取权限生成操作按钮
    var menuAction = parent.getMenuInfo().menuAction;
    var btn_up_file='<button id="upload">上传</button>';
     if(menuAction.indexOf("14")!=-1){
         $("#btn_wrap").append(btn_up_file)
     }
    //文件列表
    $("#fileShare").datagrid({
        url:"/api/bfileshare/listBfileshare",
        nowrap: true,
        fitColumns:true,
        striped: true,
        rownumbers:true,
        checkOnSelect:false,
        pagination:true,
        singleSelect:false,
        pageNumber:1,
        pageSize:8,
        pageList:[8,20,30,40,50],
        height:'100%',
        width:'100%',
        columns:[[
            {field:'check_box',checkbox:true,align:'center'},
            {field:'bfsName',title:'文件名称',width:"25%",align:'center'},
            {field:'bfsUpdatetime',title:'修改时间',width:"28%",align:'center'},
            {
                field:'permission',title:'权限',width:"15%",align:'center',
                formatter:function () {
                    return "<select class='file_select' style='padding:5px 10px;'>" +
                        "<option value='预览'>预览</option>" +
                        "<option value='下载'>下载</option>" +
                        "</select>"
                }
            },
            {
                field: 'op', title: '操作', width:"28%", align: 'center',
                formatter: function (v,r) {
                    var btnopen = "<a style='cursor: pointer' class='file_look' atpid='"+r.bfsAtpid+"' detail_title='"+r.bsatCode+"'>预览</a>";
                    var btndown = "<a style='cursor: pointer' class='btnlist_down' atpid='"+r.bfsAtpid+"'>下载</a>";
                    var del = "<a style='cursor: pointer' class='btnlist_del' atpid='"+r.bfsAtpid+"' img_name='"+r.bufName+"'>删除</a>";
                    var buttons = [];
                    //预览按钮权限
                    if(menuAction.indexOf("15")!=-1){
                        buttons.push(btnopen);
                        buttons.push(btndown)
                    }
                    if(menuAction.indexOf("6")!=-1){
                        buttons.push(del)
                    }
                    return buttons.join("<span style='margin:0 5px'>|</span>");

                }
            }
            ]],
        onLoadSuccess:function(data){
            //预览
            $("body").undelegate(".file_look","click").delegate(".file_look","click",function(){
                var atpid=$(this).attr("atpid");
                alert("预览")
            });
            //下载
            $("body").undelegate(".btnlist_down","click").delegate(".btnlist_down","click",function(){
                var atpid=$(this).attr("atpid");
                alert(atpid);
                parent.document.location.href="/api/bfileshare/downBfileshare?atpId="+atpid
            });
            //删除
            $("body").undelegate(".btnlist_del","click").delegate(".btnlist_del","click",function(){
                var atpid=$(this).attr("atpid");
                ht.data.getData("/api/bfileshare/removeBfileshare",'post',{bfileshareAtpid :atpid},function (d) {
                    if(d.status==1){
                        $("#fileShare").datagrid("reload");
                        parent.showAlert("提示",d.msg,"success")
                    }else{
                        parent.showAlert("提示",d.msg,"error")
                    }
                });
            });
        },
        onBeforeLoad:function() {
        }
    });
    //上传
    $("#upload").click(function () {
        return $(".exportBox").click();
    });
    $(".exportBox").on("change",function(){
        $("#exportForm").ajaxSubmit();
        $("#fileShare").datagrid("reload");
    })
    $(".file_select").select(function(){
        var v=$(this).val();
        alert(v)
    })
});