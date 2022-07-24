<!DOCTYPE html>
<html>

<!-- 新的头部 -->
<head>
    <#include "../common/head-link.ftl"/>
    <meta charset="UTF-8">
    <title>通知详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="ie-stand">
    <meta name="referrer" content="no-referrer">
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
                <p class="font-12 text-grey text-center p-b-20 border-b border-c-e8"><span
                        class="p-lr-10">${news.createTime}</span><span class="p-lr-10">来源：${news.source}</span></p>

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
        $('.carousel-inner .item').eq(0).addClass('active');
        $(".head_tab_select").removeClass("head_tab_select");
        $("#announcements").addClass("head_tab_select");
    });

</script>
</body>
</html>