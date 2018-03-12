this.imagePreview = function(){	
	/* CONFIG */
		
		xOffset = 10;
		yOffset = 30;
		
		// these 2 variable determine popup's distance from the cursor
		// you might want to adjust to get the right result
		
	/* END CONFIG */
	$("a.preview").hover(function(e){
		$("body").append("<div id='preview'><img width='300px' src=''/></div>");								 
		$("#preview")
			.css("top",(e.pageY - xOffset + 300) + "px")
			.css("left",(e.pageX + yOffset) + "px")
			.fadeIn("fast");
		$("#preview").find("img").attr('src',$(this).find('img').attr('src'));
    },
	function(){
		$("#preview").remove();
    });	
	$("a.preview").mousemove(function(e){
		$("#preview")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};