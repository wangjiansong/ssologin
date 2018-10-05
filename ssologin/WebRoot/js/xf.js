// 页面设置
$(document).ready(
	function(){
		$(".xf_remen ul li a span:lt(3)").css("color","#f47564");
		$(".xf_zuixin ul li a span:lt(3)").css("color","#f47564");
	}
);
// 问题列表
$(document).ready(function () {
	$(".xf_wtxj ul li").eq(0).addClass("licur").css("height",$(this).find(".xf_wtnr").height()+81).siblings().removeClass("licur");
	$(".xf_wtxj ul li").click(
		function(){
			$(this).addClass("licur").siblings().removeClass("licur");
			$(this).animate({
				"height":$(this).find(".xf_wtnr").height()+81
			},300).siblings().animate({
				"height":42
			},300);
		}
	);
});