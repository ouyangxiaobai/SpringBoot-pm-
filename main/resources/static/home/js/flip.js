$(function () {
    document.addEventListener('scroll', () => {
        var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
        if (scrollTop >= 120) {
            $('.back-top').show();
            $('.qr-code-box').css({"border-bottom":"1px solid #FFFFFF","border-radius":"unset"})
        } else {
            $('.back-top').hide();
            $('.qr-code-box').css({"border-bottom":"unset","border-radius":"0 0 5px 5px"})
        }
    });
    $('.back-top').click(function () {
        let timer = setInterval(function () {
            let osTop = document.documentElement.scrollTop || document.body.scrollTop;
            let ispeed = Math.floor(-osTop);
            document.documentElement.scrollTop = document.body.scrollTop = osTop + ispeed;
            if (osTop === 0) {
                clearInterval(timer);
            }
        }, 30)
    })
})