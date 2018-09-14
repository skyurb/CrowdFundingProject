function register() {


}

function getCode() {
    var phone = $("#phone").val()


    $.ajax({

        url: "/register/getCode",
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