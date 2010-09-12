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
  function insertOrUpdateEmployee() {
      var form = document.getElementById("zz01_02form");
      var oldKey = "<bean:write name="ZZ01_02Form" property="bean.employee_id" />";
      var newKey = document.getElementsByName("bean.employee_id")[0].value;
      var trxId = "update";
      if (trim(oldKey) != trim(newKey)) {
          trxId = "insert";
      } 
      form.trxId.value = trxId;
      form.submit();
  }
  /**
   *
   */
  function deleteEmployee() {
      var form = document.getElementById("zz01_02form");
      form.trxId.value = "delete";
      form.submit();
  }
  /**
   *
   */
  function uploadCsv() {
      var form = document.getElementById("zz01_02form");
      form.trxId.value = "upload";
      form.submit();
  }
  /**
   *
   */
  function showUploadInfo() {
      var panel = document.getElementById("uploadInfPanel");
      var newDisplay = "none";
      if (panel.style.display == "none") {
          newDisplay = "block";
      }
      panel.style.display = newDisplay;
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
<body>
  <h1><span style="margin-left:20px;">EMPLOYEE の編集</span></h1>
  <html:form styleId="zz01_02form" action="/secure/zz01_02" method="POST" enctype="multipart/form-data" onsubmit="return false">
    <html:hidden property="trxId" />
    <html:button property="update" value="更新" onclick="javascript:insertOrUpdateEmployee();" />
    <html:button property="delete" value="削除" onclick="javascript:deleteEmployee();" />
    
    <html:messages id="errors" message="true" 
                               header="errors.header" 
                               footer="errors.footer">
      <li><bean:write name="errors"/></li>
    </html:messages>
        
    <table class="sheet" style="table-layout:fixed;width:420px;" border="0">
      <col width="150px"/>
      <col width="180px"/>
      <tr>
        <th class="title-field">EMPLOYEE ID</th>
        <td><html:text property="bean.employee_id" size="6" maxlength="6" /></td>
      </tr>
      <tr>
        <th class="title-field">FIRST NAME</th>
        <td><html:text property="bean.first_name" size="20" maxlength="20"/></td>
      </tr>
      <tr>
        <th class="title-field">LAST NAME</th>
        <td><html:text property="bean.last_name" size="25" maxlength="25"/></td>
      </tr>
      <tr>
        <th class="title-field">EMAIL</th>
        <td><html:text property="bean.email" size="25" maxlength="25"/></td>
      </tr>
      <tr>
        <th class="title-field">PHONE NUMBER</th>
        <td><html:text property="bean.phone_number" size="20" maxlength="20"/></td>
      </tr>
      <tr>
        <th class="title-field">HIRE DATE</th>
        <td><html:text property="bean.hire_date" size="12" maxlength="10"/></td>
      </tr>
      <tr>
        <th class="title-field">JOB ID</th>
        <td><html:text property="bean.job_id" size="10" maxlength="10"/></td>
      </tr>
      <tr>
        <th class="title-field">SALARY</th>
        <td><html:text property="bean.salary" size="11" maxlength="11"/></td>
      </tr>
      <tr>
        <th class="title-field">COMMISSION PCT</th>
        <td><html:text property="bean.commission_pct" size="5" maxlength="5"/></td>
      </tr>
      <tr>
        <th class="title-field">MANAGER ID</th>
        <td><html:text property="bean.manager_id" size="6" maxlength="6"/></td>
      </tr>
      <tr>
        <th class="title-field">DEPARTMENT ID</th>
        <td>
            <html:select property="bean.department_id">
                <html:optionsCollection property="departments" />
            </html:select>
        </td>
      </tr>      
    </table>  
    <hr/>
    <html:link styleClass="field-label" href="javascript:showUploadInfo();">CSVファイルから一括登録</html:link>
    <div id="uploadInfPanel" style="display:none">
      <html:file property="upfile" size="40"/>
      <html:button property="upload" value="一括登録"  onclick="javascript:uploadCsv();"/>
    </div>
  </html:form>
</body>
</html:html>