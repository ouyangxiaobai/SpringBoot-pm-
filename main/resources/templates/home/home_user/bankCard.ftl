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
    <script src="/home/js/bank-card.js" type="text/javascript"></script>
    <script src="/home/js/jquery.min.3.4.js"></script>

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
            <!-- 项目报名 -->
            <div class="">
                <button onclick="addBackCard()" class="btn btn-primary" style="background-color: #D01219">添加银行卡</button>

                <div class="per_table">
                    <table border="1" borderColor="#ECE3E3">
                        <thead>
                        <tr style="background: #FFF1EC;">
                            <td>银行卡号</td>
                            <td>所属银行</td>
                            <td>所属支行</td>
                            <td>开户人姓名</td>
                            <td>开户人手机号</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageBean.content?size gt 0>
                            <#list pageBean.content as bankCard>
                                <tr>
                                    <td style="vertical-align:middle;">${bankCard.cardNumbers?substring(0,4)}******${bankCard.cardNumbers?substring(14)}</td>
                                    <td data-bank="${bankCard.bank}">
                                        <script>
                                            var text = getText('${bankCard.bank}');
                                            $("td[data-bank='"+'${bankCard.bank}'+"']").html(text);
                                        </script>
                                    </td>
                                    <td style="vertical-align:middle;">${bankCard.branch}</td>
                                    <td style="vertical-align:middle;">${bankCard.name}</td>
                                    <td style="vertical-align:middle;">${bankCard.phone}</td>
                                    <td style="vertical-align:middle;">
                                        <button class="btn btn-label btn-primary" onclick="deleteBankCard(${bankCard.id})">删除</button>
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
                                <li><a href="bankCard?currentPage=1">«</a></li>
                            </#if>
                            <#list pageBean.currentShowPage as showPage>
                                <#if pageBean.currentPage == showPage>
                                    <li class="active"><span>${showPage}</span></li>
                                <#else>
                                    <li><a href="bankCard?currentPage=${showPage}">${showPage}</a></li>
                                </#if>
                            </#list>
                            <#if pageBean.currentPage == pageBean.totalPage>
                                <li class="disabled"><span>»</span></li>
                            <#else>
                                <li><a href="bankCard?currentPage=${pageBean.totalPage}">»</a></li>
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

<script type="text/javascript">

    //跳转到添加银行卡页面
    function addBackCard() {
        window.location.href = "/home/userBankCard/add"
    }

    //删除银行卡
    function deleteBankCard(bankCardId) {
        $.ajax({
            url:'/home/userBankCard/delete',
            type:'post',
            data:{
                id:bankCardId,
            },
            dataType:'json',
            success:function (data) {
                if(data.code == 0){
                    successMsg("删除成功")
                    setTimeout(function () {
                        window.location.reload();
                    },1000)
                }
            }
        })
    }

</script>
<#include "../common/user-updatePic-script.ftl"/>

</body>
</html>