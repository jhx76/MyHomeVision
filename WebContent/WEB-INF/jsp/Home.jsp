<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css" />
	<title>MyHomeVision</title>
	<script type="text/javascript" src="../js/menu.js"></script>
</head>
<body>
	<div id="main_wrapper">
		<header>
			<jsp:include page="header.jsp" />
			<jsp:include page="menu.jsp" />
		</header>
	
	
		<section>
			<h2>Home Page</h2>
			<article>
			<h3>Bienvenue toi, le grand manitou !</h3>
			<p>Balade toi un peu ! Là j'écris juste pour mettre un peu de texte, parce que sinon la page serait complètement blanche.
			Tu peux t'amuser à lire (ou pas), m'enfin tu liras rien de très interressant ici !!</p>
			</article>
			<aside>
			La News du jour !
			</aside>
		</section>
		
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
</body>
</html>