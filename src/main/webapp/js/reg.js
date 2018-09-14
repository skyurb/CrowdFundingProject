function register() {
    var userNumber=$("#phone").val;
    var userName=$("#name").val;
    var password=$("#password").val;
    var code=$("#code").val;
    $.ajax({
        url: "/register//register.do",
        type:"post",
        data:{
            userNumber:userNumber,
            userName:userName,
            password:password,
            code:code
        },
        dataType:"json",
        success:function (resp) {
            console.log(resp)
            if (resp.isSuccess){
                alert(resp.content);
            }
            alert(resp.error);
        }

    })




}

function getCode() {
    var phone = $("#phone").val()
    console.log(phone);

    $.ajax({
        url: "/register/getCode.do",
        type:"post",
        data:{
            phoneNumber:phone
        },
        dataType:"json",
        success:function (resp) {
            alert("发送成功");
        }

    })
}