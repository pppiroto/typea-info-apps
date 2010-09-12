<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<html:rewrite page="/css/common.css"/>" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	h1 {
 	background-image: url("<html:rewrite page="/img/img_h1.gif"/>");
	}
	.title-field {
		background-image: url("<html:rewrite page="/img/img_th.gif"/>");
	}
</style>
<html:base ref="site"/>
<title>Index</title>
</head>
<body>
  <h1>Index</h1>
  <hr/>
  <ol>
    <li><html:link page="/pages/al01/secure/al01_01.do">Album</html:link></li>
    <li><html:link page="/pages/ab01_01.do">Amajon</html:link></li>
    <li><html:link page="/pages/pm01_01.do">Cocomo</html:link></li>
    <li><html:link page="/pages/kb01_01.do">Keiba</html:link></li>
    <li><html:link page="/secure/zz01_01.do">Sample Application</html:link></li>
    <li><html:link page="/api/index.html">API Javadoc</html:link></li>
  </ol>
</body>
</html>