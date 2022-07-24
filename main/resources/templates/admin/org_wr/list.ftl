<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|提现申请管理-${title!""}</title>
<#include "../common/header.ftl"/>
<style>
td{
	vertical-align:middle;
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
        <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}" alt="${siteName!""}" /></a>
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
                <#include "../common/third-menu.ftl"/>
              </div>
              <div class="card-body">
                
                <div class="table-responsive">
                  <table class="table table-bordered">
                    <thead>
                      <tr>
                        <th>申请机构</th>
                        <th>机构邮箱</th>
                        <th>机构电话</th>
                        <th>申请金额</th>
                        <th>状态</th>
                        <th>申请时间</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as with>
                      <tr>
                        <td style="vertical-align:middle;">${with.organization.name}</td>
                        <td style="vertical-align:middle;">${with.organization.email}</td>
                        <td style="vertical-align:middle;">${with.organization.phone}</td>
                        <td style="vertical-align:middle;">${with.money}</td>
                        <td style="vertical-align:middle;">
                            <#if with.status == 1>
                                <font color="orange">审核</font>
                            <#elseif with.status == 2>
                                <font color="green">通过</font>
                            <#else>
                                <font color="red">未通过</font>
                            </#if>
                        </td>
                        <td style="vertical-align:middle;"><font class="text-success">${with.createTime}</font></td>
                        <td style="vertical-align:middle; width: 20%">
                            <#if with.status == 1>
                                <button class="btn btn-label btn-primary" onclick="approvalAgree(${with.id},true)"><label><i class="mdi mdi-checkbox-marked-circle-outline"></i></label> 同意</button>
                                <button class="btn btn-label btn-danger" onclick="approval(${with.id},false)"><label><i class="mdi mdi-close"></i></label> 拒绝</button>
                            <#else>
                                <button class="btn btn-label btn-primary" disabled><label><i class="mdi mdi-checkbox-marked-circle-outline"></i></label> 同意</button>
                                <button class="btn btn-label btn-danger" disabled><label><i class="mdi mdi-close"></i></label> 拒绝</button>
                            </#if>
                        </td>
                      </tr>
                    </#list>
                    <#else>
                    <tr align="center"><td colspan="9">这里空空如也！</td></tr>
					</#if>
                    </tbody>
                  </table>
                </div>
                <#if pageBean.total gt 0>
                <ul class="pagination ">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="list?currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                  <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                  <#else>
                  <li><a href="list?currentPage=${showPage}">${showPage}</a></li>
                  </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?currentPage=${pageBean.totalPage}">»</a></li>
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

  var notPassReason = '';

  function approval(id,flag){
    $.confirm({
      title:"提现审核",
      content: '' +
              '<form action="" class="formName">' +
              '<div class="form-group">' +
              '<label>请填写具体理由</label>' +
              '<textarea type="text"  placeholder="请填写具体理由" class="form-control notPassReason" style="height:120px;"></textarea>' +
              '</div>' +
              '</form>',
      buttons: {
        confirm: {
          text: '确认',
          action: function(){
            notPassReason = this.$content.find('.notPassReason').val();
            approvalAgree(id,flag);
          }
        },
        cancel: {
          text: '关闭',
          action: function(){

          }
        }
      }
    });
  }

function approvalAgree(id,flag){
  $.ajax({
    url:'approval',
    type:'post',
    data:{
      id:id,
      flag:flag,
      notPassReason:notPassReason
    },
    dataType:'json',
    success:function (data) {
      if(data.code == 0){
        showSuccessMsg("审批成功",function () {
          	window.location.href = 'list';
        })
      }else{
        showErrorMsg(data.msg)
      }
    },
    error:function (data) {
      alert("网络错误")
    }
  })
}

$(document).ready(function(){
	
});
</script>
</body>
</html>