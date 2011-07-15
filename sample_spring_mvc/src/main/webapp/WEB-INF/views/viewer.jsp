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
	<h1>Viewer Sample</h1>
	<p><a href="#" id="usage">使い方</a></p>
	<div id="usage_dialog" title="このページが含んでいる内容について">
		この画面は、<a href="http://www.atmarkit.co.jp/fwcr/rensai/design_pattern02/01.html" target="_blank">Viewer（閲覧する） 画面</a>
		のサンプルです。	以下の内容を含んでいます。
		<ul>
			<li>Ajaxにより非同期に取得したXMLのデータをテーブルに追加</li>
			<li>jQueryプラグインによるカスタムテーブルの利用</li>
			<li>jQueryUIの利用(アコーディオン、ダイアログ)</li>
		</ul>
	</div>

	<div id="accordion"> 
	    <h3 class="head"><a href="#">First header</a></h3>
	    <div>First content</div>
	    <h3 class="head"><a href="#">Second header</a></h3>
	    <div>Second content</div>
	    <h3 class="head"><a href="#">City Table</a></h3>
		<div>
			<table id="tbl_cities" class="display">
				<thead>
				<tr>
					<th>City ID</th>
					<th>名称</th>
					<th>国</th>
					<th>言語</th>
					<th>ISOコード</th>
					<th>地域</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table> 
		</div>
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
			 chache: false,  				// Ajaxレスポンスをキャッシュするか
			 dataType: 'xml',  				// レスポンスの形式
			 error: function(xhr, status, error) {
				 alert('An error occurred:' + error);
			 },
			 timeout: 60000, 				// 60秒のタイムアウト
			 type: 'GET'
		});

		/*
		 * アコーディオンウィジット
		 * http://jqueryui.com/demos/accordion/
		 */
		$( "#accordion" ).accordion({ autoHeight: false });
		$('.accordion .head').click(function() {
			$(this).next().toggle();
			return false;
		}).next().hide();
		$('.accordion .head').click(function() {
			$(this).next().toggle('slow');
			return false;
		}).next().hide();

		/*
		 * テーブルにDataTable jQuery プラグインの適用
		 */
		var citiesTable = $('#tbl_cities').dataTable({
			 "bProcessing": true
			,"bJQueryUI": true
		});
		
		/*
		 * テーブル表示する内容を取得する
		 */
		$.ajax({
			url: 'http://${pageContext.request.serverName}:${pageContext.request.serverPort}/sample_rest_service/city/all',
			success: function(data) {
				$(data).find('city').each(function(){
					var item = $(this);
					
					/*
					 * Debug Log
					 */
					// console.log(item.text());
					
					/*
					 * DataTables API http://www.datatables.net/api
					 */
					var row = [
						    item.children("cityId").text(),  
					        item.children("cityName").text(),
					        item.children("country").text(), 
					        item.children("language").text(),
							item.children("countryBean").children("countryIsoCode").text(),
					        item.children("countryBean").children("region").text(),
							];
					citiesTable.fnAddData(row);
				});
			}
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
