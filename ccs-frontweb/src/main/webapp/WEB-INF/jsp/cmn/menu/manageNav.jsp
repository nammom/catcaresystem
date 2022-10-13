<input type="checkbox" id="manage-navbar-toggler-checkbox"/>
<div id="manage-sidenav" class="manage-sidenav">
	<div>
		<label class="manage-navbar-toggler" for="manage-navbar-toggler-checkbox">
			<span class="manage-navbar-toggler-icon"></span>
		</label>		
	</div>
	<div class="manage-navbar-nav">
		<a class="nav-link" href="#">About</a>
		<a class="nav-link" href="#">Services</a>
		<a class="nav-link" href="#">Clients</a>
		<a class="nav-link" href="#">Contact</a>
		<a class="nav-link" href="#">Portfolio</a>
	</div>
</div>

<script id="manage-sidebar-tmpl" type="text/x-jsrender">
<a class="nav-link" href="{{>menu_link}}">{{>menu_nm}}</a>
</script>

<script>
let _manageNav;

$(document).ready(function() {
	_manageNav = new fn_manageNav();
	_manageNav.initialize();
});

function fn_manageNav() {
	let $this = this;
	let PAGE_URL = "/menu/manageNav";
	let $template;
	
	this.initialize = function() {
		$template = $("#manage-sidebar-tmpl");
		$this.initData();
	}
	
	this.initData = function() {
		//$this.dataManager.getData();
	}
	
	this.dataManager = {
		getData : function() {
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : $this.dataManager.getTargetData()
				, success : function(data){
					$this.dataManager.setData(data);
				}
			});
		},
		getTargetData : function() {
			let data = {};
			data["target_cd"] = $("#target_cd").length > 0 ? $("#target_cd").val()
								: $("#cat_cd").length > 0 ? $("#cat_cd").val()
								: "";
			return data;

		},
		setData : function(data) {
			if(data['manageMenuList']){
				let htmlOut = $template.render(data['manageMenuList']);
				$(".manage-navbar-nav")
				.append(htmlOut);
			}
		}
	}
}
</script>