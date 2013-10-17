<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css" />
	<title>MHV: user subscription</title>
	<script type="text/javascript" src="../js/menu.js"></script>
</head>
<body>
	<div id="main_wrapper">
		<header>
			<jsp:include page="header.jsp" />
			<jsp:include page="menu.jsp" />
		</header>
		<a href="user_management"><img src="<%=request.getContextPath()%>/images/icon_previous.png" /></a>
        <form method="post" action="user_subscription">
            <fieldset>
                <legend>Add user</legend>
 
 				<label for="login">Login<span class="requis">*</span></label>
                <input type="text" id="login" name="login" value="<c:out value="${user.login}"/>" size="20" maxlength="20" />
                <span class="error">${form.errorMap['login']}</span>
                <br />
                
                <label for="role">Role<span class="requis">*</span></label>
                <input type="text" id="role" name="role" value="<c:out value="${user.role}"/>" size="20" maxlength="20" />
                <span class="error">${form.errorMap['role']}</span>
                <br />
 
                <label for="email">Adresse email <span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60" />
                <span class="error">${form.errorMap['email']}</span>
                <br />
 
                <label for="password">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
                <span class="error">${form.errorMap['password']}</span>
                <br />
 
                <label for="confirmation">Confirmation du mot de passe <span class="requis">*</span></label>
                <input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
                <span class="error">${form.errorMap['confirmation']}</span>
                <br />
 
                <input type="submit" value="Inscription" class="sansLabel" />
                <br />
                
                <p class="${empty form.errorMap ? 'success' : 'error'}">${form.result}</p>
                
            </fieldset>
        </form>
        <footer>
			<jsp:include page="footer.jsp" />
		</footer>
    </div>
</body>
</html>