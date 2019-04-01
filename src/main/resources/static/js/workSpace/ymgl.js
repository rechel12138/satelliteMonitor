$(function(){
    //文件列表读取
    getList();
    function getList(){
        ht.data.getData("/htdxjk/spageconfig/listSpageconfig","POST",{},function(d){
            if(d.status==1){
                var str="";
                for(var i=0;i<d.data.length;i++){
                    str+='<div class="space_list" style="text-align:center;float: left;height:100px;width:120px;padding:10px 0;margin-left: 30px;background-color: #eeeeee">\n' +
                        '      <p style="text-align:right;padding-right:10px;cursor:pointer" class="space_list_del" flag="'+d.data[i].bpc_flag+'">X</p>\n' +
                        '      <img src="/images/space.png" alt="" style="height:40px;width:30px">\n' +
                        '      <p style="padding: 20px 10px;cursor:pointer" class="open_list">'+d.data[i].bpc_name+'</p>\n' +
                        '  </div>';
                }
                $("#space_list_wrap").html(str);
            }else{
                parent.showAlert("提示",d.msg,"warning")
            }
        });
    }
    //保存
    $("#btn_save").click(function(){
        var data = getOpenedTabs();
        openDialog('文件名称',function(){
           var fileName=parent.$("#dialog_space").find('input').val();
           var _data = {name:fileName,list:data};
           _data = JSON.stringify(_data);
            ht.data.getData("/htdxjk/spageconfig/addSpageConfig","POST",_data,function(d){
                if(d["status"]=="1"){
                    parent.showAlert("恭喜","添加成功","info",function(){
                        getList();
                        closeDialog();
                    })
                }else{
                    parent.showAlert("错误",d.msg,"error");
                }
            },null,true,'application/json');
            closeDialog();
        })
    });
    //删除收藏
    $('body').delegate('.space_list_del','click',function(){
        var _flag=$(this).attr("flag");
        var _this=$(this)
        ht.data.getData("/htdxjk/spageconfig/removeSpageConfig","POST",{spageFlag:_flag},function(d){
            if(d.status==1){
               parent.showAlert("提示",d.msg,"info",function(){
                   _this.parent().remove();
               });
            }else{
                parent.showAlert("提示",d.msg,"warning")
            }
        });
    });
    //打开收藏夹
    $('body').delegate('.open_list','click',function(){
        var _flag=$(this).siblings(".space_list_del").attr("flag");
        ht.data.getData("/htdxjk/spageconfig/listOneSpageconfig","POST",{flag:_flag},function(d){
               if(d.status==1){
                   for(var i=0;i<d.data.length;i++){
                       createTab(d.data[i].bpc_title,d.data[i].bpc_url)
                   }
               }else{
                   parent.showAlert("提示",d.msg,"warning")
               }
        })
    })
});
//获取打开taps
function getOpenedTabs(){
    var items = [];
    var tabPanel = parent.$(".easyui-tabs1[arrindex=0]");
    var tabs = tabPanel.tabs("tabs");
    for(var k =0;k<tabs.length;k++){
        var obj={};
        var options = tabPanel.tabs("getTab",k).panel("options");
        obj.title = options.title;
        obj.url = $(options.content).attr("src");
        obj.sort = k+1;
        if(obj.url!="/htdxjk/workSpace/ymgl"){
            items.push(obj);
        }
    }
    return items;
}
// 打开taps列
function createTab(title,url){
    var tabPanel = parent.$(".easyui-tabs1[arrindex=0]");
    if(!tabPanel.tabs('exists',title)){
        tabPanel.tabs('add',{
            title:title,
            content:"<iframe src="+url+" frameborder='0' scrolling='auto' width='100%' height='100%' />",
            closable:true,
            iconCls:''
        })
    }else{
        tabPanel.tabs('select',title);
    }
}
//打开弹窗
function openDialog(title,okBtnFunc){
    parent.createDialog({
        element:$("#dialog_space"),
        elementId:"dialog_space",
        title:title,
        icon:'',
        ok_button_handler:function(){
            if(typeof okBtnFunc=="function") okBtnFunc();
        }
    })
}
//关闭弹窗
function closeDialog(){
    parent.$("#dialog_space").dialog("close");
}
