<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="jquery/css/smoothness/jquery-ui-1.8.14.custom.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-datatables/media/css/demo_table_jui.css"/>
	
	<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" target="_self">
	<title>Viewer Sample</title>
</head>
<body>
	<h1>Login Sample</h1>
	<p><a href="#" id="usage">使い方</a></p>
	<div id="usage_dialog" title="このページが含んでいる内容について">
		この画面は、ログインのサンプルです。	以下の内容を含んでいます。
		<ul>
			<li>xxxx</li>
			<li>xxxx</li>
			<li>xxxx</li>
		</ul>
	</div>



	<%-- 
		jQueryクックブック by jQuery Community Experts http://t.co/CQAXtYY 
		レシピ 1.2 
		･最近の最適化技術によって、JavaScript がページ解析の最後にブラウザに読み込まれるほうが、ページの読み込みがすばやい
		・JavaScriptコードをWebページの最後に配置 (</body>要素の前、ready()イベント不要)
		レシピ14.1
		・jQuery UIテーマへのリンク(<head>)、スクリプトの互換バージョン、UIスクリプトの順にインクルード
	--%>
	<script type="text/javascript" src="jquery/js/jquery-1.5.1.min.js"></script>	
	<script type="text/javascript" src="jquery/js/jquery-ui-1.8.14.custom.min.js"></script>	
	<script type="text/javascript" src="jquery-datatables/media/js/jquery.dataTables.min.js"></script>	
	<script type="text/javascript">
	var test;
	/*
	 * DOM が構築されたら呼び出される処理
	 */
	$(function(){
		/*
		 * jQueryクックブック by jQuery Community Experts http://t.co/CQAXtYY 
		 * レシピ 16.2 Ajax リクエストのデフォルト値を定義
		 */
		$.ajaxSetup({
			 chache: true,  				// Ajaxレスポンスをキャッシュするか
			 dataType: 'xml',  				// レスポンスの形式
			 error: function(xhr, status, error) {
				 alert('An error occurred:' + error);
			 },
			 timeout: 60000, 				// 60秒のタイムアウト
			 type: 'GET'
		});
	
		/*
		 * ダイアログ
		 */
		$("#usage_dialog" ).dialog({
			show: "blind",
			hide: "blind"
		});
		$("#usage").click(function(){
			$( "#usage_dialog" ).dialog();
			return false;
		});
	});
	</script>
</body>
</html>
