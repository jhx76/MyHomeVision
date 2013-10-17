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
		<form action="<c:url value="/create_pallier" />" id="pallierForm" method="post">
			<fieldset>
                <legend>Ajouter un nouveau pallier</legend>
                <label for="pallierName">Nom du pallier<span class="requis">*</span></label>
                <input type="text" id="pallierName" name="pallierName" value="${pallier.pallierName }" size="20" maxlength="20" />
                <span class="error">${form.errorMap['pallierName'] }</span> <br />
                <label for="pallierDescription">Description</label>
				<textarea name="pallierDescription" id="pallierDescription" form="pallierForm">
					${pallier.description }
				</textarea> <br />                
	  			<input type="submit" value="Créer un pallier" class="sansLabel" >
	  			<p class="${empty form.errorMap ? 'success' : 'error'}">${form.result}</p>
	  		</fieldset>
		</form>
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
</body>
</html>