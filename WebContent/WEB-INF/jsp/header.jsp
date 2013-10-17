<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="general_title">
	<img src="<%=request.getContextPath()%>/images/Zigbus_logo.png" id="logo_general"/>
	<h1>MyHomeVision<br/><small>La domotique "from scratch"</small></h1>
	<c:if test="${!empty sessionScope.userSession}">
		<a href="disconnection"><img src="<%=request.getContextPath()%>/images/icon_exit.png" id="exit_link"/></a>
		<p class="success">Bienvenue, M. ${sessionScope.userSession.login}</p>
	</c:if>
</div>	
</html>