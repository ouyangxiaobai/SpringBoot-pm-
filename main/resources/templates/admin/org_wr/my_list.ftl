<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|提现记录-${title!""}</title>
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
                  <form class="pull-right search-bar" method="get" action="my_list" role="form">
                      <div class="input-group">
                          <div class="input-group-btn">
                              <button class="btn btn-default dropdown-toggle" id="search-btn"
                                      data-toggle="dropdown" type="button" aria-haspopup="true"
                                      aria-expanded="false">
                                  银行卡号 <span class="caret"></span>
                              </button>
                              <ul class="dropdown-menu">
                                  <li><a tabindex="-1" href="javascript:void(0)" data-field="title">银行卡号</a>
                                  </li>
                              </ul>
                          </div>
                          <input type="text" class="form-control" value="${bankCard!""}" name="bankCard"
                                 placeholder="请输入银行卡号">
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
                        <th>银行卡号</th>
                        <th>申请金额</th>
                        <th>状态</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as with>
                      <tr>
                        <td style="vertical-align:middle;">${with.bankCard}</td>
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
                        <td style="vertical-align:middle;">
                            <#if with.status == 3>
                                <button class="btn btn-default" text="查看原因: ${with.notPassReason!'无'} " onclick="show(this)">
                                    查看
                                </button>

                                <#else>
                                 <button class="btn btn-default" disabled>
                                     查看
                                 </button>
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
                  <li><a href="my_list?bankCard=${bankCard!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                      <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                      <#else>
                  <li><a href="my_list?bankCard=${bankCard!""}&currentPage=${showPage}">${showPage}</a></li>
                      </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="my_list?bankCard=${bankCard!""}&currentPage=${pageBean.totalPage}">»</a></li>
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

<div style="display: none" class="dlg_content">
    <form action="" class="formName">
        <div class="form-group">
            <label>请选择银行卡</label>
            <select class="form-control" id="bankId">
                <option value="">请选择</option>
                <#list bankCards as item>
                    <option value="${item.id}">${item.name} -- ${item.cardNumbers}</option>
                </#list>
            </select>
        </div>
        <div class="form-group">
            <label>提现金额</label>
            <input type="number" min="0" id="number" class="form-control" placeholder="请输入提现金额" />
        </div>
    </form>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript" src="/common/js/msg.js"></script>
<script type="text/javascript">



function approval(){
  $.confirm({
      title: '请输入支付密码',
      content: '<input type="password" maxlength="6" id="payPassword" class="payPassword form-control" value="" placeholder="请输入支付密码" />',
      buttons: {
          confirm: {
              text: '确认',
              action: function(){
                  var payPassword = $("#payPassword").val();
                  if(payPassword == null || payPassword.length == 0)
                  {
                      showErrorMsg("请输入支付密码");
                      return ;
                  }

                  $.ajax({
                      url:'/admin/organization/pay_password_verify',
                      type:'POST',
                      data:{
                          password: payPassword
                      },
                      dataType:'json',
                      success:function(data){
                          if(data.code == 0){
                              toWithdraw();
                          }else{
                              showErrorMsg(data.msg);
                          }
                      },
                      error:function(data){
                          alert('网络错误!');
                      }
                  });

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

function toWithdraw()
{
  var content = $(".dlg_content").html();

  $.confirm({
      title:"提现",
      content: content,
      buttons: {
          confirm: {
              text: '确认',
              action: function(){
                  var bankId =  this.$content.find('#bankId').val();
                  var number = this.$content.find('#number').val();
                  approvalAgree(bankId, number);
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

function approvalAgree(bankId,number){

      if(msg.isEmpty(bankId))
      {
          showErrorMsg("请选择银行卡号码");
          return ;
      }

      if(msg.isEmpty(number))
      {
          showErrorMsg("请输入你要提现的金额");
          return ;
      }

      var money = Number(number);

      if(money < 1 || money > 1000000)
      {
          showErrorMsg("请输入的提现金额必须在1~100万之间")
          return ;
      }

        $.ajax({
            url:'add',
            type:'POST',
            data:{bankId:bankId, money:money},
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    showSuccessMsg("申请提现成功", function()
                    {
                        location.href = "my_list";
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

function show(thiz)
{
    var text = $(thiz).attr("text");
    $.alert({
        title: '查看未通过原因',
        content: text,
        buttons: {
            confirm: {
                text: '确认',
                btnClass: 'btn-primary',
                action: function () {

                }
            },
        }
    });
}

$(document).ready(function(){
    $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' id='file' href='javascript:approval();'><i class='mdi mdi-view-day'></i>申请提现</a>");
});
</script>
</body>
</html>