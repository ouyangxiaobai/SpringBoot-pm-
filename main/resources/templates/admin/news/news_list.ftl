<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|新闻管理-${title!""}</title>
<#include "../common/header.ftl"/>
    <style>
        td {
            vertical-align: middle;
        }

        p img {
            width: 60px;
            height: 60px;
        }

        .content-picture {
            width: 200px;
            height: 200px;
        }

        .jconfirm-box-container {
            width: 700px;
        }

        ;
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
                                                <li><a tabindex="-1" href="javascript:void(0)"
                                                       data-field="caption">标题</a></li>
                                            </ul>
                                        </div>
                                        <input type="text" class="form-control" value="${caption!""}" name="caption"
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
                                            <th>标题</th>
                                            <th>封面图片</th>
                                            <th>所属类型</th>
                                            <th>来源</th>
                                            <th>添加时间</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                     <#if pageBean.content?size gt 0>
                      <#list pageBean.content as news>
                      <tr>
                          <td style="vertical-align:middle;">
                              <label class="lyear-checkbox checkbox-primary">
                                  <input type="checkbox" name="ids[]" value="${news.id}"><span></span>
                              </label>
                          </td>
                          <td style="vertical-align:middle;">${news.caption}</td>
                          <td style="vertical-align:middle;">
                        	<#if news.picture??>
                        		<#if news.picture?length gt 0>
                        		    <img src="/photo/view?filename=${news.picture}" width="60px" height="60px">
                                <#else>
                        		    <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                                </#if>
                            <#else>
                                  <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                            </#if>
                          </td>
                          <td style="vertical-align:middle;">
                              ${news.newsType.name}
                          </td>
                          <td style="vertical-align:middle;">
                              ${news.source}
                          </td>
                          <td style="vertical-align:middle;" style="vertical-align:middle;"><font
                                  class="text-success">${news.createTime}</font>
                          </td>
                          <td>
                              <a class="btn btn-success m-r-5 view-content" href="javascript:void(0);"><i
                                      class="mdi mdi-check"></i>查看内容</a>
                          </td>
                          <td style="display: none" class="news-content">
                              ${news.content}
                          </td>
                      </tr>
                      </#list>
                     <#else>
                    <tr align="center">
                        <td colspan="8">这里空空如也！</td>
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
                  <li><a href="list?caption=${caption!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                      <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                      <#else>
                  <li><a href="list?caption=${caption!""}&currentPage=${showPage}">${showPage}</a></li>
                      </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?caption=${caption!""}&currentPage=${pageBean.totalPage}">»</a></li>
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

    $('.view-content').on('click', function () {
        $.alert({
            title: '查看新闻内容',
            content: $(this).parent().next().html(),
            buttons: {
                confirm: {
                    text: '确认',
                    btnClass: 'btn-primary',
                    action: function () {

                    }
                },
            }
        });
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
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条数据进行编辑！');
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
                    showSuccessMsg('新闻删除成功!', function () {
                        $("input[type='checkbox']:checked").parents("tr").remove();
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