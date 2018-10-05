$(function(){
	$(".login-input input").focus(function(){
		$(this).next('.tips').show();
	}).blur(function(){
		$(this).next('.tips').hide();
	});
	
	$(".get-yzm").on('click',function(){
	   $(this).addClass('sendTo');
		var i = 60;
	 	var time = parseInt(i);
        var countdown = null;
        function timeShow(){
            time--;
            $(".get-yzm").html(time+'秒后重新发送');
            if(time<1){
                clearInterval(countdown);
				$(".get-yzm").removeClass('sendTo');
				 $(".get-yzm").html('获取验证码');
            }
        };
        countdown = setInterval(timeShow,1000);
	});
	
	$('.login-close').click(function(){
		location.href = "http://www.fzlib.org/index";
	});
	$('.go-login').click(function(){
		$('.forgetpwd-wrapper').hide();
		$('.register-wrapper').hide();
		$('.login-wrapper').show();
	});
	
	$('.checkid-finish').click(function(){
		$('.checkid-finish a').html('马上验证并登录');
	});
	
	switchS('.pwd-next','.forgetpwd-wrapper','.setnewpwd-wrapper');
	switchS('.setpwd-finish','.setnewpwd-wrapper','.login-wrapper');
	switchS('.reg-next','.register-wrapper','.checkid-wrapper');
	switchS('.yznum-next','.yznum-wrapper','.yzidcard-wrapper');
	
	function switchS(btn,area01,area02){
		$(btn).click(function(){
			$(area01).hide();
			$(area02).show();
		})
	}
	 
});