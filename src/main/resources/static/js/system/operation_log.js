$(function () {

    //获取权限生成操作按钮:
    var menuAction = parent.getMenuInfo().menuAction;
    var menuActionHTML = ht.ui.createMenuButton(menuAction);
    $(".toolbar").html(menuActionHTML);

    //加载数据表:
    $("#excel_table").datagrid({
        url:'/api/slog/getLogListForEzui',
        singleSelect:false,
        selectOnCheck:true,
        checkOnSelect:false,
        fitColumns:true,
        scrollbarSize:0,
        striped:true,
        rownumbers:true,
        pagination:true,
        pageNumber:1,
        pageSize:10,
        height:'100%',
        width:'100%',
        pageList:[10,20,30,40,50],
        columns:[[
            {field:'check_box',checkbox:true},
            {field:'sl_ip',title:'ip',width:"20%",align:'center'},
            {field:'sl_content',title:'操作内容',width:"40%",align:'center'},
            {field:'sl_atpcreateuser',title:'操作人',width:"15%",align:'center'},
            {field:'sl_logtime',title:'操作时间',width:"20%",align:'center'}
        ]]
    });
    //查询
    $("#query").on("click",function(){
        var keyword = $("#keyword").val();
        if(keyword){
            $("#excel_table").datagrid("load",{keyword:keyword});
        }else{
            $("#excel_table").datagrid("load",{keyword:''});
        }
    });
    //点击toolbar按钮导出excel
    $("#exportExcel").on("click",function(){
        var keyword = $("#keyword").val();
        var export_url="/api/slog/logToExcel?keyword="+keyword;
        parent.document.location.href=export_url;
    })
});