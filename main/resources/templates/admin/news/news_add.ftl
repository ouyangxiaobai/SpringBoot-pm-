<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|添加新闻</title>
<#include "../common/header.ftl"/>
<link href="/admin/kindeditor/themes/default/default.css" type="text/css" rel="stylesheet">

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
              <div class="card-header"><h4>添加新闻</h4></div>
              <div class="card-body">
                <form  id="user-add-form" class="row">
                  <div class="form-group col-md-12">
                    <label>新闻封面</label>
                    <div class="form-controls">
                      <ul class="list-inline clearfix lyear-uploads-pic">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <figure>
                            <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                          </figure>
                        </li>
                        <input type="hidden" name="picture" id="picture">
                        <input type="file" id="select-file" style="display:none;" onchange="upload('show-picture-img','picture')">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <a class="pic-add" id="add-pic-btn" href="javascript:void(0)" title="点击上传"></a>
                        </li>
                      </ul>
                    </div>
                  </div>

                  <div class="input-group m-b-10">
                    <span class="input-group-addon">标题</span>
                    <input type="text" class="form-control required"
                           id="caption" name="caption" value=""
                           placeholder="请输入标题" tips="请填写标题" />
                  </div>

                    <div class="input-group m-b-10">
                        <span class="input-group-addon">来源</span>
                        <input type="text" class="form-control required"
                               id="source" name="source" value=""
                               placeholder="请输入来源" tips="请填写来源" />
                    </div>

                  <div class="input-group m-b-10">
                    <span class="input-group-addon">新闻类型</span>
                    <select name="newsType.id" class="form-control" id="newsType">
                        <option value="">请选择</option>
                        <#list newsType as item>
                            <option value="${item.id}">${item.name}</option>
                        </#list>
                    </select>
                  </div>

                    <div class="form-group">
                        <div class="row" id="distpicker5">
                            <div class="col-xs-4">
                                <label>省</label>
                                <select class="form-control" id="province" name="province"></select>
                            </div>
                            <div class="col-xs-4">
                                <label>市</label>
                                <select class="form-control" id="city" name="city"></select>
                            </div>
                            <div class="col-xs-4">
                                <label>区</label>
                                <select class="form-control" id="county" name="county"></select>
                            </div>
                        </div>
                    </div>

                    <div class="input-group m-b-10">
                        <span class="input-group-addon">内容</span>
                        <textarea style="width:auto;height:250px" id="content"
                                  name="content"></textarea>
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
<script src="/admin/Distpicker/js/distpicker.data.js"></script>
<script src="/admin/Distpicker/js/distpicker.js"></script>
<script src="/admin/Distpicker/js/main.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/moment.min.js"></script>
<script type="text/javascript" src="/admin/datepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/admin/js/datetimepicker.js"></script>
<script type="text/javascript" charset="utf-8" src="/admin/kindeditor/kindeditor-all-min.js"></script>

<script type="text/javascript">
$(document).ready(function(){

    $("#distpicker5").distpicker({
        autoSelect: false
    });

    //提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("user-add-form")){
			return;
		}

		//判断省市区
        var province = $("#province").val().trim();
		var city = $("#city").val().trim();
		var county = $("#county").val().trim();
		var newsType = $("#newsType").val().trim();

		if(province == null || province.length == 0)
        {
            showErrorMsg("请选择省份");
            return ;
        }

        if(city == null || city.length == 0)
        {
            showErrorMsg("请选择城市");
            return ;
        }

        if(county == null || county.length == 0)
        {
            showErrorMsg("请选择区");
            return ;
        }

        if(newsType == null || newsType.length == 0)
        {
            showErrorMsg("请选择新闻类型");
            return;
        }

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
	});
	//监听上传图片按钮
	$("#add-pic-btn").click(function(){
		$("#select-file").click();
	});

    kindeditor("content");
});


function kindeditor(name){
    KindEditor.ready(function (K) {
        K.create('#'+name, {
            uploadJson: '/upload/uploadFile',
            filePostName: 'imgFile',
            allowFileManager: true,
            allowImageUpload: true,
            width: '100%',  //编辑器的宽
            height: '460px',  //编辑器的高
            items: ['fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'removeformat', 'justifyleft', 'justifycenter', 'justifyright',
                'insertorderedlist', 'insertunorderedlist', 'emoticons', 'image'
            ],
            afterBlur: function () {
                this.sync();
            },
            allowImageRemote: false,
            afterUpload: function (url, data, name) { //上传文件后执行的回调函数，必须为3个参数
                if (name == "image" || name == "multiimage") { //单个和批量上传图片时
                    var img = new Image();
                    img.src = url;
                    img.onload = function () {
                    }
                }
            }
        });
    });
}
</script>
</body>
</html>