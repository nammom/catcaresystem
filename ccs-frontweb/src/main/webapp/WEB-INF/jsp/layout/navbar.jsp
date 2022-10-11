<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-warning">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">고도리</a> 
		<label class="navbar-toggler"
			for="navbar-toggler-checkbox" data-bs-toggle="collapse"
			data-bs-target="#navbarNavAltMarkup"
			aria-controls="navbarNavAltMarkup" aria-expanded="false"
			aria-label="Toggle navigation"> <span
			class="navbar-toggler-icon"></span>
		</label> 
		<input type="hidden" id="pagenm" value="<c:out value='${pagenm}' />" />
		<input type="checkbox" id="navbar-toggler-checkbox" />
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-link" data-pagenm="myzone" aria-current="page"
					href="#">나의 돌봄존</a> <a class="nav-link" data-pagenm="habitat"
					href="/manage/habitat">서식지 관리</a> <a class="nav-link"
					data-pagenm="cat" href="/manage/cat">고양이 관리</a> <a class="nav-link"
					data-pagenm="map-search" href="#">지도검색</a> <a class="nav-link"
					data-pagenm="service-center" href="#">고객센터</a>
			</div>
			<div class="icon-navbar-nav">
				<a class="nav-link" data-pagenm="habitat" href="/manage/habitat"></a>
				<a class="nav-link" data-pagenm="myzone" aria-current="page"
					href="#"></a> <a class="nav-link" data-pagenm="cat"
					href="/manage/cat"></a> <a class="nav-link" data-pagenm="etc"
					href="/menu/etc"></a>
			</div>
		</div>
	</div>
</nav>