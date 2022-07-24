<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|充值记录管理-${title!""}</title>
<#include "../common/header.ftl"/>
<style>
td{
	vertical-align:middle;
}

.price-item
{
    display: inline-block;
    width: 120px;
    height: 50px;
    margin: 10px;
    border: 2px solid #EAEAEA;
    text-align: center;
    line-height: 50px;
}

.price-item:hover
{
    border: 2px solid dodgerblue;
}

.price-item-active{
    border: 2px solid dodgerblue;
    border-radius: 4%;
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
                      订单号 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">订单号</a> </li>
                      </ul>
                    </div>
                    <input type="text" class="form-control" value="${outTradeNo!""}" name="outTradeNo" placeholder="请输入订单号">
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
                        <th>订单号</th>
                        <th>付款金额</th>
                        <th>支付状态</th>
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
                        <td style="vertical-align:middle;">${item.outTradeNo}</td>
                        <td style="vertical-align:middle;">${item.totalAmount}</td>
                        <td style="vertical-align:middle;">
                            <#if item.status == 0>
                                <font color="orange">待支付</font>
                            <#elseif item.status == 1>
                                <font color="green">已支付</font>
                            </#if>
                        </td>
                          <td>
                              <#if item.status == 0>
                                  <input type="button" class="btn btn-primary" value="继续支付" onclick="M.goPay('${item.outTradeNo}')"/>
                              <#else>
                                   <input type="button" class="btn btn-primary" value="继续支付" disabled/>
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
                  <li><a href="list?outTradeNo=${outTradeNo!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                  <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                  <#else>
                  <li><a href="list?outTradeNo=${outTradeNo!""}&currentPage=${showPage}">${showPage}</a></li>
                  </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?outTradeNo=${outTradeNo!""}&currentPage=${pageBean.totalPage}">»</a></li>
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

<div class="modal fade" id="viewCompetition" tabindex="-1" role="dialog" aria-labelledby="viewCompetition">
    <div class="modal-dialog" role="document">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="viewCompetition">充值</h4>
            </div>
            <div class="modal-body">
                <div class="input-group m-b-10">
                    <div class="layui-input-block price-item-list">
                    </div>
                </div>
                <div class="input-group m-b-10  is-show">
                    <span class="input-group-addon">金额</span>
                    <input type="text" class="form-control required"
                           id="number" name="number" value="0" min="0" placeholder="请输入金额" />
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="M.payOrder()">充值</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
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

var M = {};
M.prices = [50, 100, 300, 600, 1280, 12800]; //充值金额
M.index = -1; //默认选中
M.money = 0; //充值金额

M.init = function()
{
    var view = $(".price-item-list");
    view.html('');

    for(var i=0; i<M.prices.length; i++)
    {
        var span = '';
        if(i == M.index)
            span = '<span class="price-item price-item-active" index="'+i+'">￥'+M.prices[i]+'</span>';
        else
            span = '<span class="price-item" index="'+i+'">￥'+M.prices[i]+'</span>';

        view.append(span);
    }

    var span = '<span class="price-item price-item-active" data-target="1">其他</span>';
    view.append(span);
}();

//去支付
function toPay(url)
{
    //选择金额
    $('#viewCompetition').modal('show');
}

$(".price-item").click(function()
{
    var thiz = $(this);
    var target = thiz.attr("data-target");
    var numberView = $("#number");
    var activeView = $(".is-show")
    if(target != null)
    {
        activeView.show(300);
        M.money = 0;
        numberView.val(0);
    }
    else
    {
        activeView.hide(300);
        var index = Number($(thiz).attr("index"));
        M.money = M.prices[index];
        numberView.val(M.money);
    }

    $(".price-item-active").removeClass("price-item-active");
    thiz.addClass("price-item-active")
});

M.payOrder = function () {
    var numberView = $("#number").val();

    var number = Number(numberView);
    if(number < 1 || number > 1000000)
    {
        showErrorMsg("你充值的金额必须在1~100万之间");
        return ;
    }

    $.ajax({
        url:'add',
        type:'POST',
        data:{number:number},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                M.toPay(data.data.outTradeNo);
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    });
}

M.toPay = function(outTradeNo)
{
    location.href = "to_pay?outTradeNo=" + outTradeNo;
}

M.goPay = function(outTradeNo)
{
    location.href = "to_pay?outTradeNo=" + outTradeNo;
}
</script>
</body>
</html>