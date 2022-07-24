<!DOCTYPE html>
<html>
<title>通知列表</title>

<!-- 新的头部 -->
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="ie-stand">
    <meta name="referrer" content="no-referrer">
    <#include "../common/head-link.ftl"/>
    <link href="/admin/css/bootstrap.min.css" rel="stylesheet">

    <style type="text/css">

        #page .layui-laypage .layui-laypage-curr .layui-laypage-em
        {
            background-color: #D90C19;
        }
    </style>

</head>
<body>

<#include "../common/head.ftl"/>

<div class="container">
    <div class="h30"></div>
    <div class="clearfix m-b-60">
        <div class="w780 pull-left">
            <!-- 地区 -->
            <div class="diqu m-b-10">
                <dl class="clearfix">
                    <dt>地区</dt>
                    <dd>
                        <div class="form-group">
                            <div class="row"  data-toggle="distpicker">
                                <div class="col-xs-3">
                                    <select  class="form-control province" id="province" data-province="---- 全部 ----"></select>
                                </div>
                                <div class="col-xs-3">
                                    <select  class="form-control city"     id="city" data-city="---- 全部 ----"></select>
                                </div>
                                <div class="col-xs-3">
                                    <select  class="form-control county"   id="county" data-district="---- 全部 ----"></select>
                                </div>
                                <div class="col-xs-2">
                                    <button class="btn btn-default" onclick="query()" type="button">
                                        查询
                                    </button>
                                </div>
                            </div>
                        </div>
                    </dd>
                </dl>
            </div>
            <!-- 地区 -->

            <div class="tongzhi-list">
                <div class="tab-content">
                    <!-- njbd -->
                    <div id="zcjd" role="tabpanel" class="tab-pane active">
                        <div class="p-t-10">
                            <ul id="nongjiaozcTemplate" class="txt-ul">
                                <#list pageBean.content  as item>
                                    <li><a href="/home/inform/detail?id=${item.id}">
                                    ${item.caption}
                                    </a>${item.createTime?string('yyyy-MM-dd')}</li>
                                </#list>
                            </ul>

                            <nav class="text-center my-paging">
                                <div id="page">

                                </div>
                            </nav>
                        </div>
                    </div>
                    <!-- njbd -->
                </div>
            </div>
        </div>

        <link href="/home/layui/css/layui.css" rel="stylesheet">
        <script type="text/javascript" src="/home/layui/layui.js"></script>
    </div>
</div>

<#include "../common/foot.ftl"/>

<script src="/home/js/bootstrap-paginator.min.js"></script>
<script src="/home/distpicker/js/distpicker.data.js"></script>
<script src="/home/distpicker/js/distpicker.js"></script>
<script src="/home/distpicker/js/main.js"></script>


<!-- 自定义js部分开始 -->
<script id="information" type="text/javascript">
    $(function () {
        $(".head_tab_select").removeClass("head_tab_select");
        $("#announcements").addClass("head_tab_select");
    });

    var M = {};
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
                    location.href="list?currentPage="+obj.curr + "&province=${province!""}&county=${county!""}&city=${city!""}";
                }
            }
        });
    });

</script>

<!-- 自定义js部分结束 -->
<script>
    var province = '${province!""}';
    function query()
    {
        var provinceVal = $("#province").val();
        var city = $("#city").val();
        var county = $("#county").val();

        location.href = "list?province="+provinceVal+"&county=" + county + "&city=" + city;
    }

    $(function()
    {
        if(province == "")
        {
            $("#province").val(null);
            $("#province").trigger("change");

            $("#city").val(null);
            $("#city").trigger("change");

            $("#county").val(null);
            $("#county").trigger("change");
        }
        else
        {
            $("#province").val("${province!""}");
            $("#province").trigger("change");
            $("#city").val("${city!""}");
            $("#city").trigger("change");
            $("#county").val("${county!""}");
            $("#county").trigger("change");
        }
    });
</script>
</body>
</html>