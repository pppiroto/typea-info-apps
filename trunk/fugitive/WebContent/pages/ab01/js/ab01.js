/**** Drag&Drop用 ****/
var dragObject  = null; // 対象オブジェクト
var mouseOffset = null; // マウスオフセット
var linkTarget  = null; 
var isShowMenu  = true;

// Windowロード時にドキュメントの初期化を行う
Event.observe(window, 'load', initDocument, false);

// ドキュメントの初期化を行う
function initDocument() {

	var comment_panel = $('comment_panel');
	setEventHundler();
	if (comment_panel) {
		applyView();
		comment_panel.style.visibility = 'visible';
	}
	waitMessage(false);
	
	// makeDraggable($('main_panel'));
	makeDraggable($('recently_access_panel'));
	makeDraggable($('youtube_panel'));
	// makeDraggable($('add_panel'));
	
}
// イベントハンドラのセット
function setEventHundler() {
	// D&D用のイベントを設定
	Event.observe(document, 'mousemove', mouseMove, false);
	Event.observe(document, 'mouseup',   mouseUp,   false);
	
	// 対象書籍該当ありの場合
	var comment_panel = $('comment_panel');
	if (comment_panel) {
		Event.observe(comment_panel, 'mousedown', showMenu, false);
		// Event.observe(comment_panel, 'mouseup',   hideMenu, false);
        var link_this_page=$('link_this_page');
		link_this_page.onclick = function(event){this.select();};
	}
	
	// メニューウィンドウ
	var menu_panel = $('menu_panel');
	if (menu_panel) {
		var amazon_link  = $('amazon_link');
		var comment_link = $('comment_link');
		// Event.observe(menu_panel,   'mouseup',   hideMenu,        false);
		// Event.observe(amazon_link,  'mouseover', setLinkTarget,   false);
		// Event.observe(amazon_link,  'mouseout',  clearLinkTarget, false);
		// Event.observe(comment_link, 'mouseover', setLinkTarget,   false);
		// Event.observe(comment_link, 'mouseout',  clearLinkTarget, false);
		Event.observe(comment_link, 'click', showEditComment, false);
		//comment_link.onclick = showEditComment;
		comment_link.href = "javascript:{return false;}";
	}
	
	// 登録ウィンドウ	
	var comment_entry_panel = $('comment_entry_panel');
	if (comment_entry_panel) {
		$('comment_entry_close').href = "javascript:hideEditComment();";
		Event.observe($('addcomment'),       'click', doSubmit, false);
	}
	
	// 表示ボタン
	Event.observe($('bookinfo'),'click', doSubmit, false);
	// ASINテキストボックス
	var asin = $('asin');
	asin.onclick = function(event){this.select();};
	// Event.observe(asin,'keydown', keydownSubmit, false);
}
//
function keydownSubmit(event) {
	if (Event.KEY_RETURN == event.keyCode) {
		doSubmit(event);
	}
	return false;
}

//マウス位置オブジェクト
function MousePoint(x,y) {
	this.x = x;
	this.y = y;
}

// マウス位置のオフセットを取得
function getMouseOffset(target, event) {
	var mousePos = new MousePoint(
	 			         Event.pointerX(event),
	 			         Event.pointerY(event) 
	                   );
	var x = mousePos.x - target.offsetLeft;
	var y = mousePos.y - target.offsetTop;
	return new MousePoint(x,y);
}

// ドラッグの終了
function mouseUp(event) {
	dragObject = null;
	isShowMenu = true;
}

// ドラッグ処理
function mouseMove(event) {
	if (!dragObject) return;
	var mousePos = new MousePoint(
	 			         Event.pointerX(event),
	 			         Event.pointerY(event) 
	                   );
	if (dragObject) {
		dragObject.style.position = 'absolute';
		dragObject.style.top      = mousePos.y - mouseOffset.y + "px";
		dragObject.style.left     = mousePos.x - mouseOffset.x + "px";
		return false;
	}
}

// ドラッグ対象オブジェクトの設定
function makeDraggable(item) {
	if (item) {
		item = $(item);
		
		Event.observe(item, 'mousedown', startDrag, false);
	}
}

// ドラッグの開始
function startDrag(event) {
	dragObject  = this;
	mouseOffset = getMouseOffset(this, event);
	isShowMenu  = false;
	return false;
};

// メニューを表示
function showMenu(event) {
	if (isShowMenu) {
		var menu_panel = $('menu_panel');
		var mousePos = new MousePoint(
		 			         Event.pointerX(event),
		 			         Event.pointerY(event) 
		                   );
		menu_panel.style.top  = mousePos.y + "px";
		menu_panel.style.left = mousePos.x + "px";
		
		var isMenuVisible = (menu_panel.style.visibility == 'visible');
		menu_panel.style.visibility = (isMenuVisible)?'hidden':'visible';
	}
}
// メニューを隠す
function hideMenu(event) {
	$('menu_panel').style.visibility = 'hidden';
	
	//var link = $(linkTarget);
	//if (link != null) {
	//	if (linkTarget == 'comment_link') {
	//		showEditComment(event);
	//	} else {
	//		window.open(link.href, "_blank");
	//	}
	//} 
}
// コメント入力ウィンドウを表示
function showEditComment(event) {
    hideMenu(event);
	var comment_entry_panel = $('comment_entry_panel');
	var mousePos = new MousePoint(
	 			         Event.pointerX(event),
	 			         Event.pointerY(event) 
	                   );
	comment_entry_panel.style.top  = mousePos.y + "px";
	comment_entry_panel.style.left = mousePos.x + "px";
	comment_entry_panel.style.visibility = 'visible';
	
	return false;
}
// コメント入力ウィンドウを隠す
function hideEditComment(event) {
	$('comment_entry_panel').style.visibility = 'hidden';
}

// メニューの選択されたリンクをセットする
function setLinkTarget(event) {
	linkTarget = Event.element(event).id;
}
// メニューの選択されたリンクをクリアする
function clearLinkTarget(event) {
	linkTarget = null;
}

// コメントパネルビューの適用
function applyView() {
	
	// コメントパネル
	var comment_panel = $('comment_panel');
	$('comment_entry_close').className = 'entry_close';
	
	var comments = comment_panel.getElementsByTagName('div');

	var linCnt = 0;
	for(var i=0; i<comments.length; i++) {
		comments[i].className = 'comment';
		
		linCnt = comments[i].innerHTML.split("<wbr>").length;
		
		comments[i].style.height = ((linCnt * 20) + 20) + 'px';
		//comments[i].style.left = (left += 50) + 'px';
		
		makeDraggable(comments[i])
		Element.setStyle(comments[i],{opacity:0.8});
		
		//alert(comments[i].innerHTML);
		//comments[i].innerHTML = "<a href='http://typea.info/'>http://typea.info/<wbr>test/test/test/test</a>１２３４５６７８９＊１２３４５６<wbr>７８９＊１２３４<wbr>５６７８９＊１２３４５６７８９＊１２３４５６７８９＊１２３４５６７８９＊１２３４５６７８９＊１２３４５６７８９＊１２３４５６７８９＊１２３４５６７８９＊"
	}
}
// リクエストの送信処理
function doSubmit(event) {
	var trxId = null;
	if (event.target.id == 'bookinfo'
     || event.target.id == 'asin') {
	   trxId = 'info';
	} else if 
	   (event.target.id == 'addcomment') {
	   
	   // var mousePos = getMouseOffset($('comment_edit_box'), event);
	   $('pos_x').value = parseInt($('comment_entry_panel').style.left);
	   $('pos_y').value = parseInt($('comment_entry_panel').style.top);
	   trxId = 'comment';
	}
	if (trxId != null) {
		var form = $("ab01_01form");
		form.trxId.value = trxId;
		waitMessage(true);
		form.submit();
	}
}

function waitMessage( isWaiting ) {
	var wait_msg_panel = $('wait_msg_panel');
	if (wait_msg_panel) {
		if (isWaiting) {
			wait_msg_panel.style.visibility = 'visible';
		} else {
			wait_msg_panel.style.visibility = 'hidden';
		}
	}		
}

