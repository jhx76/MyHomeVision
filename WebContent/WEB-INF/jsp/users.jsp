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
		<ul class="table_functions">
			<li><a href="user_subscription">add user</a></li>
			<li><a href="#">remove user</a></li>
		</ul>
		 <table class="display_table">
			<tr> <th>Id</th> <th>Login</th> <th>Role</th> <th>email</th> <th></th><th></th> </tr>
    		<c:forEach items="${userList}" var="user" varStatus="loop">
            	<tr> 
            		<td>${user.id}</td> <td>${user.login}</td> <td>${user.role}</td> <td>${user.email}</td>
            		<td class="table_function">
            			<a href="#">
            				<img src="<%=request.getContextPath()%>/images/icon_modify_24x24.png"/>
            			</a>
            		</td>
            		<td class="table_function" id="delete_user">
            			<input type="image" 
            					class="deletion"
            					id="${user.id}"
            					src="<%=request.getContextPath()%>/images/icon_delete_24x24.png" />
            		</td>
            		<!-- 
            			<a href="delete_user?id=${user.id}">
            				<img src="<%=request.getContextPath()%>/images/icon_delete_24x24.png"/>
            			</a>
            		 -->
            		
            	</tr>
            </c:forEach>
		</table> 
		
		<p><span class="error">${errorMap['sql']}</span><br /></p>
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$(".deletion").click(function(){
			var choice = window.confirm('Supprimer cet utilisateur ?');
			if(choice == true) {
				var url = "delete_user?id="+$(this).attr("id");
				window.location = url;
			}
		});
	});
	</script>
</body>
</html>