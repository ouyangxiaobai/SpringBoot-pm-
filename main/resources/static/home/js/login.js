$(function() {
	//checkLogin();
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
});

/*function checkLogin(){
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
	      		str += name + "</div><a href='/personCenter/toCenterPage' class='head_user head_user_a' style='width:59px;margin-left: 10px;'>个人中心</a>"+
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
}*/
