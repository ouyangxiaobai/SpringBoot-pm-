<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|留言管理-${title!""}</title>
<#include "../common/header.ftl"/>
    <style>
        td {
            vertical-align: middle;
        }

        p img {
            width: 60px;
            height: 60px;
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
                                <form class="pull-right search-bar" method="get" action="msg_list" role="form">
                                    <div class="input-group">
                                        <div class="input-group-btn">
                                            <button class="btn btn-default dropdown-toggle" id="search-btn"
                                                    data-toggle="dropdown" type="button" aria-haspopup="true"
                                                    aria-expanded="false">
                                                留言 <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li><a tabindex="-1" href="javascript:void(0)"
                                                       data-field="caption">留言</a></li>
                                            </ul>
                                        </div>
                                        <input type="hidden" id="id" name="id" value="${id}"/>
                                        <input type="text" class="form-control" value="${msg!""}" name="msg"
                                               placeholder="请输入留言">
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
                                            <th>留言用户</th>
                                            <th>留言内容</th>
                                            <th>回复内容</th>
                                            <th>回复状态</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                         <#if pageBean.content?size gt 0>
                                            <#list pageBean.content as item>
                                              <tr>
                                                  <td style="vertical-align:middle;">
                                                      <label class="lyear-checkbox checkbox-primary">
                                                          <input type="checkbox" name="ids[]" value="${item.id}"><span></span>
                                                      </label>
                                                  </td>
                                                  <td style="vertical-align:middle;">${item.biddingProject.projectNumber}</td>
                                                  <td style="vertical-align:middle;">${item.homeUser.username}</td>
                                                  <td style="vertical-align:middle;">${item.message}</td>
                                                  <td style="vertical-align:middle;">${item.reply!""}</td>
                                                  <td>
                                                      <#if item.reply??>
                                                          <font color="green">已回复</font>
                                                      <#else>
                                                           <font color="red">未回复</font>
                                                      </#if>
                                                  </td>
                                                  <td>
                                                      <#if item.reply??>
                                                          <button type="button" class="btn btn-default" disabled>
                                                              回复
                                                          </button>
                                                      <#else>
                                                          <button type="button" class="btn btn-default" onclick="reply(${item.id})">
                                                              回复
                                                          </button>
                                                      </#if>
                                                  </td>
                                              </tr>
                                          </#list>
                                         <#else>
                                            <tr align="center">
                                                <td colspan="10">这里空空如也！</td>
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
                              <li><a href="msg_list?id=${id}&msg=${msg!""}&currentPage=1">«</a></li>
                              </#if>
                              <#list pageBean.currentShowPage as showPage>
                                  <#if pageBean.currentPage == showPage>
                              <li class="active"><span>${showPage}</span></li>
                                  <#else>
                              <li><a href="msg_list?id=${id}&msg=${msg!""}&currentPage=${showPage}">${showPage}</a></li>
                                  </#if>
                              </#list>
                              <#if pageBean.currentPage == pageBean.totalPage>
                              <li class="disabled"><span>»</span></li>
                              <#else>
                              <li><a href="msg_list?id=${id}&msg=${msg!""}&currentPage=${pageBean.totalPage}">»</a></li>
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


    function reply( id )
    {
        $.confirm({
            title: '回复消息',
            content: '<textarea class="form-control" rows="5" placeholder="请输入回复" id="reply" name="reply"></textarea>',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        var reply = $("#reply").val().trim();
                        sendReply(reply, id);
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

    function sendReply(reply, id)
    {
        $.ajax({
            url: "reply",
            type:'POST',
            data:{reply:reply, id:id},
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    showSuccessMsg('回复成功!',function(){
                        window.location.reload();
                        //$("input[type='checkbox']:checked").parents("tr").remove();
                    });
                }else{
                    showErrorMsg(data.msg);
                }
            },
            error:function(data){
                alert('网络错误!');
            }
        });
    }

</script>
</body>
</html>