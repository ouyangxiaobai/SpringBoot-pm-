<!DOCTYPE html>
<html lang="en">
<head>
    <title>个人中心</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="/home/css/personal_main.css" rel="stylesheet">
    <link rel="stylesheet" href="/home/css/view.css">
    <link rel="stylesheet" href="/home/css/font_426541_zzraipzv8od.css">
    <link rel="stylesheet" href="/home/css/lightslider.min.css">
    <link rel="stylesheet" href="/home/layui/css/layui.css">

    <#include "../common/head-link.ftl"/>
    <style>
        .footer{height:100px;}
        .notclick{
            pointer-events: none;
        }
    </style>
</head>
<body>

<#include "../common/head.ftl"/>

<!-- 主体内容 -->
<div class="personal_main">
    <div class="personal_content">
        <!-- 左侧菜单部分 -->
        <#include "../common/user-left-menu.ftl"/>
        <!--内容部分 -->
        <div class="personal_content_right">
            <!-- 修改密码 -->
            <form id="updatePass">
                <div class="per_edit_password" style="height: 400px">
                    <div class="per_edit_list">
                        <div class="per_edit_label">设置新密码</div>
                        <input name="password" id="password"
                               placeholder="密码长度为4-32位" type="password" />
                    </div>

                    <div class="per_edit_list">
                        <div class="per_edit_label">确认密码</div>
                        <input name="verifyPassword" id="verifyPassword"
                               placeholder="请再次输入密码" type="password" />
                    </div>

                    <div class="per_edit_list">
                        <div class="per_edit_label">邮箱</div>
                        <div id="emailDiv" class="per_edit_phone">
                            <#if ylrc_home??>
                                ${ylrc_home.email}
                            </#if>
                        </div>
                    </div>

                    <div class="per_edit_list">
                        <div class="per_edit_label">验证码</div>
                        <#--<input type="hidden" name="codeId" id="codeId" />-->
                        <input class="per_edit_yzm" type="text" placeholder="四位邮箱验证码"
                                name="identifyCode" id="identifyCode" />
                        <div class="per_click_yzm" style="border: 0px">
                            <#--<input type="button" value="发送" class="item-verify-code code_div per_click_yzm"/>
                            <input type="button" value="重新发送" style="display: none;" class="item-verify-code code_div per_click_yzm" disabled/>-->
                            <div class="item-verify-code code_div per_click_yzm"
                                 style="width: 95px;" onclick="sendCode()" >获取验证码</div>
                        </div>
                    </div>

                    <div class="per_edit_btn">
                        <div id="updatePass_sure" class="per_btn_sure">确认</div>
                        <div id="updatePass_cancel" class="per_btn_cancel">取消</div>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>

<!-- 页面底部 -->

<#include "../common/foot.ftl"/>

</div>
<script src="/home/layui/layui.all.js"></script>
<script src="/home/js/bootstrap-paginator.min.js"></script>
<script src="/home/js/msg.js"></script>

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
        var email = "${ylrc_home.email}";

        $.ajax({
            url : "/send_email/generate_code",
            data : {
                email : email,
                method:"user_edit_password_code",
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

    $().ready(function () {
        $("#updatePass_cancel").on("click",function(){
            $("#password").val("");
            $("#verifyPassword").val("");
            $("#identifyCode").val("");
        });
        $("#updatePass_sure").on("click",function () {
            var password = $("#password").val().trim();
            var verifyPassword = $("#verifyPassword").val().trim();
            var identifyCode = $("#identifyCode").val().trim();

            if(!flag){
                errorMsg("还未发送验证码")
                return;
            }

            if(msg.isEmpty(password)){
                errorMsg("请输入新密码")
                return;
            }

            if(msg.isEmpty(verifyPassword)){
                errorMsg("请再次输入密码")
                return;
            }

            if(msg.isEmpty(identifyCode)){
                errorMsg("请输入验证码")
                return;
            }



            if(identifyCode.length!=4){
                errorMsg("验证码长度需为4")
                return;
            }

            if(password.length<4 || password.length > 32){
                errorMsg("密码长度需在4-32位")
                return;
            }

            if(password != verifyPassword){
                errorMsg("两次密码不一致")
                return;
            }

            $.ajax({
                url:"updatePassword",
                type:"post",
                data:{
                    password:password,
                    identifyCode:identifyCode
                },
                dataType:"json",
                success:function (data) {
                    if(data.code == 0){
                        successMsg("修改成功!")
                        setTimeout(function (){
                            window.location.reload();
                        }, 1000);
                    }else{
                        errorMsg(data.msg)
                    }
                },
                error:function (data) {
                    errorMsg("网络错误")
                }
            })

        });
    })

</script>

<#include "../common/user-updatePic-script.ftl"/>
</body>
</html>