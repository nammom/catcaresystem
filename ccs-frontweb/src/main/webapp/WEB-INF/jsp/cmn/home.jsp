<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<div class="container">
		<h1>Home!</h1> 
		<sec:authorize access="isAnonymous()"> 
		<p>
			<a href="<c:url value="/login/login" />">로그인</a>
		</p> 
		</sec:authorize> 
		
		<sec:authorize access="isAuthenticated()"> 
		<form onsubmit="return false":form action="${pageContext.request.contextPath}/logout" method="POST"> 
			<input type="submit" value="로그아웃" /> 
		</form:form> 
		</sec:authorize> 
		
		<h3> 
			[<a href="<c:url value="/introduction" />">소개 페이지</a>] 
			[<a href="<c:url value="/admin/adminHome" />">관리자 홈</a>]
			<br/><br/> 
			[<a href="<c:url value="/sample/form" />">form 샘플</a>] 
			[<a href="<c:url value="/sample/fileform" />">fileform 샘플</a>] 
			[<a href="<c:url value="/sample/datatable" />">datatables 샘플</a>] 
		</h3> 
</div>		


<script type="text/javascript">
       $.ajax();
</script>
<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
