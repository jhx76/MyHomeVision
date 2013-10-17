<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css" />
<title>MHV: Connection</title>
<script type="text/javascript" src="../js/menu.js"></script>
</head>
<body>
	<div id="main_wrapper">
		<header>
			<jsp:include page="header.jsp" />
			<jsp:include page="menu.jsp" />
		</header>
		<form method="post" action="connection">
			<fieldset>
				<legend>Connection</legend>
				<p>Vous pouvez vous connecter via ce formulaire.</p>

				<label for="name">Adresse email<span class="requis">*</span></label>
				<input type="email" id="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60" />
				<span class="error">${form.errorMap['email']}</span> <br /> 
				
				<label for="password">Mot de passe <span class="requis">*</span></label> 
				<input type="password" id="password" name="password" value="" size="20" maxlength="20" /> 
				<span class="error">${form.errorMap['password']}</span>
				<br /> 
				
				<input type="submit" value="Connection" class="sansLabel" /><br />
				<p class="${empty form.errorMap ? 'success' : 'error'}">${form.result}</p>
				
                <c:if test="${!empty sessionScope.userSession}">
                    <p class="success">Vous êtes connecté(e) avec l'adresse : ${sessionScope.userSession.email}</p>
                </c:if>
			</fieldset>
		</form>
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
</body>
</html>