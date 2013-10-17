<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css" />
<title>Configuration de l'habitation</title>
<script type="text/javascript" src="../js/menu.js"></script>
</head>
<body>
	<div id="main_wrapper">
		<header>
			<jsp:include page="header.jsp" />
			<jsp:include page="menu.jsp" />
		</header>
		
		<h2>Les palliers</h2>
		<ul class="table_functions">
			<li><a href="create_pallier">add pallier</a></li>
			<li><a href="#">remove pallier</a></li>
		</ul>
		<br>
		<table class="display_table">
			<tr> <th>Name</th> <th>Description</th> <th>position</th> <th></th><th></th> </tr>
    		<c:forEach items="${pallierList}" var="pallier" varStatus="loop">
            	<tr> 
            		<td><a href="configure_locations?pid=${pallier.id}">${pallier.pallierName}</a></td> <td>${pallier.description}</td> <td>${pallier.position}</td>
            		<td class="table_function" id="modif_pallier">
            			<input type="image" class="modification" id="modifPallier${pallier.id}"
            					src="<%=request.getContextPath()%>/images/icon_modify_24x24.png" />
            		</td>
            		<td class="table_function" id="delete_pallier">
            			<input type="image" class="deletion" id="${pallier.id}"
            					src="<%=request.getContextPath()%>/images/icon_delete_24x24.png" />
            		</td>
            	</tr>
            </c:forEach>
		</table> 
	</div>
	<p class="error">${error }</p>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$(".deletion").click(function(){
			var choice = window.confirm('Supprimer ce pallier ?');
			if(choice == true) {
				var url = "delete_pallier?id="+$(this).attr("id");
				window.location = url;
			}
		});
	});
	</script>
</body>
</html>