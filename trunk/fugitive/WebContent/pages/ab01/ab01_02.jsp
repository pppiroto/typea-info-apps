<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"  %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"    prefix="c"     %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"    prefix="fmt"   %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="Content-Script-Type" content="text/javascript" />
  <link href="<html:rewrite page="/css/common.css"/>" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="<html:rewrite page="/js/prototype.js"/>"></script>
  <script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"></script>
  <script type="text/javascript">//<![CDATA[
  //]]></script>
  <html:base ref="site"/>
  <title></title>
</head>
<body>
  <h1></h1>
  <html:form styleId="ab01_02form" action="/pages/ab01_02" method="POST" enctype="multipart/form-data" onsubmit="return false">
    <html:hidden property="trxId" />
  </html:form>
</body>
</html:html>