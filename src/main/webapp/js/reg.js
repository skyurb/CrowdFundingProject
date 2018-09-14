function register() {


}

function getCode() {
    var account = $("#phone").val()
    if (account==null) {
        alert("请输入手机号或邮箱")
        window.location.href="reg.html"
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