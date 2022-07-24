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

            <div class="m-b-10 layui-row">
                <div class="layui-col-md8">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:100px">项目标题:</label>
                        <div class="layui-input-block">
                            <input class="layui-input" placeholder="请输入项目标题进行模糊查询" id="title" value="${title!""}"/>
                        </div>
                    </div>
                </div>
                <div class="layui-col-md2" style="margin-left: 15px;">
                    <button type="button" class="layui-btn" onclick="search()" style="background-color: #D90C19;">
                        <i class="layui-icon layui-icon-search"></i>搜索
                    </button>
                </div>
            </div>

            <div class="">
                <div class="per_table">
                    <table border="1" borderColor="#ECE3E3">
                        <thead>
                        <tr style="background: #FFF1EC;">
                            <td>项目编号</td>
                            <td>项目标题</td>
                            <td>项目状态</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageBean.content?size gt 0>
                            <#list pageBean.content as message>
                                <tr>
                                    <td style="vertical-align:middle;">${message.biddingProject.projectNumber}</td>
                                    <td style="vertical-align:middle;"><a href="/home/bidding/detail?id=${message.biddingProject.id}">
                                            <#if message.biddingProject.title?length gt 4>
                                                ${message.biddingProject.title?substring(0,4)}...
                                            <#else>
                                                ${message.biddingProject.title}
                                            </#if>
                                        </a></td>
                                    <td style="vertical-align:middle;">
                                        <#if message.biddingProject.projectStatus.getCode() == 3>
                                            公示中
                                        <#elseif message.biddingProject.projectStatus.getCode() == 4>
                                            竞价中
                                        <#elseif message.biddingProject.projectStatus.getCode() == 5>
                                            竞价成功
                                        <#elseif message.biddingProject.projectStatus.getCode() == 6>
                                            竞价结束
                                        <#else>
                                            已成交
                                        </#if>
                                    </td>
                                    <td style="vertical-align: middle">
                                        <button type="button" class="layui-btn" onclick="showMessage(${message.id})" style="background-color: #D90C19;">
                                            留言回复
                                        </button>
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
                                <li><a href="message?title=${title!""}&currentPage=1">«</a></li>
                            </#if>
                            <#list pageBean.currentShowPage as showPage>
                                <#if pageBean.currentPage == showPage>
                                    <li class="active"><span>${showPage}</span></li>
                                <#else>
                                    <li><a href="message?title=${title!""}&currentPage=${showPage}">${showPage}</a></li>
                                </#if>
                            </#list>
                            <#if pageBean.currentPage == pageBean.totalPage>
                                <li class="disabled"><span>»</span></li>
                            <#else>
                                <li><a href="message?title=${title!""}&currentPage=${pageBean.totalPage}">»</a></li>
                            </#if>
                            <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                        </ul>
                    </#if>
                </div>
            </div>

        </div>
    </div>
</div>
<div class="modal" id="viewProjectDetail" tabindex="-1" role="dialog" aria-labelledby="viewRegistratition">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:1000px;border: 1px solid #b1acac;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">留言回复</h4>
            </div>
            <div class="projet-body" id="projet-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
    //查找
    function search(){
        var title = $("#title").val().trim()
        window.location.href = "message?title="+title;
    }

    function showMessage(id) {
        $.get("/home/message/find", {id: id}, function (result) {
            $(".projet-body").empty();
            $(".projet-body").html(result);
            $('#viewProjectDetail').modal('show');
        });
    }

</script>
<#include "../common/user-updatePic-script.ftl"/>

</body>
</html>