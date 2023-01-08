(function ($){
	
	if($(".tree-select")){
		let $ctl = $(".tree-select");
		if($ctl.length > 1){
			$(".tree-select:not(.last-select)").change(function(){
				$.ccs.cmnCodeOption.getCmnCodeList(this);
			})
		}
	}
	
	if($(".area-select")){
		let $ctl = $(".area-select");
		if($ctl.length > 1){
			$(".area-select:not(.last-select)").change(function(){
				$.ccs.cmnCodeOption.getAreaCodeList(this, "/selectAreaCode", "area-select");
			})
		}
	}
	
	if($("a.nav-link") && $("a.nav-link").length > 0){
		let pagenm = $("#pagenm").val();
		$("a.nav-link[data-pagenm = '" + pagenm + "']").addClass("active");
		
		$("a.nav-link").click(function(){
			$("a.nav-link.active").removeClass("active");
			$(this).addClass("active");
		})	
	}
	
	$(document).on("click", ".accordion-header", function(){
	let $accorrdionBody = $(this).next();
	if(!$accorrdionBody.hasClass("accordion-body")) {
		return;
	}
	if($accorrdionBody.hasClass("show")) {
		$accorrdionBody.removeClass("show");
	} else {
		$accorrdionBody.addClass("show");
	}
});
	
})(jQuery);




