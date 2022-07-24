<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>登录页面 - ${siteName!""}</title>
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

#viewCompetition
{
    background-color: rgba(0, 0, 0,.2);
    opacity: 1;
}
</style>
</head>
  
<body>
<div class="row lyear-wrapper">
  <div class="lyear-login">
    <div class="login-center">
      <div class="login-header text-center">
        <a href=""> <img alt="light year admin" src="/admin/images/logo-sidebar.png"></a>
      </div>
      <form id="login-form" method="post">
          <div class="form-group has-feedback feedback-left">
              <select name="type" class="form-control" id="type">
                <#list loginTypes as loginType>
                    <option value="${loginType.code}">${loginType.value}登录</option>
                </#list>
              </select>
          </div>
        <div class="form-group has-feedback feedback-left">
          <input type="text" placeholder="请输入您的用户名" class="form-control required" name="username" id="username" tips="请填写用户名" />
          <span class="mdi mdi-account form-control-feedback" aria-hidden="true"></span>
        </div>
        <div class="form-group has-feedback feedback-left">
          <input type="password" placeholder="请输入密码" class="form-control required" id="password" name="password" tips="请填写密码" />
          <span class="mdi mdi-lock form-control-feedback" aria-hidden="true"></span>
        </div>
        <div class="form-group has-feedback feedback-left row">
          <div class="col-xs-7">
            <input type="text" name="cpacha" id="cpacha" maxlength="4" class="form-control required" placeholder="验证码" tips="请填验证码" >
            <span class="mdi mdi-check-all form-control-feedback" aria-hidden="true"></span>
          </div>
          <div class="col-xs-5">
            <img src="/cpacha/generate_cpacha?vl=4&fs=25&w=128&h=40&method=admin_login" class="pull-right" id="captcha" style="cursor: pointer;" onclick="this.src=this.src+'&d='+Math.random();" title="点击刷新" alt="captcha">
          </div>
        </div>
        <div class="form-group">
          <button class="btn btn-block btn-primary" type="button" id="submit-btn">立即登录</button>
        </div>
      </form>
      <hr>
      <footer class="col-sm-12 text-center">
        <p class="m-b-0">Copyright © 2020
            <a href="/home/index/index">【前往前台】</a>
            <a href="javascript:viewCompetition();">【忘记密码】</a>
            <a href="/admin/organization/register">【注册】</a>
            . All right reserved
        </p>
      </footer>
    </div>
  </div>
</div>

<div class="modal fade" id="viewCompetition" tabindex="-1" role="dialog" aria-labelledby="viewCompetition">
    <div class="modal-dialog" role="document">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">机构忘记密码</h4>
            </div>
            <div class="modal-body">
                <div class="form-group has-feedback feedback-left">
                    <input type="text" placeholder="请输入您的机构邮箱" class="form-control required" name="email" id="email"
                           tips="请输入您的机构邮箱" />
                </div>
                <div class="form-group has-feedback feedback-left">
                    <input type="password" placeholder="请新输入密码" class="form-control required" name="u-password" id="u-password"
                           tips="请新输入密码" />
                </div>
                <div class="form-group has-feedback feedback-left">
                    <input type="password" placeholder="请确认密码" class="form-control required" name="ver-password" id="ver-password"
                           tips="请确认密码" />
                </div>
                <div class="form-group has-feedback feedback-left row">
                    <div class="col-xs-8">
                        <input type="text" name="code" id="code" maxlength="4" class="form-control required" placeholder="验证码" tips="请填验证码" >
                    </div>
                    <div class="col-xs-4" style="text-align: right">
                        <button type="button" class="btn btn-primary" onclick="sendCode()" id="send-code-btn">发送验证码</button>
                        <input type="button" value="重新发送" style="display: none" class="btn btn-danger" id="send-btn" disabled/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="updatePassword()">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
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
		var username = $("#username").val();
		var password = $("#password").val();
		var cpacha = $("#cpacha").val();
		var type = $("#type").val();

		$.ajax({
			url:'/system/login',
			type:'POST',
			data:{username:username,password:password,cpacha:cpacha, type: type},
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					window.location.href = 'index';
				}else{
					showErrorMsg(data.msg);
				}
			},
			error:function(data){
				alert('网络错误!');
			}
		});
	});
});

function viewCompetition() {
    $('#viewCompetition').modal('show');
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
            method:"ylrc_update_organization_code"
        },
        dataType:'json',
        success:function (data) {
            if(data.code == 0){
                setButton();
            }else{
                showErrorMsg(data.msg)
            }
        },
        error:function (data) {
            showErrorMsg("邮箱不存在");
        }
    })
}

function updatePassword()
{
    var email = $("#email").val().trim();
    var password =  $("#u-password").val().trim();
    var ver_password = $("#ver-password").val().trim();
    var code = $("#code").val().trim();

    var checkEmail = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
    if(!checkEmail.test(email)) {
        showErrorMsg("请输入正确的邮箱");
        return false;
    }

    if(password == null || password.length == 0)
    {
        showErrorMsg("请输入新密码");
        return false;
    }

    if(ver_password == null || ver_password.length == 0)
    {
        showErrorMsg("请输入确认密码");
        return false;
    }

    if(code == null || code.length == 0)
    {
        showErrorMsg("请输入验证码");
        return false;
    }

    if(password != ver_password)
    {
        showErrorMsg("密码不一致");
        return false;
    }

    $.ajax({
        url:'/admin/organization/modification_password',
        type:'post',
        data:{
            password:password,
            code: code,
            email:email
        },
        dataType:'json',
        success:function (data) {
            if(data.code == 0){
                showSuccessMsg('修改成功！',function()
                {
                    location.href = "login";
                });
            }else{
                showErrorMsg(data.msg)
            }
        },
        error:function (data) {
            showErrorMsg("网络错误");
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