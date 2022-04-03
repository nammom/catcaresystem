<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
		<h1>Home!</h1> 
		<sec:authorize access="isAnonymous()"> 
		<p>
			<a href="<c:url value="/login/login" />">로그인</a>
		</p> 
		</sec:authorize> 
		
		<sec:authorize access="isAuthenticated()"> 
		<form:form action="${pageContext.request.contextPath}/logout" method="POST"> 
			<input type="submit" value="로그아웃" /> 
		</form:form> 
		</sec:authorize> 
		
		<h3> 
			[<a href="<c:url value="/introduction" />">소개 페이지</a>] 
			[<a href="<c:url value="/admin/adminHome" />">관리자 홈</a>] 
		</h3> 
		<script type="text/javascript">
	        $.ajax();
		</script>
<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
