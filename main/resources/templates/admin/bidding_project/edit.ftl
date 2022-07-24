<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|竞拍管理-编辑竞拍项目</title>
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
                            <div class="card-header"><h4>编辑竞拍项目</h4></div>
                            <div class="card-body">
                                <form id="bidding-edit-form" class="row">
                                    <input type="hidden" name="id" id="id" value="${biddingProject.id}"/>
                                    <div class="form-group col-md-12">
                                        <label>封面上传</label>
                                        <div class="form-controls">
                                            <ul class="list-inline clearfix lyear-uploads-pic" id="show-uploaded-pic">
                                                <input type="hidden" name="picture" id="edit-picture" value="${biddingProject.picture!""}">
                                            <#if biddingProject.picture??>
                                                <#if biddingProject.picture?length gt 0>
                                                    <#list biddingProject.picture?split(";") as picture>
                                                      <li class="col-xs-4 col-sm-3 col-md-2">
                                                          <figure>
                                                              <img src="/photo/view?filename=${picture}" width="130px" height="150px">
                                                              <figcaption>
                                                                  <a class="btn btn-round btn-square btn-danger del-img-btn" href="javascript:void(0)" data-val="${picture}"><i class="mdi mdi-delete"></i></a>
                                                              </figcaption>
                                                          </figure>
                                                      </li>
                                                    </#list>
                                                </#if>
                                            <#else>
                                              <img src="/admin/images/default-head.jpg" width="130px" height="150px">
                                            </#if>
                                                <input type="file" id="select-picture-file" style="display:none;"
                                                       onchange="uploadPicture()">
                                                <li class="col-xs-4 col-sm-3 col-md-2">
                                                    <a class="pic-add" id="edit-pic-btn" href="javascript:void(0)"
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

                                                        <#if biddingProject.certificate??>
                            <#if biddingProject.certificate?length gt 0>
                                <img src="/photo/view?filename=${biddingProject.certificate}" id="show-picture-img" width="130px" height="150px">
                            <#else>
                    		<img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像" width="130px" height="150px">
                            </#if>
                                                        </#if>
                                                    </figure>
                                                </li>
                                                <input type="hidden" name="certificate" id="certificate" value="${biddingProject.certificate}">
                                                <input type="file" id="select-file" style="display:none;"
                                                       onchange="upload('show-picture-img','certificate')">
                                                <li class="col-xs-4 col-sm-3 col-md-2">
                                                    <a class="pic-add" id="edit-certificate-btn" href="javascript:void(0)"
                                                       title="点击上传"></a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <label>项目名称</label>
                                                <input type="text" class="form-control required" id="edit-title"
                                                       name="title"
                                                       value="${biddingProject.title}" placeholder="请输入项目名称" tips="请输入项目名称"/>
                                            </div>
                                            <div class="col-xs-6">
                                                <label>标的物类型</label>
                                                <select name="labelType.id" class="form-control select" id="edit-labelType" tips="请选择标的物类型">
                                                    <option value="-1">--请选择标的物类型--</option>
                                    	<#list labelTypeList as labelType>
                                            <option value="${labelType.id}" <#if labelType.id==biddingProject.labelType.id>selected</#if>>${labelType.name}</option>
                                        </#list>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row" id="distpicker5">
                                            <div class="col-xs-4">
                                                <label>省</label>
                                                <select class="form-control" id="edit-province" name="province" >

                                                </select>
                                            </div>
                                            <div class="col-xs-4">
                                                <label>市</label>
                                                <select class="form-control" id="edit-city" name="city"></select>
                                            </div>
                                            <div class="col-xs-4">
                                                <label>区</label>
                                                <select class="form-control" id="edit-area" name="area"></select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <label>报名开始时间</label>
                                                <input type="text" class="form-control datepicker-time"
                                                       id="edit-startTime"
                                                       name="startTime" autocomplete="off"
                                                       value=""
                                                       placeholder="请选择报名开始时间" tips="请选择报名开始时间">
                                            </div>
                                            <div class="col-xs-3">
                                                <label>报名结束时间</label>
                                                <input type="text" class="form-control datepicker-time"
                                                       id="edit-endTime"
                                                       name="endTime" autocomplete="off"
                                                       value=""
                                                       placeholder="请选择报名结束时间" tips="请选择报名结束时间">
                                            </div>
                                            <div class="col-xs-3">
                                                <label>竞拍开始时间</label>
                                                <input type="text" class="form-control datepicker-time"
                                                       id="edit-biddingStartTime"
                                                       name="biddingStartTime" autocomplete="off"
                                                       value=""
                                                       placeholder="请选择竞拍开始时间" tips="请选择竞拍开始时间">
                                            </div>
                                            <div class="col-xs-3">
                                                <label>竞拍结束时间</label>
                                                <input type="text" class="form-control datepicker-time"
                                                       id="edit-biddingEndTime"
                                                       name="biddingEndTime" autocomplete="off"
                                                       value=""
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
                                                    <input type="checkbox" value="${biddingProject.yearsType}"<#if biddingProject.yearsType==1>checked</#if> name="yearsType"
                                                           id="edit-yearsType">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col-xs-2" id="edit-years" style="display: none">
                                                <label>年限数</label>
                                                <div class="input-group m-b-10">
                                                    <input type="number" class="form-control" id="edit-year"
                                                           name="years"
                                                           value="${biddingProject.years!"1"}" min="1" placeholder="请输入年限数" tips="请输入年限数" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                    <span class="input-group-addon">年</span>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>转出方</label>
                                                <input type="text" class="form-control required" id="edit-transferor"
                                                       name="transferor"
                                                       value="${biddingProject.transferor}" placeholder="请输入转出方" tips="请输入转出方"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>起拍价</label>
                                                <input type="number" class="form-control required" id="edit-startPrice" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                       name="startPrice"
                                                       value="${biddingProject.startPrice}" min="0"  placeholder="请输入起拍价" tips="请输入起拍价"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>加价幅度</label>
                                                <input type="number" class="form-control required" id="edit-rateIncrease" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                       name="rateIncrease"
                                                       value="${biddingProject.rateIncrease}" min="0" placeholder="请输入加价幅度" tips="请输入加价幅度"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>保证金</label>
                                                <input type="number" class="form-control required" id="edit-bond" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                       name="bond"
                                                       value="${biddingProject.bond}" min="0" placeholder="请输入保证金" tips="请输入保证金"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-2">
                                                <label>佣金比例</label>
                                                <div class="input-group m-b-10">
                                                    <input type="number" class="form-control required" id="edit-rate" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           name="rate"
                                                           value="${biddingProject.rate}" min="1" max="100" placeholder="佣金比例" tips="请输入佣金比例"/>
                                                    <span class="input-group-addon">%</span>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>尾款线上支付截止</label>
                                                <input type="text" class="form-control required" id="edit-paymentDate"
                                                       name="paymentDate"
                                                       value="${biddingProject.paymentDate}" placeholder="例如:竞价结束后15个自然日" tips="请输入尾款线上支付截止"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>尾款支付方式</label>
                                                <input type="text" class="form-control required" id="edit-paymentMethod"
                                                       name="paymentMethod"
                                                       value="${biddingProject.paymentMethod}" placeholder="例如: 通过平台交易支付" tips="请输入尾款支付方式"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>延时周期</label>
                                                <div class="input-group m-b-10">
                                                    <select name="delayPeriod" class="form-control select" id="edit-delayPeriod" tips="请选择延时周期">
                                                        <option value="-1">--请选择延时周期--</option>
                                    	<#list cycleTypeList as cycleType>
                                            <option value="${cycleType.days}" <#if biddingProject.delayPeriod==cycleType.days>selected</#if>>${cycleType.days}</option>
                                        </#list>
                                                    </select>
                                                    <span class="input-group-addon">分钟</span>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>联系人</label>
                                                <input type="text" class="form-control required" id="edit-contacts"
                                                       name="contacts"
                                                       value="${biddingProject.contacts}" placeholder="请填写联系人" tips="请填写联系人"/>
                                            </div>
                                            <div class="col-xs-2">
                                                <label>联系电话</label>
                                                <input type="text" class="form-control required" id="edit-phone"
                                                       name="phone"
                                                       value="${biddingProject.phone}" placeholder="请填写联系电话" tips="请填写联系电话"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="input-group m-b-10" >
                                        <span class="input-group-addon">详情描述</span>
                                        <textarea style="width:auto;height:250px" id="edit-description"
                                                  name="description">${biddingProject.description}</textarea>
                                    </div>
                                    <div class="input-group m-b-10" >
                                        <span class="input-group-addon">竞买公告</span>
                                        <textarea style="width:auto;height:250px" id="edit-notice"
                                                  name="notice">${biddingProject.notice}</textarea>
                                    </div>
                                    <div class="input-group m-b-10" >
                                        <span class="input-group-addon">竞买须知</span>
                                        <textarea style="width:auto;height:250px" id="edit-biddingInformation"
                                                  name="biddingInformation">${biddingProject.biddingInformation}</textarea>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post"
                                                id="edit-form-submit-btn">确 定
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
    var province="${biddingProject.province}";
    var city="${biddingProject.city}";
    var area="${biddingProject.area}";
    $('#distpicker5').distpicker('destroy');
    $("#distpicker5").distpicker({
        autoSelect: false,
        province:province,
        city:city,
        district:area
    });
    $(document).ready(function () {
        //判断年限开关
       var yearType=$("input[name='yearsType']:checked").val();
       if(yearType=="1"){
           //表示开启
         $("#edit-years").show();
       }
        //提交按钮监听事件
        $("#edit-form-submit-btn").click(function () {
           var picture= $(document.getElementById("edit-picture")).val();
           if(picture==""||picture==null){
               showWarningMsg("请上传封面图");
               return;
           }
            if (!checkForm("bidding-edit-form")) {
                return;
            }
            if(!checkDateTime(".form-control.datepicker-time")){
                return;
            }
            if(!checkSelectForm("form-control.select")){
                return;
            }
            if($("#edit-yearsType").val()!="0"){
               if($("#edit-year").val()==""){
                   showWarningMsg("请填写年限数！");
                   return;
               }
            }
            if(Number($("#edit-rate").val())>100){
               showWarningMsg("佣金比例不能大于100");
               return;
            }
           if($("#edit-province").val()==""){
               showWarningMsg("请选择省");
               return;
           }
           if($("#edit-city").val()==""){
               showWarningMsg("请选择市");
               return;
           }
           if($("#edit-area").val()==""){
               showWarningMsg("请选择区");
               return;
           }
           if($("#edit-description").val()==""){
               showWarningMsg("请填写详情描述");
               return;
           }
            if($("#edit-notice").val()==""){
                showWarningMsg("请填写竞买公告");
                return;
            }
            if($("#edit-biddingInformation").val()==""){
                showWarningMsg("请填写竞买须知");
                return;
            }
            if(Number($("#edit-rateIncrease").val())<=0){
                showWarningMsg("加价幅度必须大于0");
                return;
            }
            var pattern = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d)|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d))$)/;
            if(!pattern.test($("#edit-phone").val())){
                showWarningMsg("请填写正确的手机号");
                return;
            }
            var data = $("#bidding-edit-form").serialize();
          $.ajax({
                url: 'edit',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        showSuccessMsg('编辑竞拍成功!', function () {
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
        $("#edit-pic-btn").click(function () {
            $("#select-picture-file").click();
        });

        $("#edit-certificate-btn").click(function(){
            $("#select-file").click();
        });

        kindeditor("edit-description");
        kindeditor("edit-notice");
        kindeditor("edit-biddingInformation");
    });

    function uploadPicture() {
        if ($("#select-picture-file").val() == '') return;
        var picture = document.getElementById('select-picture-file').files[0];
        uploadPhoto(picture, function (data) {
            var html = '<li class="col-xs-4 col-sm-3 col-md-2 show-img"><figure>';
            html += '<img src="/photo/view?filename=' + data.data + '" alt="封面图片" width="130" height="150"></figure></li>';
            if ($("#show-uploaded-pic>li.show-img").length > 0) {
                $("#show-uploaded-pic>li.show-img:last").after(html);
            } else {
                $("#show-uploaded-pic").prepend(html);
            }
            var pictures = $("#edit-picture").val() == '' ? '' : $("#edit-picture").val() + ';';
            $("#edit-picture").val(pictures + data.data);
        });
    }

    //是否有年限开关
    $("#switch-years").change(function () {
        var value = $(this).find("input").val();
        if (value == 0) {
            $(this).find("input").val(1);
            $("#edit-years").show();
        } else {
            $(this).find("input").val(0);
            $("#edit-years").hide();
        }
    });

    //监听删除图片
    $(".del-img-btn").click(function(){
        var pic = $(this).attr('data-val');
        var picture = $("#edit-picture").val();
        picture = picture.replace(pic+';','');
        picture = picture.replace(';' + pic,'');
        picture = picture.replace(pic,'');
        $("#edit-picture").val(picture);
        $(this).closest("li").remove();
    });

</script>
</body>
</html>