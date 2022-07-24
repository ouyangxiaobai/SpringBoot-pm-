<!doctype html>
<html class="no-js" lang="">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>添加银行卡</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "../common/head-link.ftl"/>
    <link rel="stylesheet" href="/home/layui/css/layui.css" media="all">
    <style>

        .layui-form-label {
            width: 100px;
        }

        .nice-select {
            display: none;
        !important;
        }

        .box-container
        {
            width: 960px;
            height: auto;
            padding: 20px;
            margin: 15px auto;
        }

    </style>
</head>

<body>
    <#include "../common/head.ftl"/>
    <div class="box-container" style="height: 537px;">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>添加银行卡</legend>
        </fieldset>
        <div class="layui-form">
            <div class="layui-form-item">
                <span class="layui-form-label">请选择银行</span>
                <div class="layui-input-block">
                    <select  class="layui-select" lay-search="" id="bank" lay-filter="bank">
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <span class="layui-form-label">银行卡号</span>
                <div class="layui-input-block">
                    <input type="text" id="cardNumbers" name="cardNumbers" lay-verify="title" autocomplete="off" placeholder="请输入银行卡号"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <span class="layui-form-label">所属支行</span>
                <div class="layui-input-block">
                    <input type="text" id="branch" name="branch" lay-verify="title" autocomplete="off" placeholder="请输入所属支行"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <span class="layui-form-label">开户人姓名</span>
                <div class="layui-input-block">
                    <input type="text" id="name" name="name" lay-verify="title" autocomplete="off" placeholder="请输入开户人姓名"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <span class="layui-form-label">手机号</span>
                <div class="layui-input-block">
                    <input type="text" id="phone" name="phone" lay-verify="title" autocomplete="off" placeholder="请输入开户人手机号"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="submit" class="layui-btn" lay-submit="" id="add-bankcard" lay-filter="add-bankcard" onclick="addBankCard()">提交</button>
                    <#--<button type="reset" class="layui-btn layui-btn-primary">重置</button>-->
                </div>
            </div>
        </div>
    </div>
    <#include "../common/foot.ftl"/>
    <script type="text/javascript" src="/home/layui/layui.all.js"></script>

    <script type="text/javascript" src="/home/js/msg.js"></script>
    <script type="text/javascript" src="/home/js/bank-card.js"></script>


<script>

    var bank = '';

   function init() {
       var html='<option value="">直接选择或搜索选择</option>';

         for(var i = 0, len = getBank().length; i < len; i++)
         {
             html+="<option value='" + getBank()[i].value + "'>" + getBank()[i].text + "</option>";
         }

        $("#bank").append(html);

    }

   init();

    layui.use('form', function()
    {
        var form = layui.form;
        form.render();
        form.on('select(bank)',function (data) {
            bank = data.value;
        })
    })

    function addBankCard(){
        var cardNumbers = $('#cardNumbers').val().trim();
        var branch = $('#branch').val().trim();
        var name = $('#name').val().trim();
        var phone = $('#phone').val().trim();

        if(msg.isEmpty(bank)){
            errorMsg("请选择银行");
            return;
        }
        if(msg.isEmpty(cardNumbers)){
            errorMsg("请输入银行卡号");
            return;
        }
        if(msg.isEmpty(branch)){
            errorMsg("请输入所属分行");
            return;
        }
        if(msg.isEmpty(name)){
            errorMsg("请输入开户人姓名");
            return;
        }
        if(msg.isEmpty(phone)){
            errorMsg("请输入开户人手机号");
            return;
        }

        if(msg.isBankCard(cardNumbers,bank)){
            var flag = false;
            $.ajax({
                dataType: 'json',
                async: false,
                type: 'get',
                url: 'https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardBinCheck=true',
                data: {cardNo: cardNumbers},
                success: function (data) {
                    if (data.validated == false) {
                        errorMsg("请输入正确的银行卡号");
                        return false;
                    } else if (data.validated == true) {
                        if (data.cardType == 'DC') {
                            var cardBank = data.bank;
                            if (cardBank != bank) {
                                errorMsg("银行与卡号不匹配！");
                            } else {
                                flag = true;
                            }
                        } else {
                            errorMsg("请输入储蓄卡卡号！");
                        }
                    }
                }
            })
            if(!flag){
                return;
            }
        }

        if(!msg.isPhone(phone)){
            errorMsg("手机号格式错误");
            return;
        }

        $.ajax({
            url:'/home/userBankCard/add',
            type:'post',
            data:{
                cardNumbers:cardNumbers,
                branch:branch,
                name:name,
                phone:phone,
                bank:bank
            },
            dataType:'json',
            success:function (data) {
                if(data.code == 0){
                    successMsg("添加成功")
                    location.href = "/home/user/index";
                }else{
                    errorMsg(data.msg)
                }
            },
            error:function (data) {
                errorMsg("网络错误")
            }
        })
    }

</script>
</body>

</html>