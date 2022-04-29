<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<c:url value="/login" var="loginUrl" />
	<div class="col-md-12">
		<div class="col-md-6">
		<form:form class="form-group">
		    <div class="form-group">
		        <label for="username" class="form-label mt-4">아이디</label>
		        <input type="text" id="id" name="id" class="form-control"/>
		    </div>
		    <div class="form-group">
		        <label for="password" class="form-label mt-4">비밀번호</label>
		        <input type="password" id="password" name="password" class="form-control"/>
			</div>
		    <button type="submit" class="btn btn-warning">로그인</button>
		    <button type="submit" class="btn btn-secondary">취소</button>
		</form:form>
		</div>
	</div>
</div>

</body>
</html>
