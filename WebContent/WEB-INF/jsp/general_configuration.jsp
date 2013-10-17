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
		<form method="post" action="user_subscription">
            <fieldset>
                <legend>xPLHal Server XHCP connection</legend>
                <label for="ip_server">Ip address<span class="requis">*</span></label>
                <input type="text" id="ip_server" name="ip_server" value="<c:out value="${halserver.ip}"/>" size="20" maxlength="20" />
                <span class="error">${form.errorMap['ip_server']}</span>
                <br />
				<label for="port_server">Tcp Port<span class="requis">*</span></label>
				<input type="text" id="tcp_port_server" name="tcp_port_server" value="<c:out value="${halserver.port }"/>" size="20" maxlength="20" />
				<span class="error">${form.errorMap['port_server'] }</span>
				<br />
				<!-- TODO add test connection button -->
            </fieldset>
            <fieldset>
            	<legend>Database connection</legend>
            	<label for="ip_database">IP Address<span class="requis">*</span></label>
            	<input type="text" id="ip_database" name="ip_database" value="<c:out value="${database.ip }"/>" size="20" maxlength="20" />
            	<span class="error">${form.errorMap['ip_database'] }</span>
            	<br />
            	<label for="port_database">Port<span class="requis">*</span></label>
            	<input type="text" id="port_database" name="port_database" value="<c:out value="${database.port }"/>" size="20" maxlength="20" />
            	<span class="error">${form.errorMap['port_database'] }</span>
            	<br />
            	<!-- TODO add test connection button -->
            </fieldset>
            <input type="submit" value="Enregistrer" class="sansLabel" />
            <br />    
        </form>
		
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
</body>
</html>