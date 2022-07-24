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
            <!-- 项目报名 -->
            <div class="">
                <form id="search-form-projectSign">
                    <div class="per_edit_search">
                        <div class="per_edit_list">
                            <div class="per_edit_label2">项目状态</div>
                            <select name='status' id="status">
                                <option value="-1">全部</option>
                                <#list biddingStatusList as biddingStatus>
                                    <option value="${biddingStatus.code}" <#if biddingStatus.code == projectTimeVO.status>selected</#if>>${biddingStatus.value}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="per_edit_list per_edit_list2">
                            <div class="per_edit_label2">报名时间</div>
                            <input class="per_data_input5" type="text" placeholder="请选择日期" name="startTime" id="startTime">
                            <img class="per_data_icon1" src="/home/images/data_input.png" alt="data_input"/>

                            <div class="per_disblock">到</div>

                            <input type="text" class="per_data_input6" placeholder="请选择日期" name="endTime" id="endTime">
                            <img class="per_data_icon2" src="/home/images/data_input.png" alt="data_input"/>
                        </div>

                        <div class="per_edit_list per_edit_list2">
                            <div class="per_edit_label2">竞价时间</div>
                            <input class="per_data_input7" type="text" placeholder="请选择日期" name="biddingStartTime" id="biddingStartTime">
                            <img class="per_data_icon1" src="/home/images/data_input.png" alt="data_input"/>

                            <div class="per_disblock">到</div>

                            <input type="text" class="per_data_input8" placeholder="请选择日期" name="biddingEndTime" id="biddingEndTime">
                            <img class="per_data_icon2" src="/home/images/data_input.png" alt="data_input"/>
                            <div class="per_edit_list_btn" id="projectSignQuery" onclick="searchBidding()">查找</div>
                        </div>
                    </div>
                </form>

                <div class="per_table">
                    <table border="1" borderColor="#ECE3E3">
                        <thead>
                        <tr style="background: #FFF1EC;">
                            <td>项目编号</td>
                            <td>项目标题</td>
                            <td>报名开始时间</td>
                            <td>竞价开始时间</td>
                            <td>项目状态</td>
                            <td>保证金</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageBean.content?size gt 0>
                            <#list pageBean.content as biddingApply>
                                <tr>
                                    <td style="vertical-align:middle;">${biddingApply.biddingProject.projectNumber}</td>
                                    <td style="vertical-align:middle;"><a href="/home/bidding/detail?id=${biddingApply.biddingProject.id}">
                                            <#if biddingApply.biddingProject.title?length gt 4>
                                                ${biddingApply.biddingProject.title?substring(0,4)}...
                                            <#else>
                                                ${biddingApply.biddingProject.title}
                                            </#if>
                                        </a></td>
                                    <td style="vertical-align:middle;">${biddingApply.biddingProject.startTime}</td>
                                    <td style="vertical-align:middle;">${biddingApply.biddingProject.biddingStartTime}</td>
                                    <td style="vertical-align:middle;">
                                        <#if biddingApply.biddingProject.projectStatus.getCode() == 3>
                                            公示中
                                        <#elseif biddingApply.biddingProject.projectStatus.getCode() == 4>
                                            竞价中
                                        <#elseif biddingApply.biddingProject.projectStatus.getCode() == 5>
                                            竞价成功
                                        <#elseif biddingApply.biddingProject.projectStatus.getCode() == 6>
                                            竞价结束
                                        <#else>
                                            已成交
                                        </#if>
                                    </td>
                                    <td style="vertical-align:middle;">
                                        <#if biddingApply.status == 1>
                                            未返还
                                        <#elseif biddingApply.status == 2>
                                            已返还
                                        <#else>
                                            违约罚没
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
                                <li><a href="apply?status=${projectTimeVO.status}&startTime=${projectTimeVO.startTime!""}&endTime=${projectTimeVO.endTime!""}&biddingStartTime=${projectTimeVO.biddingStartTime!""}&biddingEndTime=${projectTimeVO.biddingEndTime!""}&currentPage=1">«</a></li>
                            </#if>
                            <#list pageBean.currentShowPage as showPage>
                                <#if pageBean.currentPage == showPage>
                                    <li class="active"><span>${showPage}</span></li>
                                <#else>
                                    <li><a href="apply?status=${projectTimeVO.status}&startTime=${projectTimeVO.startTime!""}&endTime=${projectTimeVO.endTime!""}&biddingStartTime=${projectTimeVO.biddingStartTime!""}&biddingEndTime=${projectTimeVO.biddingEndTime!""}&currentPage=${showPage}">${showPage}</a></li>
                                </#if>
                            </#list>
                            <#if pageBean.currentPage == pageBean.totalPage>
                                <li class="disabled"><span>»</span></li>
                            <#else>
                                <li><a href="apply?status=${projectTimeVO.status}&startTime=${projectTimeVO.startTime!""}&endTime=${projectTimeVO.endTime!""}&biddingStartTime=${projectTimeVO.biddingStartTime!""}&biddingEndTime=${projectTimeVO.biddingEndTime!""}&currentPage=${pageBean.totalPage}">»</a></li>
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

    layui.use('laydate', function() {
        var laydate = layui.laydate;
        //执行一个laydate实例
        var cartimeDate2 = laydate.render({
            elem: '.per_data_input5', //指定元素
            value: '${projectTimeVO.startTime!""}',
            <#if projectTimeVO.endTime??>
            <#if projectTimeVO.endTime?length gt 0>
            max: "new Date('${projectTimeVO.endTime!""}'.replace(/-/g, '/'))",
            </#if>
            </#if>
            done: function (value, date) {
                if (value !== '') {
                    date.month = date.month - 1;
                    returntimeDate2.config.min = date;
                }
            }
        });
        var returntimeDate2 = laydate.render({
            elem: '.per_data_input6', //指定元素
            value: '${projectTimeVO.endTime!""}',
            <#if projectTimeVO.startTime??>
            <#if projectTimeVO.startTime?length gt 0>
            min: "new Date('${projectTimeVO.startTime!""}'.replace(/-/g, '/'))",
            </#if>
            </#if>
            done: function (value, date) {
                if (value !== '') {
                    date.month = date.month - 1;
                    cartimeDate2.config.max = date;
                }
            }
        });
        var cartimeDate3 = laydate.render({
            elem: '.per_data_input7', //指定元素
            value: '${projectTimeVO.biddingStartTime!""}',
            <#if projectTimeVO.biddingEndTime??>
            <#if projectTimeVO.biddingEndTime?length gt 0>
            max: "new Date('${projectTimeVO.biddingEndTime!""}'.replace(/-/g, '/'))",
            </#if>
            </#if>
            done: function (value, date) {
                if (value !== '') {
                    date.month = date.month - 1;
                    returntimeDate3.config.min = date;
                }
            }
        });
        var returntimeDate3 = laydate.render({
            elem: '.per_data_input8', //指定元素
            value: '${projectTimeVO.biddingEndTime!""}',
            <#if projectTimeVO.biddingStartTime??>
            <#if projectTimeVO.biddingStartTime?length gt 0>
            min: "new Date('${projectTimeVO.biddingStartTime!""}'.replace(/-/g, '/'))",
            </#if>
            </#if>
            done: function (value, date) {
                if (value !== '') {
                    date.month = date.month - 1;
                    cartimeDate3.config.max = date;
                }
            }
        });
    })

    function searchBidding() {
        var data = $("#search-form-projectSign").serialize()
        window.location.href="/home/bidding/apply?"+data
    }
</script>
<#include "../common/user-updatePic-script.ftl"/>

</body>
</html>