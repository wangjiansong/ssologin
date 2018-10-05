$(document).ready(
	function(){
		$('.bgnone_p input').hover(
			function(){
				$(this).css('background','#6CC92D');
				},function(){
				$(this).css('background','#6fd52b');
			}
		);
		$('.fsyzm').hover(
			function(){
				$(this).css('background','#eee');
			},function(){
				$(this).css('background','#ece8da');
			}
		);
		$('.tongyiann').hover(
			function(){
				$(this).css('background','#6CC92D');
			},function(){
				$(this).css('background','#6fd52b');
			}
		);
		$('.zctab a').eq(0).addClass('cur').siblings().removeClass('cur');
		$('.zctab a').click(
			function(){
				index =$(this).index();
				$(this).addClass('cur').siblings().removeClass('cur');
				if(index==1){
					$('.yzfs_box').animate({
						'left':-836
					},300);
				}else{
					$('.yzfs_box').animate({
						'left':0
					},300);
				}
			}
		);
	}
);