<script>

    var currentUrl = window.location.pathname;
    var curs = currentUrl.split("/");
    currentUrl = curs[2];
    var currentUrl1 = curs[3];
    $(".left_tab_1").each(function (i, e) {
        if (currentUrl.includes($(e).find("a").attr("href").split("/")[2])) {
            $(e).addClass("tauction");
        }
    });

    $(".per_left_tab_list_2").each(function (i, e) {
        if (currentUrl.includes($(e).find("a").attr("href").split("/")[2]) && currentUrl1.includes($(e).find("a").attr("href").split("/")[3])) {
            $(e).addClass("tauction");
        }
    });


    $(".tauction").addClass("left-active");
    /*$(".tauction-1").addClass("left-active");*/

    layui.use('upload', function() {
        var $ = layui.jquery
            , upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#add-pic-btn'
            , url: '/upload/upload_pic' //改成您自己的上传接口
            ,size:1024
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#show-picture-img').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                console.log(res);
                if (res.code == 0) {
                    //上传成功
                    $.ajax({
                        url:'/home/user/updatePic',
                        type:'post',
                        data:{
                            headPic:res.data,
                        },
                        success:function (data) {
                            if(data.code == 0){
                                successMsg("头像上传成功")
                                setTimeout(function () {
                                    window.location.reload();
                                },1000)
                            }else{
                                errorMsg(data.msg)
                            }
                        },
                        error:function (data) {
                            errorMsg("网络错误")
                        }
                    })
                }else{
                    return layer.msg('上传失败');
                }
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
    })
</script>
