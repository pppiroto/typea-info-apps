/**
 * Twitter API Documentation : http://apiwiki.twitter.com/Twitter-API-Documentation 
 */

var TwitterUtil = function(format){
	TwitterUtil.fn.init(format);
};
TwitterUtil.fn = TwitterUtil.prototype = {
		version:"0.0.1",
		default_format:"json",
		init:function( format ) {
			if (format == null) {
				format = this.default_format;
			}
			this.format = format;
		},
		search_url:function(query_string) {
			if (query_string == undefined) {
				query_string = "";
			} else {
				query_string = "?" + query_string;
			}
			return "http://search.twitter.com/search.format"
					.replace("format", this.format) + query_string;
		}
};

