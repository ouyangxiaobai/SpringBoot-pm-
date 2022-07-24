<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|添加银行卡</title>
<#include "../common/header.ftl"/>
    <link href="/admin/select2/select2.min.css" rel="stylesheet">
    <link href="/admin/select2/select2-bootstrap.css" rel="stylesheet"/>
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
              <div class="card-header"><h4>添加银行卡</h4></div>
              <div class="card-body">
                <form id="user-add-form" class="row">
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">开户人</span>
                    <input type="text" class="form-control required" id="name" name="name"
                           value="" placeholder="请输入开户人" tips="请填写开户人" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">银行卡号</span>
                    <input type="text" class="form-control required"
                           id="cardNumbers" name="cardNumbers" value="" placeholder="请输入银行卡号" tips="请填写银行卡号" />
                  </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">所属银行</span>
                        <select class="form-control selectRequired selectpicker select2-multiple" id="bank" name="bank"
                                data-none-selected-text="请选择年级" >

                        </select>
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">所属支行</span>
                        <input type="text" class="form-control required"
                               id="branch" name="branch" value="" placeholder="请输入所属支行" tips="请填写所属支行" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">手机号</span>
                        <input type="text" class="form-control required"
                               id="phone" name="phone" value="" placeholder="请输入手机号" tips="请填写手机号" />
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
<script type="text/javascript" src="/admin/select2/select2.min.js"></script>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript" src="/common/js/msg.js"></script>
<script type="text/javascript" src="/common/js/bank-card.js"></script>

<script type="text/javascript">
$(document).ready(function(){

    function init()
    {
        $("#bank").html('');
        var html='<option value="">直接选择或搜索选择</option>';

        for(var i = 0, len = getBank().length; i < len; i++)
        {
            html+="<option value='" + getBank()[i].value + "'>" + getBank()[i].text + "</option>";
        }

        $("#bank").append(html);
    }

    init();

    $("#bank").select2(
            {placeholder: "--请选择银行--"}
    );

	//提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("user-add-form")){
			return;
		}

		//手机号验证
        var phone = $("#phone").val().trim();

		//银行卡
		var bankNumber = $("#cardNumbers").val().trim();

		var myBank = $("#bank").val().trim();

		if(msg.isEmpty(myBank))
        {
            showErrorMsg("请输入所属银行");
            return ;
        }

		if(!msg.isPhone(phone))
        {
            showErrorMsg("手机号格式错误");
            return ;
        }

        if(msg.isEmpty(bankNumber))
        {
            showErrorMsg("请输入银行卡号码");
            return ;
        }

        var bank = /^\d+$/
        if (!bank.test(bankNumber)) {
            errorMsg("请输入正确的银行卡号");
            return false;
        }

        $.ajax({
            dataType:'json',
            async: false,
            type:'get',
            url:'https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardBinCheck=true',
            data:{cardNo:bankNumber},
            success:function(data){
                if(data.validated == false) {
                    showErrorMsg("银行卡号错误");
                    return ;
                }

                //类型
                if(data.cardType != "DC") {
                    showErrorMsg("请输入储蓄卡卡号");
                    return;
                }

                if(data.bank != myBank)
                {
                    showErrorMsg("银行与卡号不匹配");
                    return ;
                }

                add();
            }
        });

        return;

	});
	//监听上传图片按钮
	$("#add-pic-btn").click(function(){
		$("#select-file").click();
	});
});


function add() {
    var data = $("#user-add-form").serialize();
    $.ajax({
        url:'add',
        type:'POST',
        data:data,
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg('添加成功!',function(){
                    window.location.href = 'list';
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

</script>
</body>
</html>