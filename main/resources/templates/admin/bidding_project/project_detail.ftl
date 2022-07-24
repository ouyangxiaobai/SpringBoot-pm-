<table class="table table-bordered">
    <thead>
    <tr class="competition-tr">
        <th>封面图片</th>
 <#if biddingProject.picture??>
                <#if biddingProject.picture?length gt 0>
                    <#list biddingProject.picture?split(";") as picture>
            <li class="col-xs-4 col-sm-3 col-md-2">
                <figure>
                    <td width="174px">
                    <img src="/photo/view?filename=${picture}" width="130px" height="150px">
                    </td>
                </figure>
            </li>
                    </#list>
                </#if>
 <#else>
            <img src="/admin/images/default-head.jpg" width="130px" height="150px">
 </#if>
        </td>

    </tr>
    <tr class="competition-tr">
        <th>授权书</th>
        <td>
            <#if biddingProject.picture??>
                   <#if biddingProject.picture?length gt 0>
                <img src="/photo/view?filename=${biddingProject.certificate!""}" id="show-picture-img" width="130px" height="150px">
                   <#else>
                 <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像" width="130px" height="150px">
                   </#if>
                <#else>
                      <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像" width="130px" height="150px">
            </#if>
        </td>
    </tr>
    <tr class="competition-tr">
        <th style="vertical-align: middle;">项目编号</th>
        <td>${biddingProject.projectNumber}</td>
        <th style="vertical-align: middle;">标题</th>
        <td>${biddingProject.title}</td>
        <th style="vertical-align: middle;" >标的类型</th>
        <td>${biddingProject.labelType.name}</td>
        <th style="vertical-align: middle;">项目状态</th>
        <td>${biddingProject.projectStatus.getValue()}</td>
    </tr>
    <tr class="competition-tr">
        <th style="vertical-align: middle;">发布机构</th>
        <td>${biddingProject.organization.name}</td>
        <th style="vertical-align: middle;">省</th>
        <td>${biddingProject.province}</td>
        <th style="vertical-align: middle;">市</th>
        <td>${biddingProject.city}</td>
        <th style="vertical-align: middle;">区</th>
        <td>${biddingProject.area}</td>
    </tr>
    <tr class="competition-tr">
        <th style="vertical-align: middle;">报名开始时间</th>
        <td>${biddingProject.startTime}</td>
        <th style="vertical-align: middle;">报名结束时间</th>
        <td>${biddingProject.endTime}</td>
        <th style="vertical-align: middle;">竞拍开始时间</th>
        <td>${biddingProject.biddingStartTime}</td>
        <th style="vertical-align: middle;">竞拍结束时间</th>
        <td>${biddingProject.biddingEndTime}</td>
    </tr>
    <tr class="competition-tr">
        <th style="vertical-align: middle;">是否有年限</th>
        <td>
            <#if biddingProject.yearsType==0>
                无年限
            <#else>
                有年限
            </#if>
        </td>
        <th style="vertical-align: middle;">年限数</th>
        <td>
            <#if biddingProject.yearsType==0>
                无年限
            <#else>
                ${biddingProject.years}
            </#if>
        </td>
        <th style="vertical-align: middle;">起拍价</th>
        <td>${biddingProject.startPrice}</td>
        <th style="vertical-align: middle;">加价幅度</th>
        <td>${biddingProject.rateIncrease}</td>
    </tr>
    <tr class="competition-tr">
        <th style="vertical-align: middle;">转出方</th>
        <td>${biddingProject.transferor}</td>
        <th style="vertical-align: middle;">保证金</th>
        <td>${biddingProject.bond}</td>
        <th style="vertical-align: middle;">佣金比例</th>
        <td>${biddingProject.rate}%</td>
        <th style="vertical-align: middle;">尾款截止</th>
        <td>${biddingProject.paymentDate}</td>
    </tr>
    <tr class="competition-tr">
        <th style="vertical-align: middle;">延时周期</th>
        <td>${biddingProject.delayPeriod}分钟</td>
        <th style="vertical-align: middle;">联系人</th>
        <td>${biddingProject.contacts}</td>
        <th style="vertical-align: middle;">联系方式</th>
        <td>${biddingProject.phone}</td>
        <th style="vertical-align: middle;">当前价</th>
        <td>${biddingProject.currentPrice}</td>
    </tr>
    </thead>
</table>
<div>
    <span style="color: red;font-weight: bolder">详情描述</span>
    <div class="details">
    ${biddingProject.description}
    </div>
</div>
<div>
    <span style="color: red;font-weight: bolder">竞买公告</span>
    <div class="details">
    ${biddingProject.notice}
    </div>
</div>
<div>
    <span style="color: red;font-weight: bolder">竞买须知</span>
    <div class="details">
    ${biddingProject.biddingInformation}
    </div>
</div>

