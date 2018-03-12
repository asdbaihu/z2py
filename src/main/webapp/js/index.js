$(function(){ 
	$('table.datatable').datatable({sortable: true});
	$('[data-toggle="tooltip"]').tooltip();
	imagePreview();
	
	var optOpened = false;
	$('.btn-switch').click(function(){
		$('.opt-more-box').toggle('fast', function() {
			if (!optOpened)
				$('.btn-switch').addClass('opt-opened');
			else
				$('.btn-switch').removeClass('opt-opened');
			optOpened = !optOpened;
		});
	});
	$('.pic-list li').hover(function(){
	    $(this).stop().stop().animate({marginRight:"-4px"},500).siblings("li").stop().animate({marginRight:"-42.5px"},500);
	},function(){
		$(this).stop().animate({marginRight:"-42.5px"},500);
	});
});