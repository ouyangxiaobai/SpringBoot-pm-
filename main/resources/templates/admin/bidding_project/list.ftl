<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|竞拍管理-${title!""}</title>
<#include "../common/header.ftl"/>
    <style>
        td {
            vertical-align: middle;
        }
    </style>
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}"
                                             alt="${siteName!""}"/></a>
            </div>
            <div class="lyear-layout-sidebar-scroll">
        <#include "../common/left-menu.ftl"/>
            </div>

        </aside>
        <!--End 左侧导航-->
    
    <#include "../common/header-menu.ftl"/>

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-toolbar clearfix">
                                <form class="pull-right search-bar" method="get" action="list" role="form">
                                    <div class="input-group">
                                        <div class="input-group-btn">
                                            <button class="btn btn-default dropdown-toggle" id="search-btn"
                                                    data-toggle="dropdown" type="button" aria-haspopup="true"
                                                    aria-expanded="false">
                                                标题 <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li><a tabindex="-1" href="javascript:void(0)" data-field="title">标题</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <input type="text" class="form-control" value="${titles!""}" name="title"
                                               placeholder="请输入标题">
                                        <span class="input-group-btn">
                      <button class="btn btn-primary" type="submit">搜索</button>
                    </span>
                                    </div>
                                </form>
                <#include "../common/third-menu.ftl"/>
                            </div>
                            <div class="card-body">

                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>
                                                <label class="lyear-checkbox checkbox-primary">
                                                    <input type="checkbox" id="check-all"><span></span>
                                                </label>
                                            </th>
                                            <th>项目编号</th>
                                            <th>项目名称</th>
                                            <th>报名开始时间</th>
                                            <th>竞价开始时间</th>
                                            <th>起拍价</th>
                                            <th>当前价</th>
                                            <th>保证金</th>
                                            <th>状态</th>
                                            <th>添加时间</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as biddingProject>
                      <tr>
                          <td style="vertical-align:middle;">
                              <label class="lyear-checkbox checkbox-primary">
                                  <input type="checkbox" name="ids[]" value="${biddingProject.id}"
                                         data-status="${biddingProject.projectStatus.getCode()}"><span></span>
                              </label>
                          </td>
                          <td style="vertical-align:middle;">${biddingProject.projectNumber}</td>
                          <td style="vertical-align:middle;">${biddingProject.title}</td>
                          <td style="vertical-align:middle;">${biddingProject.startTime}</td>
                          <td style="vertical-align:middle;">${biddingProject.biddingStartTime}</td>
                          <td style="vertical-align:middle;">${biddingProject.startPrice}</td>
                          <td style="vertical-align:middle;">${biddingProject.currentPrice}</td>
                          <td style="vertical-align:middle;">${biddingProject.bond}</td>
                          <td style="vertical-align:middle;color: #0F910F">${biddingProject.projectStatus.getValue()}</td>
                          <td style="vertical-align:middle;">${biddingProject.createTime}</td>
                          <td>
                              <#if biddingProject.projectStatus.getCode()==0>
                                  <button class="btn btn-danger btn-w-md" type="button" data-id="${biddingProject.id}"
                                          onclick="releaseProject(this)">上架
                                  </button>
                              <#else>
                              <button class="btn btn-danger btn-w-md" type="button" disabled>上架</button>
                                  <#if biddingProject.projectStatus.getCode()==1 || biddingProject.projectStatus.getCode()==3>
                                       <button class="btn btn-danger btn-w-md" type="button"
                                               data-id="${biddingProject.id}" onclick="shelf(this)">下架
                                       </button>
                                  </#if>
                                  <#if biddingProject.projectStatus.getCode()==2>
                                    <button class="btn btn-cyan btn-w-md" type="button" data-reason="${biddingProject.reason}" onclick="viewReason(this)">查看拒绝原因</button>
                                  </#if>
                              </#if>
                              <#if biddingProject.projectStatus.getCode() == 5>
                                    <button class="btn btn-default btn-w-md" onclick="setOverdue(${biddingProject.id})">
                                        设置逾期
                                    </button>
                              </#if>
                          </td>
                      </tr>
                      </#list>
                      <#else>
                    <tr align="center">
                        <td colspan="15">这里空空如也！</td>
                    </tr>
                      </#if>
                                        </tbody>
                                    </table>
                                </div>
                <#if pageBean.total gt 0>
                <ul class="pagination ">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="list?title=${titles!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                      <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                      <#else>
                  <li><a href="list?title=${titles!""}&currentPage=${showPage}">${showPage}</a></li>
                      </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?title=${titles!""}&currentPage=${pageBean.totalPage}">»</a></li>
                  </#if>
                    <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                </ul>
                </#if>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {

    });

    function del(url) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条数据进行删除！');
            return;
        }
        var id = $("input[type='checkbox']:checked").val();
        $.confirm({
            title: '确定删除？',
            content: '删除后数据不可恢复，请慎重！',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        deleteReq(id, url);
                    }
                },
                cancel: {
                    text: '关闭',
                    action: function () {

                    }
                }
            }
        });
    }

    //打开编辑页面
    function edit(url) {

        var checked = $("input[type='checkbox']:checked");
        if (checked.length != 1) {
            showWarningMsg('请选择一条数据进行编辑！');
            return;
        }
        var status=$(checked).attr("data-status");
        if (status != "0" && status != "2" &&status !="9") {
            showWarningMsg('当前项目状态不可编辑！');
            return;
        }
        window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
    }

    //调用删除方法
    function deleteReq(id, url) {
        $.ajax({
            url: url,
            type: 'POST',
            data: {id: id},
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    showSuccessMsg('竞拍删除成功!', function () {
                        $("input[type='checkbox']:checked").parents("tr").remove();
                        location.reload();
                    })
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        });
    }

    //上架项目
    function releaseProject(t) {
        var id = $(t).attr("data-id");
        ajaxRequest("update_status", "POST", {id: id}, function (result) {
            if (result.code == 0) {
                showSuccessMsg('上架成功,等待管理员审核!', function () {
                    window.location.href = 'list';
                })
            } else {
                showErrorMsg(data.msg);
            }
        });
    }

    //下架
    function shelf(t) {
        var id = $(t).attr("data-id");
        ajaxRequest("/admin/bidding/frame", 'POST', {type: 'shelf', id: id}, function (result) {
            if (result.code == 0) {
                location.href = "list";
            } else {
                showErrorMsg(result.msg);
            }
        });
    }

    //上架
    function shelves(t){
        var id = $(t).attr("data-id");
        ajaxRequest("/admin/bidding/frame", 'POST', {type: 'shelves', id: id}, function (result) {
            if(result.code==0){
                location.href="list";
            }else{
                showErrorMsg(result.msg);
            }
        });
    }

    //查看拒绝原因
    function viewReason(t){
       var reason=$(t).attr("data-reason");
        $.alert({
            title: '查看拒绝原因',
            content: reason,
            buttons: {
                cancel: {
                    text: '关闭',
                    action: function () {
                    }
                }
            }
        });
    }

    function showMsg(url)
    {
        var checked = $("input[type='checkbox']:checked");
        if (checked.length != 1) {
            showWarningMsg('请选择一条数据进行查看留言消息！');
            return;
        }

        window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
    }

    function setOverdue( id )
    {
        $.confirm({
            title: '确定是否设置逾期？',
            content: '设置逾期之后不可恢复',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        overdue( id )
                    }
                },
                cancel: {
                    text: '关闭',
                    action: function () {

                    }
                }
            }
        });
    }

    function overdue( id )
    {
        $.ajax({
            url: 'set_overdue',
            type: 'POST',
            data: {id: id},
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    showSuccessMsg('设置逾期成功!', function () {
                        location.reload();
                    })
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        });
    }
</script>
</body>
</html>