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
		        <label for="title" class="form-label mt-4">제목</label>
		        <input type="text" id="title" name="title" class="form-control"/>
		    </div>
		    <div class="form-group">
		        <label for="contents" class="form-label mt-4">내용</label>
		        <textarea id="contents" name="contents" class="form-control"></textarea>
			</div>
			<div class="form-group">
		        <label for="password" class="form-label mt-4">첨부파일</label>
		        <input type="file" id="" name="" class="form-control"/>
			</div>
		    <button type="submit" class="btn btn-warning">저장</button>\
		</form:form>
		</div>
	</div>
</div>
<script type="text/javascript">

</script>
<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>