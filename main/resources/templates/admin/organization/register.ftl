<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>注册页面 - ${siteName!""}</title>
<#include "../common/header.ftl"/>
<style>
.lyear-wrapper {
    position: relative;
}
.lyear-login {
    display: flex !important;
    min-height: 100vh;
    align-items: center !important;
    justify-content: center !important;
}
.login-center {
    background: #fff;
    min-width: 38.25rem;
    padding: 2.14286em 3.57143em;
    border-radius: 5px;
    margin: 2.85714em 0;
}
.login-header {
    margin-bottom: 1.5rem !important;
}
.login-center .has-feedback.feedback-left .form-control {
    padding-left: 38px;
    padding-right: 12px;
}
.login-center .has-feedback.feedback-left .form-control-feedback {
    left: 0;
    right: auto;
    width: 38px;
    height: 38px;
    line-height: 38px;
    z-index: 4;
    color: #dcdcdc;
}
.login-center .has-feedback.feedback-left.row .form-control-feedback {
    left: 15px;
}

.login-center
{
    width: 690px;
}
</style>
</head>
  
<body>
<div class="row lyear-wrapper">
  <div class="lyear-login">
    <div class="login-center">
      <div class="login-header text-center">
        <a style="font-size: 16px">
            注册机构
        </a>
      </div>
      <form id="login-form" method="post">
          <div class="form-group has-feedback feedback-left">
              <label>营业执照</label>
              <div class="form-controls">
                  <ul class="list-inline clearfix lyear-uploads-pic">
                      <li class="col-xs-4 col-sm-3 col-md-2">
                          <figure>
                              <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                          </figure>
                      </li>
                      <input type="hidden" name="tradingImg" id="tradingImg" />
                      <input type="file" id="select-file2" style="display:none;" onchange="upload2('show-picture-img','tradingImg')" />
                      <li class="col-xs-4 col-sm-3 col-md-2">
                          <a class="pic-add" id="add-pic-btn2" href="javascript:void(0)" title="点击上传"></a>
                      </li>
                  </ul>
              </div>
          </div>
          <div class="form-group has-feedback feedback-left">
              <label>身份证图片:</label>
              <div class="form-controls">
                  <ul class="list-inline clearfix lyear-uploads-pic" id="show-uploaded-pic">
                      <input type="hidden" name="cardImg" id="cardImg">
                      <input type="file" id="select-file" style="display:none;" onchange="uploadPicture()/*upload('show-picture-img','picture')*/">
                      <li class="col-xs-4 col-sm-3 col-md-2" id="card-upload">
                          <a class="pic-add" id="add-pic-btn" href="javascript:void(0)" title="点击上传"></a>
                      </li>
                  </ul>
              </div>
          </div>
        <div class="form-group has-feedback feedback-left">
          <input type="email" placeholder="请输入你的邮箱" class="form-control required" name="email" id="email" tips="请输入你的邮箱" />
        </div>

        <div class="form-group has-feedback feedback-left">
          <input type="tel" placeholder="请输入你的手机号" class="form-control required" id="phone"
                 name="phone" tips="请输入你的手机号" />
        </div>

          <div class="form-group has-feedback feedback-left">
              <input type="password" placeholder="请输入密码" class="form-control required" id="password"
                     name="password" tips="请输入密码" />
          </div>

          <div class="form-group has-feedback feedback-left">
              <input type="password" placeholder="请确认密码" class="form-control required" id="ver_password"
                     tips="请确认密码" />
          </div>

          <div class="form-group has-feedback feedback-left">
              <input type="text" placeholder="请输入机构名称" class="form-control required" id="name"
                     name="name" tips="请输入机构名称" />
          </div>

          <div class="form-group has-feedback feedback-left">
              <input type="text" placeholder="请输入地址" class="form-control required" id="address"
                     name="address" tips="请输入地址" />
          </div>
          <div class="form-group has-feedback feedback-left">
              <input type="text" placeholder="请输入联系人" class="form-control required" id="legalPerson"
                     name="legalPerson" tips="请输入联系人" />
          </div>

        <div class="form-group has-feedback feedback-left row">
          <div class="col-xs-9">
            <input type="text" name="code" id="code" maxlength="4" class="form-control required" placeholder="验证码" tips="请填验证码" >
            <span class="mdi mdi-check-all form-control-feedback" aria-hidden="true"></span>
          </div>
          <div class="col-xs-3" style="text-align: right">
              <input class="btn btn-default" value="发送验证码" type="button" onclick="sendCode()" id="send-code-btn"/>
              <input type="button" value="重新发送" style="display: none" class="btn btn-danger" id="send-btn" disabled/>
          </div>
        </div>
        <div class="form-group">
          <button class="btn btn-block btn-primary" type="button" id="submit-btn">立即注册</button>
        </div>
      </form>
      <hr>
      <footer class="col-sm-12 text-center">
        <p class="m-b-0">Copyright © 2020 <a href="/system/login">【登录】</a> . All right reserved</p>
      </footer>
    </div>
  </div>
</div>


<#include "../common/footer.ftl"/>
<script type="text/javascript">
$(document).ready(function(){
	$("#submit-btn").click(function(){

	    if(!checkForm("login-form")){
			return;
		}

		var password = $("#password").val().trim();
		var ver_password = $("#ver_password").val().trim();
		var code = $("#code").val().trim();
		var email = $("#email").val().trim();
		var phone = $("#phone").val().trim();

		if(password != ver_password)
        {
            showErrorMsg("你输入的密码不一致");
            return ;
        }

        if(code == null || code.length == 0)
        {
            showErrorMsg("请输入验证码");
            return ;
        }

        //验证邮箱
        var checkEmail = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
        if(!checkEmail.test(email)) {
            showErrorMsg("请输入正确的邮箱");
            return ;
        }

        //验证手机号
        var checkPhone = /^1[3|4|5|7|8]\d{9}$/;
        if(!checkPhone.test(phone)) {
            showErrorMsg("请输入正确手机号")
            return ;
        }

        //验证身份证图片
        var tradingImg = $("#tradingImg").val().trim();
        var cardImg = $("#cardImg").val().trim();

        if(tradingImg == null || tradingImg.length == 0)
        {
            showErrorMsg("请上传营业执照");
            return ;
        }

        if(cardImg == null || cardImg.length == 0)
        {
            showErrorMsg("请上传身份证图片");
            return ;
        }

        if(count != 2)
        {
            showErrorMsg("请上传身份证正反面");
            return ;
        }

        var data = $("#login-form").serialize();

        $.ajax({
            url:'register',
            type:'POST',
            data:data,
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    showSuccessMsg('注册成功!',function(){
                        window.location.href = '/system/login';
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

    //监听上传图片按钮
    $("#add-pic-btn2").click(function(){
        $("#select-file2").click();
    });
});

var count = 0;
function uploadPicture(){
    if($("#select-file").val() == '')return;
    var picture = document.getElementById('select-file').files[0];
    uploadPhoto(picture,function(data){
        var html = '<li class="col-xs-4 col-sm-3 col-md-2 show-img"><figure>';
        html += '<img src="/photo/view?filename='+data.data+'" alt="封面图片" style="height: 100px; width:100px;">'
                '</figure></li>';
        if($("#show-uploaded-pic>li.show-img").length > 0){
            $("#show-uploaded-pic>li.show-img:last").after(html);
        }else{
            $("#show-uploaded-pic").prepend(html);
        }
        var pictures = $("#cardImg").val() == '' ? '' : $("#cardImg").val() + ';';
        $("#cardImg").val( pictures + data.data);
    });

    count ++;
    if(count == 2)
        $("#card-upload").hide();
}

function sendCode()
{
    var email = $("#email").val().trim().toLowerCase();

    var checkEmail = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
    if(!checkEmail.test(email)) {
        showErrorMsg("请输入正确的邮箱");
        return false;
    }

    $.ajax({
        url:'/send_email/generate_code',
        type:'post',
        data:{
            email:email,
            method:"ylrc_register_organization"
        },
        dataType:'json',
        success:function (data) {
            if(data.code == 0){
                showSuccessMsg('已发送验证码，请注意接收！', function()
                {
                    setButton();
                });
            }else{
                showErrorMsg(data.msg)
            }
        },
        error:function (data) {
            showErrorMsg("邮箱不存在");
        }
    })
}

//按钮不能连续点击
var countdown = 60;  // 时长
function setButton()
{
    $("#send-code-btn").hide();
    $("#send-btn").show();
    var timer = setInterval(function () {
        if (countdown == 0) {
            clearInterval(timer);
            $("#send-code-btn").show();
            $("#send-btn").hide();
            countdown = 60;
        }
        $("#send-btn").val('重新发送('+(countdown-1)+'秒)');
        countdown --;
    }, 1000);
}
</script>
</body>
</html>