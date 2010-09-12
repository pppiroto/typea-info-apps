<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"  %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"    prefix="c"     %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
  <meta http-equiv="Cotent-Type" content="text/html; charset=UTF-8" />
  <meta http-equiv="Content-Script-Type" content="text/javascript" />
  <link href="<html:rewrite page="/css/common.css"/>" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="<html:rewrite page="/js/prototype.js"/>"></script>
  <script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"></script>
  <script type="text/javascript">//<![CDATA[
  /**
   *
   */
  function doSubmit(trxId) {
      var form = $("zz01_01form");
      form.trxId.value = trxId;
      form.submit();
  }
  /**
   *
   */
  function showEditWindow(employeeId) {
      var url = "<html:rewrite action='/secure/zz01_02' />"
              + "?trxId=edit"
              + "&employee_id=" + employeeId
              ;
      window.open(url,"sbwin","width=440px,height=460");
  }
  /**
   *
   */
  function resizeItemPanel() {
      var H_MARGIN = 280;
      var W_MARGIN = 20;
      var SCROLLBAR_MARGIN = 16;

  	  var clientHeight = getWindowClientHeight();
      var clientWidth  = getWindowClientWidth();		
      
      var headerPanel = $("headerPanel");
      var itemPanel   = $("itemPanel");
      
      if (clientHeight > H_MARGIN) {
          itemPanel.style.height = (clientHeight - H_MARGIN) + "px";
      }
      if (clientWidth > W_MARGIN) {
          var tmpWidth = clientWidth - W_MARGIN;
	      headerPanel.style.width = (tmpWidth - SCROLLBAR_MARGIN) + "px"
          itemPanel.style.width   =  tmpWidth + "px"
      }
  }
  /**
   *
   */
  function scrollItemPanel() {
        $("headerPanel").scrollLeft = $("itemPanel").scrollLeft;
  }
  //]]></script>
  <style type="text/css">
  	h1 {
	  	background-image: url("<html:rewrite page="/img/img_h1.gif"/>");
  	}
  	.title-field {
  		background-image: url("<html:rewrite page="/img/img_th.gif"/>");
  	}
  </style>
  <html:base ref="site"/>
  <title>Sample Application</title>
</head>
<body onload="javascript:resizeItemPanel();" onresize="javascript:resizeItemPanel();">
  
  <h1><span style="margin-left:20px;">EMPLOYEE 一覧</span></h1>
  
  <html:form styleId="zz01_01form" action="/secure/zz01_01" method="POST" onsubmit="return false">
    <html:hidden property="trxId" />
    
    <fieldset style="width:550px;">
      <legend class="field-label">検索条件</legend>
      <table class="sheet" style="table-layout:fixed;width:520px;" border="0">
        <col width="160px"/>
        <col width="240px"/>
        <col width="*" />
        <tr>
          <th class="title-field">FIRST NAME</th>
          <td><html:text property="first_name" size="20" maxlength="20"/></td>
        </tr>
        <tr>
          <th class="title-field">LAST NAME</th>
          <td><html:text property="last_name" size="25" maxlength="25"/></td>
        </tr>
        <tr>
          <th class="title-field">EMAIL</th>
          <td><html:text property="email" size="25" maxlength="25"/></td>
        </tr>
        <tr>
          <th class="title-field">SALARY</th>
          <td><html:text property="salary_from" size="10" maxlength="10"/>&nbsp;～&nbsp;<html:text property="salary_to" size="10" maxlength="10"/></td>
        </tr>
      </table>
      <table border="0">
      	<tr>
	      <td><html:button property="search" onclick="javascript:doSubmit('search');" value="検索" /></td>
    	  <td><html:button property="entry"  onclick="javascript:showEditWindow('');" value="新規登録" /></td>
    	</tr>
      </table>
    </fieldset>
    
    <div>
      <span class="field-label" style="padding-left:10px;">検索結果</span>
      <span class="field-value" style="padding-left:20px;">
        <bean:size id="emp_size" name="employees" scope="session" />
        <c:out value="${emp_size}"/>
      </span>
      <span class="field-label">件</span>
      <html:img page="/img/csv.jpg" onclick="javascript:doSubmit('download');" style="margin-left:460px;"/>
    </div>
    
    <div id="headerPanel" class="header-panel" style="width:1050px;border:1px solid lightgrey">
      <table class="sheet" border="1" style="width:1050px;">    
        <col width="80px"  />
      	<col width="150px" />
      	<col width="150px" />
      	<col width="100px" />
      	<col width="150px" />
	    <col width="100px" />
      	<col width="100px" />
      	<col width="100px" />
        <col width="80px"  />
        <col width="80px"  />
        <col width="80px"  />
      	<tr>
          <th class="sheet title-field">EMP.ID      </th>
          <th class="sheet title-field">FIRST NAME  </th>
          <th class="sheet title-field">LAST NAME   </th>
          <th class="sheet title-field">EMAIL       </th>
          <th class="sheet title-field">PHONE NUMBER</th>
          <th class="sheet title-field">HIRE DATE   </th>
          <th class="sheet title-field">JOB ID      </th>
          <th class="sheet title-field">SALARY      </th>
          <th class="sheet title-field">COM.PCT     </th>
          <th class="sheet title-field">MNG.ID      </th>
          <th class="sheet title-field">DPT.ID      </th>
        </tr>    
      </table>
    </div>
    <div id="itemPanel" class="item-panel" onscroll="javascript:scrollItemPanel();" style="width:1050px;border:1px solid lightgrey;">
      <table class="sheet" border="1" style="width:1050px;">    
        <col width="80px"  />
        <col width="150px" />
        <col width="150px" />
        <col width="100px" />
        <col width="150px" />
        <col width="100px" />
        <col width="100px" />
        <col width="100px" />
        <col width="80px"  />
        <col width="80px"  />
        <col width="80px"  />
        <c:forEach var="emp" items="${sessionScope.employees}" varStatus="loop">
          <c:set var="trcls" value=""/>
          <c:if test="${loop.count % 2 == 0}">
            <c:set var="trcls" value="evenrow"/>
          </c:if>
          <tr class="${trcls}">
            <td class="sheet">
              <html:link href="javascript:showEditWindow('${emp.employee_id}');">
                ${emp.employee_id}
              </html:link>
            </td>
            <td class="sheet"          >${emp.first_name}     </td>
            <td class="sheet"          >${emp.last_name}      </td>
            <td class="sheet"          >${emp.email}          </td>
            <td class="sheet"          >${emp.phone_number}   </td>
            <td class="sheet"          >${emp.hire_date}      </td>
            <td class="sheet"          >${emp.job_id}         </td>
            <td class="sheet num-field">${emp.salary}         </td>
            <td class="sheet num-field">${emp.commission_pct} </td>
            <td class="sheet num-field">${emp.manager_id}     </td>
            <td class="sheet num-field">${emp.department_id } </td>            
          </tr>
        </c:forEach>
      </table>
    </div>
  </html:form>
</body>
</html:html>