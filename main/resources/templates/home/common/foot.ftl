<script src="/home/js/jquery.min.3.4.js"></script>
<script src="/home/js/swiper.min.js"></script>
<script src="/home/js/jquery.cookie.js"></script>
<script src="/home/js/vendor.min.js"></script>
<script src="/home/js/head.js"></script>
<script src="/home/js/login.js"></script>
<script src="/home/js/lafite_alert.js"></script>
<script src="/home/js/flip.js"></script>
<div class="foots-link">
    <div class="foot-link-one">
        <div class="ali-product">
            <div class="ali-product-list">
                <div class="ali-product-l">
                    <div class="links-box">
                        <img src="/home/picture/links_icon.png" alt="">
                        <span class="product-list-title">友情链接</span>
                    </div>
                    <div class="product-list-wrap" id="footer_list">
                        <a target="_blank" class="product-list-link" href="http://programmer.ischoolbar.com/">猿来入此-博客</a>
                        <a target="_blank" class="product-list-link" href="https://www.yuanlrc.com/?fuid=6666">猿来入此-教程</a>
                    </div>
                </div>
                <div class="ali-product-r">
                    <div class="ali-product-qr-code">
                        <img src="/admin/images/default-head.jpg" alt="" style="border-radius: 4px;">
                        <p>
                            <span>官方微信</span>
                            <span>关注有礼</span>
                        </p>
                    </div>
                    <div class="ali-product-qr-code">
                        <img src="/admin/images/default-head.jpg" alt="" style="border-radius: 4px;">
                        <p>
                            <span>猿礼客服</span>
                            <span>扫码咨询</span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

  <!-- 公用底部 -->
<div class="footer">
    <div class="footer_bottom">

        <div class="contain">
            <a  href="/home/index/index" style="border: unset">首页</a>
            <a  href="/home/bidding/transaction">产权交易</a>
            <a  href="/home/agency/list">竞价大厅</a>
            <a  href="/home/org/list">交易机构</a>
            <a  href="/home/common_problem/list"> 常见问题</a>
            <a  href="/home/news/list">新闻咨询</a>
            <a  href="/home/inform/list">通知公告</a>
        </div>

        <div class="phone_box">
            <div class="phone_info">
                <img src="/home/picture/phone_pic.png">
                <p class="phone_size">1888 XXXXXXX</p>
                <p>9:00-18:00 (周一至周日)</p>
            </div>
        </div>
    </div>
</div>
<!-- 统计代码 -->
<div style="display: none">
    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?c6f24f3cafb3ccbdb77a284a7f2d8089";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();

        $(document).ready(function() {
            var pathname=window.location.pathname;
            var path=pathname.split("/");
            $(".head_tab").find("a").each(function(i,e){
               var secondUrl= $(e).attr("href").split("/");
                if(secondUrl[2]==path[2]){
                    $(e).siblings().removeClass("head_tab_select");
                    $(e).addClass("head_tab_select");
                }
            });
        });

    </script>

    <script>
        function searchProject()
        {
            var search_value = $("#search_value").val().trim();
            location.href = "/home/bidding/transaction?title=" + search_value;
        }
    </script>
</div>
