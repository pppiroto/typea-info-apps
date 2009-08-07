/**
 * Twitter API Documentation : http://apiwiki.twitter.com/Twitter-API-Documentation 
 */

var TwitterUtil = function(format){
	TwitterUtil.fn.init(format);
};
TwitterUtil.fn = TwitterUtil.prototype = {
		version:"0.0.1",
		domain:"twitter.com",
		default_format:"json",
		init:function( format ) {
			if (format == null) {
				format = this.default_format;
			}
			this.format = format;
		},
		search_url:function() {
			return "http://search.twitter.com/search.format".replace("format", this.format);
		}
};

