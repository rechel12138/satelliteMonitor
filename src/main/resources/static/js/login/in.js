$(function () {


    //点击按钮登录处理
    $(".btn").on("click",function(){

            login();
    })

    //输完密码按enter键处理登录
    $("#password").on("keyup",function(e){

        if(e.keyCode=="13"){

            login();
        }
    })

    function login(){

        var userName = $("#userName").val();
        var password = $("#password").val();


        if(userName==""){

            $.messager.alert("提示", "登录帐号不能空", "error");
            return false;
        }

        if(password==""){

            $.messager.alert("提示", "登录密码不能空", "error");
            return false;
        }

        var data = {su_account:userName,su_password:password};
        ht.data.getData("/api/login/login","POST",data,function(d){

            if(d.status=="1"){

                //存储服务器返回的user信息,cookie
                var userInfo = d.data;
                //{"userName":xxx,"userId":xxx}
                ht.cookie.setCookie("ht_user_info",userInfo);
                var userId =  ht.cookie.getCookie("ht_user_info","userId");
                document.location.href="/index";

            }else{

                $.messager.alert("提示", d.msg, "error");
            }
        })
    }

})