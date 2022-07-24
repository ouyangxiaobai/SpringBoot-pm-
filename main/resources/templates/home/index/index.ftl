<!DOCTYPE html>
<html>
<title>猿来入此竞拍信息 - 竞拍价格、时间 - 猿来入此</title>
<meta name="keywords" content="猿来入此竞拍系统"/>
<meta name="description" content="猿来入此竞拍系统、竞拍信息、竞拍价格、竞拍竞价时间、竞拍状态等。"/>
<!-- 新的头部 -->
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="ie-stand">
    <meta name="referrer" content="no-referrer">
    <#include "../common/head-link.ftl"/>
    <link href="/admin/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-control:focus {
            border-color: red;!important;
            outline: 0;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6)
        }
    </style>
</head>
    <#include "../common/head.ftl"/>

</div>


<div class="auction_hall_all">
    <div class="auction_hall_all_title">项目列表
        <div style="float: right">
            <a href="/home/bidding/transaction">
                更多
                <img src="/home/images/more.png" style="width: 12px;" alt="more"/>
            </a>
        </div>
    </div>
</div>
<!-- 项目展示 -->
<div class="content_display" style="height:800px;">
    <#if projectList?size gt 0>
        <#list projectList as project>
            <div class="content_list_pic" style="margin-left: 18px;">
                <#list project.picture?split(";") as picture>
                    <#if picture_index==0>
                        <img class="content_pic" src="/photo/view?filename=${picture}"
                             onclick='window.location.href="/home/bidding/detail?id=${project.id}"'>
                    </#if>
                </#list>
                <div class="content_list_title">${project.title}</div>
                <div class="content_now_price">
                    <div class="content_captitle">
                        当前价
                    </div>
                    <div class="content_list_money">
                        ${project.currentPrice}
                    <span style="font-size: 12px;color:#999999;padding-left: 10px;">元</span></div>
                </div>
                <div class="content_list_time">
                    <div class="content_captitle">出&nbsp;&nbsp;&nbsp;价</div>
                    <div class="content_list_stauts">${project.auctionTimes}
                        <span style="padding-left: 10px;">次</span>
                    </div>
                </div>
                <div class="content_list_time">
                    <div class="content_captitle">交易中心</div>
                    <div class="content_list_stauts  captitle-contnt" >${project.organization.name}
                    </div>
                </div>
                <div class="content_list_time">
                    <div class="content_captitle">竞拍开始时间</div>
                    <div class="captitle-content">${project.biddingStartTime?string("yyyy-MM-dd HH-mm")}</div>
                </div>

                <div class="content_list_footer">
                    <div class="content_list_footer_list content_list_footer_list_width2 content_list_footer_list_special">
                        <img src="/home/picture/paimai.png" alt="content_list_footer_icon"/>
                        <#if project.projectStatus.getCode() == 3>
                        <span>公示中</span>
                        <#elseif project.projectStatus.getCode() == 4>
                        <span>竞价中</span>
                        <#elseif project.projectStatus.getCode() == 5>
                        <span>竞价成功</span>
                        <#elseif project.projectStatus.getCode() == 6>
                        <span>竞价结束</span>
                        <#elseif project.projectStatus.getCode() == 10>
                        <span>报名中</span>
                        <#elseif project.projectStatus.getCode() == 11>
                        <span>即将开始竞拍</span>
                        <#else>
                        <span>已成交</span>
                        </#if>
                    </div>

                        <#--<div class="content_list_footer_list content_list_footer_list_width content_list_footer_list_special">
                            <img src="picture/content_status.png" alt="content_list_footer_icon"/>

                        </div>

                    <div class="content_list_footer_list content_list_footer_list_width">
                        <img src="picture/paimai.png" alt="content_list_footer_icon"/>

                    </div>-->
                    <#--{{/if}}
                    {{if value.statusFlag!=3 && value.statusFlag != 4}}
                    <div class="content_list_footer_list content_list_footer_list_width2 content_list_footer_list_special">
                        <img src="picture/paimai.png" alt="content_list_footer_icon">
                        {{if value.status==1}}
                        <span>公示中</span>
                        {{/if}}
                        {{if value.status==3}}
                        <span>竞价成功</span>
                        {{/if}}
                        {{if value.status==4}}
                        <span>竞价结束</span>
                        {{/if}}
                    </div>
                    {{/if}}-->

                    <div class="content_list_footer_list">
                        <span>浏览：${project.viewsNumber}次</span>
                    </div>
                </div>
            </div>
        </#list>
    </#if>
</div>
<!-- 引导 -->
<div class="home_guide">
    <img src="/home/images/middle_guide.png" alt="home_guide"/>
</div>

<div class="auction_hall_all">
    <div class="auction_hall_all_title">最新新闻
        <div style="float: right">
            <a href="/home/news/list">
                更多
                <img src="/home/images/more.png" style="width: 12px;" alt="more"/>
            </a>
        </div>
    </div>
</div>
<!-- 新闻展示 -->
<div class="content_display" style="height:400px;">
    <#if newsList?size gt 0>
        <#list newsList as news>
            <div class="content_list_pic" style="margin-left: 18px; height: 200px;">
                <img class="content_pic" src="/photo/view?filename=${news.picture}"
                     onclick='window.location.href="/home/news/detail?id=${news.id}"'>
                <div class="content_list_title">${news.caption}</div>

            </div>
        </#list>
    </#if>
</div>

    <#include "../common/foot.ftl"/>

</body>
</html>
<script src="/home/js/bootstrap-paginator.min.js"></script>
<script src="/home/distpicker/js/distpicker.data.js"></script>
<script src="/home/distpicker/js/distpicker.js"></script>
<script src="/home/distpicker/js/main.js"></script>
<script>

</script>