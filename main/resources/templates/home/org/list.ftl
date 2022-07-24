<!DOCTYPE html>
<html>
<title>机构列表</title>

<!-- 新的头部 -->
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="ie-stand">
    <meta name="referrer" content="no-referrer">
    <#include "../common/head-link.ftl"/>
    <link href="/home/layui/css/layui.css" rel="stylesheet">
    <script type="text/javascript" src="/home/layui/layui.js"></script>
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

    <div class="diqu m-b-10 layui-row">
        <div class="layui-col-md10">
            <div class="layui-form-item">
                <label class="layui-form-label">名称:</label>
                <div class="layui-input-block">
                    <input class="layui-input" placeholder="请输入机构名称进行模糊查询" id="filter" value="${name!""}"/>
                </div>
            </div>
        </div>
        <div class="layui-col-md2" style="text-align: right">
            <button type="button" class="layui-btn" onclick="search()" style="background-color: #D90C19; margin-right: 15px;">
                <i class="layui-icon layui-icon-search"></i>搜索
            </button>
        </div>
    </div>

    <div class="h10"></div>
    <div class="m-b-60">
        <div class="mechanism-list-title clearfix"><span class="pull-left text-grey-6">机构列表：</span>
            <ul>
            </ul>
            <span class="text-grey-6 pull-right">共 ${pageBean.total} 个</span>
        </div>
        <div class="mechanism-list">
            <ul>
                <#list pageBean.content as item>
                    <li>
                        <div class="li-cont w750">
                            <div class="li-txtl">
                                <div class="li-txtl-title">
                                    <h3><a href="/home/org/detail?id=${item.id}" target="_blank">${item.name}</a></h3>
                                    <span class="tags tag-blue">合作机构</span>
                                </div>
                                <div class="li-txtl-dl">
                                    <dl>
                                        <dt>地址：</dt>
                                        <dd>${item.address}</dd>
                                    </dl>
                                    <dl>
                                        <dt>电话：</dt>
                                        <dd>${item.phone}</dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                        <div class="li-txtr" style="width: 270px;">
                            <p class="text-grey-6 m-b-5"><span class="font-24 text-warning p-l-5"></span></p>
                            <p class="font-12 text-grey-9">数据来自猿礼科技</p>
                        </div>
                    </li>
                </#list>
            </ul>


        </div>


        <nav class="text-center my-paging">
            <div id="page">

            </div>
        </nav>
    </div>
</div>

    <#include "../common/foot.ftl"/>


<!-- 自定义js部分开始 -->
<script>
    $(function () {
        $('.carousel-inner .item').eq(0).addClass('active');
        $("#homePage").removeClass("head_tab_select");
        $("#mechanism").addClass("head_tab_select");
    });

    function search() {
        var filter = $("#filter").val().trim();
        location.href = "list?name=" + filter;
    }

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
                    location.href="list?currentPage="+obj.curr + "&name=${name!""}";
                }
            }
        });

    });
</script>
</body>
</html>