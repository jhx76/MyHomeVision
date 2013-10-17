<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<!-- inclusion du style CSS de base -->
<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/default.css" />
<!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/tools.css" />-->
<title>Define Locations for ${pallier.pallierName }</title>
<script type="text/javascript" src="../js/menu.js"></script>
</head>
<body>
	<div id="main_wrapper">
		<header>
			<jsp:include page="header.jsp" />
			<jsp:include page="menu.jsp" />
		</header>
		
		<div id="tool_bar">
		<ul>
			<li><span class="ui-icon ui-icon-extlink"></span></li>
		</ul>
		</div>
		<canvas id="home_panel" width="900" height="600" >
		</canvas>
		
		<div id="pallierContentList">
			<h1>${pallier.pallierName }</h1>
			<div class="LocationList">
				<h3>location n°1<span class="ui-icon ui-icon-trash"></span></h3>
				<ul>
					<li>x: 100; y: 200</li>
					<li>x:150; y: 200</li>
					<li>x:150; y: 150</li>
					<li>x: 100; y: 200</li>
				</ul>
				<h3>location n°2<span class="ui-icon ui-icon-trash"></span></h3>
				<ul>
					<li>x: 100; y: 200</li>
					<li>x:150; y: 200</li>
					<li>x:150; y: 150</li>
					<li>x: 100; y: 200</li>
				</ul>
			</div>
		</div>

		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
	<!-- inclusion des librairies jQuery et jQuery UI (fichier principal et plugins) -->
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js"></script>
    <!-- bibliothèque jcanvas qui permet de dessiner sur le canvas html5 en jQuery -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jcanvas.js"></script>
    <!-- le script qui permet de définir les locations sur le canvas -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/drawLocations.js"></script>
</body>
</html>