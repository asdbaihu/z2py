$(function(){
	var position = $('#position').val();
	var arr = position.split('-');
	if (arr.length == 1) {
		$('.' + arr[0]).addClass('active');
	} else if (arr.length == 2) {
		$('.' + arr[0]).addClass('open active');
		$('.' + arr[1]).addClass('active');
	}
});