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
    <link rel="stylesheet" href="/home/js/jconfirm/jquery-confirm.min.css">
    <#include "../common/head-link.ftl"/>
    <style>
        .footer{height:100px;}
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

            <div class="">
                <button onclick="applyWithdrawal()" class="btn btn-primary" style="background-color: #D01219">申请提现</button>

                <div class="per_table">
                    <table border="1" borderColor="#ECE3E3">
                        <thead>
                        <tr style="background: #FFF1EC;">
                            <td style="width: 40%">银行卡号</td>
                            <td style="width: 20%">金额</td>
                            <td style="width: 10%">状态</td>
                            <td style="width: 20%">查看</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageBean.content?size gt 0>
                            <#list pageBean.content as with>
                                <tr>
                                    <td style="vertical-align:middle;">${with.bankCard}</td>
                                    <td style="vertical-align:middle;">￥${with.money}元</td>
                                    <td>
                                        <#if with.status == 1>
                                            <font style="color: orange">审核中</font>
                                        <#elseif with.status = 2>
                                            <font style="color: green">已通过</font>
                                        <#else>
                                            <font style="color: red">未通过</font>
                                        </#if>
                                    </td>
                                    <td style="vertical-align:middle;">
                                        <#if with.status == 3>
                                            <button class="layui-btn layui-btn-primary layui-btn-sm" type="button" onclick="layer.alert('提现未通过原因:${with.notPassReason!""}')">
                                                查看原因
                                            </button>
                                        <#else>
                                            <button class="layui-btn layui-btn-primary layui-btn-sm layui-disabled" disabled type="button">
                                                查看原因
                                            </button>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                        <#else>
                            <tr align="center"><td colspan="9">这里空空如也！</td></tr>
                        </#if>
                        </tbody>
                    </table>
                    <#if pageBean.total gt 0>
                        <ul class="pagination ">
                            <#if pageBean.currentPage == 1>
                                <li class="disabled"><span>«</span></li>
                            <#else>
                                <li><a href="withdrawalRecord?currentPage=1">«</a></li>
                            </#if>
                            <#list pageBean.currentShowPage as showPage>
                                <#if pageBean.currentPage == showPage>
                                    <li class="active"><span>${showPage}</span></li>
                                <#else>
                                    <li><a href="withdrawalRecord?currentPage=${showPage}">${showPage}</a></li>
                                </#if>
                            </#list>
                            <#if pageBean.currentPage == pageBean.totalPage>
                                <li class="disabled"><span>»</span></li>
                            <#else>
                                <li><a href="withdrawalRecord?currentPage=${pageBean.totalPage}">»</a></li>
                            </#if>
                            <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                        </ul>
                    </#if>
                </div>

            </div>

        </div>
    </div>
</div>
<!-- 页面底部 -->


<#include "../common/foot.ftl"/>

<script src="/home/layui/layui.all.js"></script>
<script src="/home/js/bootstrap-paginator.min.js"></script>
<script src="/home/js/msg.js"></script>
<script src="/home/js/jconfirm/jquery-confirm.min.js"></script>

<script type="text/javascript">

    //提现支付密码输入
    function applyWithdrawal() {
        $.confirm({
            title: '请输入支付密码',
            content: '<input type="password" maxlength="6" id="payPassword" class="payPassword form-control" value="" placeholder="请输入支付密码" />',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function(){
                        var payPassword = $("#payPassword").val();
                        if(payPassword == null || payPassword.length == 0)
                        {
                            errorMsg("请输入支付密码");
                            return;
                        }

                        $.ajax({
                            url:'/home/user/pay_password_ensure',
                            type:'POST',
                            data:{
                                payPassword: payPassword
                            },
                            dataType:'json',
                            success:function(data){
                                if(data.code == 0){
                                    sendApply();
                                }else{
                                    errorMsg(data.msg);
                                }
                            },
                            error:function(data){
                                alert('网络错误!');
                            }
                        });

                    }
                },
                cancel: {
                    text: '关闭',
                    action: function(){

                    }
                }
            }
        });

    }

    function sendApply() {
        $.confirm({
            title:"申请提现",
            content: '' +
                '<div class="dlg_content">\n' +
                '    <form action="" class="formName">\n' +
                '        <div class="form-group">\n' +
                '            <label>请选择银行卡</label>\n' +
                '            <select class="form-control" id="bankId">\n' +
                '                <option value="">请选择</option>\n' +
                '                <#list bankCardList as item>\n' +
                '                    <option value="${item.id}">${item.name} -- ${item.cardNumbers}</option>\n' +
                '                </#list>\n' +
                '            </select>\n' +
                '        </div>\n' +
                '        <div class="form-group">\n' +
                '            <label>提现金额</label>\n' +
                '            <input type="number" min="0" id="number" class="form-control" placeholder="请输入提现金额,仅保留小数后两位" oninput="if(value>100000)value=100000;if (value<0)value=0"/>\n' +
                '        </div>\n' +
                '    </form>\n' +
                '</div>',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function(){
                        var number = this.$content.find('#number').val();
                        var bankId = this.$content.find('#bankId').val();
                        if(bankId == null || bankId == ""){
                            errorMsg("请选择银行卡")
                            return;
                        }
                        if(number == null || number == ""){
                            errorMsg("请输入提现金额")
                            return;
                        }
                        var money = null;
                        try
                        {
                            money = parseFloat(number)
                        }catch (e) {
                            errorMsg("格式错误")
                            return ;
                        }

                        money = Math.floor(money * 100) /100

                        if(money <= 0){
                            errorMsg("提现金额不能小于0")
                            return;
                        }
                        if(money > 100000){
                            errorMsg("提现金额不能大于100000")
                            return;
                        }
                        console.log(money)
                        console.log(typeof(money))
                        console.log(bankId)
                        $.ajax({
                            url:'/home/user_wr/sendApply',
                            type:'post',
                            data:{
                                money:money,
                                bankCardId:bankId,
                            },
                            dataType:'json',
                            success:function (data) {
                                if(data.code == 0){
                                    successMsg("申请已发送")
                                    setTimeout(function (){
                                        window.location.reload();
                                    }, 1000);
                                }else{
                                    errorMsg(data.msg)
                                }
                            },
                            error:function (data) {
                                alert("网络错误")
                            }
                        })

                    }
                },
                cancel: {
                    text: '关闭',
                    action: function(){

                    }
                }
            }
        });
    }


</script>
<#include "../common/user-updatePic-script.ftl"/>

</body>
</html>