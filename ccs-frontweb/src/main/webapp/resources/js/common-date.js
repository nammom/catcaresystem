(function ($){
	
	if($(".datePicker-control")){
		let $ctl = $(".datePicker-control");
		if($ctl.length > 0){
			$.each($ctl, function(){
				let classStr = $(this).attr("class");
				if(classStr.indexOf("startDate") > 0){
					let $sDate = $(this);
					$(this).datepicker({
				    	language: "ko",
						maxDate: new Date(),
						autoClose : true,
						onSelect : function(fd, date){
							let sDate = date.getDate();
							let $eDate = $sDate.next().next();
							if($eDate && $eDate.attr("class").indexOf("endDate") > 0){
								let eDatepicker = $eDate.datepicker().data("datepicker");
								eDatepicker.update("minDate", new Date(fd))
							}
						}
				    });
				}else if(classStr.indexOf("endDate") > 0){
					$(this).datepicker({
				    	language: "ko",
						autoClose : true,
						minDate: new Date()
				    });
				}else{
					$(this).datepicker({
				    	language: "ko",
						autoClose : true
				    });
				}
			});
		}
	}
	
})(jQuery);

