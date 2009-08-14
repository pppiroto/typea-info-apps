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
	},
    markup_fuzzy_url :function (text) {
        var ret = text;
        var ptn = /(http:\/\/.*?)[ $]/g; // 行末にマッチしない？？？
        var ary = ptn.exec(text);
        while(ary) {
            ret = ret.replace(ary[0], "<a href='" + RegExp.$1 + "' target='_blank'>" + ary[0] + "</a>");
            ary = ptn.exec(text);
        }
        return ret;
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
	search_url : function(query_string, is_query_encoded) {
		return "http://search.twitter.com/search.format"
				.replace("format", this.format) 
				+ ((is_query_encoded)?query_string:this.urlencode(query_string));
	},
	tw_search_gadget : function(jsondata, callback) {
		var html = "";
        var next_page = jsondata['next_page'];
        var query     = jsondata['query'];
        var html_next = "";
        
        
        html_next = "<div id='search_query'>" + query + " の検索結果</div>";
        if (next_page) {
            html_next = "<a href='javascript:" + callback.name + "(\"" + this.search_url(next_page, true) + "\");'>&gt;&gt;&nbsp;next page</a>"
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
            html +=     this.markup_links(result['text'], callback) + "</br>";
            html +=   "</td>";
            html += "</tr>";
        }
        html += "</table>";
        html += html_next;
        return html;
	},
	markup_links : function (text, callback) {
		text = this.markup_fuzzy_url(text + ' ');
		text = this.markup_tw_user(text);
		text = this.markup_tag(text, callback);
		return text;
    },
    markup_tw_user : function (text) {
        var ret = text;
        var ptn = /@([A-Za-z0-9_-]{1,})/g;
        var ary = ptn.exec(text);
        while(ary) {
            ret = ret.replace(ary[0], "<a href='http://twitter.com/" + RegExp.$1 + "' target='_blank'>" + ary[0] + "</a>");
            ary = ptn.exec(text);
        }
        return ret;
    },
    markup_tag : function (text, callback) {
        var ret = text;
        var ptn = /#(.*?)[ ,\.$]/g;
        var ary = ptn.exec(text);
        while(ary) {
        	var url = "<a href='javascript:" + callback.name + "(\"" + this.search_url("q=" + RegExp.$1) + "\");'>" + ary[0] + "</a>";
            ret = ret.replace(ary[0], url);
            ary = ptn.exec(text);
        }
        return ret;
    }
};





