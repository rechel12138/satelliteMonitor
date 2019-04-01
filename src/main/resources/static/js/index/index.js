
//滚动通知
noticeFn();
function noticeFn(){
    ht.data.getData("/api/bnotice/getAvailableNoticeLists","POST",{},function (data){
        if(data['status']==1){
            var str='';
            for(var i=0;i<data.data.length;i++){
                str+=data.data[i].content+"&nbsp&nbsp&nbsp"
            }
            $("#notice_text").html(str)
        }else{
            showAlert("提示",data.msg,"warning");
        }
    });

}
//置顶列表读取
top_getlist();
function top_getlist(){
//卫星置顶列表读取
    ht.data.getData("/api/bsatTop/listBsatTop","POST",{},function (data){
        if(data.status==1){
            var topPic='';
            if(data.data.length>0){
                for(var i=0;i<data.data.length;i++){
                    var img_url="/images/big_pic.png";
                    var img_name=data.data[i].bsat_code;
                    topPic+='<div class="top_pic" style="position:relative"><span style="position:absolute;top:5px;right:5px;color:#FF0000;border:0" class="top_listSatelite_del" atpid="'+data.data[i].bst_atpid+'">X</span><img src="'+data.data[i].bsat_minipic+'"/><p class="top_pic_title">'+img_name+'</p></div>'
                }
            }
            // parent.$(".top_area").append(topPic)
            //常用文件置顶列表读取
            ht.data.getData("/api/busualFileTop/listBusualFileTop","POST",{},function (data){
                if(data.status==1){
                    // var topPic='';
               if(data.data.length>0){
                   for(var i=0;i<data.data.length;i++){
                       var img_url="../../images/word_icon.png";
                       var img_name=data.data[i].buf_name;
                       topPic+='<div style="text-align:center;position:relative" class="top_pic"><span style="position:absolute;top:5px;right:5px;color:#FF0000;border:0" class="top_listfile_del" atpid="'+data.data[i].buft_atpid+'">X</span><img style="width:64px;height:64px" src="'+img_url+'"/><p style="font-size: 16px" class="top_pic_title">'+img_name+'</p></div>';
                   }
               }
                $(".top_area").html(topPic)
                }else{
                    showAlert("提示",data.msg,"warning");
                }
            });
        }else{
            showAlert("提示",data.msg,"warning");
        }
    });
}



//卫星置顶删除
$("body").delegate(".top_listSatelite_del","click",function(){
    var atpid=$(this).attr("atpid");
    var _this=$(this);
    console.log(atpid)
    ht.data.getData("/api/bsatTop/removeBsatTop","POST",{bstAtpid:atpid},function (data){
        if(data.status==1){
            _this.parent().remove();
       showAlert("提示",data.msg,"info")
        }else{
           showAlert("提示",data.msg,"warning")
        }
    })
});
//常用文件置顶删除
$("body").delegate(".top_listfile_del","click",function(){
    var atpid=$(this).attr("atpid");
    var _this=$(this);
    console.log(111);
    console.log(atpid);
    ht.data.getData("/api/busualFileTop/removeBusualFileTop","POST",{buftAtpid :atpid},function (data){
        console.log(data)
        if(data.status==1){
            _this.parent().remove();
         showAlert("提示",data.msg,"info")
        }else{
          showAlert("提示",data.msg,"warning")
        }
    });
});
//显示对话框
function createDialog(option) {

    var para = $.extend({

        element: null,
        elementId: null,
        modal: true,
        icon: '',
        title: '',
        ok_button_text: '确定',
        ok_button_icon: '',
        ok_button_handler: null,
        ok_button_disabled: false,
        display_ok_button:true,
        cancel_button_text: '取消',
        cancel_button_icon: '',
        cancel_button_handler: null,
        cancel_button_disabled:false,
        display_cancel_button:true

    }, option);


    if (document.getElementById(para.elementId) == null) {

        $("body").append(para.element);
    }

    var buttons =[];
    if(para.display_ok_button){

        buttons.push({

            text: para.ok_button_text,
            iconCls: para.ok_button_icon,
            handler: para.ok_button_handler,
            disabled: para.ok_button_disabled
        })
    }

    if(para.display_cancel_button){

      buttons.push({

          text: para.cancel_button_text,
          iconCls: para.cancel_button_icon,
          disabled: para.cancel_button_disabled,
          handler: function(){

              $("#" + para.elementId).dialog("close");
              if(typeof para.cancel_button_handler =="function"){

                  para.cancel_button_handler();
              }
          }
      })
    }

    $("#" + para.elementId).dialog({

        modal: para.modal,
        iconCls: para.icon,
        title: para.title,
        buttons: buttons

    })

}

function createDialogAlert(option) {

    var para = $.extend({

        element: null,
        elementId: null,
        modal: true,
        icon: '',
        title: '',
        ok_button_text: '确定',
        ok_button_icon: '',
        ok_button_handler: null,
        ok_button_disabled: false,
        display_ok_button:false,
        cancel_button_text: '确定',
        cancel_button_icon: '',
        cancel_button_handler: null,
        cancel_button_disabled:false,
        display_cancel_button:true

    }, option);


    if (document.getElementById(para.elementId) == null) {

        $("body").append(para.element);
    }

    var buttons =[];
    if(para.display_ok_button){

        buttons.push({

            text: para.ok_button_text,
            iconCls: para.ok_button_icon,
            handler: para.ok_button_handler,
            disabled: para.ok_button_disabled
        })
    }

    if(para.display_cancel_button){

        buttons.push({

            text: para.cancel_button_text,
            iconCls: para.cancel_button_icon,
            disabled: para.cancel_button_disabled,
            handler: function(){

                $("#" + para.elementId).dialog("close");
                if(typeof para.cancel_button_handler =="function"){

                    para.cancel_button_handler();
                }
            }
        })
    }

    $("#" + para.elementId).dialog({

        modal: para.modal,
        iconCls: para.icon,
        title: para.title,
        buttons: buttons

    })

}

//alert
function showAlert(title, msg, icon, fn) {

    $.messager.alert(title, msg, icon, fn);
}

//confirm
function showConfirm(title, msg, fn) {

    $.messager.confirm(title, msg, fn);
}

// 获取菜单权限
function getMenuInfo(){

    return {

        menuId:$("#menuId").val(),
        menuIcon:$("#menuIcon").val(),
        menuIsLoginedDisplay:$("#menuIsLoginedDisplay").val(),
        menuName:$("#menuName").val(),
        menuAction:$("#menuAction").val(),
        menuUrl:$("#menuUrl")
    }
}




//获取系统菜单
//如果未登录调/htdxjk/suser/getDefaltMenus获取
//如果登录调/htdxjk/suser/getPermissionBySuAtpid获取
var urls = {"unlogindedUrl":"/api/suser/getDefaltMenus","loginedUrl":"/api/suser/getPermissionBySuAtpid"};
var SystemMenu = null;
var responseData = null;
//判断是否登录，判断是否有cookie信息，cookie的key=htUserInfo
var userId = ht.cookie.getCookie("ht_user_info","suAtpid");
if(userId){

    //登录后根据userId获取菜单
    var data = {userId:userId};
    responseData = ht.data.getData(urls.loginedUrl,"POST",data,null,null,false);

    //登录后，显示登录信息
    var userChineseName = ht.cookie.getCookie("ht_user_info","suChinesename");
    $(".user-name").text(userChineseName+"，欢迎您！");
    $(".user-log    out").text("注销");
    $(".user-logout").attr("href","/htdxjk/login/out");


}else{

    responseData = ht.data.getData(urls.unlogindedUrl,"POST",null,null,null,false);
}


if(responseData.status=="0"){

    showAlert("提示",responseData.msg,"error");

}else{

    SystemMenu = responseData.data;
}







