
<input type="hidden" id="m_target_type" name="m_target_type" value="<c:out value="${target_type}" />"/>
<input type="hidden" id="m_target_cd" name="m_target_cd" value="<c:out value="${target_cd}" />"/>
<input type="checkbox" id="manage-navbar-toggler-checkbox"/>
<div id="manage-sidenav" class="manage-sidenav">
	<div>
		<label class="manage-navbar-toggler" for="manage-navbar-toggler-checkbox">
			<span class="manage-navbar-toggler-icon"></span>
		</label>		
	</div>
	<div class="manage-navbar-nav">
	</div>
</div>

<script id="manage-sidebar-tmpl" type="text/x-jsrender">
{{if childList}}
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          {{>menu_nm}}
        </a>
        <div class="dropdown-menu manage-dropdown-menu" aria-labelledby="navbarDropdown">
			{{for childList}}
          		<a class="dropdown-item" href="{{>menu_link}}">{{>menu_nm}}</a>
			{{/for}}
        </div>
      </li>
{{else}}
	<li class="nav-item">
		<a class="nav-link" href="{{>menu_link}}">{{>menu_nm}}</a>
	</li>
{{/if}}
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
		$this.dataManager.getData();
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
		/* 	let target_cd= $("#target_cd").length > 0 ? $("#target_cd").val()
								: $("#cat_cd").length > 0 ? $("#cat_cd").val()
								: ""; */
			data["target_cd"] = Number($("#m_target_cd").val());
			data["target_type"] = $("#m_target_type").val();
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
<style>
    .manage-dropdown-menu {
    	border: revert;
    }
</style>