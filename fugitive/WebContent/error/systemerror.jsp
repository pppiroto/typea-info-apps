<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isErrorPage="true" isELIgnored="false" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"  %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"    prefix="c"     %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt"     prefix="fmt"   %>

<html:html>
<head>
  <meta http-equiv="Cotent-Type" content="text/html; charset=UTF-8" />
  <meta http-equiv="Content-Script-Type" content="text/javascript" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/prototype.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
  <link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">//<![CDATA[
  /**
   *
   */
  function resizeStacktracePanel() {
      var W_MARGIN = 40;
  	  var iheight = getInnerHeight();
      var iWidth  = getInnerWidth();		
	  
	  var msgPanel = $("errmessagePanel");
      var stkPanel = $("stacktracePanel");
	  msgPanel.style.height = (iheight * 0.18) + "px";
      stkPanel.style.height = (iheight * 0.6)  + "px";
      if (iWidth > W_MARGIN) {
          msgPanel.style.width  = (iWidth - W_MARGIN) + "px";
          stkpanel.style.width  = (iWidth - W_MARGIN) + "px";
      }
  }
  //]]></script>
  <title>システムエラー</title>
</head>
<body onload="javascript:resizeStacktracePanel();" onresize="javascript:resizeStacktracePanel();">
  <c:choose>
    <c:when test="${not empty requestScope['javax.servlet.error.exception']}">
      <c:set var="errType" value="servlet"/>
      <c:set var="appException" value="${requestScope['javax.servlet.error.exception'].rootCause}" />                    
    </c:when>
    <c:when test="${not empty pageContext.exception}">
      <c:set var="errType" value="jsp"/>
      <c:set var="appException" value="${pageContext.exception}" />
    </c:when>
    <c:when test="${not empty requestScope['org.apache.struts.action.EXCEPTION']}">
      <c:set var="errType" value="struts"/>
      <c:set var="appException" value="${requestScope['org.apache.struts.action.EXCEPTION']}" />
    </c:when>
    <c:otherwise>
      <c:set var="errType" value="other"/>
    </c:otherwise>
  </c:choose>
  <h1>システムエラー</h1>
  <span style="font-size:80%;color:red;">メッセージ [<c:out value="${errType}"/>]</span>
  <div id="errmessagePanel" class="errmessage-panel">    
    <c:out value="${appException}" />
  </div>
  <div id="stacktracePanel" class="stacktrace-panel" style="margin-top:5px;">
    <c:forEach var="stackItem" items="${appException.stackTrace}">
      <c:out value="${stackItem}"/><br/>
      </c:forEach>
   </div>
</body>
</html:html>