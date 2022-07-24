//显示成功信息
function showSuccessMsg(msg,callback){
	$.confirm({
        title: '成功',
        content: msg,
        type: 'green',
        typeAnimated: false,
        buttons: {
            omg: {
                text: '确定！',
                btnClass: 'btn-green',
                action: function(){
                	callback();
        		}
            }
        }
    });
}
//显示错误信息
function showErrorMsg(msg){
	$.confirm({
        title: '错误',
        content: msg,
        type: 'red',
        typeAnimated: false,
        buttons: {
            omg: {
                text: '知道了！',
                btnClass: 'btn-red',
                action: function(){
                	
        		}
            }
        }
    });
}
//显示警告信息
function showWarningMsg(msg){
	$.confirm({
        title: '警告',
        content: msg,
        type: 'red',
        typeAnimated: false,
        buttons: {
            omg: {
                text: '知道了！',
                btnClass: 'btn-red',
                action: function(){
                	
        		}
            }
        }
    });
}
//表单验证公用方法，传表单form的id进来即可
function checkForm(formId){
	var flag = true;
	$("#"+formId).find(".required").each(function(i,e){
		if($(e).val() == ''){
			showWarningMsg($(e).attr('tips'));
			flag = false;
			return false;
		}
	});
	return flag;
}
//统一图片上传方法
function upload(showPictureImg,input){
	//formdata
	var formData = new FormData();
	formData.append('photo',document.getElementById('select-file').files[0]);
	$.ajax({
		url:'/upload/upload_photo',
		contentType:false,
		processData:false,
		data:formData,
		type:'POST',
		success:function(data){
				if(data.code == 0){
					showSuccessMsg('图片上传成功!',function(){
						$("#"+showPictureImg).attr('src','/photo/view?filename=' + data.data);
						$("#"+input).val(data.data);
					})
				}else{
					data = $.parseJSON(data);
					showErrorMsg(data.msg);
				}
			},
			error:function(data){
				alert('网络错误!');
			}
	});
}

function upload2(showPictureImg,input){
    //formdata
    var formData = new FormData();
    formData.append('photo',document.getElementById('select-file2').files[0]);
    $.ajax({
        url:'/upload/upload_photo',
        contentType:false,
        processData:false,
        data:formData,
        type:'POST',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg('图片上传成功!',function(){
                    $("#"+showPictureImg).attr('src','/photo/view?filename=' + data.data);
                    $("#"+input).val(data.data);
                })
            }else{
                data = $.parseJSON(data);
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    });
}

//统一ajax请求
function ajaxRequest(url,requestType,data,callback){
	$.ajax({
		url:url,
		type:requestType,
		data:data,
		dataType:'json',
		success:function(rst){
			if(rst.code == 0){
				callback(rst);
			}else{
				showErrorMsg(rst.msg);
			}
		},
		error:function(data){
			alert('网络错误!');
		}
	});
}


//多文件上传公共方法
function uploadPhoto(photo,callback){
    //formdata
    var formData = new FormData();
    formData.append('photo',photo);
    $.ajax({
        url:'/upload/upload_photo',
        contentType:false,
        processData:false,
        data:formData,
        type:'POST',
        success:function(data){
            if(data.code == 0){
                callback(data);
            }else{
                //data = $.parseJSON(data);
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    });
}



//验证下拉框
function checkSelectForm(selectClass){
    var flag = true;
    $("."+selectClass).each(function(i,e){
        if($(e).val() ==-1){
            showWarningMsg($(e).attr('tips'));
            flag = false;
            return false;
        }
    });
    return flag;
}

//验证日期
function checkDateTime(dateTimeClass) {
    var flag = true;
    $(dateTimeClass).each(function (i, e) {
        if ($(e).val() == "" || $(e).val() == null) {
            showWarningMsg($(e).attr('tips'));
            flag = false;
            console.log(e);
            return false;
        }
    });
    return flag;
}

function checkSelected(){
    if ($("input[name='ids[]']").length == $("input[name='ids[]']:checked").length) {
        $('#check-all').attr('checked', true);
    } else {
        $('#check-all').attr('checked', false);
    }
}

$(document).ready(function(){
    $("#order-auth-btn").click(function(){
        $.confirm({
            title: '订单验证提示',
            content: '' +
            '<form action="" class="formName">' +
            '<div class="form-group">' +
            '<input type="text" placeholder="请输入订单编号" class="orderSn form-control" required maxlength="18" /><br/>' +
            '<input type="text" placeholder="请输入订单手机号" class="phone form-control" maxlength="11" required />' +
            '</div>' +
            '</form>',
            buttons: {
                formSubmit: {
                    text: '提交',
                    btnClass: 'btn-blue',
                    action: function () {
                        var orderSn = this.$content.find('.orderSn').val();
                        if(!orderSn){
                            $.alert('请您输入订单编号');
                            return false;
                        }
                        var phone = this.$content.find('.phone').val();
                        if(!phone){
                            $.alert('请您输入订单手机号');
                            return false;
                        }
                        ajaxRequest('/system/auth_order','post',{orderSn:orderSn,phone:phone},function(){
                            showSuccessMsg('验证成功！',function(){});
                            window.location.reload();
                        });
                        return false;
                    }
                },
                cancel: {
                    text: '取消'
                },
            },
            onContentReady: function () {
                var jc = this;
                this.$content.find('form').on('submit', function (e) {
                    e.preventDefault();
                    jc.$$formSubmit.trigger('click');
                });
            }
        });
    });

});