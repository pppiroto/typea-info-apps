<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %>

<div style="border:1px dashed lightgrey;background-color:lightskyblue;width:400px;padding:20px;color:navy;">
<h2>使い方</h2>
<p>ここから、Amasonのページを開き、対象の商品を検索します。</p>
<iframe src="http://rcm-jp.amazon.co.jp/e/cm?t=typea09-22&o=9&p=40&l=ur1&category=amazongeneral&banner=0HWDQZNQKPDYKKBZFJR2&f=ifr" width="120" height="60" scrolling="no" marginwidth="0" style="border:none;" frameborder="0"></iframe>
<p>ISBN-10または、ASINという項目のコードをコピーします。</p>
<img src="<html:rewrite page="/pages/ab01/img/usage02.jpg"/>" /><br/>
<p>画面上部のasinに、コードを貼り付けます。</p>
<img src="<html:rewrite page="/pages/ab01/img/usage03.jpg"/>" /><br/>
<p>対象の商品が表示されるので、画面上でクリックするとコメント追加用のメニューが開きます。</p>
<img src="<html:rewrite page="/pages/ab01/img/usage04.jpg"/>" /><br/>
<p>商品に対して、コメントを付けることができます。</p>
<img src="<html:rewrite page="/pages/ab01/img/usage05.jpg"/>" /><br/>
</div>