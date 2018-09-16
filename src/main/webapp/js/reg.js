function register() {
    var account=$("#account").val()
    var usName=$("#name").val()
    var usPassword=$("#password").val()
    var usCode=$("#code").val()
    if (account==null||usName==null||usPassword==null||usCode==null){
        alert("请输入完整信息");
        return;
    }
    $.ajax({
        url:"/register/reg.do",
        type:"post",
        data:{
            account:account,
            usName:usName,
            usPassword:usPassword,
            usCode:usCode
        },
        dataType:"json",
        success:function (resp) {
            if (resp.isSuccess){
                alert("注册成功!")
                window.location.href="/login.html"
            }else {
                console.log(resp)
                alert("注册失败!")
            }

        }
    })




}

function getCode() {
    var account = $("#account").val()
    if (account==null) {
        alert("请输入手机号或邮箱")
        return;

    }
    $.ajax({
        url: "/register/getCode.do",
        type:"post",
        data:{
            account:account
        },
        dataType:"json",
        success:function (resp) {
            console.log(resp)
                alert("发送成功")
        }

    })
}