<!-- 新的头部 -->
<body>

<!-- 公用头部 -->
<div class="head">
    <div class="contain">
        <div class="head_top">
            <div class="head_logo">
                <a href="/home/index/index">
                    <img src="/home/picture/head_logo.png" alt="head_logo"/>
                </a>
            </div>
            <div class="logo_font">猿来如此竞拍</div>

            <div class="head_input_bg">
                <input type="text" placeholder="输入竞拍关键字搜索" value="" id="search_value"/>
                <img src="/home/picture/head_search.png" alt="head_search" onclick="searchProject()"/>
            </div>

            <span id="userDiv">
                <#if ylrc_home ??>
                    <div class="admin_right" style="float: right;line-height: 0px;width: 300px;">
                          <div class="head_user_icon" style="width: 34px;height: 34px;float: left;margin-top: 18px;padding-left: 17px;">
                              <#if ylrc_home.headPic??>
                                  <#if ylrc_home.headPic?length gt 0>
                                      <img style="height:34px;width:34px" src="/photo/view?filename=${ylrc_home.headPic}"/>
                                   <#else>
                                      <img style="height:34px;width:34px" src="/home/images/user_icon.jpg"/>
                                  </#if>
                              <#else>
                                  <img style="height:34px;width:34px" src="/home/images/user_icon.jpg"/>
                              </#if>
                          </div>

                          <div class="head_user head_personal_user">
                              ${ylrc_home.mobile?substring(0,3)}***${ylrc_home.mobile?substring(8)}
                          </div>
                          <a style="margin-left: 17%;" href="/home/user/index" class="head_user head_user_a">个人中心</a>
                          <div class="head_user" style="float:right" id='head_logout'>
                            退出
                          </div>
                    </div>
                <#else>
                    <div class="head_login frist_login" style="margin-top: 14px; cursor:pointer;" id="head_login">
		                <img src="/home/picture/head_login.png"  alt="head_login"/>
		                <span>用户登录</span>
		             </div>
                    <div class="head_sign last_sign" style="margin-top: 14px; cursor:pointer;" id="head_sign">
                        <img src="/home/picture/head_sign.png"  alt="head_sign"/>
                        <span>注册</span>
                    </div>
                </#if>
            </span>
        </div>
<#if ylrc_auth??>
    <#if ylrc_auth!= 1>
    <div class="top-nav" id="show-copyright">
        <div class="container fn-clear" align="center">
            <p style="color:red;font-size:16px;">
                本系统由<a href="https://www.yuanlrc.com/product/details.html?pid=413">【猿来入此】</a>发布，请认准官网获取，官网获取的正版源码提供免费更新升级！
                <a href="javascript:alert('请登录后台首页填写订单号进行验证，验证通过后刷新此页面，版权信息会自动消失！')" id="order-auth-btn">点此去版权</a>
                <a href="https://www.yuanlrc.com/">点此进入官网</a>
            </p>
        </div>
    </div>
    </#if>
</#if>
        <div class="head_tab">
            <a id="homePage" href="/home/index/index" class="head_tab_select">首页</a>
            <a id="auctionHall" href="/home/bidding/transaction">产权交易</a>
            <a id="auctionList" href="/home/agency/list">竞价大厅</a>
            <a id="mechanism"href="/home/org/list">交易机构</a>
            <a id="solution" href="/home/common_problem/list">常见问题</a>
            <a id="head_news"href="/home/news/list">新闻资讯</a>
            <a id="announcements" href="/home/inform/list">通知</a>
        </div>
    </div>
</div>


<style type="text/css">

    .sa-confirm-button-container button {
        background: #D01219 !important;
    }

    .footer_title{
        height: auto !important;
        line-height: 25px !important;
        font-size: 16px !important;
        color: #FFFFFF !important;
        padding-top: 38px !important;
    }
    .captitle-content{
        float: left;
        font-size: 14px;
        margin-left: 15px;
        max-width:200px;
        word-wrap:break-word;
        text-overflow:ellipsis;
        white-space:nowrap;
        overflow:hidden;
    }

    /*.captitle-content:hover{*/
    /*    max-width:800px;*/
    /*    white-space:normal;*/
    /*    overflow:auto;*/
    /*}*/

    #userDiv img
    {
        margin-bottom:0px;
        margin-left: 6px;
        margin-top: 0px;
        margin-right: 0px;
        vertical-align: middle;
    }


</style>

