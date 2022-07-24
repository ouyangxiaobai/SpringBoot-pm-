<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|我的个人信息</title>
<#include "../common/header.ftl"/>

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
              <div class="card-header"><h4>我的个人信息</h4></div>
              <div class="card-body">
                <form id="user-add-form"  class="row">
                  <input type="hidden" name="id" value="${item.id}">
                  <div class="form-group col-md-12">
                    <label>头像上传</label>
                    <div class="form-controls">
                      <ul class="list-inline clearfix lyear-uploads-pic">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <figure>
                              <#if item.headPic??>
                                  <#if item.headPic?length gt 0>
                    		        <img src="/photo/view?filename=${item.headPic}" id="show-picture-img">
                                  <#else>
                    		        <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                                  </#if>
                              <#else>
                                  <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                              </#if>
                          </figure>
                        </li>
                        <input type="hidden" name="headPic" id="headPic" value="${item.headPic!''}">
                        <input type="file" id="select-file" style="display:none;" onchange="upload('show-picture-img','headPic')">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <a class="pic-add" id="add-pic-btn" href="javascript:void(0)" title="点击上传"></a>
                        </li>
                      </ul>
                    </div>
                  </div>
                    <div class="form-group col-md-12">
                        <label>身份证图片</label>
                        <div class="form-controls">
                            <ul class="list-inline clearfix lyear-uploads-pic">
                                <#list item.image as img>
                                 <li class="col-xs-4 col-sm-3 col-md-2">
                                     <figure>
                                        <img src="/photo/view?filename=${img}">
                                     </figure>
                                 </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                    <div class="form-group col-md-12">
                        <label>营业执照</label>
                        <div class="form-controls">
                            <ul class="list-inline clearfix lyear-uploads-pic">
                                <li class="col-xs-4 col-sm-3 col-md-2">
                                    <figure>
                                      <#if item.tradingImg??>
                                          <#if item.tradingImg?length gt 0>
                                            <img src="/photo/view?filename=${item.tradingImg}" id="show-picture-img">
                                          <#else>
                                            <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                                          </#if>
                                      <#else>
                                          <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                                      </#if>
                                    </figure>
                                </li>
                            </ul>
                        </div>
                    </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">邮箱</span>
                    <input type="text" class="form-control required" id="email" name="email" disabled
                           value="${item.email}" placeholder="请输入邮箱" tips="请填写邮箱" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">手机号</span>
                    <input type="text" class="form-control required" id="phone" name="phone" disabled
                           value="${item.phone}" placeholder="请输入登录密码" tips="请填写登录密码" />
                  </div>

                  <div class="input-group m-b-10">
                      <span class="input-group-addon">是否支付押金</span>
                      <input type="email" class="form-control" disabled id="email" name="email" value="<#if item.earnestMoney == 1>已支付<#else>未支付</#if>" />
                  </div>
                  <div class="input-group m-b-10">
                      <span class="input-group-addon">余额</span>
                      <input type="email" class="form-control" disabled id="email" name="email" value="${item.balance}" />
                  </div>
                  <div class="input-group m-b-10">
                      <span class="input-group-addon">机构名称</span>
                      <input type="text" class="form-control" id="name" name="name" value="${item.name}" disabled/>
                  </div>
                  <div class="input-group m-b-10">
                      <span class="input-group-addon">地址</span>
                      <input type="email" class="form-control" id="address" name="address" value="${item.address}" disabled />
                  </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">联系人</span>
                        <input type="email" class="form-control" id="address" name="address" value="${item.legalPerson}" disabled />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">默认支付密码</span>
                        <input type="email" class="form-control" id="address" name="address" value="123456" disabled />
                    </div>
                  <div class="form-group col-md-12">
                    <button type="button" class="btn btn-primary ajax-post" id="add-form-submit-btn">确 定</button>
                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                  </div>
                </form>
       
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
	//提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("user-add-form")){
			return;
		}
		var data = $("#user-add-form").serialize();
		$.ajax({
			url:'info',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('保存成功!',function(){
						window.location.href = 'info';
					})
				}else{
					showErrorMsg(data.msg);
				}
			},
			error:function(data){
				alert('网络错误!');
			}
		});
	});
	//监听上传图片按钮
	$("#add-pic-btn").click(function(){
		$("#select-file").click();
	});
});
</script>
</body>
</html>