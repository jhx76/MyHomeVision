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
		<a href="configure_habitation"><img src="<%=request.getContextPath()%>/images/icon_previous.png" /></a>
		<form action="<c:url value="/set_pallier_picture" />" method="post" enctype="multipart/form-data">
			<fieldset>
				<legend>Image du pallier</legend>
				<label for="pallierName">Nom du pallier</label>
				<input type="text" name="pallierName" readonly="readonly" value="${pallier.pallierName }" /><br />
				<p>${pallier.description }</p><br/>
				<label for="pallierImage">Image</label>
				<input type="file" name="pallierImage" id="pallierImage" /><br />
				<input type="submit" value="Envoyer l'image" /><br />
				<p class="error">${upload_error }</p>
			</fieldset>
		</form>
		
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
</body>
</html>