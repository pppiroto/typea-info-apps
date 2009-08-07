/**
 * This library depends on jQuery 1.3.2
 */

/**
 * Base class of my utility. 
 */
var TypeA = function() {
};
TypeA.prototype = {
	version : "0.0.1",
	urlencode : function(query) {
		if (query == null) return "";
		if (typeof(query) == "string") {
			var queries = query.split("&");
			query = [];
			for (var i=0; i<queries.length; i++) {
				var k_v = queries[i].split("=");
				query[k_v[0]] = k_v[1]; 
			}
		}
		var query_string = "";
		var sep = "?";
		for (k in query) {
			query_string += (sep + k + "=" + encodeURIComponent(query[k]));
			sep = "&";
		}
		return query_string;
	}
};

var TwitterUtil = function(format) {
	return TwitterUtil.prototype.init();
};
TwitterUtil.prototype = {
	format : "json",
	init : function(format) {
		if (format != null) this.format = format;
		return jQuery.extend(new TypeA(), this);
	},
	search_url : function(query_string) {
		return "http://search.twitter.com/search.format"
				.replace("format", this.format) 
				+ this.urlencode(query_string);
	},
	json_to_html : function(jsondata) {
        var html = "";
        var next_page = jsondata['next_page'];
        var html_next = "";
        if (next_page) {
            html_next = "<a href='javascript:search_twitter(\"" + next_page + "\");'>&gt;&gt;&nbsp;next page</a>"
                      + "<br/>";
            html += html_next
        }
        var results = jsondata['results'];
        html +="<table border='0' id='twitter'>";
        for (var i=0; i<results.length; i++) {
            var result = results[i];
            html += "<tr style='font-size:small;'>";
            html +=   "<td>";
            html +=     "<a href='http://twitter.com/" + result['from_user'] + "' target='_blank'>";
            html +=         "<img src='" + result['profile_image_url'] + "' border='none'/>";
            html +=     "</a>";
            html +=   "</td>";
            html +=   "<td>";
            html +=     "<a href='http://twitter.com/" + result['from_user'] + "' target='_blank'>";
            html +=       "<span style='color:#2FC2EF;font-weight:bold;'>" + result['from_user'] + ":</span>"
            html +=     "</a>";
            html +=     this.createLink(result['text']) + "</br>";
            html +=   "</td>";
            html += "</tr>";
        }
        html += "</table>";
        html += html_next;
        return html;
	},
	createLink : function (text) {
        return this.toUserUrlText(this.toFuzzyUrlText(text + ' '));
    },
    toFuzzyUrlText :function (text) {
        var ret = text;
        var ptn = /(http:\/\/.*?)[ $]/g; // 行末にマッチしない？？？
        var ary = ptn.exec(text);
        while(ary) {
            ret = ret.replace(ary[0], "<a href='" + RegExp.$1 + "' target='_blank'>" + ary[0] + "</a>");
            ary = ptn.exec(text);
        }
        return ret;
    },
    toUserUrlText : function (text) {
        var ret = text;
        var ptn = /@([A-Za-z]{1,}?):/g;
        var ary = ptn.exec(text);
        while(ary) {
            ret = ret.replace(ary[0], "<a href='http://twitter.com/" + RegExp.$1 + "' target='_blank'>" + ary[0] + "</a>");
            ary = ptn.exec(text);
        }
        return ret;
    }
};





