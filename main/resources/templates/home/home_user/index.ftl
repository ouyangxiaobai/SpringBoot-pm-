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
        .per_edit_basic {
            width: 843px;
            height: 600px;
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
            <!-- 修改基本信息 -->
            <form id="basicInformation">
                <div class="per_edit_basic">
                    <div class="per_edit_list">
                        <div class="per_edit_label">用户名</div>
                        <input name="username" id="username"
                               placeholder="用户名长度需在2-18位" type="text" value="${homeUser.username}"/>
                    </div>
                    <div class="per_edit_list">
                        <div class="per_edit_label">姓名</div>
                        <input name="name" id="name"
                               placeholder="姓名最多为18位" type="text" value="${homeUser.name!""}"/>
                    </div>

                    <div class="per_edit_list">
                        <div class="per_edit_label">手机号码</div>
                        <div id="phoneNumberDiv" class="per_edit_phone">${homeUser.mobile?substring(0,3)}***${homeUser.mobile?substring(8)}</div>
                    </div>

                    <div class="per_edit_list">
                        <div class="per_edit_label">邮箱</div>
                        <div id="emailDiv" class="per_edit_phone">${homeUser.email}</div>
                    </div>

                    <div class="per_edit_list">
                        <div class="per_edit_label">余额</div>
                        <div id="balance" class="per_edit_phone">${homeUser.balance}
                            <span>(默认支付密码为:123456，请及时修改)</span>
                        </div>
                    </div>

                    <div class="per_edit_list">
                        <div class="per_edit_label">身份证号</div>
                        <div id="idNumberDiv" class="per_edit_phone">${homeUser.idNumber?substring(0,3)}***${homeUser.idNumber?substring(14)}</div>
                    </div>

                    <div class="input-group" style="margin-top:30px;margin-bottom:15px;padding-left:25px;font-size: 16px;">
                        <div class="per_edit_label">性别</div>
                        <label class="lyear-radio radio-inline radio-primary">
                            <input type="radio" name="sex" value="1" <#if homeUser.sex == 1> checked </#if>>
                            <span>男</span>
                        </label>
                        <label class="lyear-radio radio-inline radio-primary">
                            <input type="radio" name="sex" value="2" <#if homeUser.sex == 2> checked </#if>>
                            <span>女</span>
                        </label>
                        <label class="lyear-radio radio-inline radio-primary">
                            <input type="radio" name="sex" value="0" <#if homeUser.sex == 0> checked </#if>>
                            <span>未知</span>
                        </label>
                    </div>
                    <div class="per_edit_btn">
                        <div id="updateBasic_sure" class="per_btn_sure">确认</div>
                        <div id="updateBasic_cancel" class="per_btn_cancel">取消</div>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>

<!-- 页面底部 -->

<#include "../common/foot.ftl"/>

<!-- 统计代码 -->
<div style="display: none">
    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?c6f24f3cafb3ccbdb77a284a7f2d8089";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</div>
<script src="/home/layui/layui.all.js"></script>
<script src="/home/js/bootstrap-paginator.min.js"></script>
<script src="/home/js/msg.js"></script>

<script type="text/javascript">
    $().ready(function () {
        $("#updateBasic_cancel").on("click",function(){
            $("#username").val("${homeUser.username}");
            $("#name").val("${homeUser.name!""}");
            $("input[name='sex']").each(function(index, element) {
                //判断当前按钮的值与input的值是否一致，一致则赋值
                if($(this).val()==${homeUser.sex}){
                    $(this).prop("checked",true);
                }

            });
        });
        $("#updateBasic_sure").on("click",function () {
            var username = $("#username").val().trim();
            var name = $("#name").val().trim();

            if(msg.isEmpty(username)){
                errorMsg("请输入用户名")
                return;
            }

            if(username.length<2 || username.length > 18){
                errorMsg("用户名长度需在2-18位")
                return;
            }

            if(name.length > 18){
                errorMsg("姓名长度最多为18位")
                return;
            }

            var data = $("#basicInformation").serialize()

            $.ajax({
                url:"updateBasic",
                type:"post",
                data:data,
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