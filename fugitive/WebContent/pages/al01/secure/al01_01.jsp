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
  <script type="text/javascript">//<![CDATA[
  var del_s_img  = new Image();
  del_s_img.src  = '<html:rewrite page="/pages/al01/img/del_s.jpg"/>';
  var del_n_img  = new Image();
  del_n_img.src  = '<html:rewrite page="/pages/al01/img/del_n.jpg"/>';
  
  var albm_o_img  = new Image();
  albm_o_img.src  = '<html:rewrite page="/pages/al01/img/albm_o.jpg"/>';
  var albm_c_img  = new Image();
  albm_c_img.src  = '<html:rewrite page="/pages/al01/img/albm_c.jpg"/>';
  /**
   *
   */
  function openAlubum(imgId, albmId) {
      var img  = document.getElementById(imgId);
      var albm = document.getElementById(albmId);
      if (albm.style.display == 'block') {
          albm.style.display = "none";
          img.src = albm_c_img.src;
      } else {
          albm.style.display = "block";
          img.src = albm_o_img.src;
      }
  }
  /**
   *
   */
  function delImgUpdate(img, isMouseOver) {
      if (isMouseOver) {
          img.src = del_s_img.src;
      } else {
          img.src = del_n_img.src;
      }
  }
  //]]></script>
  <html:base ref="site"/>
  <title>${username} のアルバム</title>
</head>
<body>
  <h1>${username} のアルバム</h1>
  <html:form styleId="al01_01form" action="/pages/al01/secure/al01_01" method="POST" onsubmit="return false">
  <html:hidden property="trxId" value=""/>

  <logic:iterate id="album" name="albums" indexId="album_idx">
    <div>
      <img id="dir_img_${album_idx}}" src="<html:rewrite page="/pages/al01/img/albm_c.jpg"/>" alt="アルバム" border="0" onclick="javascript:openAlubum('dir_img_${album_idx}}','albm_${album_idx}}');">
      <a class="album_title" href="javascript:openAlubum('dir_img_${album_idx}}','albm_${album_idx}}');">${album.dirName}</a>
      <c:if test="${isAdmin}">
        <html:link style="font-size :80%;padding-left:10px;" action="/pages/al01/secure/al01_01.do?trxId=thumb&bid=${album.baseDirIndex}&dn=${album.dirName}">
          <img src="<html:rewrite page="/pages/al01/img/thum.jpg"/>">
                    サムネイルを作成
        </html:link>
      </c:if>      
    </div>
    
    <div id="albm_${album_idx}}" style="display:none;background-color: lightyellow;">
      <hr>
      <table border="0">
        <tr>
          <logic:iterate id="src" name="album" property="src" indexId="src_idx">
            <td>      
              <div>
                <html:link action="/pages/al01/secure/al01_01.do?trxId=disp&bid=${album.baseDirIndex}&dn=${album.dirName}&src=${src}" target="_blank">
                  <html:img action="/pages/al01/secure/al01_01.do?trxId=get&bid=${album.baseDirIndex}&dn=${album.thumbDirName}&src=${src}" />
                </html:link>
              </div>
              <div>
                <span class="photo_title">${src}</span>
                <c:if test="${isAdmin}">
                  <html:link action="/pages/al01/secure/al01_01.do?trxId=del&bid=${album.baseDirIndex}&dn=${album.dirName}&src=${src}">
                    <img src="<html:rewrite page="/pages/al01/img/del_n.jpg"/>" alt="削除" border="0" onmouseover="delImgUpdate(this,true);" onmouseout="delImgUpdate(this,false);">
                  </html:link>
                </c:if>
              </div>
            </td>
            <%=(((src_idx + 1) % 8 == 0)?"<tr><tr>":"") %>
          </logic:iterate>
        </tr>
      </table>
      <hr>
    </div>
  </logic:iterate>
  </html:form>  
  <div style="margin-top:50px;">
    <%=(new java.util.Date()).toString() %><br><br>
    <iframe src="http://rcm-jp.amazon.co.jp/e/cm?t=typea09-22&o=9&p=40&l=ur1&category=amazongeneral&banner=0HWDQZNQKPDYKKBZFJR2&f=ifr" width="120" height="60" scrolling="no" marginwidth="0" style="border:none;" frameborder="0"></iframe>
    <jsp:include flush="true" page="/pages/include/rakuten2.jsp" /><br/>
    <jsp:include flush="true" page="/pages/include/google_analisys.jsp" />
  </div>
</body>
</html:html>