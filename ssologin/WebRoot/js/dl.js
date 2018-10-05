
$(document).ready(
	function(){
		var _winheight = $(window).height();
		var _winwidth = $(window).width();
		$(".xf_bg").css({"height":_winheight,"width":_winwidth,"overflow":"hidden"});
		$(".wrap_top .xf_logo").css({"margin-top":_winheight*0.15});
		$(".wrap_top .dlyanz").css({"margin-top":_winheight*0.15,"margin-left":45});
		$(".xf_bodl").css({"background":"#38b1ea"});
		// input获取焦点
		$(".dlyanz input").focus(function(){
			$(".xf_jiaodianp").css({"display":"block"});
		});
	}
);
$(window).resize(
	function(){
		var _winheight = $(window).height();
		var _winwidth = $(window).width();
		$(".xf_bg").css({"height":_winheight,"width":_winwidth,"overflow":"hidden"});
	}
);