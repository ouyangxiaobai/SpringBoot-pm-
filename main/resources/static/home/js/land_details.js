$(function() {
  if( $(".bpic-max li").length == 1){
    $(".bpic-btn").css({"display":"none"})
  }
  $(".bpic-min li").eq(0).addClass("active");
  var pic_li = $(".bpic-max li").length;
  $(".bpic-max ul").css({
    "width": pic_li * $(".bpic-max li").width()
  }); //大图片ul宽度
  $(".bpic-min ul").css({
    "width": pic_li * ($(".bpic-min li").width() + 24)
  }); //小图片ul宽度

  var bpic_i = 0;
  var bpics_lazy = function(){
    $('.v2-ld-status-img img').lazyload();
    $('.landD-supply-bpic .bpic-max .lazy').lazyload();
    $('.landD-supply-bpic .bpic-min .lazy').lazyload();
  }

  setTimeout(function(){
    bpics_lazy();
  },500);

  //自动播放
  var pic_auto = function() {
    bpic_i++;
    bpic_i = bpic_i > pic_li - 1 ? 0 : bpic_i;
    show_pic(bpic_i);
  };

  var show_pic = function(x) {
    bpics_lazy();

    $(".bpic-max ul").animate({
      "left": -bpic_i * $(".bpic-max li").width()
    }, "fast");
    if (bpic_i > 4) {
      $(".bpic-min ul").animate({
        "left": -(bpic_i - 4) * ($(".bpic-min li").width() + 24)
      }, "fast");
    } else {
      $(".bpic-min ul").animate({
        "left": 0
      }, "fast");
    };

    $(".bpic-min li").removeClass("active");
    $(".bpic-min li").eq(x).addClass("active");
    setBtnStatus();
  }

  var setBtnStatus = function() {
    if (bpic_i == pic_li - 1) {
      $(".bpic-btn-next").addClass('disabled');
    } else {
      $(".bpic-btn-next").removeClass('disabled');
    }

    if (bpic_i == 0) {
      $(".bpic-btn-prev").addClass('disabled');
    } else {
      $(".bpic-btn-prev").removeClass('disabled');
    }
  }

  $(".bpic-btn-prev").addClass('disabled');
  if (pic_li < 2) {
    $(".bpic-btn-next").addClass('disabled');
  }
  //左右按钮点击
  $(".bpic-btn-next").click(function() {
    bpic_i++;
    if (bpic_i <= pic_li - 1) {
      show_pic(bpic_i);
    } else {
      bpic_i = pic_li - 1;
    }
  });
  $(".bpic-btn-prev").click(function() {
    bpic_i--;
    if (bpic_i >= 0) {
      show_pic(bpic_i);
    } else {
      bpic_i = 0;
    }
  });

  var myInterval = setInterval(pic_auto, 5000);


  //鼠标经过小图片
  $(".bpic-min li").on('mouseover', function() {
    var i = $(this).index();
    bpic_i = i;
    show_pic(i);
  });

  //鼠标经过时添加/删除自动播放
  $(".bpic-max,.bpic-btn,.bpic-min").on('mouseover', function() {
    clearInterval(myInterval);
  });

  $(".bpic-max,.bpic-btn,.bpic-min").on('mouseout', function() {
    myInterval = setInterval(pic_auto, 5000);
  });
});
