(function ($){
	
	if($(".tree-select")){
		let $ctl = $(".tree-select");
		if($ctl.length > 1){
			$(".tree-select:not(:last)").change(function(){
				$.ccs.cmnCodeOption.getCmnCodeList(this);
			})
		}
	}
	
	if($(".area-select")){
		let $ctl = $(".area-select");
		if($ctl.length > 1){
			$(".area-select:not(:last)").change(function(){
				$.ccs.cmnCodeOption.getAreaCodeList(this, "/selectAreaCode", "area-select");
			})
		}
	}
})(jQuery);

