

<!DOCTYPE html>
<html>

<head>
    <title>用户注册</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="/home/css/vendor.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/home/css/nj_register.css">
    <link rel="stylesheet" href="/home/css/iconfont.css">
    <link rel="stylesheet" href="/home/layui/css/layui.css">
    <link rel="icon" href="/home/picture/icon.png" type="image/x-icon">

    <link href="/home/css/main.css" rel="stylesheet">
    <style type="text/css">
        .formError {
            font-size: 12px;
            color: #D91715;
            padding-left: 1px;
            width: 100%;
            line-height: 26px;
        }
        .notclick{
            pointer-events:none;
        }
        .item-verify-code{
            color: #D91715;
            width: 25%;
            height: 100%;
            line-height: 50px;
            text-align: center;
            border: 1px solid #E4E4E4;
        }
        .errorInput{
            background: #FFF0F5;
            border: 1px solid red;
        }
        .demand-item {
            font-size: 12px;
            color: #D91715;
            padding-left: 22%;
            width: 100%;
            line-height: 26px;
        }
        .nj-register-20190412 .register-bg .register-block .block-from{
            height: 400px !important
        }

        .sweet-alert h2 {
            font-size: 1.5rem;margin:0 0;
            font-weight: 400;
        }
        .sweet-alert{
            width:299px;
            height: 140px;
            left: 56%;

        }
        .sweet-alert button{
            font-size: 12px; }
    </style>
</head>
<body>

<div class="nj-register-20190412">
    <div class="register-bg">
        <div class="register-head">
            <a href="/home/index/index">
                <img src="/home/picture/icon.png">
            </a>
            <span class="font-color1">您好！欢迎光临</span><a href="/home/user/login" class="font-color2">请登录</a>
        </div>
        <form id="registerForm">
            <div class="register-block flex-col-around">
                <div style="justify-content: center;" class="block-content-item"><span class="title-span">用户注册</span></div>
                <div class="block-from">
                    <div class="block-content-item">
                        <label>用户名称</label>
                        <input type="text" name="username" id="username" placeholder="请输入用户名称" class="validate[required]">
                    </div>
                    <div class="block-content-item">
                        <label>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</label>
                        <input type="email" name="email" id="email" placeholder="验证码将发送至该邮箱" class="validate[required]">
                    </div>
                    <div class="block-content-item">
                        <label>手机号码</label>
                        <input  oninput="value=value.replace(/[^\d]/g,'');if(value.length>11)value=value.slice(0,11)" type="text" name="mobile" id="mobile" placeholder="请填写手机号码" class="validate[required]">
                    </div>
                    <div class="block-content-item">
                        <label>身份证号</label>
                        <input type="text" name="idNumber" id="idNumber" placeholder="请输入身份证号" class="validate[required]">
                    </div>
                    <div class="block-content-item">
                        <label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label>
                        <input class="validate[minSize[4] maxSize[20] required]" type="password" name="password" id="password" placeholder="密码为4-32位">
                    </div>
                    <div class="block-content-item">
                        <label>确认密码</label>
                        <input class="validate[required]" type="password" name="verifyPassword" id="verifyPassword" placeholder="请再次输入密码">
                    </div>
                    <div class="block-content-item">
                        <label>邮箱验证</label>
                        <!-- TODO 短信验证码处理 -->
                        <input class="code_input" type="hidden" name="codeId" id="codeId"/>
                        <input class="verify validate[required]" type="text" placeholder="四位邮箱验证码" name="identifyCode" id="identifyCode">
                        <div class="verify-code" style="border:0px">
                            <div onclick="sendCode()" class="item-verify-code code_div" style="width:95px;cursor:pointer;">获取验证码</div>
                        </div>
                    </div>
                </div>
                <div class="content-btn btn_submit" onclick="userRegister()" style="cursor:pointer;">注册</div>
            </div>
        </form>
    </div>
</div>

<!-- 公用js部分开始 -->
<script src="/home/layui/layui.all.js"></script>
<script src="/home/js/vendor.min.js"></script>
<script src="/home/js/jquery.validationengine.js"></script>
<script src="/home/js/jquery.validationengine.min.js"></script>
<script src="/home/js/main.js"></script>
<script src="/home/js/msg.js"></script>

<!-- 公用js部分结束 -->
<!-- 自定义js部分开始 -->
<script type="text/javascript">
    var countdown = 120;

    var flag = false;

    //重新获取验证码
    function settime() {
        if (countdown == 0) {
            $(".code_div").text("重新获取")
            $(".code_div").removeClass("notclick");
            countdown = 119;
            return;
        } else {
            $(".code_div").addClass("notclick");
            $(".code_div").text(countdown + "秒");
            countdown--;
        }
        setTimeout(function() {
            settime()
        }, 1000);
    }

    //发送验证码
    function sendCode(){
            var email = $("#email").val();
            if(msg.isEmpty(email)){
                errorMsg("请输入邮箱!")
                return;
            };

            if(!msg.isEmail(email)){
                errorMsg("邮箱格式错误!")
                return;
            };

            $.ajax({
                url : "/send_email/generate_code",
                data : {
                    email : email,
                    method:"user_register_email_code",
                },
                type : "post",
                cache : false,
                async : false,
                dataType : "json",
                success : function(data) {
                    if (data.code == 0) {
                        successMsg("验证码已发送");
                        flag = true;
                        settime();
                    } else {
                        errorMsg(data.msg)
                    }
                },
                error : function(data) {
                    errorMsg("网络错误")
                }
            });
    }

    //注册提交
    function userRegister(){
        var username = $("#username").val().trim();
        var email = $("#email").val().trim();
        var mobile = $("#mobile").val().trim();
        var idNumber = $("#idNumber").val().trim();
        var password = $("#password").val().trim();
        var verifyPassword = $("#verifyPassword").val().trim();
        var identifyCode = $("#identifyCode").val().trim();

        if(msg.isEmpty(username)){
            errorMsg("请输入用户名")
            return;
        }
        if(msg.isEmpty(email)){
            errorMsg("请输入邮箱")
            return;
        }
        if(msg.isEmpty(mobile)){
            errorMsg("请输入手机号")
            return;
        }
        if(msg.isEmpty(idNumber)){
            errorMsg("请输入身份证号")
            return;
        }
        if(msg.isEmpty(password)){
            errorMsg("请输入密码")
            return;
        }
        if(msg.isEmpty(verifyPassword)){
            errorMsg("请输入确认密码")
            return;
        }
        if(!flag){
            errorMsg("还未发送过验证码")
            return;
        }
        if(msg.isEmpty(identifyCode)){
            errorMsg("请输入验证码")
            return;
        }

        if(!msg.isEmail(email)){
            errorMsg("邮箱格式错误")
            return;
        }
        if(!msg.isPhone(mobile)){
            errorMsg("手机号格式错误")
            return;
        }
        if(!msg.isCard(idNumber)){
            errorMsg("身份证号格式错误")
            return;
        }
        if(password.length<4 || password.length>32){
            errorMsg("密码需在4-32位")
            return;
        }
        if(password != verifyPassword){
            errorMsg("两次密码不一致")
            return;
        }
        var data = $("#registerForm").serialize()
        $.ajax({
            url:'register',
            type:'post',
            data:data,
            dataType:'json',
            success:function (data) {
                if(data.code == 0){
                    successMsg("注册成功")
                    setTimeout(function () {
                        window.location.href="/home/user/login"
                    },1000)
                }else{
                    errorMsg(data.msg)
                }
            },
            error:function (data) {
                errorMsg("网络错误")
            }
        })
    }


</script>

</body>
</html>