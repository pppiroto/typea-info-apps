<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="jquery/css/smoothness/jquery-ui-1.8.14.custom.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-datatables/media/css/demo_table_jui.css"/>
	<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" target="_self">
	<title>Retriever Sample</title>
</head>
<body>
	<h1>Retriever Sample</h1>
	<p><a href="#" id="usage">使い方</a></p>
	<div id="usage_dialog" title="このページが含んでいる内容について">
		この画面は、<a href="http://www.atmarkit.co.jp/fwcr/rensai/design_pattern02/01.html" target="_blank">Retriever（検索する） 画面</a>
		のサンプルです。	以下の内容を含んでいます。
		<ul>
			<li>xxxx</li>
			<li>jQueryUIによるオートコンプリート(入力補完)</li>
			<li>jQueryUIの利用(タブ、ダイアログ)</li>
		</ul>
	</div>

	<div id="tabs"> 
		<ul> 
			<li><a href="#tabs-searchcondition">検索条件</a></li> 
			<li><a href="#tabs-searchresults">検索結果</a></li> 
		</ul> 
	    <div id="tabs-searchcondition">
	    	<table id="tbl_search_city" class="">
	    		<tbody>
		    		<tr><td>City ID</td><td><input id="txt_cityId" type="text" /></td></tr>
					<tr><td>名称</td><td><input id="txt_cityName" type="text" /></td></tr>
					<tr><td>国</td><td><input id="txt_country" type="text" /></td></tr>
					<tr><td>言語</td><td><input id="txt_language" type="text" /></td></tr>
					<tr><td>ISOコード</td><td><input id="txt_countryIsoCode" type="text" /></td></tr>
					<tr><td>地域</td><td><input id="txt_region" type="text" /></td></tr>	
				</tbody>
	    	</table>
	    	<button id="btn_search">検索</button>
	    	<button id="btn_cancel">クリア</button>
		    </div>
	   <div id="tabs-searchresults">
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
			 chache: true,  				// Ajaxレスポンスをキャッシュするか
			 dataType: 'xml',  				// レスポンスの形式
			 error: function(xhr, status, error) {
				 alert('An error occurred:' + error);
			 },
			 timeout: 60000, 				// 60秒のタイムアウト
			 type: 'GET'
		});

		/*
		 * タブウィジェット
		 * http://jqueryui.com/demos/tabs/
		 */
		var tabs = $( "#tabs" ).tabs();
		
		/*
		 * テーブルにDataTable jQuery プラグインの適用
		 */
		var citiesTable = $('#tbl_cities').dataTable({
			 "bProcessing": true
			,"bJQueryUI": true
		});
		
		/*
		 * オートコンプリート用項目を取得
		 */
		redyAutoComplete('cityNames', 		$('#txt_cityName'));
		redyAutoComplete('country',   		$("#txt_country"));
		redyAutoComplete('language',  		$("#txt_language"));
		redyAutoComplete('countryIsoCode', 	$("#txt_countryIsoCode"));
		redyAutoComplete('region', 			$("#txt_region"));
		
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
		
		/*
		 * ボタン
		 */
		$("button").button();
		
		/*
		 * 検索
		 */
		$("#btn_search").click(function(){
			citiesTable.fnClearTable();
			$.ajax({
				url: 'http://${pageContext.request.serverName}:${pageContext.request.serverPort}/sample_rest_service/city/search/',
				data: {
					"cityId"         : $("#txt_cityId").val(),
					"cityName"       : $("#txt_cityName").val(),
					"country"        : $("#txt_country").val(),
					"language"       : $("#txt_language").val(),
					"countryIsoCode" : $("#txt_countryIsoCode").val(),
					"region"         : $("#txt_region").val()
				},
				success: function(data) {
					$(data).find('city').each(function(){
						var item = $(this);
						
						/*
						 * Debug Log
						 */
						// console.log(item.text());
						
						/*
						 * DataTables 
						 * http://www.datatables.net/api
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
					tabs.tabs("select",1);	
				}
			});			
		});
		
		/*
		 * クリア
		 */
		$("#btn_cancel").click(function(){
			$("#txt_cityId").val("");
			$("#txt_cityName").val("");
			$("#txt_country").val("");
			$("#txt_language").val("");
			$("#txt_countryIsoCode").val("");
			$("#txt_region").val("");
		});
	});
	
	function redyAutoComplete(fieldName, input) {
		$.ajax({
			url: 'http://${pageContext.request.serverName}:${pageContext.request.serverPort}/sample_rest_service/city/suggest/' + fieldName,
			success: function(data) {
				var tags = new Array();
				$(data).find('values').each(function(){
					tags.push($(this).text());
				});
				input.autocomplete({
					source: tags
				});
			}
		});		
	}	
	</script>
</body>
</html>
