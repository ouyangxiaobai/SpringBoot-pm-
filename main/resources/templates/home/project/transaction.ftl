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
    <link href="/home/layui/css/layui.css" rel="stylesheet">
    <script type="text/javascript" src="/home/layui/layui.js"></script>
    <style>
        .form-control:focus {
            border-color: red;
        !important;
            outline: 0;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6)
        }

        #page .layui-laypage .layui-laypage-curr .layui-laypage-em
        {
            background-color: #D90C19;
        }

        html
        {
            overflow-x: hidden;
        }
    </style>
</head>
    <#include "../common/head.ftl"/>
<!-- 条件筛选 -->
<div class="auction_hall_condition">
    <table border="1" bordercolor="#E4E4E4" cellspacing="0" cellpadding="0">
        <tr class="auction_tr">
            <td class="auction_td">标的类型</td>
            <td>
                <div class="auction_all_right2" id="auction_all_right_standard_type">
                    <div class="auction_all_one2 project_type">
                        <div class="auction_hall_table_tab auction_hall_table_tab_select" id="standardTypeall" onclick="selectedType('')">全部</div>
                           <#list labelTypeList as labelType>
                                    <div class="auction_hall_table_tab" id="type${labelType.id}" data-id="${labelType.name}"
                                         onclick="selectedType('${labelType.id}')">${labelType.name}</div>
                           </#list>
                    </div>

                </div>
            </td>
        </tr>
        <tr class="auction_tr">
            <td class="auction_td">标的所在地</td>
            <td>
                <div class="form-group">
                    <div class="row" data-toggle="distpicker">
                        <div class="col-xs-4">
                            <select class="form-control" data-province="---- 选择省 ----" id="province"></select>
                        </div>
                        <div class="col-xs-4">
                            <select class="form-control" data-city="---- 选择市 ----" id="city"></select>
                        </div>
                        <div class="col-xs-4">
                            <select class="form-control" data-district="---- 选择区 ----" id="district"></select>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        <tr class="auction_tr" id="project_status">
            <td class="auction_td">项目状态</td>
            <td>
                <div class="auction_hall_table_tab auction_hall_table_tab_select" id="statusall"
                     onclick="selectedStatus('')">全部
                </div>

                <div class="auction_hall_table_tab" id="status3" onclick="selectedStatus('3')">公示中</div>

                <div class="auction_hall_table_tab" id="status4" onclick="selectedStatus('4')">竞价中</div>

                <div class="auction_hall_table_tab" id="status5" onclick="selectedStatus('5')">竞价成功</div>

                <div class="auction_hall_table_tab" id="status6" onclick="selectedStatus('6')">竞价结束</div>

                <div class="auction_hall_table_tab" id="status7" onclick="selectedStatus('7')">已成交</div>

            </td>
        </tr>
        <tr class="auction_tr">
            <td class="auction_td">报名时间</td>
            <td style="position: relative">
                <input type="text" class="data_input datas_input per_data_input5" placeholder="请选择日期" id="startTime" name="startTime">
                <img class="input_data_icon" src="/home/picture/data_input.png" alt="data_input"/>

                <div class="data_input_line"></div>

                <input type="text" class="data_input2 datas_input per_data_input6" placeholder="请选择日期" id="endTime" name="endTime">
                <img class="input_data_icon input_data_icon2" src="/home/picture/data_input.png" alt="data_input"/>

                <!--  <div class="input_data_btn" onclick="sign_query()">查询</div> -->
            </td>
        </tr>
        <tr class="auction_tr">
            <td class="auction_td">竞价时间</td>
            <td style="position: relative">
                <input type="text" class="data_input3 datas_input per_data_input7" placeholder="请选择日期" id="biddingStartTime" name="biddingStartTime">
                <img class="input_data_icon" src="/home/picture/data_input.png" alt="data_input"/>

                <div class="data_input_line"></div>

                <input type="text" class="data_input4 datas_input per_data_input8" placeholder="请选择日期" id="biddingEndTime" name="biddingEndTime">
                <img class="input_data_icon input_data_icon2" src="/home/picture/data_input.png" alt="data_input"/>

                <div class="input_data_btn" onclick="query()">查询</div>
            </td>
        </tr>

    </table>

</div>

<!-- 共有拍品 -->
<div class="auction_hall_all">
    <div class="auction_hall_all_title">项目列表（<span id="count_rows">${pageBean.total}</span>）</div>
</div>
<!-- 项目展示 -->
<div class="content_display">
        <#if pageBean.content??>
             <#list pageBean.content as project>
        <div class="content_list_pic" style="margin-left: 18px;">
              <#if project.projectStatus.getCode()==3>
            <div class="content_list_status_green content_list_status_start">
                公示中
            </div>
              <#elseif project.projectStatus.getCode()==4>
                   <div class="content_list_status_green content_list_status_green">
                       竞价中
                   </div>
              <#elseif project.projectStatus.getCode()==5>
                <div class="content_list_status_green content_list_status_gray">
                    竞价成功
                </div>
              <#elseif project.projectStatus.getCode()==6>
                  <div class="content_list_status_green content_list_status_gray">
                      竞价结束
                  </div>
              <#elseif project.projectStatus.getCode()==7>
               <div class="content_list_status_green content_list_status_gray">
                   已成交
               </div>
              </#if>
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
                <div class="content_list_money">${project.currentPrice}元<span
                        style="font-size: 12px;color:#999999;padding-left: 10px;">第${project.auctionTimes!0}次出价</span>
                </div>
            </div>
            <div class="content_list_time">
                <div class="content_captitle">交易中心</div>
                <div title="${project.organization.name}" class="captitle-content">${project.organization.name}</div>
            </div>
            <div class="content_list_time">
                <div class="content_captitle">开始时间</div>
                <div title="${project.biddingStartTime}" class="captitle-content">${project.biddingStartTime}</div>
            </div>
            <div class="content_list_time">
                <div class="content_captitle">结束时间</div>
                <div title="${project.biddingEndTime}" class="captitle-content">${project.biddingEndTime}</div>
            </div>
            <div class="content_list_footer">
                <div class="content_list_footer_list">
                    <span>浏览：${project.viewsNumber}人</span>
                </div>
                <div class="content_list_footer_list">
                    <span>报名人数:${project.applicantsNumber}</span>
                </div>
                <div class="content_list_footer_list" style="width: 100px;">
                    <span>加价幅度:${project.rateIncrease}元</span>
                </div>
            </div>
        </div>
             </#list>
        </#if>
</div>


<nav class="pageing-content  my-paging ">
    <ul id="pageInitialization" class="pagination-sm" style="float: left;"></ul>
    <div class="my-paging-all" style="float: left; margin-left: 15px;font-size: 14px;display:none">共<span id="max_page">0</span>页
    </div>
</nav>

<!-- 分页功能 -->
<div class="row" style="text-align: center">
    <div id="page">

    </div>
</div>

<#include "../common/foot.ftl"/>

</body>
</html>
<script src="/home/js/bootstrap-paginator.min.js"></script>
<script src="/home/distpicker/js/distpicker.data.js"></script>
<script src="/home/distpicker/js/distpicker.js"></script>
<script src="/home/distpicker/js/main.js"></script>
<script>
    var M = {};
    M.projectStatus = "${projectVo.projectStatus!""}";
    M.labelType = "${projectVo.labelType!""}";
    M.province = "${projectVo.province!""}"; //省份
    M.city = "${projectVo.city!""}"; //市
    M.area = "${projectVo.area!""}";
    M.count = ${pageBean.total};
    M.curr = ${pageBean.currentPage};
    M.pageSize = ${pageBean.pageSize};
    M.title = "${projectVo.title!""}";

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

                    location.href = "transaction?projectStatus="
                            + M.projectStatus + "&province="
                            + M.province + "&city="
                            + M.city + "&area="
                            + M.area + "&labelType="
                            + M.labelType + "&startTime=${projectVo.startTime!""}&endTime=${projectVo.endTime!""}&biddingStartTime=${projectVo.biddingStartTime!""}&title=${projectVo.title!""}&biddingEndTime=${projectVo.biddingEndTime!""}&currentPage=" + M.curr;
                }
            }
        });
    });

    $(function()
    {
        //初始化
       M.init = function()
       {
           if(M.province == "")
           {
               $("#province").val(null);
               $("#province").trigger("change");
           }
           else
           {
               $("#province").val(M.province);
               $("#province").trigger("change");
               $("#city").val(M.city);
               $("#city").trigger("change");
               $("#district").val(M.area);
               $("#district").trigger("change");
           }

           $("#search_value").val(M.title);

           selectedStatus(M.projectStatus);
           selectedType(M.labelType);
       }();
    });

    //设置状态点击
    function selectedStatus( id )
    {
        M.projectStatus = id;
        $("#project_status .auction_hall_table_tab_select").removeClass("auction_hall_table_tab_select");
        if(M.projectStatus == "")
        {
            $("#project_status #statusall").addClass("auction_hall_table_tab_select");
        }
        else
        {
            $("#project_status #status" + M.projectStatus).addClass("auction_hall_table_tab_select");
        }
    }

    //设置类型点击
    function selectedType( id )
    {
        M.labelType = id;
        $(".project_type .auction_hall_table_tab_select").removeClass("auction_hall_table_tab_select");
        if(M.labelType == "")
        {
            //设置高亮
            $(".project_type #standardTypeall").addClass("auction_hall_table_tab_select");
        }
        else
        {
            $(".project_type #type" + M.labelType).addClass("auction_hall_table_tab_select");
        }
    }


    function query()
    {
        M.province = $("#province").val().trim();
        M.city = $("#city").val().trim();
        M.area = $("#district").val().trim();

        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var biddingStartTime = $("#biddingStartTime").val();
        var biddingEndTime = $("#biddingEndTime").val();


        location.href = "transaction?projectStatus="
                + M.projectStatus + "&province="
                + M.province + "&city="
                + M.city + "&area="
                + M.area + "&labelType="
                + M.labelType + "&startTime="
                + startTime + "&endTime="
                + endTime + "&biddingStartTime="
                + biddingStartTime + "&biddingEndTime="
                + biddingEndTime;
    }

</script>
<script>
    layui.use('laydate', function() {
        var laydate = layui.laydate;

        //执行一个laydate实例
        var cartimeDate2 = laydate.render({
            elem: '.per_data_input5', //指定元素
            value: '${projectVo.startTime!""}',
            <#if projectVo.endTime??>
                <#if projectVo.endTime?length gt 0>
            max: "new Date('${projectVo.endTime!""}'.replace(/-/g, '/'))",
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
            value: '${projectVo.endTime!""}',
            <#if projectVo.startTime??>
                <#if projectVo.startTime?length gt 0>
            min: "new Date('${projectVo.startTime!""}'.replace(/-/g, '/'))",
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
            value: '${projectVo.biddingStartTime!""}',
            <#if projectVo.biddingEndTime??>
                <#if projectVo.biddingEndTime?length gt 0>
            max: "new Date('${projectVo.biddingEndTime!""}'.replace(/-/g, '/'))",
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
            value: '${projectVo.biddingEndTime!""}',
            <#if projectVo.biddingStartTime??>
                <#if projectVo.biddingEndTime?length gt 0>
            min: "new Date('${projectVo.biddingStartTime!""}'.replace(/-/g, '/'))",
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
</script>