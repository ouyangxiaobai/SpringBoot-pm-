<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|竞拍管理-交易中心列表</title>
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
                                <form class="pull-right search-bar" method="get" action="trading_center" role="form">
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

                              <button class="btn btn-w-md btn-round btn-success"
                                      <#if biddingProject.projectStatus.getCode()==1>disabled</#if>
                                      data-id="${biddingProject.id}" onclick="shelves(this)">上架
                              </button>

                              <button class="btn btn-w-md btn-round btn-danger"
                                      <#if biddingProject.projectStatus.getCode()==9>disabled</#if> data-id="${biddingProject.id}" onclick="shelf(this)">下架
                              </button>
                          </td>
                      </tr>
                      </#list>
                      <#else>
                    <tr align="center">
                        <td colspan="11">这里空空如也！</td>
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
                  <li><a href="trading_center?title=${titles!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                      <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                      <#else>
                  <li><a href="trading_center?title=${titles!""}&currentPage=${showPage}">${showPage}</a></li>
                      </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="trading_center?title=${titles!""}&currentPage=${pageBean.totalPage}">»</a></li>
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
    //下架
    function shelf(t) {
        var id = $(t).attr("data-id");
        ajaxRequest("/admin/bidding/frame", 'POST', {type: 'shelf', id: id}, function (result) {
            if(result.code==0){
                location.href="trading_center";
            }else{
                showErrorMsg(result.msg);
            }
        });
    }
    //上架
    function shelves(t){
        var id = $(t).attr("data-id");
        ajaxRequest("/admin/bidding/frame", 'POST', {type: 'shelves', id: id}, function (result) {
            if(result.code==0){
                location.href="trading_center";
            }else{
                showErrorMsg(result.msg);
            }
        });
    }
</script>
</body>
</html>