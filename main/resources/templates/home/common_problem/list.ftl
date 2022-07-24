<!DOCTYPE html>
<html>
<title>常见问题</title>

<#include "../common/head-link.ftl"/>
<!-- 新的头部 -->
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="ie-stand">
    <meta name="referrer" content="no-referrer">
    <style>
        body
        {
            background-color: #FFFFFF;
        }

        .layui-timeline{
            overflow-y: auto;
            background-color: #f8f8f8;
            height: 200px;
            padding: 10px;
        }
        .layui-timeline::-webkit-scrollbar {/*滚动条整体样式*/
            width: 4px;     /*高宽分别对应横竖滚动条的尺寸*/
            height: 4px;
            scrollbar-arrow-color:red;

        }
        .layui-timeline::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
            border-radius: 5px;
            -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
            background: rgba(0,0,0,0.2);
            scrollbar-arrow-color:red;
        }
        .layui-timeline::-webkit-scrollbar-track {/*滚动条里面轨道*/
            -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
            border-radius: 0;
            background: rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

<#include "../common/head.ftl"/>

<div class="container">
    <div class="h30"></div>
    <div class="clearfix m-b-60">
        <div class="solution_title solution_title_length1">
            常见<span>问题</span>
            <div class="solution_line solution_line1"></div>
        </div>
        <div class="solution_con_policy_list" style="height: 100%; margin-top: 80px;">
            <ul class="layui-timeline" style="overflow-y: scroll; height: 600px;">
                <#list commonProblemList as item>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">${item.createTime?string('yyyy年MM月dd日')}</h3>
                            <p>
                                <span style="color: white; background-color: red; padding: 1px; border-radius: 4px;">问</span>
                                ${item.name}
                            </p>
                            <p style="margin-top: 5px">
                                <span style="color: white; background-color: green; padding: 1px; border-radius:4px">答</span>
                                ${item.answer}
                            </p>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
</div>

<#include "../common/foot.ftl"/>

<link href="/home/layui/css/layui.css" rel="stylesheet">
<script type="text/javascript" src="/home/layui/layui.js"></script>


<!-- 自定义js部分开始 -->
<script id="information" type="text/javascript">
    $(function () {
        $(".head_tab_select").removeClass("head_tab_select");
        $("#solution").addClass("head_tab_select");
    });

</script>

</body>
</html>