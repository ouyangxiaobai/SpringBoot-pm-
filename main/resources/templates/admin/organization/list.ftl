<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|机构管理-${title!""}</title>
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
                <form class="pull-right search-bar" method="get" action="list" role="form">
                  <div class="input-group">
                    <div class="input-group-btn">
                      <button class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                      机构 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">机构</a> </li>
                      </ul>
                    </div>
                    <input type="text" class="form-control" value="${name!""}" name="name" placeholder="请输入机构名">
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
                        <th>头像</th>
                        <th>邮箱</th>
                        <th>手机号</th>
                        <th>机构名称</th>
                        <th>身份证图片</th>
                        <th>营业执照</th>
                        <th>审核状态</th>
                        <th>地址</th>
                        <th>余额</th>
                        <th>联系人</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as item>
                      <tr row-id="${item.id}" row-status="${item.auditStatus}">
                        <td style="vertical-align:middle;">
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" name="ids[]" value="${item.id}"><span></span>
                          </label>
                        </td>
                        <td style="vertical-align:middle;">
                        	<#if item.headPic??>
                        		<#if item.headPic?length gt 0>
                        		    <img src="/photo/view?filename=${item.headPic}" width="60px" height="60px">
                        		<#else>
                        		    <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                        		</#if>
                            <#else>
                                 <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                        	</#if>
                        </td>
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
                        <td style="vertical-align:middle;">${item.address}</td>
                        <td style="vertical-align:middle;">${item.balance}</td>
                        <td style="vertical-align:middle;">${item.legalPerson}</td>
                        <td style="vertical-align:middle;">
                            <#if item.auditStatus == 1>
                                <button class="btn btn-label btn-primary" onclick="approvalAgree(${item.id},true)">
                                    <label><i class="mdi mdi-checkbox-marked-circle-outline"></i></label>
                                    同意
                                </button>
                                <button class="btn btn-label btn-danger" onclick="approval(${item.id},false)">
                                    <label><i class="mdi mdi-close"></i></label>
                                    拒绝
                                </button>
                            <#else>
                                 <button class="btn btn-label btn-primary" disabled>
                                     <label><i class="mdi mdi-checkbox-marked-circle-outline"></i></label>
                                     同意
                                 </button>
                                <button class="btn btn-label btn-danger" disabled>
                                    <label><i class="mdi mdi-close"></i></label>
                                    拒绝
                                </button>
                            </#if>
                        </td>
                      </tr>
                    </#list>
                    <#else>
                    <tr align="center"><td colspan="15">这里空空如也！</td></tr>
					</#if>
                    </tbody>
                  </table>
                </div>
                <#if pageBean.total gt 0>
                <ul class="pagination ">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                  <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=${showPage}">${showPage}</a></li>
                  </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=${pageBean.totalPage}">»</a></li>
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
$(document).ready(function(){
	
});
function del(url){
	if($("input[type='checkbox']:checked").length != 1){
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
                action: function(){
                    deleteReq(id,url);
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
//打开编辑页面
function edit(url){

    if($("input[type='checkbox']:checked").length != 1){
		showWarningMsg('请选择一条数据进行编辑！');
		return;
	}

	var id = $("input[type='checkbox']:checked").val();
	var tr = $("tr[row-id="+id+"]");
	var status = tr.attr("row-status");

	if(status == '0' || status == '1' || status == '2')
    {
        showWarningMsg("该机构不在冻结或可用状态无法编辑改机构状态");
        return ;
    }

	window.location.href = url + '?id=' + id;
}
//调用删除方法
function deleteReq(id,url){
	$.ajax({
		url:url,
		type:'POST',
		data:{id:id},
		dataType:'json',
		success:function(data){
			if(data.code == 0){
				showSuccessMsg('删除成功!',function(){
					$("input[type='checkbox']:checked").parents("tr").remove();
				})
			}else{
				showErrorMsg(data.msg);
			}
		},
		error:function(data){
			alert('网络错误!');
		}
	});
}

var notPassReason = "";

function approval(id,flag){
    $.confirm({
        title:"机构审核",
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
</script>
</body>
</html>