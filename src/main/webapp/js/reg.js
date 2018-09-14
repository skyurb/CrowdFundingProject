function register() {



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