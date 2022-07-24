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
            <div class="per_collection" style="display: block;">
                <div class="per_table">
                    <table border="1" bordercolor="#ECE3E3">
                        <thead>
                        <tr style="background: #FFF1EC;">
                            <td>新闻标题</td>
                            <td>类型</td>
                            <td>地区</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageBean.content?size gt 0>
                            <#list pageBean.content as newsCollect>
                                <tr>
                                    <td style="vertical-align:middle;"><a href="/home/news/detail?id=${newsCollect.news.id}">
                                            <#if newsCollect.news.caption?length gt 4>
                                                ${newsCollect.news.caption?substring(0,4)}.....
                                            <#else>
                                                ${newsCollect.news.caption}
                                            </#if>

                                        </a></td>
                                    <td style="vertical-align:middle;">${newsCollect.news.newsType.name}</td>
                                    <td style="vertical-align:middle;">${newsCollect.news.province}-${newsCollect.news.city}-${newsCollect.news.county!""}</td>
                                    <td style="vertical-align:middle;">
                                        <button class="layui-btn layui-btn-primary layui-btn-sm" type="button" onclick="deleteNews(${newsCollect.id})">
                                            取消收藏
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
                                <li><a href="news?currentPage=1">«</a></li>
                            </#if>
                            <#list pageBean.currentShowPage as showPage>
                                <#if pageBean.currentPage == showPage>
                                    <li class="active"><span>${showPage}</span></li>
                                <#else>
                                    <li><a href="news?currentPage=${showPage}">${showPage}</a></li>
                                </#if>
                            </#list>
                            <#if pageBean.currentPage == pageBean.totalPage>
                                <li class="disabled"><span>»</span></li>
                            <#else>
                                <li><a href="news?currentPage=${pageBean.totalPage}">»</a></li>
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
    function deleteNews(id){
        $.ajax({
            url:"/home/news/delete_collect",
            type:'POST',
            data:{id: id},
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    successMsg("取消收藏成功");
                    setInterval(function()
                    {
                        window.location.reload();
                    }, 1000);
                }else{
                    errorMsg(data.msg);
                }
            },
            error:function(data){
                alert('网络错误!');
            }
        });
    }
</script>
<#include "../common/user-updatePic-script.ftl"/>

</body>
</html>