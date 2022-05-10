<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<!DOCTYPE html> <html lang="ko"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta name="title" content="고양이 돌봄 시스템">	
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<meta name="_csrf" content="${_csrf.token}">

	<link rel="stylesheet" type="text/css" href="/resources/css/jquery.multifile.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/datatables/datatables.css" />
	<script type="text/javascript" src="/resources/js/jquery-3.6.0.min.js"></script>

</head>

<body class="main"> 