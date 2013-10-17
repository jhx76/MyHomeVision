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

		<!-- CODE DE LA PAGE -->
		<p class="${empty errorMap ? 'success' : 'error'}">${errorMap['SERVER_ERROR'] }</p>
		<table class="display_table">
		<th>xPL identifier</th><th>Config status</th><th>Registered ?</th>
			<c:forEach items="${deviceList}" var="device" varStatus="loop">
				<tr>${device.address }</tr><tr>${device.configStatus }</tr><tr>false</tr>
			</c:forEach>
		</table>

		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
</body>
</html>