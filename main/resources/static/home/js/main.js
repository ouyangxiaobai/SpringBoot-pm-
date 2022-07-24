(function($) {
  "use strict";

  $.fn.fireOnDisable = function(settings) {
    // Only perform this DOM change if we have to watch changes with propertychange
    // Also only perform if getOwnPropertyDescriptor exists - IE>=8
    // I suppose I could test for "propertychange fires, but not when form element is disabled" - but it would be overkill
    if (!('onpropertychange' in document.createElement('input')) || Object.getOwnPropertyDescriptor === undefined) {
      return this;
    }

    // IE9-10 use HTMLElement proto, IE8 uses Element proto
    var someProto = window.HTMLElement === undefined ? window.Element.prototype : window.HTMLElement.prototype,
      someTrigger = function() {},
      origDisabled = Object.getOwnPropertyDescriptor(someProto, 'disabled');

    if (document.createEvent) {
      someTrigger = function(newVal) {
        var event = document.createEvent('MutationEvent');
        /*
        Instantiate the event as close to native as possible:
        event.initMutationEvent(eventType, canBubble, cancelable, relatedNodeArg, prevValueArg, newValueArg, attrNameArg, attrChangeArg);
        */
        event.initMutationEvent('DOMAttrModified', true, false, this.getAttributeNode('disabled'), '', '', 'disabled', 1);
        this.dispatchEvent(event);
      };
    } else if (document.fireEvent) {
      someTrigger = function() {
        this.fireEvent('onpropertychange');
      };
    }

    return this.each(function() {
      // call prototype's set, and then trigger the change.
      Object.defineProperty(this, 'disabled', {
        set: function(isDisabled) {
          // We store preDisabled here, so that when we inquire as to the result after throwing the event, it will be accurate
          // We can't throw the event after the native send, because it won't be be sent.
          // We must do a native fire/dispatch, because native listeners don't catch jquery trigger 'propertychange' events
          $.data(this, 'preDisabled', isDisabled);
          if (isDisabled) {
            // Trigger with dispatchEvent
            someTrigger.call(this, isDisabled);
          }

          return origDisabled.set.call(this, isDisabled);
        },
        get: function() {
          var isDisabled = $.data(this, 'preDisabled');
          if (isDisabled === undefined) {
            isDisabled = origDisabled.get.call(this);
          }
          return isDisabled;
        }
      });
    });
  };
})(jQuery);

jQuery.browser = {};
jQuery.browser.msie = false;
jQuery.browser.version = 0;
if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
  jQuery.browser.msie = true;
  jQuery.browser.version = RegExp.$1;
}

$(function() {
  /*$(".lazy").lazyload();*/

  if (typeof template != 'undefined') {
    template.config('openTag', '<%');
    template.config('closeTag', '%>');
  }

  if(typeof alertify != "undefined"){
    alertify.logPosition('center').maxLogItems(1);
  }

  moment.locale('zh-cn');

  if ($.fn.select2) {
    $.fn.select2.defaults.set("minimumResultsForSearch", "Infinity");
    $.fn.select2.defaults.set("language", "zh-CN");
    $('.select2-select').length && $('.select2-select').fireOnDisable().select2({});
  }

  if ($.validationEngine) {
      $.extend(true, $.validationEngine.defaults, {
          promptPosition: "centerRight",
          showArrow: false
      });
  } 
  
  function isIe8() {
    var b_version = navigator.appVersion;
    var version = b_version.split(";");
    if (version && version.length > 1) {
      var trim_Version = parseInt(version[1].replace(/[ ]/g, "").replace(/MSIE/g, ""));
      if (trim_Version <= 9) {

        return true;
      }
    }
    return false;
  }

  if (window.swal) {
    var swalDefualtPram = {
      allowOutsideClick: true,
      showConfirmButton: true,
      confirmButtonText: '确定',
      cancelButtonText: '取消'
    };

    swal.setDefaults(swalDefualtPram);
  }

  if (isIe8()) {
    $('input[type=text]').focus(function() {
      var input = $(this);
      if (input.val() == input.attr('placeholder')) {
        input.val('');
        input.removeClass('placeholder');
      }
    }).blur(function() {
      var input = $(this);
      if (input.val() == '' || input.val() == input.attr('placeholder')) {
        input.addClass('placeholder');
        input.val(input.attr('placeholder'));
      }
    }).blur();
  };
  
  
  //checkLogin();
});



//大全banner
if( $(".a-close").length !=0 )
{
  $(".a-close").click(function()
  {
    $(".daquan-img3").hide();
  })
}


function checkLogin(){
	var token = localStorage.getItem("token");
	var userPhone = localStorage.getItem("userPhone");
	var userId = localStorage.getItem("userId");
	//校验登录
	if(userPhone!=null && userId!=null){
		$.ajax({
	          url : "/AuctionLogin/isLogin",
	          data : {
	              "userPhone" : userPhone,
	              "userId" : userId
	          },
	          type : "post",
	          cache : false,
	          async : false,
	          dataType : "json",
	          success : function(data) {
		        console.log(data);
	        	if(data.code!="200"){
	        		return;
	        	}  
	      		var str = "<div class='head_user' style='text-overflow:ellipsis;word-break:keep-all;white-space:nowrap;overflow:hidden;width:100px;margin-left: 15px;'>您好 "
	      		var userName = localStorage.getItem("userName");
	      		//console.log(userName);
	      		var name = '';
	      		if(userName!=null && userName!="" && userName!="null"){
	      			name = userName;
	      		}else{
	      			name = localStorage.getItem('userPhone');
	      		}
	      		str += name + "</div><a href='/usercenter/tAuctionSign' class='head_user head_user_a' style='width:59px;margin-left: 10px;'>个人中心</a>"+
	      					  "<div class='head_user' id='logOut' style='width:20px;text-overflow: ellipsis;word-break: keep-all;white-space: nowrap;'>"+
	      					  //"<img src='/foreside2/images/exit.png' style='width:10px' alt='退出icon'>"+
	              			  "<span>退出</span></div>"
	          	$("#userDiv").html(str);
	      		
	          }, 
	          error : function(XMLHttpRequest, textStatus, errorThrown) {
		      		localStorage.removeItem("token");
		    		localStorage.removeItem("userName");
		    		localStorage.removeItem("userPhone");
		    		localStorage.removeItem("userId");
	        	  console.log(XMLHttpRequest);
	        	  console.log(textStatus);
	        	  console.log(errorThrown);
		            //swal('服务器没有返回数据，可能服务器忙，请重试');
		          }
	        });
	}
	
	$("#logOut").on("click",function(){
		localStorage.removeItem("token");
		localStorage.removeItem("userName");
		localStorage.removeItem("userPhone");
		localStorage.removeItem("userId");
		$.ajax({
	          url : "/AuctionLogin/logOut",
	          data : {
	              "userPhone" : $("#userPhone2").val(),
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
		            swal('服务器没有返回数据，可能服务器忙，请重试');
		          }
	        });
	});
}