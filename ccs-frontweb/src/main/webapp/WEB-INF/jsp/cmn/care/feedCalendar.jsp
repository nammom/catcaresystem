<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<input type="hidden" id="target_cd" value="<c:out value="${target_cd}" />"/>
<input type="hidden" id="target_type" value="<c:out value="${target_type}" />"/> 
<div class="col-md-12">
	<button type="button" id="btn-list" class="btn btn-warning">통계</button>
	<button type="button" id="btn-calendar" class="btn btn-warning">달력</button>
	<button type="button" id="btn-add" class="btn btn-warning">등록</button>
</div>
<div class="col-md-12" id="feed-calendar">
<!--calendar 영역  -->
</div>

<script>
let _mypage = null;

$(document).ready(function() {
	_mypage = new fn_page();
	_mypage.initialize();
});

function fn_page() {
	let $this = this;
	let PAGE_URL = "/care/feed";
	let $calendar;
	let TARGET_CD, TARGET_TYPE;
	
	this.initialize = function() {
		//캘린더 생성		
		$this.calendarManager.createCalendar();
		//데이터 조회
		$this.initData();

		$("#btn-list").click(function() {
			$this.locationManager.list();
		});
		
		$("#btn-calendar").click(function() {
			$this.locationManager.calendar();
		});
		
		$("#btn-add").click(function() {
			$this.locationManager.form();
		});
 		
		//달력 이전 버튼
		$('button.fc-prev-button').click(function() {
            $this.dataManager.getData();
        });
		//달력 다음 버튼
		$('button.fc-next-button').click(function() {
            $this.dataManager.getData();
        }); 
	}
	
	this.initData = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		TARGET_CD = $("#target_cd").val() ? Number($("#target_cd").val()) : null;
		TARGET_TYPE = $("#target_type").val();
		
		window['sessionStorage'].clear();
		window['sessionStorage'].setItem("list_type", "calendar");
		window['sessionStorage'].setItem("target_type", TARGET_TYPE);
		window['sessionStorage'].setItem("target_cd", TARGET_CD);
		
		$this.dataManager.getData();
	}
	
	this.calendarManager = {
		createCalendar : function() {
			
			let feedDt = window['sessionStorage'].getItem("feedDt");
			let calendarEl = document.getElementById('feed-calendar');
	        $calendar = new FullCalendar.Calendar(calendarEl, {
	          initialView: 'dayGridMonth'
	          , locale: "ko"
	          , headerToolbar: {
	              left: 'prev,next today',
	              center: 'title',
	              right: 'dayGridMonth,timeGridWeek,timeGridDay'
	            }
	          , initialDate : feedDt? new Date(feedDt) : new Date()
	          , eventTimeFormat: { // like '14:30:00'
				    hour: '2-digit',
				    minute: '2-digit',
				    hour12: false
				  }
	          , eventClick: function(info) {
	        	    if (info.event.id) {
	        	    	$this.locationManager.detail(info.event.id);
	        	    }
	        	  }
	        });
	        $calendar.render();
		},
		getDate : function(info) {
			let result = {};
			let tglCurrent = $calendar.getDate();
			let date = new Date(tglCurrent);
			result['date'] = date;
			result['year'] = date.getFullYear();
			result['month'] = date.getMonth() + 1;
			
			return result;
		}
	}
	
	this.dataManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/calendar/selectData"
				, data : this.getSelectData()
				, success : function(data){
					$this.dataManager.setData(data['feedList']);
				}
			});
		},
		//getData의 ajax Data
		getSelectData : function(){
			let data;
			
			let date = $this.calendarManager.getDate();
			let feed_date = String(date['year']) + String(date['month'] < 10 ? "0" + date['month'] : date['month']);
			
			switch(TARGET_TYPE){
				case "cat" : data = {"cat_cd" : TARGET_CD, "feed_date" : feed_date};
					break;
				case "grp" : data = {"cat_grp_cd" : TARGET_CD, "feed_date" : feed_date};
					break;
				case "habitat" : data = {"habitat_cd" : TARGET_CD, "feed_date" : feed_date};
					break;
			}
			return data;
		},
		setData : function(data) {
			$calendar.removeAllEventSources("feedList");
			let events = {
					id : "feedList"
					, events : this.setCalendarEvent(data)
			}
			$calendar.addEventSource(events);
		},
		setCalendarEvent : function(data) {
			let eventList = [];
			if(data && data.length > 0) {
				eventList = data.map(function(e, idx){
					let event = {};
					//제목
					event['title'] = "급여 대상 : " + e['cat_cd'];
					if(e['cat_name']){
						event['title'] += "(" + e['cat_name'] + ")";
					}
					event['start'] = e['feed_dt'] + "T" + e['feed_time1'] + ":" + e['feed_time2'];
					//url
					event['id'] = e['feed_cd'];
					return event;
				});
			}
			return eventList;
		}
	}
	
	this.locationManager = {
		detail : function(feed_cd){
			let date = $this.calendarManager.getDate();
			//sessionStorage에 년월 저장(상세페이지에서 뒤로가기시 사용)
			window['sessionStorage'].setItem("feedDt", date['date']);
			
			location.href = PAGE_URL + "/detail/" + feed_cd;
		},
		form : function(){
			location.href = PAGE_URL + "/form/" + TARGET_CD;
		},
		list : function(){
			location.href = PAGE_URL + "/" + TARGET_TYPE + "/" + TARGET_CD;
		},
		calendar : function(){
			location.href = PAGE_URL + "/calendar/" + TARGET_TYPE + "/" + TARGET_CD;
		},
	}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>