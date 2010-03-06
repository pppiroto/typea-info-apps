var PROCESSING_MSG = "処理中・・・"; 


function toggle_visble(area) {
	if (area.css("display") == 'none') {
		area.show("normal");
	} else {
		area.hide("normal");
	}
}



function initial_message_area() {
	$("#loading").corner()
	$("#message").corner()
}
function processing_msg(msg){
	var msg_area = $("#loading");
	if (msg == null || jQuery.trim(msg) == "") {
		msg_area.hide("normal");
	} else {
		msg_area.text(msg)
		msg_area.show("normal");
	}
}
function error_msg(msg) {
	var msg_area = $("#message");
	if (msg == null || jQuery.trim(msg) == "") {
		msg_area.hide("normal");
	} else {
		msg_area.html(msg)
		msg_area.show("normal");
	}
}
