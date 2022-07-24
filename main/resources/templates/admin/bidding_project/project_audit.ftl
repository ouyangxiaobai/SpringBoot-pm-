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

        .project-th {
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
                                <form class="pull-right search-bar" method="get" action="project_audit" role="form">
                                    <div class="input-group">
                                        <div class="input-group-btn">
                                            <button class="btn btn-default dropdown-toggle" id="search-btn"
                                                    data-toggle="dropdown" type="button" aria-haspopup="true"
                                                    aria-expanded="false">
                                                项目名称 <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li><a tabindex="-1" href="javascript:void(0)" data-field="title">项目名称</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <input type="text" class="form-control" value="${titles!""}" name="title"
                                               placeholder="请输入项目名称">
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
                                            <th>发布机构</th>
                                            <th>项目编号</th>
                                            <th>项目名称</th>
                                            <th>报名开始时间</th>
                                            <th>竞价开始时间</th>
                                            <th>起拍价</th>
                                            <th>当前价</th>
                                            <th>保证金</th>
                                            <th>状态</th>
                                            <th>添加时间</th>
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
                          <td style="vertical-align:middle;">${biddingProject.organization.name}</td>
                          <td style="vertical-align:middle;">${biddingProject.projectNumber}</td>
                          <td style="vertical-align:middle;">${biddingProject.title}</td>
                          <td style="vertical-align:middle;">${biddingProject.startTime}</td>
                          <td style="vertical-align:middle;">${biddingProject.biddingStartTime}</td>
                          <td style="vertical-align:middle;">${biddingProject.startPrice}</td>
                          <td style="vertical-align:middle;">${biddingProject.currentPrice}</td>
                          <td style="vertical-align:middle;">${biddingProject.bond}</td>
                          <td style="vertical-align:middle;color: #0F910F">${biddingProject.projectStatus.getValue()}</td>
                          <td style="vertical-align:middle;">${biddingProject.createTime}</td>
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
                  <li><a href="project_audit?title=${titles!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                      <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                      <#else>
                  <li><a href="project_audit?title=${titles!""}&currentPage=${showPage}">${showPage}</a></li>
                      </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="project_audit?title=${titles!""}&currentPage=${pageBean.totalPage}">»</a></li>
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
<div class="modal" id="viewProjectDetail" tabindex="-1" role="dialog" aria-labelledby="viewRegistratition">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:1000px;border: 1px solid #b1acac;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">项目详情</h4>
            </div>
            <div class="projet-body" id="projet-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
    //查看项目详情
    function viewProject(url) {
        var checked = $("input[type='checkbox']:checked");
        if (checked.length != 1) {
            showWarningMsg('请选择一条数据进行查看详情！');
            return;
        }
        $.get("project_detail", {id: checked.val()}, function (result) {
            $(".projet-body").empty();
            $(".projet-body").html(result);
            $('#viewProjectDetail').modal('show');
        });
    }

    //审核
    function approved(url) {
        var checked = $("input[type='checkbox']:checked");
        if (checked.length != 1) {
            showWarningMsg('请选择一条数据进行审核！');
            return;
        }
        $.confirm({
            title:"项目审核",
            content: '' +
            '<form action="" class="formName">' +
            '<div class="form-group">' +
            '<label>(选填)具体理由</label>' +
            '<textarea type="text"  placeholder="请填写具体理由" class="form-control notPassReason" style="height:120px;"></textarea>' +
            '</div>' +
            '</form>',
            buttons: {
                formSubmit: {
                    text: '提交',
                    btnClass: 'btn-blue',
                    action: function () {
                        var reason = this.$content.find('.notPassReason').val();
                        var uri = url + "&biddingProject.id=" + checked.val()+"&reason="+reason;
                        ajaxRequest(uri, "POST", {}, function (result) {
                            if(result.code==0){
                                //表示成功
                                showSuccessMsg('审核成功!', function () {
                                    window.location.href = 'project_audit';
                                })
                            }else{
                                showErrorMsg(result.msg);
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消'
                }
            }
        });

    }

</script>
</body>
</html>