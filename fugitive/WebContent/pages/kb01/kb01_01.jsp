<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"    prefix="c"     %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<title>秘密のかいめちゃん</title>
</head>
<body>
<h1>秘密のかいめちゃん</h1>
<html:link action="/pages/kb01_01?trxId=init">次レース</html:link>
<html:form action="/pages/kb01_01" method="POST">
<input type="submit" value="計算！！">
<html:hidden property="trxId" value="calc"/>
<h2>&lt;条件&gt;</h2>
対象<input size="2" type="text" name="target_race" maxlength="2" value="${KB01_01Form.target_race}">R、中
<input size="2" type="text" name="hit_race" maxlength="2" value="${KB01_01Form.hit_race}">Rの的中を狙う。
今日の予算は、<input size="4" type="text" maxlength="7" name="yosan" value="${KB01_01Form.yosan}">円で、
<input size="4" type="text" maxlength="7" name="rieki" value="${KB01_01Form.rieki}">円の儲けを狙う。
<h2>&lt;オッズ&gt;</h2>
<table>
<tr>
<td>1</td><td><html:text property="odds[0]" size="3" maxlength="7" /></td>
<td>2</td><td><html:text property="odds[1]" size="3" maxlength="7" /></td>
<td>3</td><td><html:text property="odds[2]" size="3" maxlength="7" /></td>
<td>4</td><td><html:text property="odds[3]" size="3" maxlength="7" /></td>
<td>5</td><td><html:text property="odds[4]" size="3" maxlength="7" /></td>
</tr>
<tr>
<td>6</td><td><html:text property="odds[5]" size="3" maxlength="7" /></td>
<td>7</td><td><html:text property="odds[6]" size="3" maxlength="7" /></td>
<td>8</td><td><html:text property="odds[7]" size="3" maxlength="7" /></td>
<td>9</td><td><html:text property="odds[8]" size="3" maxlength="7" /></td>
<td>10</td><td><html:text property="odds[9]" size="3" maxlength="7" /></td>
</tr>
<tr>
<td>11</td><td><html:text property="odds[10]" size="3" maxlength="7" /></td>
<td>12</td><td><html:text property="odds[11]" size="3" maxlength="7" /></td>
<td>13</td><td><html:text property="odds[12]" size="3" maxlength="7" /></td>
<td>14</td><td><html:text property="odds[13]" size="3" maxlength="7" /></td>
<td>15</td><td><html:text property="odds[14]" size="3" maxlength="7" /></td>
</tr>
<tr>
<td>16</td><td><html:text property="odds[15]" size="3" maxlength="7" /></td>
<td>17</td><td><html:text property="odds[16]" size="3" maxlength="7" /></td>
<td>18</td><td><html:text property="odds[17]" size="3" maxlength="7" /></td>
</tr>
</table>
<h2>&lt;狙い&gt;</h2>
1～2頭の選択が吉<br>
◎<html:select property="choise[0]">
<html:option value=""/>
<html:option value="1"/><html:option value="2"/><html:option value="3"/><html:option value="4"/><html:option value="5"/>
<html:option value="6"/><html:option value="7"/><html:option value="8"/><html:option value="9"/><html:option value="10"/>
<html:option value="11"/><html:option value="12"/><html:option value="13"/><html:option value="14"/><html:option value="15"/>
<html:option value="16"/><html:option value="17"/><html:option value="18"/>
</html:select>
○<html:select property="choise[1]">
<html:option value=""/>
<html:option value="1"/><html:option value="2"/><html:option value="3"/><html:option value="4"/><html:option value="5"/>
<html:option value="6"/><html:option value="7"/><html:option value="8"/><html:option value="9"/><html:option value="10"/>
<html:option value="11"/><html:option value="12"/><html:option value="13"/><html:option value="14"/><html:option value="15"/>
<html:option value="16"/><html:option value="17"/><html:option value="18"/>
</html:select>
▲<html:select property="choise[2]">
<html:option value=""/>
<html:option value="1"/><html:option value="2"/><html:option value="3"/><html:option value="4"/><html:option value="5"/>
<html:option value="6"/><html:option value="7"/><html:option value="8"/><html:option value="9"/><html:option value="10"/>
<html:option value="11"/><html:option value="12"/><html:option value="13"/><html:option value="14"/><html:option value="15"/>
<html:option value="16"/><html:option value="17"/><html:option value="18"/>
</html:select>
△<html:select property="choise[3]">
<html:option value=""/>
<html:option value="1"/><html:option value="2"/><html:option value="3"/><html:option value="4"/><html:option value="5"/>
<html:option value="6"/><html:option value="7"/><html:option value="8"/><html:option value="9"/><html:option value="10"/>
<html:option value="11"/><html:option value="12"/><html:option value="13"/><html:option value="14"/><html:option value="15"/>
<html:option value="16"/><html:option value="17"/><html:option value="18"/>
</html:select>
×<html:select property="choise[4]">
<html:option value=""/>
<html:option value="1"/><html:option value="2"/><html:option value="3"/><html:option value="4"/><html:option value="5"/>
<html:option value="6"/><html:option value="7"/><html:option value="8"/><html:option value="9"/><html:option value="10"/>
<html:option value="11"/><html:option value="12"/><html:option value="13"/><html:option value="14"/><html:option value="15"/>
<html:option value="16"/><html:option value="17"/><html:option value="18"/>
</html:select>
<br>
<input type="submit" value="計算！！">
<hr>
<h2>&lt;結果&gt;</h2>
${KB01_01Form.senarioMsg }
<br>
印:★買い(予算内)☆検討(的中確率が期待値まで)＿見送り
※狙い馬
<br>
<table border="1">
<col>
<col align="right">
<col align="right">
<col>
<col align="right">
<col align="right">
<col align="right">
<col align="right">
<col align="right"> 
<tr>
<td>印</td><td>的中確率</td><td>point</td>
<td>馬連</td><td>予想オッズ</td><td>投資金額</td>
<td>期待配当</td><td>掛金計</td><td>的中時利益</td>
</tr>
<c:forEach var="k" items="${KB01_01Form.kaime}" varStatus="loop">
<tr>
<td>${k.coverageMark}</td>
<td>${k.coverageStr}</td>
<td>${k.hoseiPointStr}</td>
<td>${k.umaren}</td>
<td>${k.yosouOddsStr}</td>
<td>${k.tousiKinStr}</td>
<td>${k.kitaiHaitouStr}</td>
<td>${k.yosanHi}</td>
<td>${k.yosanTaihiStr}</td>
</tr>
</c:forEach>
</table>
</html:form>
<html:link action="/pages/kb01_01?trxId=init">次レース</html:link>
</body>
<jsp:include flush="true" page="/pages/include/google_mobile.jsp" />
</html:html>


