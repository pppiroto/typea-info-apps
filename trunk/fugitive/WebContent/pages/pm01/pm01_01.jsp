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
  <link href="<html:rewrite page="/css/common.css"/>" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="<html:rewrite page="/js/prototype.js"/>"></script>
  <script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"></script>
  <script type="text/javascript">//<![CDATA[
  /**
   *
   */
  function doSubmit(trxId) {
      var form = $("pm01_01form");
      form.trxId.value = trxId;
      form.submit();
  }
  
  //]]></script>
  <style type="text/css">
    h1 {
      background-image: url("<html:rewrite page="/img/img_h1.gif"/>");
      background-repeat: repeat-x;
      background-position: bottom;
    }
    .title-field {
      background-image: url("<html:rewrite page="/img/img_th.gif"/>");
      background-repeat: repeat-x;
      background-position: bottom;
    }
    .num-field {
      padding-right:5px;
      color:blue;
    }
  </style>
  <html:base ref="site"/>
  <title>COCOMOによる工数計算</title>
</head>
<body>
  <h1>COCOMOによる工数計算</h1>
  <div style="font-size:80%;color:navy;">
    プログラムサイズまたは、工数を選択し値を入力、計算ボタンを押します。
  </div>
  <c:set var="nfmt" value="0.00"/>
  <html:form styleId="pm01_01form" action="/pages/pm01_01" method="POST" onsubmit="return false">
    <html:hidden property="trxId" />
    <table border="0" class="sheet" style="width:400px;">
      <tr>
        <td><html:radio property="calc_mode" value="kdsi">プログラムサイズ(KLOC)</html:radio></td>
        <td><html:text property="kdsi" size="7"/></td>
      </tr>
      <tr>
        <td><html:radio property="calc_mode" value="effort">工数(PM)</html:radio></td>
        <td><html:text property="effort" size="7"/></td>
      </tr>
      <tr>
        <td><html:button property="search" onclick="javascript:doSubmit('calc');" value="計算" /></td>
      </tr>
    </table>
    <hr/>
    <table border="0">
      <tr>
      <td>
        <table border="1" class="sheet">
          <caption>COCOMO計算式</caption>
          <col style="width:200px;"/>
          <col style="width:80px;"/>
          <tr class="sheet">
            <th class="title-field">プログラムサイズ(KLOC)</th>
            <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.kdsi}"/></td>
          </tr>
          <tr class="sheet">
            <th class="title-field">工数(PM)</th>
            <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.effort}"/></td>
          </tr>
         <tr class="sheet">
           <th class="title-field">開発期間(M)</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.tdev}"/></td>
         </tr>
         <tr class="sheet">
           <th class="title-field">開発要員(P)</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.fsp}"/></td>
         </tr>
         <tr class="sheet">
           <th class="title-field">生産性(KLOC/PM)</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.prod}"/></td>
         </tr>
       </table>
       <table border="1" class="sheet">
         <caption>工数の局面への分布</caption>
         <col style="width:200px;"/>
         <col style="width:80px;"/>
         <tr class="sheet">
           <th class="title-field">計画と要件定義</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.plan_requirements}"/></td>
         </tr>
         <tr class="sheet">
           <th class="title-field">製品設計</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.product_design}"/></td>
         </tr>
         <tr class="sheet">
           <th class="title-field">詳細設計</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.detailed_design}"/></td>
         </tr>
         <tr class="sheet">
           <th class="title-field">プログラミングと単体テスト</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.code_unit_test}"/></td>
         </tr>
         <tr class="sheet">
           <th class="title-field">統合とテスト</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.integration_test}"/></td>
         </tr>
         <tr class="sheet">
           <th class="title-field">合計</th>
           <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.total}"/></td>
         </tr>
       </table>
     </td>
     <td valign="top" style="padding:20px;">
       ${util:amazon("4798107204")}<br>
       <span style="color:blue;font-size:90%;">計算方法は、この本から。</span>
     </td>
     </tr>
    </table>

    <table border="1" class="sheet">
      <caption>活動の局面ごとの分布</caption>    
      <col style="width:150px;"/>
      <col style="width:100px;"/>
      <col style="width:80px;"/>
      <col style="width:80px;"/>
      <col style="width:80px;"/>
      <col style="width:80px;"/>
      <col style="width:80px;"/>
      <tr class="sheet">
        <th class="title-field" rowspan="2">局面</th>
        <th class="title-field" rowspan="2">計画と要件定義</th>
        <th class="title-field" rowspan="2">製品設計</th>
        <th class="title-field" colspan="2">プログラミング</th>
        <th class="title-field" rowspan="2">統合とテスト</th>
        <th class="title-field" rowspan="2">合計</th>
      </tr>
      <tr class="sheet">
       <th class="title-field" >詳細設計</th>
        <th class="title-field" >プログラミングと単体テスト</th>
      </tr>
      <c:forEach var="i" begin="0" end="8">
        <tr class="sheet">
          <th class="title-field">
          <c:choose>
            <c:when test="${i==0}">要件分析</c:when>
            <c:when test="${i==1}">製品設計</c:when>
            <c:when test="${i==2}">プログラミング</c:when>
            <c:when test="${i==3}">テスト計画</c:when>
            <c:when test="${i==4}">検証</c:when>
            <c:when test="${i==5}">プロジェクト管理</c:when>
            <c:when test="${i==6}">構成管理と品質保障</c:when>
            <c:when test="${i==7}">文書化</c:when>
            <c:when test="${i==8}">合計</c:when>
          </c:choose>
          </th>
            <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.phase_plan_requirements[i]}"/></td>
            <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.phase_product_design[i]}"/></td>
            <td class="num-field" colspan="2"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.phase_programming[i]}"/></td>
            <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.phase_integration_test[i]}"/></td>
            <td class="num-field"><fmt:formatNumber pattern="${nfmt}" value="${cocomo.phase_total[i]}"/></td>
         </tr>
      </c:forEach>  
    </table>
  </html:form>
  <br>
  ${util:amazon("1930699751")}
  ${util:amazon("4872685342")}
  ${util:amazon("4872685563")}
  ${util:amazon("4872685644")}
  ${util:amazon("4274066150")}
  ${util:amazon("4798113441")}
  <br>
  <jsp:include flush="true" page="/pages/include/amazon.jsp" />  
  <jsp:include flush="true" page="/pages/include/google_ads3.jsp" />
  <jsp:include flush="true" page="/pages/include/google_analisys.jsp" />
</body>
</html:html>