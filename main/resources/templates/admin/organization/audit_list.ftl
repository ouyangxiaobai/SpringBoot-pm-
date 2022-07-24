<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|我的个人信息-${title!""}</title>
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
                        <th>邮箱</th>
                        <th>手机号</th>
                        <th>机构名称</th>
                        <th>身份证图片</th>
                        <th>营业执照</th>
                        <th>审核状态</th>
                        <th>押金状态</th>
                        <th>地址</th>
                        <th>余额</th>
                        <#if item.auditStatus == 2> <th>操作</th> </#if>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td style="vertical-align:middle;">${item.email}</td>
                        <td style="vertical-align:middle;">${item.phone}</td>
                        <td style="vertical-align:middle;">${item.name}</td>
                        <td style="vertical-align:middle;">
                            <#list item.image as img>
                                <img src="/photo/view?filename=${img}" style="width: 60px; height: 60px; margin-left: 4px"/>
                            </#list>
                        </td>
                        <td style="vertical-align:middle;">
                            <#if item.tradingImg??>
                        		<#if item.tradingImg?length gt 0>
                        		    <img src="/photo/view?filename=${item.tradingImg}" width="60px" height="60px">
                                <#else>
                        		    <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                                </#if>
                            <#else>
                                 <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                            </#if>
                        </td>
                        <td style="vertical-align:middle;">
                              <#if item.auditStatus == 0>
                                  未提交
                              <#elseif item.auditStatus == 1>
                                  <font color="orange">审核中</font>
                              <#elseif  item.auditStatus == 2>
                                  <font color="red">未通过</font>
                              <#elseif item.auditStatus == 3>
                                  <font color="green">通过</font>
                              <#else>
                                  <font color="#1e90ff">冻结</font>
                              </#if>
                          </td>
                        <td style="vertical-align:middle;">
                              <#if item.earnestMoney == 0>
                                  <font color="red">未交</font>
                              <#else>
                                  <font color="green">已交</font>
                              </#if>
                          </td>
                        <td style="vertical-align:middle;">${item.address}</td>
                        <td style="vertical-align:middle;">${item.balance}</td>
                        <#if item.auditStatus == 2>
                            <td style="vertical-align:middle;">
                                <button class="btn btn-label btn-primary" text="查看原因: ${item.notPassReason!'无'} " onclick="show(this)">
                                    <label><i class="mdi mdi-checkbox-marked-circle-outline"></i></label>
                                    查看原因
                                </button>
                            </td>
                        </#if>
                      </tr>
                    </tbody>
                  </table>
                </div>
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
$(document).ready(function(){
	
});

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


//打开编辑页面
function auditEdit(url){
    var status = ${item.auditStatus};

    if(!(status == 0 || status == 2 || status == 4))
    {
        ("你不在编辑状态");
        return ;
    }

    window.location.href =  url;
}

function submitAudit(url)
{
    $.ajax({
        url:'submit_audit',
        type:'POST',
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("提交成功", function()
                {
                    window.location.href = 'audit_list';
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

function cashPledge (url)
{
    $.confirm({
        title: '支付押金100000元',
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
                        url:'pay_password_verify',
                        type:'POST',
                        data:{
                            password: payPassword
                        },
                        dataType:'json',
                        success:function(data){
                            if(data.code == 0){
                                payCashPledge();
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

function payCashPledge()
{
    $.ajax({
        url:'pay_cash_pledge',
        type:'POST',
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("押金付款成功", function()
                {
                    window.location.href = 'audit_list';
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