<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<div class="container">
	<h3>아이디와 비밀번호를 입력해주세요.</h3>
	<c:url value="/login" var="loginUrl" />
	<div class="col-md-12">
		<div class="col-md-6">
			<form:form name="f" action="${loginUrl}" method="POST" class="form-group">
			    <c:if test="${param.error != null}">
			        <p class="text-danger">아이디와 비밀번호가 잘못되었습니다.</p>
			    </c:if>
			    <c:if test="${param.logout != null}">
			        <p class="text-muted">로그아웃 하였습니다.</p>
			    </c:if>
			    <div class="form-group">
			        <label for="username" class="form-label mt-4">아이디</label>
			        <input type="text" id="id" name="id" class="form-control"/>
			    </div>
			    <div class="form-group">
			        <label for="password" class="form-label mt-4">비밀번호</label>
			        <input type="password" id="password" name="password" class="form-control"/>
				</div>
			    <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
			    <button type="submit" class="btn btn-warning">로그인</button>
			</form:form>
		</div>
	</div>
</div>
</html>
