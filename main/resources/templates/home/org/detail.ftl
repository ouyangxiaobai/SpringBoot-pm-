<!DOCTYPE html>
<html>

<!-- 新的头部 -->
<head>
    <#include "../common/head-link.ftl"/>
    <meta charset="UTF-8">
    <title>机构详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="ie-stand">
    <meta name="referrer" content="no-referrer">

    <link href="/home/layui/css/layui.css" rel="stylesheet">
    <script type="text/javascript" src="/home/layui/layui.js"></script>
    <style type="text/css">

        #page .layui-laypage .layui-laypage-curr .layui-laypage-em
        {
            background-color: #D90C19;
        }

        body
        {
            background-color: #FFFFFF;
        }
    </style>
    <link rel="stylesheet" href="/home/layui/css/layui.css"/>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="container" style="margin-top: 20px;">
    <div class="clearfix m-b-60" style="min-height: 550px;">
        <blockquote class="layui-elem-quote" style="border-left:5px solid #D90C19; background-color: ">
            机构名称: ${organization.name}
        </blockquote>
        <table class="layui-table">
            <thead>
                <tr>
                    <th>项目编号</th>
                    <th>标的名称</th>
                    <th>联系人</th>
                    <th>手机号</th>
                    <th>状态</th>
                </tr>
            </thead>
            <tbody>
                <#if pageBean.content?size gt 0>
                   <#list pageBean.content as item>
                        <tr>
                            <td>
                                <a href="/home/bidding/detail?id=${item.id}">
                                    ${item.projectNumber}
                                </a>
                            </td>
                            <td>${item.title}</td>
                            <td>${item.contacts}</td>
                            <td>${item.phone}</td>
                            <td>
                                <#if item.projectStatus.getCode() == 3>
                                    <font color="orange">公示中</font>
                                <#elseif item.projectStatus.getCode() == 4>
                                     <font color="red">竞价中</font>
                                <#elseif item.projectStatus.getCode() == 5>
                                    <font color="green">竞价成功</font>
                                <#elseif item.projectStatus.getCode() == 6>
                                    <font color="gray">竞价结束</font>
                                <#elseif item.projectStatus.getCode() == 7>
                                    <font color="#7fff00">已成交</font>
                                </#if>
                            </td>
                        </tr>
                   </#list>
                <#else>
                    <tr>
                        <td colspan="10" style="text-align: center">暂无数据</td>
                    </tr>
                </#if>
            </tbody>
        </table>
        <div class="layui-row" style="text-align: center">
            <div id="page">

            </div>
        </div>
    </div>
</div>

<#include "../common/foot.ftl"/>


<!-- 自定义js部分开始 -->
<script>
    $(function () {
        $("#homePage").removeClass("head_tab_select");
    });

    var M = {};
    M.id = ${organization.id};
    M.count = ${pageBean.total};
    M.curr = ${pageBean.currentPage};
    M.pageSize = ${pageBean.pageSize};

    layui.use('laypage', function()
    {
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: M.count //数据总数，从服务端得到
            ,limit:M.pageSize
            ,curr:M.curr
            ,jump: function(obj, first){
                //首次不执行
                if(!first){
                    M.curr = obj.curr;
                    location.href="detail?id=${organization.id}&currentPage="+obj.curr;
                }
            }
        });

    });
</script>
</body>
</html>