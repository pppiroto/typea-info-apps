<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"  %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"    prefix="c"     %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"    prefix="fmt"   %>
<%@ taglib uri="http://typea.info/fugitive/eltags"   prefix="util"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="Content-Script-Type" content="text/javascript" />
  <link href="<html:rewrite page="/pages/al01/css/al01.css"/>" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="<html:rewrite page="/pages/al01/js/al01.js"/>"></script>
  <html:base ref="site"/>
  <title>${username} のアルバム 「${photo_title}」</title>
</head>
<body>
  <h1>${username} のアルバム 「${photo_title}」</h1>
  <hr>
  <html:img action="/pages/al01/secure/al01_01.do?${actionParam}" />
  <hr>
  <%=(new java.util.Date()).toString() %>
  <div style="margin-top:50px;">
    <iframe src="http://rcm-jp.amazon.co.jp/e/cm?t=typea09-22&o=9&p=40&l=ur1&category=amazongeneral&banner=0HWDQZNQKPDYKKBZFJR2&f=ifr" width="120" height="60" scrolling="no" marginwidth="0" style="border:none;" frameborder="0"></iframe>
    <jsp:include flush="true" page="/pages/include/rakuten2.jsp" /><br/>
    <jsp:include flush="true" page="/pages/include/google_analisys.jsp" />
  </div>
  
</body>
</html:html>