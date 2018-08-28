function createCookie (name,value,days) {
	if (days) {
	    var date = new Date();
	    date.setTime( date.getTime() + (days*24*60*60*1000) );
	    var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name + "=" + value + expires + "; path=/";
}

(function($) {
	$.fn.equalHeights = function(minHeight, maxHeight) {
		tallest = (minHeight) ? minHeight : 0;
		this.each(function() {
			if($(this).height() > tallest) {
				tallest = $(this).height();
			}
		});
	}
})(jQuery);

$(document).ready(function(){ 
						   
	// ##############  Set listitems item height ##############
	//$("#list_subpages .listitem").equalHeights();
	
	// ##############  Set featured image html height ##############
	//var featured_image_height = $(".featured_image img").height();
	//$(".featured_image .content").height(featured_image_height);
	
	// ##############  Set image slide html height ##############
	//var image_slide_height = $("#image_slide_container #campaign_slider img:eq(0)").height();
	//image_slide_height = image_slide_height - 51;
	//$("#image_slide_container h2").css("top", image_slide_height + "px");
	
	
	// ################### Campaign slide ################
	var starting_slide = $('#campaign_slide_navigation li').size();
	starting_slide = starting_slide;
	
	$('#campaign_slider_content').cycle({
		fx:     'fade',
		timeout: 6000,
		pager:  '#campaign_slide_navigation',
		startingSlide: starting_slide,
		pagerAnchorBuilder: function(idx, slide) { 
			return '#campaign_slide_navigation li:eq(' + idx + ') a'; 
		}
	});
	
	$.fn.cycle.pagerAnchorBuilder = function(pager, currSlideIndex) { 
		$("#campaign_slide_navigation").find('campaign_navi_item').removeClass('active') 
			.filter('.campaign_navi_item:eq('+currSlideIndex+')').addClass('active');
	}; 
	
	var campaign_hover_handler = function() {  
		$(this).cycle('pause'); 
		$(this).mouseleave(function(){
			  $(this).cycle('resume'); 
		});
	};
	
	$('#campaign_slider_content').mouseover(campaign_hover_handler);
	
	$('#campaign_slide_navigation li').click(function(){
		var current_slide = $(this).attr('id');
		current_slide = parseInt(current_slide.replace('campaign_navi_item_',''));
		current_slide = current_slide - 1;
		//var current_slide = $(this).parent().children("li").index(this);
		$('#campaign_slider_content').cycle(current_slide);
	});
	// #################################################
	
	
	// ############ Dropdown ############	
	$(".dropdown .button").click(function(e) {
		$(".dropdown").not( $(this).parent() ).removeClass('active');
		e.preventDefault();
		e.stopPropagation();
		$(this).parent().toggleClass('active');
	});

	$(".dropdown .close").click(function(e) {
		e.preventDefault();
		$(this).parents('.dropdown').toggleClass('active');
	})

	$("html").click(function() {
		$('.dropdown').removeClass('active');
	});

	$(".dropdown-content").click(function(e) {
		e.stopPropagation();
	})

	$(".home #header_right").click(function(e){
		e.stopPropagation()
		$("#markets").toggleClass('active');
	});

	$("#close-cookie-message").click( function (e) {
		e.preventDefault();
		createCookie( 'cookies-accepted', true, 0.05 );
		$("#cookie-message").slideUp();
	});	

});
