<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|竞拍管理-添加竞拍项目</title>
<#include "../common/header.ftl"/>
    <link href="/admin/datepicker/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="/admin/kindeditor/themes/default/default.css" type="text/css" rel="stylesheet">

</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}"
                                             alt="${siteName!""}"/></a>
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
                            <div class="card-header"><h4>添加竞拍项目</h4></div>
                            <div class="card-body">
                                <form id="bidding-add-form" class="row">
                                    <div class="form-group col-md-12">
                                        <label>封面上传</label>
                                        <div class="form-controls">
                                            <ul class="list-inline clearfix lyear-uploads-pic" id="show-uploaded-pic">
                                                <input type="hidden" name="picture" id="picture">
                                                <input type="file" id="select-picture-file" style="display:none;"
                                                       onchange="uploadPicture()">
                                                <li class="col-xs-4 col-sm-3 col-md-2">
                                                    <a class="pic-add" id="add-pic-btn" href="javascript:void(0)"
                                                       title="点击上传"></a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <label>授权书上传</label>
                                        <div class="form-controls">
                                            <ul class="list-inline clearfix lyear-uploads-pic">
                                                <li class="col-xs-4 col-sm-3 col-md-2">
                                                    <figure>
                                                        <img src="/admin/images/default-head.jpg" id="show-picture-img"
                                                             alt="默认头像">
                                                    </figure>
                                                </li>
                                                <input type="hidden" name="certificate" id="certificate">
                                                <input type="file" id="select-file" style="display:none;"
                                                       onchange="upload('show-picture-img','certificate')">
                                                <li class="col-xs-4 col-sm-3 col-md-2">
                                                    <a class="pic-add" id="add-certificate-btn" href="javascript:void(0)"
                                                       title="点击上传"></a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <label>项目名称</label>
                                                <input type="text" class="form-control required" id="add-title"
                                                       name="title"
                                                       value="" placeholder="请输入项目名称" tips="请输入项目名称"/>
                                            </div>
                                            <div class="col-xs-6">
                                                <label>标的物类型</label>
                                                <select name="labelType.id" class="form-control select" id="add-labelType" tips="请选择标的物类型">
                                                    <option value="-1">--请选择标的物类型--</option>
                                    	<#list labelTypeList as labelType>
                                            <option value="${labelType.id}">${labelType.name}</option>
                                        </#list>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row" id="distpicker5">
                                            <div class="col-xs-4">
                                                <label>省</label>
                                                <select class="form-control" id="add-province" name="province"></select>
                                            </div>
                                            <div class="col-xs-4">
                                                <label>市</label>
                                                <select class="form-control" id="add-city" name="city"></select>
                                            </div>
                                            <div class="col-xs-4">
                                                <label>区</label>
                                                <select class="form-control" id="add-area" name="area"></select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <label>报名开始时间</label>
                                                <input type="text" class="form-control datepicker-time"
                                                       id="add-startTime"
                                                       name="startTime" autocomplete="off"
                                                       placeholder="请选择报名开始时间" tips="请选择报名开始时间">
                                            </div>
                                            <div class="col-xs-3">
                                                <label>报名结束时间</label>
                                                <input type="text" class="form-control datepicker-time"
                                                       id="add-endTime"
                                                       name="endTime" autocomplete="off"
                                                       placeholder="请选择报名结束时间" tips="请选择报名结束时间">
                                            </div>
                                            <div class="col-xs-3">
                                                <label>竞拍开始时间</label>
                                                <input type="text" class="form-control datepicker-time"
                                                       id="add-biddingStartTime"
                                                       name="biddingStartTime" autocomplete="off"
                                                       placeholder="请选择竞拍开始时间" tips="请选择竞拍开始时间">
                                            </div>
                                            <div class="col-xs-3">
                                                <label>竞拍结束时间</label>
                                                <input type="text" class="form-control datepicker-time"
                                                       id="add-biddingEndTime"
                                                       name="biddingEndTime" autocomplete="off"
                                                       placeholder="请选择竞拍结束时间" tips="请选择竞拍结束时间">
                                            </div>
                                        </div>


                                    </div>

                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-1">
                                                <label>是否有年限</label>
                                                <br>
                                                <label class="lyear-switch switch-solid switch-primary"
                                                       id="switch-years"
                                                       style="margin-left: 10px;margin-top: 10px;">
                                                    <input type="checkbox" value="0" name="yearsType"
                                                           id="add-yearsType">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col-xs-2" id="add-years" style="display: none">
                                                <label>年限数</label>
                                                <div class="input-group m-b-10">
                                                    <input type="number" class="form-control" id="years" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           name="years"
                                                           value="1" min="1" placeholder="请输入年限数" tips="请输入年限数"/>
                                                    <span class="input-group-addon">年</span>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>转出方</label>
                                                <input type="text" class="form-control required" id="add-transferor"
                                                       name="transferor"
                                                       value="" placeholder="请输入转出方" tips="请输入转出方"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>起拍价</label>
                                                <input type="number" class="form-control required" id="add-startPrice" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                       name="startPrice"
                                                       value="0" min="0"  placeholder="请输入起拍价" tips="请输入起拍价"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>加价幅度</label>
                                                <input type="number" class="form-control required" id="add-rateIncrease" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                       name="rateIncrease"
                                                       value="1" min="1" placeholder="请输入加价幅度" tips="请输入加价幅度"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>保证金</label>
                                                <input type="number" class="form-control required" id="add-bond" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                       name="bond"
                                                       value="0" min="0" placeholder="请输入保证金" tips="请输入保证金"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-2">
                                                <label>佣金比例</label>
                                                <div class="input-group m-b-10">
                                                    <input type="number" class="form-control required" id="add-rate" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           name="rate"
                                                           value="0" min="0" max="100" placeholder="佣金比例" tips="请输入佣金比例"/>
                                                    <span class="input-group-addon">%</span>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>尾款线上支付截止</label>
                                                <input type="text" class="form-control required" id="add-paymentDate"
                                                       name="paymentDate"
                                                       value="" placeholder="例如:竞价结束后15个自然日" tips="请输入尾款线上支付截止"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>尾款支付方式</label>
                                                <input type="text" class="form-control required" id="add-paymentMethod"
                                                       name="paymentMethod"
                                                       value="" placeholder="例如: 通过平台交易支付" tips="请输入尾款支付方式"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>延时周期</label>
                                                <div class="input-group m-b-10">
                                                    <select name="delayPeriod" class="form-control select" id="delayPeriod" tips="请选择延时周期">
                                                        <option value="-1">--请选择延时周期--</option>
                                    	<#list cycleTypeList as cycleType>
                                            <option value="${cycleType.days}">${cycleType.days}</option>
                                        </#list>
                                                    </select>
                                                    <span class="input-group-addon">分钟</span>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>联系人</label>
                                                <input type="text" class="form-control required" id="add-contacts"
                                                       name="contacts"
                                                       value="" placeholder="请填写联系人" tips="请填写联系人"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>联系电话</label>
                                                <input type="text" class="form-control required" id="add-phone"
                                                       name="phone"
                                                       value="" placeholder="请填写联系电话" tips="请填写联系电话"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="input-group m-b-10" >
                                        <span class="input-group-addon">详情描述</span>
                                        <textarea style="width:auto;height:250px" id="add-description"
                                                  name="description"></textarea>
                                    </div>
                                    <div class="input-group m-b-10" >
                                        <span class="input-group-addon">竞买公告</span>
                                        <textarea style="width:auto;height:250px" id="add-notice"
                                                  name="notice"></textarea>
                                    </div>
                                    <div class="input-group m-b-10" >
                                        <span class="input-group-addon">竞买须知</span>
                                        <textarea style="width:auto;height:250px" id="add-biddingInformation"
                                                  name="biddingInformation"></textarea>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post"
                                                id="add-form-submit-btn">确 定
                                        </button>
                                        <button type="button" class="btn btn-default"
                                                onclick="javascript:history.back(-1);return false;">返 回
                                        </button>
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
<script src="/admin/Distpicker/js/distpicker.data.js"></script>
<script src="/admin/Distpicker/js/distpicker.js"></script>
<script src="/admin/Distpicker/js/main.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/moment.min.js"></script>
<script type="text/javascript" src="/admin/datepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/admin/js/datetimepicker.js"></script>
<script type="text/javascript" charset="utf-8" src="/admin/kindeditor/kindeditor-all-min.js"></script>

<script type="text/javascript">
    $("#distpicker5").distpicker({
        autoSelect: false
    });
    $(document).ready(function () {
        //提交按钮监听事件
        $("#add-form-submit-btn").click(function () {
           var picture= $(document.getElementById("picture")).val();
           if(picture==""||picture==null){
               showWarningMsg("请上传封面图");
               return;
           }
            if (!checkForm("bidding-add-form")) {
                return;
            }
            if(!checkDateTime(".form-control.datepicker-time")){
                return;
            }
            if(!checkSelectForm("form-control.select")){
                return;
            }
            if($("#add-yearsType").val()!="0"){
               if($("#years").val()==""){
                   showWarningMsg("请填写年限数！");
                   return;
               }
               if($("#years").val()<=0){
                   showWarningMsg("年限数至少为一年");
                   return;
               }
            }
            if(Number($("#add-rate").val())>100){
               showWarningMsg("佣金比例不能大于100");
               return;
            }
           if($("#add-province").val()==""){
               showWarningMsg("请选择省");
               return;
           }
           if($("#add-city").val()==""){
               showWarningMsg("请选择市");
               return;
           }
           if($("#add-area").val()==""){
               showWarningMsg("请选择区");
               return;
           }
           if($("#add-description").val()==""){
               showWarningMsg("请填写详情描述");
               return;
           }
            if($("#add-notice").val()==""){
                showWarningMsg("请填写竞买公告");
                return;
            }
            if($("#add-biddingInformation").val()==""){
                showWarningMsg("请填写竞买须知");
                return;
            }
            var pattern = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d)|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d))$)/;
            if(!pattern.test($("#add-phone").val())){
                showWarningMsg("请填写正确的手机号");
                return;
            }
            if(Number($("#add-rateIncrease").val())<=0){
                showWarningMsg("加价幅度必须大于0");
                return;
            }
            var data = $("#bidding-add-form").serialize();
           $.ajax({
                url: 'add',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        showSuccessMsg('添加竞拍成功!', function () {
                            window.location.href = 'list';
                        })
                    } else {
                        showErrorMsg(data.msg);
                    }
                },
                error: function (data) {
                    alert('网络错误!');
                }
            });
        });

        //监听上传图片按钮
        $("#add-pic-btn").click(function () {
            $("#select-picture-file").click();
        });


        $("#add-certificate-btn").click(function(){
            $("#select-file").click();
        });

        //富文本
        kindeditor("add-description");
        kindeditor("add-notice");
        kindeditor("add-biddingInformation");
    });


    //封面图片上传
    function uploadPicture() {
        if ($("#select-picture-file").val() == '') return;
        var picture = document.getElementById('select-picture-file').files[0];
        uploadPhoto(picture, function (data) {
            var html = '<li class="col-xs-4 col-sm-3 col-md-2 show-img"><figure>';
            html += '<img src="/photo/view?filename=' + data.data + '" alt="封面图片" width="130px" height="150px"></figure></li>';
            if ($("#show-uploaded-pic>li.show-img").length > 0) {
                $("#show-uploaded-pic>li.show-img:last").after(html);
            } else {
                $("#show-uploaded-pic").prepend(html);
            }
            var pictures = $("#picture").val() == '' ? '' : $("#picture").val() + ';';
            $("#picture").val(pictures + data.data);
        });
    }

    //是否有年限开关
    $("#switch-years").change(function () {
        var value = $(this).find("input").val();
        if (value == 0) {
            $(this).find("input").val(1);
            $("#add-years").show();
        } else {
            $(this).find("input").val(0);
            $("#add-years").hide();
        }
    });

</script>
</body>
</html>