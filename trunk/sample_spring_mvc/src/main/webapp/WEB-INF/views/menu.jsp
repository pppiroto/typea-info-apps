<!DOCTYPE html>
<%--
DOCTYPE
  http://www.htmq.com/html5/doctype.shtml
     ・DOCTYPE宣言が無いと、一般的なブラウザではレンダリングモードが互換モード（Quirks mode）となります。 互換モードのブラウザではCSSの解釈が標準と異なるため、制作者の意図しないレイアウトになってしまう場合があります 
     ・IE6も含めて、主要なブラウザでは文書の先頭に <!DOCTYPE html> と記述することで標準モードとなります
 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="jquery/css/smoothness/jquery-ui-1.8.14.custom.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-datatables/media/css/demo_table_jui.css"/>
	<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" target="_self">
	<title>Sample Menu</title>
</head>
<body>
	<h1>Sample Menu</h1>
	<ul>
		<c:forEach items="${menulist}" var="menu">
			<li><a href="${menu.url}" id="menu_${menu.menuId}" target="_blank">${menu.name}</a></li>
		</c:forEach>
	</ul>
</body>
</html>
