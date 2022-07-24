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
                <button onclick="recharge()" class="btn btn-primary" style="background-color: #D01219">充值</button>

                <div class="per_table">
                    <table border="1" borderColor="#ECE3E3">
                        <thead>
                        <tr style="background: #FFF1EC;">
                            <td style="width: 50%">订单编号</td>
                            <td style="width: 20%">金额</td>
                            <td style="width: 10%">状态</td>
                            <td style="width: 20%">操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageBean.content?size gt 0>
                            <#list pageBean.content as userAlipay>
                                <tr>
                                    <td style="vertical-align:middle;">${userAlipay.outTradeNo}</td>
                                    <td style="vertical-align:middle;">￥${userAlipay.totalAmount}元</td>
                                    <td>
                                        <#if userAlipay.status == 0>
                                            <font style="color: orange">待支付</font>
                                        <#elseif userAlipay.status = 1>
                                            <font style="color: green">已支付</font>
                                        <#elseif userAlipay.status = 2>
                                            <font style="color: blue">已退款</font>
                                        <#else>
                                            <font style="color: red">已关闭</font>
                                        </#if>
                                    </td>
                                    <td style="vertical-align:middle;">
                                        <#if userAlipay.status == 0>
                                            <button class="layui-btn layui-btn-primary layui-btn-sm" type="button" onclick="toPay('${userAlipay.outTradeNo}')">
                                                继续支付
                                            </button>
                                        <#else>
                                            <button class="layui-btn layui-btn-primary layui-btn-sm layui-disabled" disabled type="button">
                                                继续支付
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
                                <li><a href="alipay?currentPage=1">«</a></li>
                            </#if>
                            <#list pageBean.currentShowPage as showPage>
                                <#if pageBean.currentPage == showPage>
                                    <li class="active"><span>${showPage}</span></li>
                                <#else>
                                    <li><a href="alipay?currentPage=${showPage}">${showPage}</a></li>
                                </#if>
                            </#list>
                            <#if pageBean.currentPage == pageBean.totalPage>
                                <li class="disabled"><span>»</span></li>
                            <#else>
                                <li><a href="alipay?currentPage=${pageBean.totalPage}">»</a></li>
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

    //充值弹出层
    function recharge() {
        $.confirm({
            title:"充值",
            content: '' +
                '<form action="" class="formName">' +
                '        <div class="form-group">\n' +
                '            <label>充值金额</label>\n' +
                '            <input type="number" min="0" id="totalAmount" class="form-control" placeholder="请输入充值金额" oninput="if(value>100000)value=100000;if (value<0)value=0"/>\n' +
                '        </div>\n' +
                '</form>',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function(){
                        var totalAmount = this.$content.find('#totalAmount').val();
                        creatOrder(totalAmount);
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

    function creatOrder(money){
        if(msg.isEmpty(money)){
            errorMsg("请输入要充值的金额")
            return;
        }
        var totalAmount = null;
        try
        {
            totalAmount = parseFloat(money)
        }catch (e) {
            errorMsg("格式错误")
            return ;
        }

        totalAmount = Math.floor(totalAmount * 100) /100


        if(totalAmount < 1 || totalAmount > 100000)
        {
            errorMsg("充值金额需在1~100000之间");
            return ;
        }
        $.ajax({
            type: "POST",
            url: '/home/user_alipay/create_order',
            data: {totalAmount:totalAmount},
            dataType: 'json',
            cache: false,
            success: function (data) {
                if (data.code == 0) {
                    toPay(data.data.outTradeNo);
                }
                else
                {
                    errorMsg(data.msg);
                }
            },
            error: function (data) {
                errorMsg('网络错误!');
            }
        });
    }

    function toPay(outTradeNo) {
        location.href = "/home/user_alipay/to_pay?outTradeNo=" + outTradeNo;
    }

</script>
<#include "../common/user-updatePic-script.ftl"/>

</body>
</html>