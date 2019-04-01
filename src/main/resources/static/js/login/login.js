$(function () {
    $(".username input").blur(function(){
        var username_str = $(this).val();
        if(username_str.trim()==''){
            $(this).parent().next().html("帐户名不能为空！")
        }else{
            $(this).parent().next().html("")
        }
    });
    $(".password input").blur(function(){
        var password_str = $(this).val();
        if(password_str.trim()==''){
            $(this).parent().next().html("密码不能为空！")
        }else{
            $(this).parent().next().html("")
        }
    });
    $(".btn").on("click",function () {
        
    })
});