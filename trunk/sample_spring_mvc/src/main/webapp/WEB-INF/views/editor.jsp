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
			<li>データのCRUD操作</li>
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
				<th>空港</th>
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
    	<table id="tbl_edit_city" class="">
    		<tbody>
	    		<tr><td>City ID</td><td><input id="txt_cityId" type="text" readonly class="ui-state-disabled" size="6" /></td></tr>
				<tr><td>名称        </td><td><input id="txt_cityName" type="text" size="16" maxlength="24"/></td></tr>
				<tr><td>国             </td><td><input id="txt_country" type="text" size="16" maxlength="26"/></td></tr>
				<tr><td>空港        </td><td><input id="txt_airport" type="text" size="3" maxlength="3"/></td></tr>
				<tr><td>言語        </td><td><input id="txt_language" type="text" size="10" maxlength="16"/></td></tr>
				<tr><td>ISOコード</td><td><select id="sel_countryIsoCode"></select></td></tr>
			</tbody>
    	</table>
    	<button id="btn_update_city">更新</button>
    	<button id="btn_clear_city">クリア</button>
    	<button id="btn_insert_city">新規作成</button>    	
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
			,"fnInitComplete": function() {
				reloadTableData(this);
			}
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
		/*
		 * ボタン
		 */
		$("button").button();
		
		/* クリア */
		$("#btn_clear_city").click(function(){
			var edit_table = $('#tbl_edit_city'); 
			var targets = ['input','select'];
			for (var i=0;i<targets.length; i++) {
				$(targets[i], edit_table).each(function(){
					$(this).val("");
				});
			}
		});
		
		$("#btn_insert_city").click(function(){
			if ($("#txt_cityId").val().trim() != "") {
				alert("Error : 新規作成時、IDはブランク");
				exit();	
			}
			var city = new City();
			city.cityId = '';
			city.cityName = $("#txt_cityName").val();
			city.country = $("#txt_country").val();
			city.airport = $("#txt_airport").val();
			city.language = $("#txt_language").val();
			city.countryIsoCode = $("#sel_countryIsoCode").val();
			
			$.ajax({
				url: 'http://${pageContext.request.serverName}:${pageContext.request.serverPort}/sample_rest_service/city'
				,contentType: 'application/xml;charset=UTF-8'   
				,type: 'POST'
				,data: city.toXML()
				,success: function(data) {
					//alert("Inserted : " + $(data).text() + ", Reload Table data.")
					reloadTableData(citiesTable);
				}
			});
			
		});
		
		$("#btn_update_city").click(function(){
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
		$("#txt_airport").val(row[3]);
		$("#txt_language").val(row[4]);
		$("#sel_countryIsoCode").val(row[5]);
		$("#sel_region").val(row[6]);
		
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
	 * テーブル表示する内容を取得する
	 */	
	function reloadTableData(target) {
		$.ajax({
			url: 'http://${pageContext.request.serverName}:${pageContext.request.serverPort}/sample_rest_service/city/all',
			success: function(data) {
				target.fnClearTable();
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
					        item.children("airport").text(), 
					        item.children("language").text(),
							item.children("countryBean").children("countryIsoCode").text(),
					        item.children("countryBean").children("region").text(),
							];
					target.fnAddData(row);
				});
			}
		});	
	}
	
	/**
	 * City オブジェクト 
	 */
	function City() {
		this.cityId = '';
		this.cityName = '';
		this.country = '';
		this.airport = '';
		this.language = '';
		this.countryIsoCode = '';
	}
	
	City.prototype = {
		toXML : function() {
			// TODO 
			//   URI エンコーディング対応方法検討
			//     1.エンコードする
			//     2.入力不可とする
			//
			var xml = 
				'<city>'
				+'<cityId>'           + this.cityId   + '</cityId>'
				+'<cityName>'         + this.cityName + '</cityName>'
				+'<airport>'          + this.airport  + '</airport>'
				+'<country>'          + this.country  + '</country>'
				+'<language>'         + this.language + '</language>'
				+'<countryBean>'
				+  '<countryIsoCode>' + this.countryIsoCode +'</countryIsoCode>'
				+  '</countryBean>'
				+'</city>'
				;			
			return xml;
		}
	}
	</script>
</body>
</html>
