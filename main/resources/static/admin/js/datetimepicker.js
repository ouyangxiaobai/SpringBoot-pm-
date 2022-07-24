/**
 * 日期js
 */
$("#add-startTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true,
    startDate: new Date()
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#add-endTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#add-endTime").datetimepicker('setStartDate', null);
    }
});
$("#add-endTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#add-biddingStartTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#add-biddingStartTime").datetimepicker('setStartDate', null);
    }
});

$("#add-biddingStartTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#add-biddingEndTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#add-biddingEndTime").datetimepicker('setStartDate', null);
    }
});
$("#add-biddingEndTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
});

$("#edit-startTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true,
    startDate: new Date()
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#edit-endTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#edit-endTime").datetimepicker('setStartDate', null);
    }
});
$("#edit-endTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#edit-biddingStartTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#edit-biddingStartTime").datetimepicker('setStartDate', null);
    }
});

$("#edit-biddingStartTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#edit-biddingEndTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#edit-biddingEndTime").datetimepicker('setStartDate', null);
    }
});

$("#edit-biddingEndTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
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