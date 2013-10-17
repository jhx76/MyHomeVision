<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css" />
<title>From Scratch</title>
<script type="text/javascript" src="../js/menu.js"></script>
</head>
<body>
	<div id="main_wrapper">
		<header>
			<jsp:include page="header.jsp" />
			<jsp:include page="menu.jsp" />
		</header>

		<p>
		Pour accéder à cette page, vous devez vous authentifier !
		</p>
		
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
</body>
</html>