<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/home/css/vendor.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/home/css/nj_login.css">
    <link rel="stylesheet" href="/home/css/iconfont.css">
    <link href="/home/css/main.css" rel="stylesheet">
    <link rel="icon" href="/home/picture/icon.png" type="image/x-icon">

    <style type="text/css">
        .notclick{
            pointer-events:none;
        }
        .code_div{
            color:#D91715;
        }

        .nj-username-login .login-bg .login-block .login-block-sel .login.on,.nj-username-login .login-bg .login-block .login-block-sel .login {
            display: block;
            float: left;
            width: 50%;
            text-align: center;
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
<div class="nj-username-login">
    <div class="login-bg">
        <div class="login-head">
            <a href="/home/index/index">
                <img src="/home/picture/icon.png">
            </a>
            <span>欢迎登录</span>
        </div>
        <div class="lafite_three_login">
            <div id="head_login" class="head_login frist_login">
                <img style="margin-top: 11px;"  src="/home/picture/head_login.png" alt="head_login"/>
                <span>登录</span>
            </div>
            <div id="head_sign" class="head_sign last_sign">
                <img src="/home/picture/head_sign.png" alt="head_sign"/>
                <span>注册</span>
            </div>
        </div>
        <div class="login-block">
            <div class="login-block-sel"><span class="login on" style="cursor:pointer;">密码登录</span><span class="login" style="cursor:pointer;">验证码登录</span></div>
            <div class="contentbox">
                <div class="box active">
                    <div class="content-username">
                        <div class="content-item"><i class="iconfont icon-ArtboardCopy3 font-icon"></i>
                            <input type="text" id="email1" placeholder="请输入邮箱">
                        </div>
                        <div class="content-item"><i class="iconfont icon-ArtboardCopy2 font-icon"></i>
                            <input type="password" id="password" placeholder="请输入您的密码">
                            <input type="hidden" id="passType" value="password">
                        </div>
                        <div class="content-btn" id="loginByPass" onclick="loginByPass()" style="cursor:pointer;"> <span>登录</span></div><a href="/system/login" class="member-text">前往后台</a>
                    </div>
                </div>
                <div class="box">
                    <div class="content-iphone">
                        <div class="content-item"><i class="iconfont icon-ArtboardCopy3 font-icon"></i>
                            <input type="text" id="email2" placeholder="请输入您的邮箱">
                        </div>
                        <div class="content-item-verify">
                            <div class="item-verify"><i class="iconfont icon-ArtboardCopy5"></i>
                                <input class="code_input" type="hidden" name="codeId" id="codeId"/>
                                <input type="text" id="identifyCode" name="identifyCode" placeholder="请输入您的验证码">
                            </div>
                            <div class="item-verify-code code_div" onclick="sendCode()" style="cursor:pointer;"><span>获取验证码</span></div>
                        </div>
                        <div class="content-btn" id="loginByVal" onclick="loginByVal()" style="cursor:pointer;"> <span>登录</span></div>
                        <a href="/system/login" class="member-text">前往后台</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 公用js部分开始 -->
<script src="/home/layui/layui.all.js"></script>
<script src="/home/js/vendor.min.js"></script>
<script src="/home/js/jquery.cookie.js"></script>
<script src="/home/js/main.js"></script>
<script src="/home/js/msg.js"></script><!-- 公用js部分结束 -->
<!-- 自定义js部分开始 -->
<script type="text/javascript">
    //发送验证码
    var countdown = 120;

    var flag = false;

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
        var email = $("#email2").val();
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
                method:"user_login_email_code",
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

    function loginByPass(){
        var email = $("#email1").val();
        var password = $("#password").val();
        if(msg.isEmpty(email)){
            errorMsg("请输入邮箱");
            return;
        }
        if(msg.isEmpty(password)){
            errorMsg("请输入密码");
            return;
        }
        if(!msg.isEmail(email)){
            errorMsg("邮箱格式错误");
            return;
        }
        $.ajax({
            url : "loginByPass",
            data : {
                email : email,
                password : password,
            },
            type : "post",
            dataType : "json",
            success:function (data) {
                if(data.code == 0){
                    successMsg("登录成功！");
                    setTimeout(function (){
                        window.location.href="/home/index/index";
                    }, 1000);
                }else{
                    errorMsg(data.msg);
                }
            },
            error:function (data) {
                errorMsg("网络错误");
            }
        })
    }

    function loginByVal(){
        var email = $("#email2").val();
        var identifyCode = $("#identifyCode").val();
        if(!flag){
            errorMsg("还未发送验证码");
            return;
        }
        if(msg.isEmpty(email)){
            errorMsg("请输入邮箱");
            return;
        }
        if(msg.isEmpty(identifyCode)){
            errorMsg("请输入验证码");
            return;
        }
        if(!msg.isEmail(email)){
            errorMsg("邮箱格式错误");
            return;
        }
        $.ajax({
            url : "loginByVal",
            data : {
                email : email,
                identifyCode : identifyCode,
            },
            type : "post",
            dataType : "json",
            success:function (data) {
                if(data.code == 0){
                    successMsg("登录成功！");
                    location.href = "/home/index/index";
                }else{
                    errorMsg(data.msg);
                }
            },
            error:function (data) {
                errorMsg("网络错误");
            }
        })
    }

    $().ready(function(){
        $(function(){
            var DOMAIN = document.location.protocol + "//" + location.host;
            $("#head_login").click(function(){
                window.location.href=DOMAIN+"/home/user/login";
            });
            $("#head_sign").click(function(){
                window.location.href=DOMAIN+"/home/user/register";
            });

            $(".login-block-sel span").off("click").on("click",function(){
                var index = $(this).index();
                $(this).addClass("on").siblings().removeClass("on");
                $(".contentbox .box").eq(index).addClass("active").siblings().removeClass("active");
            });

        });
    })
</script>
</body>
</html>