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
  <link href="<html:rewrite page="/pages/ab01/css/ab01.css"/>" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="<html:rewrite page="/pages/ab01/js/prototype.js"/>"></script>
  <script type="text/javascript" src="<html:rewrite page="/pages/ab01/js/ab01.js"/>"></script>
  <html:base ref="site"/>
  <title>あまじょん : ${book.title}</title>
</head>
<body>
  <html:form styleId="ab01_01form" action="/pages/ab01_01" method="POST" onsubmit="return false">
    <html:hidden property="trxId" value=""/>
    <html:hidden property="selected_asin" value="${book.asin}"/>
    <div id="titie_panel">
    	<a href="<html:rewrite page="/pages/ab01_01.do"/>"><img id="title_img" border="0" src="<html:rewrite page="/pages/ab01/img/amajon.gif"/>" /></a>
	  	<a href="<html:rewrite page="/pages/ab01_01.do"/>"><span style="font-size:80%">使い方</span></a>
	  	asin : <html:text styleId="asin"  property="asin" size="13" maxlength="20" />
	  	<html:button styleId="bookinfo" property="bookinfo" value="表示" />
	  	<iframe src="http://rcm-jp.amazon.co.jp/e/cm?t=typea09-22&o=9&p=40&l=ur1&category=amazongeneral&banner=0HWDQZNQKPDYKKBZFJR2&f=ifr" width="120" height="60" scrolling="no" marginwidth="0" style="border:none;" frameborder="0"></iframe>
	  	<jsp:include flush="true" page="/pages/include/rakuten.jsp" />
  	</div>
  	<c:if test='${book.asin != null && book.asin != ""}'>
	  	<div>
	  		このページへのリンク : <input id="link_this_page" type="text" readonly="readonly" size="150" value="http://${applicationScope.serverName}<html:rewrite action='/pages/ab01_01?trxId=info&asin=${book.asin}'/>"/>
	  	</div>
	</c:if>
  	<hr/>
  	<div id="wait_msg_panel">
		<table>
			<tr>
				<td><img id="wait_img" src="<html:rewrite page="/pages/ab01/img/wait.gif"/>" />	</td>
				<td style="vertical-align:middle;color:lemonchiffon">ただいま読み込み中。</td>
			</tr>
		</table>
	</div>
	<div id="main_panel">
	    <c:choose>
			<c:when test='${book.asin != null && book.asin != ""}'>
				<html:link href="${book.page_url}" target="_blank" styleId="book_title">${book.title}</html:link>
				<a href="${book.page_url}'/>" target="_blank">
					<img src="<html:rewrite page="/pages/ab01/img/opnlnk.gif"/>" border="0" alt="リンクを開く"/>
				</a>
				<div>画面をクリックするとコメントを追加できます。</div>
				<div id="comment_panel" style="visibility:hidden;">
			  		<html:img styleId="book_image" src="${book.large_image}"/>
			  		<logic:iterate id="comment" name="book" property="commentList" indexId="idx">
						<div id="comment_${idx}" style="position:absolute;top:${comment.pos_y}px;left:${comment.pos_x}px;">
						    ${util:blockComment(util:addlink(comment.book_comment),30)}
						</div>
					</logic:iterate>
				</div>
				<div id="comment_entry_panel" style="visibility:hidden;">
					<div id="comment_entry_menu"><a href="#" id="comment_entry_close">×</a></div>
					<div id="comment_entry_content">
						<html:textarea styleId="comment_edit_box" property="comment" rows="4" cols="20" />	
					</div>
					<div id="comment_entry_control">
						<html:button styleId="addcomment" property="add_comment" value="登録" />
						<html:hidden styleId="pos_x" property="pos_x"/>
						<html:hidden styleId="pos_y" property="pos_y"/>
					</div>
				</div>
				<div id="menu_panel" style="visibility:hidden;">
					<html:link styleId="amazon_link" href="http://www.amazon.co.jp/exec/obidos/ASIN/${book.asin}/typea09-22" target="_blank">リンクを表示</html:link>
					<hr/>
					<a href="#" id="comment_link">コメントを追加</a>
				</div>
			</c:when>
			<c:otherwise>
				<jsp:include flush="true" page="/pages/ab01/usage.jsp" />
			</c:otherwise>
		</c:choose>
	</div>
	<div id="youtube_panel">
	    <div style="background-color:darkgray;height:20px;width:485px;">youtube</div>
		<jsp:include flush="true" page="/pages/include/adsense_player.jsp" />
	</div>
	<div id="recently_access_panel">
		<div style="background-color:darkgray;height:18px;width:600px;margin:8px;">最近アクセスされたページ</div>
		<logic:iterate id="recently_book" name="access_books" indexId="books_idx">
			<a href="${recently_book.page_url}'/>" style="text-decoration:none;" target="_blank">
				<html:img src="${recently_book.small_image}" border="0"/>
			</a>
			<a href="http://${applicationScope.serverName}<html:rewrite action='/pages/ab01_01?trxId=info&asin=${recently_book.asin}'/>" style="text-decoration:none;">
				<span style="color:white;font-size:7pt;">${recently_book.shortTitle}</span>
			</a>
			<a href="${recently_book.page_url}'/>" style="text-decoration:none;" target="_blank">
				<img src="<html:rewrite page="/pages/ab01/img/opnlnk.gif"/>" border="0" alt="リンクを開く"/>
			</a>
		</logic:iterate>
	</div>
  </html:form>  
  <a href="http://hb.afl.rakuten.co.jp/hsc/07085e46.ed25c89e.07085ebd.ed521f9c/" target="_blank"><img src="http://hbb.afl.rakuten.co.jp/hsb/07085e46.ed25c89e.07085ebd.ed521f9c/" border="0"></a><br/>
  
  <div id="add_panel">
	  <jsp:include flush="true" page="/pages/include/google_ads2.jsp" /><br/>
	  <jsp:include flush="true" page="/pages/include/google_ads.jsp" /><br/>
	  <jsp:include flush="true" page="/pages/include/rakuten2.jsp" /><br/>
  </div>
  
  <jsp:include flush="true" page="/pages/include/amazon.jsp" />
  <jsp:include flush="true" page="/pages/include/google_analisys.jsp" />
</body>
</html:html>