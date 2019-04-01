$(function () {

    parent.createDialog({
        element:$("#rset_panel"),
        elementId:"rset_panel",
        title:'修改密码',
        icon:'',
        ok_button_handler:function(){

            var oldPassword = parent.$("#oldPassword");
            var newPassword = parent.$("#newPassword");
            var confirmPassword = parent.$("#confirmPassword");

            if(!oldPassword.val()){

                parent.showAlert("提示","原密码不能为空","warning");
                return;

            }

            if(!newPassword.val()){

                parent.showAlert("提示","新密码不能为空","warning");
                return;

            }

            if(!confirmPassword.val()){

                parent.showAlert("提示","确认新密码不能为空","warning");
                return;

            }

            if(confirmPassword.val()!=newPassword.val()){

                parent.showAlert("提示","两次输入的新密码不一致","warning");
                return;

            }
            var userId = ht.cookie.getCookie("ht_user_info","suAtpid");
            var data = {userId:userId,oldPassword:oldPassword.val(),newPassword:newPassword.val()};

            ht.data.getData("/api/suser/modifyPassword","POST",data,function(d){

                if(d["status"]=="1"){

                    parent.showAlert("提示","密码修改成功，请重新登录","info",function(){

                        parent.location.href='/htdxjk/login/out';
                    });

                }else{

                    parent.showAlert("提示",d.msg,"warning");
                }
            })

        },
        cancel_button_handler:function(){

            parent.$("#oldPassword").val('');
            parent.$("#newPassword").val('');
            parent.$("#confirmPassword").val('');
        }

    })



})