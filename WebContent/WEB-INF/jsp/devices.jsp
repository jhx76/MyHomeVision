<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css" />
<title>Devices configuration</title>
<script type="text/javascript" src="../js/menu.js"></script>
</head>
<body>
	<div id="main_wrapper">
		<header>
			<jsp:include page="header.jsp" />
			<jsp:include page="menu.jsp" />
		</header>

		<table class="display_table">
			<tr> <th>Id</th> <th>Module address</th> <th>nb numeric</th> <th>nb analogic</th> <th></th><th></th> </tr>
    		<c:forEach items="${moduleList}" var="module" varStatus="loop">
            	<tr> 
            		<td>${module.id}</td> <td>${module.moduleId}</td> <td>${user.role}</td> <td>${user.email}</td>
            		<td class="table_function"><a href="#"><img src="<%=request.getContextPath()%>/images/icon_modify_24x24.png"/></a></td>
            		<td class="table_function"><a href="#"><img src="<%=request.getContextPath()%>/images/icon_delete_24x24.png"/></a></td>
            	</tr>
            </c:forEach>
		</table> 
		
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
</body>
</html>