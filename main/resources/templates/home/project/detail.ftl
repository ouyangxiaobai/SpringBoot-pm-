<!DOCTYPE html>
<html>
<title>${title!""}</title>
<meta name="keywords" content="猿来入此竞拍系统"/>
<meta name="description" content="猿来入此竞拍系统、竞拍信息、竞拍价格、竞拍竞价时间、竞拍状态等。"/>
<!-- 新的头部 -->
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="ie-stand">
    <meta name="referrer" content="no-referrer">
    <#include "../common/head-link.ftl"/>
    <link rel="stylesheet" href="/home/css/ylrc_project_details.css">
    <link rel="stylesheet" href="/home/layui/css/layui.css">

    <style>
        /* 新增倒计时样式 */
        .Days_val, .Hour_val, .Min_val, .Second_val {
            font-size: 24px !important;
            font-weight: bold;
            margin-right: 2px;
        }

        .Days_unit, .Hour_unit, .Min_unit, .second_unit {
            display: inline-block;
            font-size: 12px;
            margin-right: 2px;
            line-height: 20px !important;
        }

        /* 新增弹窗确认取消样式 */
        .sweet-alert button {
            font-size: 1.4rem !important;
        }

        .sweet-alert {
            width: 299px;
            height: auto !important;
            left: 56%;
        }

        .lafite_cover {
            z-index: 99;
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            background: rgba(0, 0, 0, .5);
            display: none;
        }

        .lafite_content {
            position: fixed;
            width: 400px;
            padding: 20px 0;
            top: 30%;
            left: 0;
            right: 0;
            background: white;
            margin: auto;
            border-radius: 10px;
        }

        .lafite_content p {
            font-size: 16px;
            text-align: center;
            padding: 30px 0;
            padding-bottom: 20px;
            width: 92%;
            margin-left: 4%;
        }

        .dialog-box-content {
            float: left;
            width: 100% !important;
        }

        .dialog_sure {
            float: left;
            width: 35%;
            height: 40px;
            line-height: 40px;
            text-align: center;
            font-size: 16px;
            color: #C71D2C;
            margin-left: 10%;
            border: 1px solid #C71D2C;
            border-radius: 10px;
            cursor: pointer;
        }

        .dialog_del {
            float: left;
            width: 35%;
            height: 40px;
            text-align: center;
            font-size: 16px;
            border: 1px solid #999;
            margin-left: 10%;
            border-radius: 10px;
            line-height: 40px;
            color: #999;
            cursor: pointer;
        }

        .map_icon img {
            width: 100%;
        }

        .head_tab {
            margin-top: 50px !important;
        }

        .footer_list_con {
            margin-top: 30px !important;
        }

        #modal_txt {
            border-radius: 6px;
            position: fixed;
            top: 50%;
            left: 50%;
            margin-left: -130px;
            margin-top: -30px;
            z-index: 999;
            min-width: 260px;
            height: 60px;
            padding: 0 15px;
            background-color: #C71D2C;
            color: #fff;
            font-size: 18px;
            text-align: center;
            line-height: 60px;
            display: none;
        }

        .nj-project-details20190315 .nj-pj-bidding .transation-block {
            display: block;
        }

        .nj-project-details20190315 .nj-pj-bidding .bidding-price-block .block-item-col {
            display: block !important;
            float: left;
        }

        .sweet-alert h2 {
            font-size: 1.5rem;
            margin: 0 0;
            font-weight: 400;
        }

        .sweet-alert {
            width: 299px;
            height: 170px;
            left: 56%;
        }

        .sweet-alert button {
            font-size: 1px;
        }

        .important-tips {
            position: absolute;
            width: 920px;
            height: 65px;
            font-size: 12px;
            color: #676767;
            line-height: 18px;
            background-color: #fff1f1;
            padding: 4px 13px;
        }

        #targetsDiv .risk-basic-con {
            display: block;
        !important;
            float: left !important;
            width: 96% !important;
            display: -moz-box;
            display: -ms-flexbox;
            -moz-box-orient: vertical;
            -moz-box-direction: normal;
            -ms-flex-direction: column;
            margin: 0 10px 20px 25px !important;
            padding: 2px 10px !important;
            height: auto !important;
            border: 1px solid #E4E4E4;
        }

        #targetsDiv .risk-basic-con span {
            /* display: block; */
            margin-left: 18px;
            width: 48% !important;
            /*height: 40px !important;*/
            font-size: 16px !important;
            color: #333333;
            text-align: left !important;
            line-height: 43px !important;
            float: left;
            /* background: red; */
            margin-top: 4px;
        }

        #page .layui-laypage .layui-laypage-curr .layui-laypage-em {
            background-color: #D90C19;
        }

        .nj-project-details20190315 img {
            max-width: 90%;
        }
    </style>
</head>
    <#include "../common/head.ftl"/>
<div class="lafite_cover">
    <div class="lafite_content">
        <p>
            您本次的报价额为<span id="ts_price">0</span>元,是否确认报价
        </p>

        <div class="dialog_del" onclick="cancel()">取消</div>
        <div class="dialog_sure" onclick="submitPrice(this)" data-projectId="${projectDetail.id}">确认</div>
    </div>
</div>
<div id="modal_txt">恭喜你，报价成功！</div>
<div class="nj-project-details20190315">
    <div class="page-location">
        <a href="/home/index/index">首页</a> <span style="padding: 0 10px;">></span> <a
            href="/home/bidding/transaction">产权交易</a> <span style="padding: 0 10px;">></span><a
            style="color: #D90C19;"> ${projectDetail.title}</a>
    </div>
    <div class="nj-pj-bidding">

        <div class="important-tips">
            重要提示：拍卖之前要了解标的情况、竞买资格、注册报名、保证金缴纳、竞买操作及款项支付方式等内容。如未全面了解相关内容，违反相关规定，您将承担无法参与项目竞买、保证金不予退还等不利后果，请审慎参与竞买，延时周期:如果在拍卖结束前${projectDetail.delayPeriod}
            分钟出价，拍卖结束时间会在出价时间基础上延长${projectDetail.delayPeriod}分钟，直至无人出价，拍卖结束。
        </div>
        <div class="img-block">
            <div class="landD-supply-pshow">
                <div class="landD-supply-bpic">
                    <div class="bpic-max">
                        <ul class="clearfix nj_detail_banner">
                            <#list projectDetail.picture?split(";") as picture>
                                <li><a href="javascript:void(0)"><img
                                        data-original="/photo/view?filename=${picture}"
                                        src="/photo/view?filename=${picture}"
                                        class="lazy"></a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                    <div class="bpic-min">
                        <ul class="clearfix">
                            <#list projectDetail.picture?split(";") as picture>
                                <li>
                                    <a href="javascript:void(0)"><img
                                            data-original="/photo/view?filename=${picture}"
                                            src="/photo/view?filename=${picture}"
                                            class="lazy"></a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                    <div class="bpic-btn bpic-btn-prev">
                        <i class="iconfont icon-jiantouxiangzuo"></i>
                    </div>
                    <div class="bpic-btn bpic-btn-next">
                        <i class="iconfont icon-jiantouxiangyou"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="bidding-price-block">
            <div class="img-block-title">
                <h1 class="auction-detail-content">${projectDetail.title}</h1>
            </div>
            <div class="block-item margin-top-20">
                <#if projectDetail.projectStatus.getCode()==7>
                      <span>成交价</span>
                <span class="color1 font-size1 margin-left-20" id="this_price">${projectDetail.transactionPrice}</span>
                    <span
                            class="color1">元</span>
                <#else>
                     <span>当前价</span>
                <span class="color1 font-size1 margin-left-20" id="this_price">${projectDetail.currentPrice}</span><span
                        class="color1">元</span>
                </#if>
                <div class="zj_function zj_page">
                    <#if ylrc_home??>
                        <#if projectCollect??>
                            <span class="p-lr-10" onclick="deleteBiddingCollect(${projectCollect.id})"
                                  style="cursor: pointer">
                               <i class="iconfont icon-heart"></i>取消收藏
                            </span>
                        <#else>
                            <span class="p-lr-10" onclick="addBiddingCollect()" style="cursor: pointer">
                               <i class="iconfont icon-shoucang"></i>收藏
                             </span>
                        </#if>
                        <#if biddingRemind??>
                            <span class="p-lr-10" onclick="deleteBiddingRemind(${biddingRemind.id})"
                                  style="cursor: pointer">
                               <i class="iconfont icon-heart"></i>取消提醒
                            </span>
                        <#else>
                            <span class="p-lr-10" onclick="addBiddingRemind()" style="cursor: pointer">
                               <i class="iconfont"></i>设置提醒
                             </span>
                        </#if>
                    </#if>
                <#--<a class="icon-shoucangp like" onclick="addBiddingCollect()"><i class="iconfont  icon-shoucang"></i>收藏</a>-->
                </div>
            </div>
            <#if projectDetail.projectStatus.getCode()==10>
                <div class="block-item lafite_djs">
                    <span class="t_djs_value">距离开始</span>
                    <span class="lafitewu_main font-size1 margin-left-20 date_value">

					</span>
                </div>
            <#elseif projectDetail.projectStatus.getCode()==4>
            <div class="block-item lafite_djs">
                <span class="t_djs_value">距离结束</span>
                <span class="lafitewu_main font-size1 margin-left-20 date_value">

					</span>
            </div>
            </#if>

            <#if projectDetail.projectStatus.getCode()==7>
                 <div class="block-item lafite_djs">
                     <span class="t_djs_values">交易时间</span>
                     <span class="font-size1 margin-left-20 date_value">${projectDetail.transactionTime!""}</span>
                 </div>
            <#else>
                <div class="block-item lafite_djs">
                    <span class="t_djs_values">结束时间</span>
                    <span class="font-size1 margin-left-20 date_value">${projectDetail.biddingEndTime}</span>
                </div>


            <div class="block-price">
                <div class="block-item">
                    <span>出&nbsp;&nbsp;&nbsp;价</span>
                    <input oninput="value=value.replace(/[^\d]/g,'');" maxlength="9" type="text" class="price"
                           onchange="changePrice(this)" <#if recordList.total==0> value="${projectDetail.currentPrice}"
                           <#else>value="${projectDetail.currentPrice+projectDetail.rateIncrease}"</#if>
                           data-value="${projectDetail.startPrice}" data-increase="${projectDetail.rateIncrease}"
                           readonly
                           style="width:236px !important;"/>
                    <#if projectDetail.projectStatus.getCode()!=6>
                    <div class="symbol-itme">
                        <div data-type="add" class="symbol-btn">+</div>
                        <div data-type="subtract" class="symbol-btn">-</div>
                    </div>
                    </#if>
                </div>
                <#if ylrc_home??>
                    <#if projectDetail.projectStatus.getCode()==10>
                 <div onclick="signUp(this)" data-bond="${projectDetail.bond}" data-id="${projectDetail.id}"
                      class="block-item-btn start-bg">
                     开始报名,需交保证金${projectDetail.bond}
                 </div>
                    <#elseif projectDetail.projectStatus.getCode()==4>
                        <div onclick="baojia()" class="block-item-btn start-bg">
                            开始竞拍
                        </div>
                    <#elseif projectDetail.projectStatus.getCode()==5>
                     <div class="block-item-btn end-bg">
                         竞价成功
                     </div>
                    <#elseif  projectDetail.projectStatus.getCode()==7>
                    <div class="block-item-btn end-bg">
                        已成交
                    </div>
                    <#elseif projectDetail.projectStatus.getCode()==6>
                    <div class="block-item-btn end-bg">
                        本次拍卖已流拍
                    </div>
                    <#elseif projectDetail.projectStatus.getCode()==11>
                     <div onclick="" class="block-item-btn start-bg">
                         ${projectDetail.projectStatus.getValue()}
                     </div>
                    <#else>
                        <div onclick="" class="block-item-btn start-bg">
                            还未开始报名
                        </div>
                    </#if>
                <#else>
                    <#if projectDetail.projectStatus.getCode()==5>
                    <div class="block-item-btn end-bg">
                        已结束
                    </div>
                    <#elseif projectDetail.projectStatus.getCode()==6>
                    <div class="block-item-btn end-bg">
                        本次拍卖已流拍
                    </div>
                    <#else>
                     <div onclick="window.location.href='/home/user/login'" class="block-item-btn start-bg">
                         还未登录请登录
                     </div>
                    </#if>
                </#if>
            </div>
            </#if>

            <div class="position-bottom"
                 <#if projectDetail.projectStatus.getCode()==7>style="padding: 0 0px;width: 93%;height: 1px;background: #e4e4e4; margin-left: 4%; margin-top: 35px;"</#if>>
                <#if projectDetail.projectStatus.getCode()==7>
                    <div>
                        <div class="block-item-col" style="width:75%;line-height: 30px;margin-top: 30px;">
                            <span>转出方：${projectDetail.transferor!""}</span><br>
                            <span>成交方：
                                <#if projectDetail.homeUser??>
                                    ${projectDetail.homeUser.username}
                                </#if>

                            </span><br>
                            <#if projectDetail.circulationEndTime??>
                            <span>流转起始时间：${projectDetail.transactionTime}---${projectDetail.circulationEndTime}</span>
                            </#if>
                        </div>
                    </div>
                <#else>
                <div class="block-text-item">
                      <#if projectDetail.projectStatus.getCode()==1>
                      <span class="over-tip" style="color: green;">
                         即将开始
                       </span>
                      <#elseif projectDetail.projectStatus.getCode()==4>
                       <span class="over-tip" style="color: red;">
                          竞价中
                       </span>
                      <#elseif projectDetail.projectStatus.getCode()==10>
                       <span class="over-tip" style="color: brown;">
                          正在报名
                       </span>
                      <#elseif projectDetail.projectStatus.getCode()==5>
                      <span class="over-tip" style="color: gray;">
                          竞价成功
                      </span>
                      <#elseif projectDetail.projectStatus.getCode()==6>
                       <span class="over-tip" style="color: gainsboro;">
                          本场拍卖已流拍
                      </span>
                      <#elseif projectDetail.projectStatus.getCode()==11>
                     <span class="over-tip" style="color: rebeccapurple;">
                         ${projectDetail.projectStatus.getValue()}
                     </span>
                      <#else>
                       <span class="over-tip" style="color: rebeccapurple;">
                          还未开始报名
                       </span>
                      </#if>
                    <span class="margin-left-10">说明：已经开始竞价的项目不再接受报名</span>
                </div>
                <div>
                    <div class="block-item-col">
                        <span>起&nbsp;拍&nbsp;价： ¥ ${projectDetail.startPrice}元</span><br/>
                        <span>竞价周期：${cycleDate}</span><br/>
                        <#if projectDetail.delayPeriod??>
                               <span>延时周期：${projectDetail.delayPeriod}分钟</span>
                        <#else>
                                  <span>延时周期：暂无</span>
                        </#if>
                    </div>
                    <div class="block-item-col">
                        <span>加价幅度： ¥ ${projectDetail.rateIncrease}元
						</span>
                        <br>
                        <span>保证金： ¥ ${projectDetail.bond}元
						</span>
                    </div>
                </div>
                </#if>
            </div>
        </div>

        <div class="transation-block">
            <p>
                <span>挂牌交易中心：</span>
            </p>
            <p>
                <span class="bidding-text">${projectDetail.organization.name}</span>
            </p>

            <p>
                <span class="margin-top-20">联系人：</span><span>${projectDetail.organization.legalPerson!""}</span>
            </p>
            <p>
                <span class="margin-top-20">联系电话：</span><span>${projectDetail.organization.phone!""}</span>
            </p>


            <p>
                <span class="margin-top-20">地址：</span><span>${projectDetail.organization.address!""}</span>
            </p>

            <p>
                <button class="layui-btn" onclick="message()">留言咨询</button>
            </p>
        </div>
    </div>

    <div class="auction_ng">
        <img src="/home/images/middle_guide.png" alt="home_guide">
    </div>

    <div id="nav-container" class="nj-pj-info">
        <div class="pj-nav">
            <ul id="nav-box">
                <li class="curr"><a href="#a_name1">标的介绍</a></li>
                <li><a href="#a_name2">竞买公告</a></li>
                <li><a href="#a_name3">竞买须知</a></li>
                <li><a href="#a_name5">授权书</a></li>
                <li><a href="#a_name4">竞买记录（<span id="biddingCount">${projectDetail.auctionTimes}</span>）
                </a></li>

            </ul>
        </div>
        <div id="pj-info-content">
            <div class="info-con-risk-basic">
                <div class="stub"></div>
                <div class="nj-pj-info-title">
                    <div class="line"></div>
                    <span><a name="a_name1">标的详情</a></span>
                    <div class="line"></div>
                </div>
                <div class="risk-basic-con">
                ${projectDetail.description}
                </div>
            </div>

            <!-- 新增竞价公告 -->
            <div class="info-con-risk-basic">
                <div class="stub"></div>
                <div class="nj-pj-info-title">
                    <div class="line"></div>
                    <span><a name="a_name2">竞买公告</a></span>
                    <div class="line"></div>
                </div>
                <div class="risk-basic-con">
                ${projectDetail.notice}
                </div>
            </div>

            <div class="info-apply a_name2">
                <div class="stub"></div>
                <div class="nj-pj-info-title">
                    <div class="line"></div>
                    <span><a name="a_name3">竞买须知</a></span>
                    <div class="line"></div>
                </div>
                <div class="info-apply-con">
                ${projectDetail.biddingInformation}
                </div>
            </div>
            <div class="info-con-risk-basic">
                <div class="stub"></div>
                <div class="nj-pj-info-title">
                    <div class="line"></div>
                    <span><a name="a_name5">授权书</a></span>
                    <div class="line"></div>
                </div>
                <div class="risk-basic-con">
                    <#if projectDetail.certificate??>
                        <#if projectDetail.certificate?length gt 0>
                        <img src="/photo/view?filename=${projectDetail.certificate}"/>
                        <#else>
                        暂无授权书
                        </#if>
                    <#else>
                          暂无授权书
                    </#if>
                </div>
            </div>
            <!-- 新增标的物介绍 -->
            <div class="info-con-risk-basic a_name3" id="targetsDiv">

            </div>
            <!-- 新增竞买记录 -->
            <div class="info-con-risk-basic">
                <div class="nj-pj-info-title">
                    <div class="line"></div>
                    <span><a name="a_name4">竞买记录</a></span>
                    <div class="line"></div>
                </div>
                <div class="add_table">
                    <table>
                        <tr>
                            <td>状态</td>
                            <td>竞买人</td>
                            <td>价格</td>
                            <td>时间</td>
                        </tr>
                        <#if recordList.content?size gt 0>
                            <#list recordList.content as biddingRecord>
                                <tr>
                                    <#if biddingRecord.biddingStatus == 1>
                                        <td style="vertical-align:middle;" class="add_table_btn">
                                            领先
                                        </td>
                                    <#else>
                                        <td style="vertical-align:middle;" class="add_table_btn add_table_btn_gray">
                                            出局
                                        </td>
                                    </#if>

                                    <td style="vertical-align:middle;">
                                        ${biddingRecord.homeUser.username}
                                    </td>
                                    <td style="vertical-align:middle;">${biddingRecord.bid}</td>
                                    <td style="vertical-align:middle;">${biddingRecord.createTime}</td>
                                </tr>
                            </#list>
                        <#else>
                            <tr align="center">
                                <td colspan="9">这里空空如也！</td>
                            </tr>
                        </#if>
                    </table>
                    <nav class="text-center my-paging">
                        <div id="page">

                        </div>
                    </nav>
                </div>
            </div>

        </div>
    </div>
</div>

<#include "../common/foot.ftl"/>
<script src="/home/js/countdown.min.js"></script>
<script src="/home/js/land_details.js"></script>
<script src="/home/layui/layui.all.js"></script>

</body>
</html>

<script>

    var count = ${recordList.total}
    var curr = ${recordList.currentPage}
    var pageSize = ${recordList.pageSize}
            layui.use('laypage', function () {
                var laypage = layui.laypage;

                //执行一个laypage实例
                laypage.render({
                    elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
                    , count: count //数据总数，从服务端得到
                    , limit: pageSize
                    , curr: curr
                    , jump: function (obj, first) {
                        //首次不执行
                        if (!first) {
                            curr = obj.curr;
                            location.href = "detail?currentPage=" + obj.curr + "&id=${projectDetail.id}#a_name4";
                        }
                    }
                });
            });

    var projectId = '${projectDetail.id}'

    function message() {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '留言咨询',
                content: '<textarea type="text"  placeholder="请填写留言" class="form-control message" style="height:120px;"></textarea>',
                area: ['500px'],
                btn: ['发送留言', '取消'],
                yes: function (index, layero) {
                    var message = $(".message").val().trim();
                    sendMessage(message)
                }
            })
        });
    }

    function sendMessage(message) {
        if (message == null || message == "") {
            layer.msg("留言不能为空");
            return;
        }
        $.ajax({
            url: '/home/message/add',
            type: 'post',
            data: {
                projectId: projectId,
                message: message
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    layer.msg("留言已发送");
                    setInterval(function () {
                        window.location.reload();
                    }, 1000);
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        })

    }

    /**
     * 添加收藏
     * @param projectId
     */
    function addBiddingCollect() {
        $.ajax({
            url: '/home/collect/add_project',
            type: 'post',
            data: {
                projectId: projectId,
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    layer.msg("添加收藏成功");
                    setInterval(function () {
                        window.location.reload();
                    }, 1000);
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        })
    }

    /**
     * 取消收藏
     * @param id
     */
    function deleteBiddingCollect(id) {
        $.ajax({
            url: "/home/collect/delete_project",
            type: 'POST',
            data: {id: id},
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    layer.msg("取消收藏成功");
                    setInterval(function () {
                        window.location.reload();
                    }, 1000);
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        });
    }

    /**
     * 设置提醒
     */
    function addBiddingRemind() {
        $.ajax({
            url: '/home/remind/add',
            type: 'post',
            data: {
                projectId: projectId,
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    layer.msg("设置提醒成功");
                    setInterval(function () {
                        window.location.reload();
                    }, 1000);
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        })
    }

    /**
     * 取消提醒
     * @param id
     */
    function deleteBiddingRemind(id) {
        $.ajax({
            url: "/home/remind/delete",
            type: 'POST',
            data: {id: id},
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    layer.msg("取消提醒成功");
                    setInterval(function () {
                        window.location.reload();
                    }, 1000);
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        });
    }

    //倒计时
    function countDown(times, systemDate) {
        // 倒计时
        $(".lafitewu_main").countDown({
            times: times,  //'2018/8/13 18:00:00'
            system_time: systemDate,
            days: true,
            ms: false,
            Hour: true,
            unit: {
                days: '天',
                hour: '时',
                min: '分',
                second: '秒'
            }
        }, function () {
            $(".Days_val,.Hour_val,.Min_val,.Second_val").html("00");
            window.location.reload();
        });
    }


    $(document).ready(function () {
        var status =${projectDetail.projectStatus.getCode()};
        if (status == 4) {
            countDown('${projectDetail.biddingEndTime}', '${systemDate}');
        }
        else {
            if (status != 5 && status != 6 && status != 7) {
                countDown('${projectDetail.biddingStartTime}', '${systemDate}');
            }
        }

    });
    var thisReserverBiddingMoney = Number($(".price").val());
    var auctionLadder = Number($(".price").attr("data-increase"));
    //加减
    $(document).on('click', '.symbol-btn', function (e) {
        e.preventDefault();
        var type = $(this).data("type");
        var thisMoney = Number($(".price").val());
        if (type == 'subtract') {//减
            thisMoney = thisMoney - auctionLadder;
            if (thisReserverBiddingMoney > Number(thisMoney)) {
                layer.msg("已经是最低价了！");
                return;
            }
            $('.price').val(thisMoney);
            $('.price').attr('data-value', thisMoney);
        } else {//加
            thisMoney = thisMoney + auctionLadder;
            $('.price').val(thisMoney);
            $('.price').attr('data-value', thisMoney);
        }
    });

    //变更金额
    function changePrice(t) {
        var _this = $(t);
        if (thisReserverBiddingMoney > _this.val()) {
            layer.msg("抱歉，已经是最低出价了！");
            $(".price").val(thisReserverBiddingMoney);
        }
    }

    //开始报名
    function signUp(t) {
        var _this = $(t);
        var projectId = _this.attr("data-id");
        //prompt层
        layer.prompt({title: '请输入支付密码', formType: 1}, function (pass, index) {
            $.ajax({
                url: 'signUp',
                type: 'POST',
                data: {payPassword: pass, projectId: projectId},
                dataType: 'json',
                success: function (rst) {
                    if (rst.code == 0) {
                        $("#modal_txt").html("报名成功");
                        $("#modal_txt").show();
                        setTimeout(function () {
                            $("#modal_txt").hide();
                        }, 1000)
                        layer.close(index);
                        window.location.reload();
                    } else {
                        layer.msg(rst.msg);
                        layer.close(index);
                    }
                },
                error: function (data) {
                    alert('网络错误!');
                }
            });
        });
    }

    //开始报价
    function baojia() {
        show();
    }

    //关闭
    function cancel() {
        $(".lafite_cover").hide();
    }

    //开始报价
    function show() {
        $(".lafite_cover").show();
        $("#ts_price").html($(".price").val());
    }

    // 点击确认
    function submitPrice(t) {
        var money = 0;
        var _this = $(t);
        var projectId = _this.attr("data-projectId");
        money = $(".price").val();
        $.ajax({
            type: 'POST',
            url: "offer",
            data: {projectId: projectId, money: money},
            success: function (resp) {
                if (0 != resp.code) {
                    $("#modal_txt").html(resp.msg);
                    $("#modal_txt").show()
                } else {
                    $("#modal_txt").html("恭喜您,报价成功");
                    $("#modal_txt").show();
                    location.reload();
                }
                setTimeout(function () {
                    $("#modal_txt").hide();
                    $("#mask").hide()
                }, 1000)
            }
        })
        cancel();
    }

</script>