<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="jquery/css/smoothness/jquery-ui-1.8.14.custom.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-datatables/media/css/demo_table_jui.css"/>
	
	<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" target="_self">
	<title>Editor Sample</title>
</head>
<body>
	<div id="usage_dialog" title="このページが含んでいる内容について">
		この画面は、Editor(編集する)のサンプルです。	以下の内容を含んでいます。
		<ul>
			<li>xxxx</li>
			<li>xxxx</li>
			<li>xxxx</li>
		</ul>
	</div>
	
	<div class="ui-layout-north">
		<h1>Editor Sample</h1>
		<a href="#" id="usage">使い方</a>
	</div>
	<div class="ui-layout-center">
		<table id="tbl_cities" class="display">
			<thead>
			<tr>
				<th>ID</th>
				<th>名称</th>
				<th>国</th>
				<th>言語</th>
				<th>ISO</th>
				<th>地域</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div class="ui-layout-east">
    	<table id="tbl_search_city" class="">
    		<tbody>
	    		<tr><td>City ID</td><td><input id="txt_cityId" type="text" /></td></tr>
				<tr><td>名称        </td><td><input id="txt_cityName" type="text" /></td></tr>
				<tr><td>国             </td><td><input id="txt_country" type="text" /></td></tr>
				<tr><td>言語        </td><td><input id="txt_language" type="text" /></td></tr>
				<tr><td>ISOコード</td><td><select id="sel_countryIsoCode"></select></td></tr>
				<tr><td>地域         </td><td><select id="sel_region"></select></td></tr>	
			</tbody>
    	</table>	
	</div>
	<div class="ui-layout-south">South</div>
	<!-- 
	<div class="ui-layout-west">West</div>
 	-->
	
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
	<script type="text/javascript" src="jquery-layout/jquery.layout.min.js"></script>	
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
		 * jQuery UI Layout
		 *     http://layout.jquery-dev.net/
		 * Document
		 *     http://layout.jquery-dev.net/documentation.cfm
		 */
		var dispLayout = $('body').layout({
			 defaults:{
				 applyDefaultStyles: true
			 }	
			,east: {
				size:500
			 }
			,center : {
				/* リサイズ時にテーブルのサイズを調節 */
				onresize  : function(){
					if (citiesTable != null && citiesTable.length > 0) {
						citiesTable.fnAdjustColumnSizing();
					}
				}
			}
		});
		
		/*
		 * テーブルにDataTable jQuery プラグインの適用
		 * Options 
		 *     http://www.datatables.net/usage/options
		 * Columns 
		 *     http://www.datatables.net/usage/columns
		 */
		var citiesTable = $('#tbl_cities').dataTable({
			 "bProcessing": true
			,"sScrollY": "300px"
			,"bScrollCollapse": true
			,"bPaginate": true
			,"bJQueryUI": true
			,"aoColumnDefs": [
							 { "sSortDataType": "dom-text", "sType": "numeric", "aTargets": [ 0 ] }
			                ,{ "sWidth": "20px", "aTargets": [ 0, 4 ] }
			 			]
		});	
		/*
		 * テーブルにコールバックイベントハンドラを追加する
		 */
		$("#tbl_cities").click(function(event){
			$(citiesTable.fnSettings().aoData).each(function (){
				$(this.nTr).removeClass('row_selected');
			});
			var selectedRow = $(event.target.parentNode);
			selectedRow.addClass('row_selected');
			
			var row = new Array();
			var index = 0;
			$('td', selectedRow).each(function(){
				row[index++] = this.innerText;
			});
			selectRow(row);
		});

		/*
		 * ドロップダウンの設定
		 */
		redyDropDown('countryIsoCode', 	$("#sel_countryIsoCode"));
		redyDropDown('region', 			$("#sel_region"));
		
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
					//console.log(item.text());
					
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
	});
	
	/**
	 * 行が選択されたときの処理
	 */
	function selectRow(row) {
		test = row;
		$("#txt_cityId").val(row[0]);
		$("#txt_cityName").val(row[1]);
		$("#txt_country").val(row[2]);
		$("#txt_language").val(row[3]);
		$("#sel_countryIsoCode").val(row[4]);
		$("#sel_region").val(row[5]);
		
	}
	/**
	 * Webサービスを利用してドロップダウンの内容を設定する
	 */
	function redyDropDown(fieldName, dropdown) {
		$.ajax({
			url: 'http://${pageContext.request.serverName}:${pageContext.request.serverPort}/sample_rest_service/city/suggest/' + fieldName,
			success: function(data) {
				$('<option/>').attr('value', "").text("").appendTo(dropdown);
				$(data).find('values').each(function(){
					/*
					 * jQueryクックブック by jQuery Community Experts http://t.co/CQAXtYY 
					 * レシピ 10.6  選択オプションの追加と削除
					 */
					var val = $(this).text();
					$('<option/>').attr('value', val).text(val).appendTo(dropdown);
				});
			}
		});		
	}

	/*
	 * ダイアログ
	 */
	$("#usage_dialog" ).dialog({
		 show: "blind"
		,hide: "blind"
		,modal: true
		
	});
	$("#usage").click(function(){
		$( "#usage_dialog" ).dialog();
		return false;
	});
	
	</script>
</body>
</html>
