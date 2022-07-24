<!--项目报名查询内容显示  -->
<script type="text/html" id="dataSign">
    {{each list as value i}}
    <tr>
        <td>{{value['auctionSeq']}}</td>
        <td>{{value['auctionTitle']}}</td>
        <td>{{value['signStartTime']}}</td>
        <td>{{value['auctionStartTime']}}</td>
        <td>{{value['organizationName']}}</td>
        <td>{{value['projectStatus']}}</td>
        <td>
            {{if value['ifSignCenter'] == '3'}}
            <a class="per_table_a_left" target="_blank" href="/Auction/toEnroll?AuctionId={{value.id}}&bond={{value.bond}}&LandName={{value.targetName}}">报名</a>
            {{/if}}
            <a class="per_table_a_right" target="_blank"  href="/auction/detail-{{value.id}}">详情</a>
        </td>
    </tr>
    {{/each}}
</script>
<script>
    var showPic=0;
    //是否报名
    var isSign=false;
    //是否存在审核中报名
    var isSignChecking=false;
    //报名类型
    var signType="";
    //查询结果
    var queryInfo=null;
    //附件绑定id
    var uploaderId="";
    var moneyCerti=[];
    //查看打款凭证
    function viewFinalPay(id,applyId){
        //查询凭证
        $.ajax({
            type : 'post',
            url : '/auction/getApplyPayAttachment?acid='
                + id+'&applyId='+applyId,
            dataType : "json",
            success : function(data) {
                //循环将图片路径放入
                moneyCerti=data.picList;
                for (var i = 0; i < data.picList.length; i++) {
                    var datas = data.picList[i];
                    var url="<div class='swiper-slide'><img src='"+datas.url+"' data-index='"+i+"'/></div>";
                    //将html画出来
                    $(".swiper-wrapper").append(url);
                }
                //$(".lafitewu_cover,.alert_banner").show();
                var ziv = new ZxImageView(_config, moneyCerti);
                ziv.view(0);
            },
            error : function(e) {

            }
        });

    }


    // 初始化参数
    var _config = {
        // 分页mouseover切换图片
        paginationable: true,
        // 显示关闭按钮
        showClose: true,
        // 显示上一张/下一张箭头
        showSwitchArrow: true,
        // 显示分页导航栏
        showPagination: true,
        // 显示工具栏
        showToolbar: true,
        // 缩放
        scalable: true,
        // 旋转
        rotatable: true,
        // 移动
        movable: true,
        // 键盘配置
        keyboard: {
            // scale: ['equal', 'minus']
        }
    }
    //初始化预览
    function viewPhoto(obj){
        //alert("!!!!!!!!");
        console.log(obj);
        var $oo = $(obj);
        var $images = $oo.parent().parent().parent().find("img");
        var photoUrl = $oo.parent().siblings(".mu-img-wrap").children("img")[0].src;
        var urls = []
        console.log($images.length);
        for (var i = 0; i < $images.length; i++) {
            console.log($images[i]);
            urls.push($images[i].src);
        }
        urls.shift();
        console.log(urls);

        var ziv = new ZxImageView(_config, urls);
        console.log(ziv);
        for (var i = 0; i < urls.length; i++) {
            if(photoUrl==urls[i]){
                ziv.view(i);
            }
        }
    }

    //我的项目查询按钮
    var maxPageNumber = 5;
    var myProjectOptionsPage = {
        bootstrapMajorVersion : 3,
        currentPage : 1,
        totalPages : maxPageNumber,
        numberOfPages : 5,
        size : 'small',
        useBootstrapTooltip : true,
        alignment : 'right',
        tooltipTitles : function(type, page, current) {
            switch (type) {
                case "first":
                    return "第一页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "最后一页";
                case "page":
                    return "第" + page + "页";
            }
        },
        onPageClicked : function(e, originalEvent, type, page) {
            myProjectContract.list(page);
        }
    };

    var myProjectContract = {
        list : function(page) {
            $("#myProjectData").empty();
            $.ajax({
                async : true,
                type : "POST",
                dataType : "json",
                url : "/auction/myItemList?"+$("#search-form-myProject").serialize(),
                data : {
                    "page" : page,
                    "rows" : 5
                },
                error : function() {
                    $("#myProjectData").html("查询失败");
                },
                success : function(reps) {
                    if(undefined != reps && undefined!= reps.data && undefined != reps.pagination && 1 < reps.pagination.maxPageNumber){
                        myProjectOptionsPage.currentPage = reps.pagination.pageNumber;
                        myProjectOptionsPage.numberOfPages = reps.pagination.pageSize;
                        myProjectOptionsPage.totalPages = reps.pagination.maxPageNumber || 1;
                        $('#pageInitialization').bootstrapPaginator(myProjectOptionsPage);
                        $("#pageInitialization").css({"display":"block"});
                        $("#project").css({"display":"block"});
                        $("#max_page").html(reps.pagination.maxPageNumber);
                    }else{
                        $("#pageInitialization").css({"display":"none"});
                        $("#project").css({"display":"none"});
                    }
                    var data = {};
                    data['list'] = reps.data;
                    $("#myProjectData").empty();
                    if (data['list']) {
                        var html = template('dataProject', data);
                        $("#myProjectData").html(html);
                    }
                }
            });
        }
    }

    //项目报名查询按钮
    var projectSignOptionsPage = {
        bootstrapMajorVersion : 3,
        currentPage : 1,
        totalPages : maxPageNumber,
        numberOfPages : 5,
        size : 'small',
        useBootstrapTooltip : true,
        alignment : 'right',
        tooltipTitles : function(type, page, current) {
            switch (type) {
                case "first":
                    return "第一页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "最后一页";
                case "page":
                    return "第" + page + "页";
            }
        },
        onPageClicked : function(e, originalEvent, type, page) {
            projectSignContract.list(page);
        }
    };

    var projectSignContract = {
        list : function(page) {
            $("#projectSignData").empty();
            $.ajax({
                async : true,
                type : "POST",
                dataType : "json",
                url : "/auction/userSignItmeList?"+$("#search-form-projectSign").serialize(),
                data : {
                    "page" : page,
                    "rows" : 5
                },
                error : function() {
                    $("#projectSignData").html("查询失败");
                },
                success : function(reps) {
                    if(undefined != reps && undefined!= reps.data && undefined != reps.pagination && 1 < reps.pagination.maxPageNumber){
                        projectSignOptionsPage.currentPage = reps.pagination.pageNumber;
                        projectSignOptionsPage.numberOfPages = reps.pagination.pageSize;
                        projectSignOptionsPage.totalPages = reps.pagination.maxPageNumber || 1;
                        $('#pageInitializationSign').bootstrapPaginator(projectSignOptionsPage);
                        $("#pageInitializationSign").css({"display":"block"});
                        $("#sign").css({"display":"block"});
                        $("#max_page_sign").html(reps.pagination.maxPageNumber);
                    }else{
                        $("#pageInitializationSign").css({"display":"none"});
                        $("#sign").css({"display":"none"});
                    }
                    var data = {};
                    data['list'] = reps.data;
                    if (data['list']) {
                        var html = template('dataSign', data);
                        $("#projectSignData").html(html);
                    }
                }
            });
        }
    }

    //执行一个laydate实例
    var cartimeDate = laydate.render({
        elem: '.per_data_input1', //指定元素
        done: function(value,date) {
            if(value !== '') {
                date.month = date.month -1;
                returntimeDate.config.min = date;
            }
        }
    });
    var returntimeDate = laydate.render({
        elem: '.per_data_input2', //指定元素
        done: function(value,date) {
            if(value !== '') {
                date.month = date.month -1;
                cartimeDate.config.max = date;
            }
        }
    });
    var cartimeDate1 = laydate.render({
        elem: '.per_data_input3', //指定元素
        done: function(value,date) {
            if(value !== '') {
                date.month = date.month -1;
                returntimeDate1.config.min = date;
            }
        }
    });
    var returntimeDate1 = laydate.render({
        elem: '.per_data_input4', //指定元素
        done: function(value,date) {
            if(value !== '') {
                date.month = date.month -1;
                cartimeDate1.config.max = date;
            }
        }
    });
    var cartimeDate2 = laydate.render({
        elem: '.per_data_input5', //指定元素
        done: function(value,date) {
            if(value !== '') {
                date.month = date.month -1;
                returntimeDate2.config.min = date;
            }
        }
    });
    var returntimeDate2 = laydate.render({
        elem: '.per_data_input6', //指定元素
        done: function(value,date) {
            if(value !== '') {
                date.month = date.month -1;
                cartimeDate2.config.max = date;
            }
        }
    });
    var cartimeDate3 = laydate.render({
        elem: '.per_data_input7', //指定元素
        done: function(value,date) {
            if(value !== '') {
                date.month = date.month -1;
                returntimeDate3.config.min = date;
            }
        }
    });
    var returntimeDate3 = laydate.render({
        elem: '.per_data_input8', //指定元素
        done: function(value,date) {
            if(value !== '') {
                date.month = date.month -1;
                cartimeDate3.config.max = date;
            }
        }
    });

    var attachIds = [];
    //我的项目查询按钮
    $("#myProjectQuery").on("click",function(e){
        if($(".per_data_input1").val()!=""&&$(".per_data_input2").val()!=""&&$(".per_data_input2").val()<$(".per_data_input1").val()){
            swal({title:'报名结束时间不能早于报名开始时间！',text:"",confirmButtonColor: "#D01219",});
            return;
        }
        if($(".per_data_input3").val()!=""&&$(".per_data_input4").val()!=""&&$(".per_data_input3").val()>$(".per_data_input4").val()){
            swal({title:'竞价结束时间不能早于竞价开始时间！',text:"",confirmButtonColor: "#D01219",});
            return;
        }
        myProjectContract.list(1);
    });
    //项目报名查询按钮
    $("#projectSignQuery").on("click",function(e){
        if($(".per_data_input5").val()!=""&&$(".per_data_input6").val()!=""&&$(".per_data_input5").val()>$(".per_data_input6").val()){
            swal({title:'报名结束时间不能早于报名开始时间！',text:"",confirmButtonColor: "#D01219",});
            return;
        }
        if($(".per_data_input7").val()!=""&&$(".per_data_input8").val()!=""&&$(".per_data_input7").val()>$(".per_data_input8").val()){
            swal({title:'竞价结束时间不能早于竞价开始时间！',text:"",confirmButtonColor: "#D01219",});
            return;
        }
        projectSignContract .list(1);
    });

    //检查该客户是否有填写报名信息
    checkIsSign();
    showDetailInfo();
    //根据报名状态显示或隐藏资料管理


    //资料修改提交按钮
    $(".per_edit_upload_btn").click(function() {
        //验证用户是否存在报名待审核的情况，如果是则不让提交
        checkIsSign();
        if(isSignChecking){//审核中
            swal({title:'您存在审核中的报名信息，不能提交修改资料信息！',text:"",confirmButtonColor: "#D01219",});
            return;
        }
        //验证填写信息的正确性
        checkDatas();
    });

    // 附件默认图片显示
    var Pic = "/foreside2/images/upload.png";

    function init() {
        $(".per_left_jinpai").attr("src","/foreside2/images/left_jingpai.png");
        $(".per_left_msg").attr("src","/foreside2/images/left_msg.png");
        $(".per_left_tab_list").removeClass("per_left_tab_select");

        $(".per_right_top1,.per_right_top2,.per_right_top3,.per_edit_password,.per_edit_project,.per_sign_up,.per_edit_details,.per_question_con").hide();
    }
    $(".per_left_tab_list").click(function() {
        init();
        var Index = $(this).index();
        $(".per_left_tab_list").eq(Index).addClass("per_left_tab_select");
        if(Index == 0) {
            $(".per_left_jinpai").attr("src","/foreside2/images/left_jingpaiSelect.png");
            $(".per_left_tab_list").eq(1).addClass("per_left_tab_select");
            //清空项目报名条件值
            $("#projectSignStatus").val("1");
            $("#projectSignStartTime").val("");
            $("#projectSignEndTime").val("");
            $("#projectSignauctionStartTime").val("");
            $("#projectSignauctionendTime").val("");
            $("#projectSignData").empty();
            $("#pageInitializationSign").css({"display":"none"});
            $("#sign").css({"display":"none"});
            $(".per_right_top3,.per_sign_up").show();
            projectSignContract .list(1);
        }
        // 项目报名
        if(Index == 1) {
            //清空条件值
            $("#projectSignStatus").val("1");
            $("#projectSignStartTime").val("");
            $("#projectSignEndTime").val("");
            $("#projectSignauctionStartTime").val("");
            $("#projectSignauctionendTime").val("");
            $("#projectSignData").empty();
            $("#pageInitializationSign").css({"display":"none"});
            $("#sign").css({"display":"none"});
            $(".per_right_top3,.per_sign_up").show();
            projectSignContract .list(1);
        }
        // 我的项目
        if(Index == 2) {
            //清空条件值
            $("#myProjectStatus").val("");
            $("#myProjectsignStartTime").val("");
            $("#myProjectsignEndTime").val("");
            $("#myProjectauctionStartTime").val("");
            $("#myProjectauctionendTime").val("");
            $("#myProjectData").empty();
            $("#pageInitialization").css({"display":"none"});
            $("#project").css({"display":"none"});
            $(".per_right_top2,.per_edit_project").show();
            myProjectContract.list(1);
        }
        if(Index < 3) {
            $(".per_left_tab_list").eq(0).addClass("per_left_tab_select");
            $(".per_left_jinpai").attr("src","/foreside2/images/left_jingpaiSelect.png");
        }
        // 信息管理
        if(Index == 3) {
            //window.location.reload();
            //初始化信息管理的数据
            showDetailInfo();

            $(".per_left_msg").attr("src","/foreside2/images/left_msgSelect.png");
            $(".per_right_top1").show();

            $(".per_btn_list").removeClass("per_btn_list_select");
            $("#pwdMange").addClass("per_btn_list_select");
            $(".per_edit_password").show();
        }
    });

    // 修改密码、资料管理库
    $(".per_btn_list").click(function() {
        $(".per_btn_list").removeClass("per_btn_list_select");
        $(".per_edit_details,.per_edit_password").hide();

        $(this).addClass("per_btn_list_select");
        if($(this).index() == 0) {
            $(".per_edit_password").show();
        }else {
            $(".per_edit_details").show();
        }
    });

    //竞拍帮助
    $(".per_left_que_list").click(function() {
        init();
        $(".per_question_con").show();
    });

    //查询客户是否有报名信息
    function checkIsSign(){
        $.ajax({
            async : false,
            type : "POST",
            dataType : "json",
            url : "/auction/checkIsSign?userId="+localStorage.getItem('userId'),
            data : {},
            error : function() {
                alert("查询出错！");
            },
            success : function(reps) {
                if(reps!=null && reps.code=='200'){
                    if(reps.data!=null){//有报名信息
                        isSign=true;
                        signType=reps.data.applyType;
                        queryInfo=reps.data;
                        if(reps.data.num!=0){
                            isSignChecking=true;
                        }else{
                            isSignChecking=false;
                        }
                    }else{//没有报名信息，需要隐藏资料库管理
                        isSign=false;
                    }
                }
            }
        });
    };

    function showDetailInfo(){
        if(!isSign){//未报名，隐藏
            $("#dataMange").hide();
            //初始化修改密码的值
            $("input[name='password']").val("");
            $("input[name='verifyPassword']").val("");
            $("input[name='identifyCode']").val("");
        }else{//已报名，显示
            $("#dataMange").show();
            $("#pwdMange").show();
            //根据报名类型显示资料填写模块
            $(".per_edit_1").hide();
            $(".per_edit_2").hide();
            $(".per_edit_3").hide();
            $(".per_edit_4").hide();
            if(signType=="0"){//个人
                $("input[name='idCard']").val(queryInfo.idCard);
                $(".per_edit_4").show();
                uploaderId="uploader4";
            }else if(signType=="1"){//单位
                $("input[name='unitName']").val(queryInfo.unitName);
                $(".per_edit_1").show();
                uploaderId="uploader1";
            }else if(signType=="2"){//合作社
                $("input[name='unitName']").val(queryInfo.unitName);
                $(".per_edit_2").show();
                uploaderId="uploader2";
            }else {//家庭农场
                $("input[name='unitName']").val(queryInfo.unitName);
                $(".per_edit_3").show();
                uploaderId="uploader3";
            }
            //设初始值  str.substring(0,3)+"****"+str.substring(7,11)
            $("input[name='personName']").val(queryInfo.personName);
            $("input[name='phoneShow']").val(queryInfo.phone.substring(0,3)+"****"+queryInfo.phone.substring(7,11));
            $("input[name='phone']").val(queryInfo.phone);
            $("input[name='address']").val(queryInfo.address);
            $("input[name='openingBank']").val(queryInfo.openingBank);
            $("input[name='payAccount']").val(queryInfo.payAccount);
            $("input[name='accountName']").val(queryInfo.accountName);
            attachIds=[];
            //上传控件1
            $('#'+uploaderId).myuploader({
                server: '/Auction/anon/uploadPic',
                swf:'/foreside2/js/vendor/webuploader.swf',
                accept: {
                    extensions: 'jpg,jpeg,bmp,png'
                },
                fileNumLimit: 10,
                fileSizeLimit: 20 * 1024 * 1024, // 20 M，文件总大小是否超出限制
                fileSingleSizeLimit: 4 * 1024 * 1024 // 4 M,验证单个文件大小是否超出限制
            }, {
                initUploaderCallback:function(obj){
                },
                addFileCallback: function(file) {
                },
                removeFileCallback: function(file) {
                    var url = $('#' + file.id).find('.mu-remove').attr('del');
                    attachIds.splice(jQuery.inArray(url, attachIds),1);
                    return true;
                },
                uploadSuccessCallback:function(file,res) {
                    console.log(res);
                    $('#' + file.id).find('.mu-remove').attr('del',res.fileUrl);
                    $('#' + file.id).find('.mu-preview').attr('href','javascript:void(0);');
                    $('#' + file.id).find('.mu-preview').attr('onclick','viewPhoto(this);return false;');
                    $('#' + file.id).find('.mu-preview').attr('src','http://img4s.tuliu.com/'+res.fileUrl);
                    $('#' + file.id).find('.mu-preview').data('src','http://img4s.tuliu.com/'+res.fileUrl);
                    attachIds.push(res.fileUrl);
                    $('#' + file.id).find('img').attr('src','http://img4s.tuliu.com/'+res.fileUrl);
                },
                initUploaderImgCallback:function(obj){
                }
            });


            //图片附件
            var myuploader = $('#'+uploaderId).data('myuploader');
            $('#imageDiv1').find('.mu-thumbnail-item').remove();
            if(queryInfo.attachmentList && queryInfo.attachmentList != ''){
                queryInfo.attachmentList.forEach(function(item,ind){
                    myuploader.createThumb('http://img4s.tuliu.com/'+item.url,true, function(elem){
                        $('#' + elem.id).find('.mu-remove').attr('del',item.url);
                        attachIds.push(item.url);
                    },function(elem){
                        var url = $('#' + elem.id).find('.mu-remove').attr('del');
                        attachIds.splice(jQuery.inArray(item.url, attachIds),1);
                        return true;
                    });
                });
            }
            $(".mu-thumbnail-item .mu-preview").attr('href','javascript:void(0);');
            $(".mu-thumbnail-item .mu-preview").attr('onclick','viewPhoto(this);return false;');
        }
    }

    //检查上传资料信息
    function checkDatas(){
        var unitName="";
        var personName="";
        var phone="";
        var idCard="";
        var address="";
        var openingBank="";
        var payAccount="";
        var accountName="";
        if(signType=="0"){//个人
            personName= $(".per_edit_4 input[name='personName']").val();
            idCard= $(".per_edit_4 input[name='idCard']").val();
            phone= $(".per_edit_4 input[name='phone']").val();
            address= $(".per_edit_4 input[name='address']").val();
            openingBank= $(".per_edit_4 input[name='openingBank']").val();
            payAccount= $(".per_edit_4 input[name='payAccount']").val();
            accountName= $(".per_edit_4 input[name='accountName']").val();
        }else if(signType=="1"){//单位
            unitName= $(".per_edit_1 input[name='unitName']").val();
            personName= $(".per_edit_1 input[name='personName']").val();
            phone= $(".per_edit_1 input[name='phone']").val();
            address= $(".per_edit_1 input[name='address']").val();
            openingBank= $(".per_edit_1 input[name='openingBank']").val();
            payAccount= $(".per_edit_1 input[name='payAccount']").val();
            accountName= $(".per_edit_1 input[name='accountName']").val();
            if(unitName==""){
                swal({title:'请先输入单位名称',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(unitName.length>100){
                swal({title:'单位名称长度不能大于100',text:"",confirmButtonColor: "#D01219",});
                return;
            }
        }else if(signType=="2"){//合作社
            unitName= $(".per_edit_2 input[name='unitName']").val();
            personName= $(".per_edit_2 input[name='personName']").val();
            phone= $(".per_edit_2 input[name='phone']").val();
            address= $(".per_edit_2 input[name='address']").val();
            openingBank= $(".per_edit_2 input[name='openingBank']").val();
            payAccount= $(".per_edit_2 input[name='payAccount']").val();
            accountName= $(".per_edit_2 input[name='accountName']").val();
            if(unitName==""){
                swal({title:'请先输入合作社名称',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(unitName.length>100){
                swal({title:'合作社名称长度不能大于100',text:"",confirmButtonColor: "#D01219",});
                return;
            }
        }else {//家庭农场
            unitName= $(".per_edit_3 input[name='unitName']").val();
            personName= $(".per_edit_3 input[name='personName']").val();
            phone= $(".per_edit_3 input[name='phone']").val();
            address= $(".per_edit_3 input[name='address']").val();
            openingBank= $(".per_edit_3 input[name='openingBank']").val();
            payAccount= $(".per_edit_3 input[name='payAccount']").val();
            accountName= $(".per_edit_3 input[name='accountName']").val();
            if(unitName==""){
                swal({title:'请先输入合作社名称',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(unitName.length>100){
                swal({title:'合作社名称长度不能大于100',text:"",confirmButtonColor: "#D01219",});
                return;
            }
        }

        if(signType=="0"){//个人
            //判断身份证格式
            var re = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            if(personName==""){
                swal({title:'请先输入姓名',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(personName.length>50){
                swal({title:'姓名长度不能大于50',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(idCard==""){
                swal({title:'请先输入身份证号码',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(!re.test(idCard)){
                swal({title:'请输入正确的身份证格式',text:"",confirmButtonColor: "#D01219",});
                return;
            }
        }else{
            if(personName==""){
                swal({title:'请先输入法定代表人',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(personName.length>50){
                swal({title:'法定代表人长度不能大于50',text:"",confirmButtonColor: "#D01219",});
                return;
            }
        }

        if(address==""){
            swal({title:'请先输入地址',text:"",confirmButtonColor: "#D01219",});
            return;
        }else if(address.length>500){
            swal({title:'地址长度不能大于500',text:"",confirmButtonColor: "#D01219",});
            return;
        }else if(openingBank==""){
            swal({title:'请先输入开户行',text:"",confirmButtonColor: "#D01219",});
            return;
        }else if(openingBank.length>100){
            swal({title:'开户行长度不能大于100',text:"",confirmButtonColor: "#D01219",});
            return;
        }else if(payAccount==""){
            swal({title:'请先输入账号',text:"",confirmButtonColor: "#D01219",});
            return;
        }else if(payAccount.length>30){
            swal({title:'账号长度不能大于30',text:"",confirmButtonColor: "#D01219",});
            return;
        }else if(accountName==""){
            swal({title:'请先输入账号姓名',text:"",confirmButtonColor: "#D01219",});
            return;
        }else if(accountName.length>50){
            swal({title:'账号姓名长度不能大于50',text:"",confirmButtonColor: "#D01219",});
            return;
        }

        //附件检查是否上传
        if(signType=="0"){//个人
            $("#attachIds4").val(attachIds);
            if ($('#attachIds4').val() == null || $('#attachIds4').val() == "") {
                swal({title:'请先上传身份证',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(attachIds.length>10){
                swal({title:'最多只能上传10个附件',text:"",confirmButtonColor: "#D01219",});
                return;
            }

            attachIds= $(".per_edit_4 input[name='attachIds']").val();
        }else if(signType=="1"){//单位
            $("#attachIds1").val(attachIds);
            if ($('#attachIds1').val() == null || $('#attachIds1').val() == "") {
                swal({title:'请先上传相关附件',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(attachIds.length>10){
                swal({title:'最多只能上传10个附件',text:"",confirmButtonColor: "#D01219",});
                return;
            }
            attachIds= $(".per_edit_1 input[name='attachIds']").val();
        }else if(signType=="2"){//合作社
            $("#attachIds2").val(attachIds);
            if ($('#attachIds2').val() == null || $('#attachIds2').val() == "") {
                swal({title:'请先上传相关附件',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(attachIds.length>10){
                swal({title:'最多只能上传10个附件',text:"",confirmButtonColor: "#D01219",});
                return;
            }
            attachIds= $(".per_edit_2input[name='attachIds']").val();
        }else if(signType=="3"){//家庭农场
            $("#attachIds3").val(attachIds);
            if ($('#attachIds3').val() == null || $('#attachIds3').val() == "") {
                swal({title:'请先上传相关附件',text:"",confirmButtonColor: "#D01219",});
                return;
            }else if(attachIds.length>10){
                swal({title:'最多只能上传10个附件',text:"",confirmButtonColor: "#D01219",});
                return;
            }
            attachIds= $(".per_edit_3 input[name='attachIds']").val();
        }

        //验证通过，更新信息
        $.ajax({
            async : false,
            type : "POST",
            dataType : "json",
            url : "/auction/updateInfo?userId="+localStorage.getItem('userId'),
            data : {
                "id":queryInfo.id,
                "personName" : personName,
                "idCard" : idCard,
                "unitName" : unitName,
                "phone" : phone,
                "address" : address,
                "openingBank" : openingBank,
                "payAccount" : payAccount,
                "accountName" : accountName,
                "attachIds":attachIds
            },
            error : function() {
                alert("更新出错！");
            },
            success : function(reps) {
                if(reps!=null && reps.code=='200'){
                    //刷新页面
                    swal({
                            title: "",
                            text: "操作成功！",
                            type: "info",
                            showCancelButton: false,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "是",
                            cancelButtonText: "否",
                            closeOnConfirm: false,
                            closeOnCancel: false
                        },
                        function(isConfirm){
                            if (isConfirm) {
                                window.location.reload();
                            }
                        });
                }
            }
        });
    };
</script>
<script type="text/javascript">
    //此处开始 updatePass代码
    var countdown = 120;
    var timeOut;
    $().ready(function(){
        //取消
        $("#updatePass_cancel").on("click",function(){
            $("#password").val("");
            $("#verifyPassword").val("");
            $("#identifyCode").val("");
            $("#codeId").val("");
            $(".code_div").text("获取验证码")
            $(".code_div").removeClass("notclick");
            countdown = 120;
            clearTimeout(timeOut);
        });
        //提交表单
        $("#updatePass_sure").on("click",function(){
            var password = $("#password").val();
            var verifyPassword = $("#verifyPassword").val();
            var identifyCode = $("#identifyCode").val();
            var codeId = $("#codeId").val();

            var flag = true;
            console.log("password == "+password+"  verifyPassword == "+verifyPassword+"  identifyCode=="+identifyCode+"  codeId=="+codeId);

            if(null==identifyCode || ""==identifyCode){
                swal({title:'请输入短信验证码',text:"",confirmButtonColor: "#D01219",});
                flag = false;
            }
            if(null==codeId || "" ==codeId){
                swal({title:'请先获取短信验证码',text:"",confirmButtonColor: "#D01219",});
                flag = false;
            }

            if(null==password || ""==password){
                swal({title:'请先输入密码',text:"",confirmButtonColor: "#D01219",});
                flag = false;
            }else if(false == valPassword()){
                swal({title:'密码最少六位字符且必须同时含有数字和字母',text:"",confirmButtonColor: "#D01219",});
                flag = false;
            }

            if(null==verifyPassword || ""==verifyPassword){
                swal({title:'请输入确认密码',text:"",confirmButtonColor: "#D01219",});
                flag = false;
            }else if(verifyPassword!= password){
                swal({title:'请确认两次输入的密码一致',text:"",confirmButtonColor: "#D01219",});
                flag = false;
            }

            if(flag==false){
                return;
            }

            console.log(flag);

            if (flag==true) {
                $('.updatePass_sure').text('提交中...');
                $('.updatePass_sure').addClass("notclick");

                $.ajax({
                    type:'post',
                    url:'/AuctionLogin/updatePass?'+"userPhone="+$("#phoneNumberDiv").html()+"&userId="+localStorage.getItem("userId")+"&"+$("#updatePass").serialize(),
                    dataType:'json',
                    success:function(data){
                        console.log(data);
                        if (data.code == "200") {
                            swal({
                                title: "密码修改成功",
                                //type: "success",
                                confirmButtonColor: "#D01219",
                                button: "确认",
                            });

                            $('.updatePass_sure').text('确定');
                            $('.btn_submit').removeClass("notclick");
                            $("#logOut").trigger("click");

                        }  else {
                            swal("", data.msg, "error");
                            $('.btn_submit').text('确定');
                            $('.btn_submit').removeClass("notclick");
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        swal("", e, "error");

                        $('.btn_submit').text('确定');
                        $('.btn_submit').removeClass("notclick");
                    }
                });
            }else{
                return;
            }
        });


        //验证码触发器
        $(".code_div").on("click", function() {
            var userPhone = $("#phoneNumberDiv").html();
            if(null==userPhone || ""==userPhone){
                swal({title:'请输入手机号码!',text:"",confirmButtonColor: "#D01219",});
                return;
            };
            console.log(isPhoneAvailable(userPhone));
            if(false == isPhoneAvailable(userPhone)){
                swal({title:'请输入正确手机号',text:"",confirmButtonColor: "#D01219",});
                return;
            };
            console.log(userPhone);
            if(null != userPhone && ""!=userPhone){
                $.ajax({
                    url : "/AuctionLogin/veryifyCode_updatePass",
                    data : {
                        "userPhone" : $("#phoneNumberDiv").html(),
                    },
                    type : "post",
                    cache : false,
                    async : false,
                    dataType : "json",
                    success : function(data) {
                        console.log(data);
                        if (data.code == 200 && data.data) {
                            $("#codeId").val(data.data.codeId);
                            settime();
                        } else {
                            swal({title: data.msg ,text:"",confirmButtonColor: "#D01219",});
                        }
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown) {
                        console.log(XMLHttpRequest);
                        console.log(textStatus);
                        console.log(errorThrown);
                        swal({title:'服务器没有返回数据，可能服务器忙，请重试',text:"",confirmButtonColor: "#D01219",});
                    }
                });
            }
        })

        $("#logOut").on("click",function(){
            localStorage.removeItem("token");
            localStorage.removeItem("userName");
            localStorage.removeItem("userPhone");
            localStorage.removeItem("userId");
            $.ajax({
                url : "/AuctionLogin/logOut",
                data : {
                    "userPhone" : $("#phoneNumberDiv").html(),
                },
                type : "post",
                cache : false,
                async : false,
                dataType : "json",
                success : function(data) {
                    console.log(data);
                    if (data.code == 200 ) {
                        window.location.href = "/";
                    } else {
                        swal(data.msg);
                        window.location.href = "/";
                    }
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest);
                    console.log(textStatus);
                    console.log(errorThrown);
                    swal({title:'服务器没有返回数据，可能服务器忙，请重试',text:"",confirmButtonColor: "#D01219",});
                }
            });
        });
    });

    //手机号验证
    function isPhoneAvailable(str) {
        var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
        var flag = myreg.test(str);
        console.log(flag);
        if (myreg.test(str)) {
            return true;
        }
        else{
            return false;
        }
    }
    //倒计时
    function settime() {
        if (countdown == 0) {
            $(".code_div").text("重新获取")
            $(".code_div").removeClass("notclick");
            countdown = 119;
            return;
        } else {
            $(".code_div").addClass("notclick");
            $(".code_div").text(countdown + "秒");
            countdown--;
        }
        timeOut = setTimeout(function() {
            settime()
        }, 1000);
    }
    //密码校验
    function valPassword(){
        var pwd=document.getElementById('password').value;
        var vpwd = document.getElementById('verifyPassword').value;
        var count=0;
        if(/[\d]+/.test(pwd))count++;//数字
        if(/[A-Z]+/.test(pwd))count++;//大写
        if(/[a-z]+/.test(pwd))count++;//小写
        //if(/[~!@#$%^&*]+/.test(pwd))count++;//特殊符号
        //if(/[_]+/.test(pwd))count++;//下划线
        if(pwd.length<6 || count<2){
            swal({title:'密码最少六位字符且必须同时含有数字和字母',text:"",confirmButtonColor: "#D01219",});
            return false;
        }
        return true;
    }

    $(function(e){
        projectSignContract .list(1);
    });

</script>
<script>
    $(".tauction").addClass("left-active");
    $(".tauction-1").addClass("left-active");
</script>