$('#keyword').keypress(function(e){
    if(e.keyCode==13){
        e.preventDefault();
    }
});
$('.search').each(function(){
	$(this).click(function(){
		var type = $(this).attr('rel');
		$('input[name="type"]').val(type);
		var keyword = $('#keyword').val();
		if (keyword.trim()=='')
			return;
		$('#search-form').attr('action', $('#search-form').attr('action') + encodeURI(encodeURI(keyword)));
		$('#search-form').submit();
	});
});
$('.extend-link').click(function(){
	$(this).next().removeClass('hide').show();$(this).hide();
});
$('.extend-search').each(function(){
	$(this).click(function(){
		var href = $(this).parent().parent().find('a').attr('href');
		if(href.indexOf('?')<0){
			href += '?'+ $(this).attr('data-type') + '=' + $(this).parent().find('.extend-input').val();
		}else{
			href += (href == '?' ? '' : '&') + $(this).attr('data-type') + '=' + $(this).parent().find('.extend-input').val();
		}
		window.location.href=href;
	})
});
