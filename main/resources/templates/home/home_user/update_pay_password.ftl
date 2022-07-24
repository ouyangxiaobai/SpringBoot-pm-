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
        .per_edit_pay_password {
            width: 843px;
            height: 300px;
            border: 1px solid #ECE3E3;
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
                <div class="per_edit_pay_password">
                    <div class="per_edit_list">
                        <div class="per_edit_label">登录密码</div>
                        <input name="password" id="password"
                               placeholder="请输入登录密码" type="password" />
                    </div>
                    <div class="per_edit_list">
                        <div class="per_edit_label">新支付密码</div>
                        <input name="payPassword" id="payPassword"
                               placeholder="支付密码长度为6位" type="password" />
                    </div>
                    <div class="per_edit_list">
                        <div class="per_edit_label">确认支付密码</div>
                        <input name="verifyPayPassword" id="verifyPayPassword"
                               placeholder="请再次输入支付密码" type="password" />
                    </div>

                    <div class="per_edit_btn">
                        <div id="updatePayPass_sure" class="per_btn_sure">确认</div>
                        <div id="updatePayPass_cancel" class="per_btn_cancel">取消</div>
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


    $().ready(function () {
        $("#updatePayPass_cancel").on("click",function(){
            $("#password").val("");
            $("#payPassword").val("");
            $("#verifyPayPassword").val("");
        });
        $("#updatePayPass_sure").on("click",function () {
            var password = $("#password").val().trim();
            var payPassword = $("#payPassword").val().trim();
            var verifyPayPassword = $("#verifyPayPassword").val().trim();

            if(msg.isEmpty(password)){
                errorMsg("请输入密码")
                return;
            }

            if(msg.isEmpty(payPassword)){
                errorMsg("请输入支付密码")
                return;
            }

            if(msg.isEmpty(verifyPayPassword)){
                errorMsg("请再次输入支付密码")
                return;
            }

            if(payPassword.length!=6){
                errorMsg("支付密码长度需为6位")
                return;
            }

            if(payPassword != verifyPayPassword){
                errorMsg("两次支付密码不一致")
                return;
            }

            $.ajax({
                url:"updatePayPassword",
                type:"post",
                data:{
                    password:password,
                    payPassword:payPassword
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