<!DOCTYPE html>
<html>



<!-- 新的头部 -->
<head>
    <#include "../common/head-link.ftl"/>
    <meta charset="UTF-8">
    <title>新闻详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="ie-stand">
    <meta name="referrer" content="no-referrer">

    <link rel="stylesheet" href="/home/layui/css/layui.css"/>
    <script type="text/javascript" src="/home/layui/layui.all.js"></script>
    <script type="text/javascript" src="/home/js/msg.js"></script>

    <style type="text/css">

        .cont-txt img
        {
            max-width: 80%;
        }
    </style>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="container" style="margin-top: 20px;">
    <div class="clearfix m-b-60">
        <div class="w780 pull-left">
            <article class="zixun-cont">
                <h2>${news.caption}</h2>
                <p class="font-12 text-grey text-center p-b-20 border-b border-c-e8">
                    <span class="p-lr-10">${news.createTime}</span>
                    <span class="p-lr-10">来源：${news.source}</span>
                    <#if ylrc_home??>
                        <#if newCollect??>
                            <span class="p-lr-10" onclick="deleteCollect(${newCollect.id})" style="cursor: pointer">
                               <i class="layui-icon layui-icon-star-fill"></i>取消收藏
                            </span>
                        <#else>
                            <span class="p-lr-10" onclick="addCollect()" style="cursor: pointer">
                               <i class="layui-icon layui-icon-star"></i>收藏
                             </span>
                        </#if>

                    </#if>
                </p>
                <div class="cont-txt">
                    ${news.content}
                </div>
            </article>
        </div>
    </div>
</div>

<#include "../common/foot.ftl"/>


<!-- 自定义js部分开始 -->
<script>
    $(function () {
        $("#homePage").removeClass("head_tab_select");
        $("#head_news").addClass("head_tab_select");
    });

    var M = {};
    M.id = ${news.id};

    <!--添加收藏-->
    function addCollect()
    {
        $.ajax({
            url:"add_collect",
            type:'POST',
            data:{id:M.id},
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    successMsg("添加收藏成功");
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

    function deleteCollect(id)
    {
        $.ajax({
            url:"delete_collect",
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


    $(function () {
        $('.carousel-inner .item').eq(0).addClass('active');
    });


</script>
</body>
</html>