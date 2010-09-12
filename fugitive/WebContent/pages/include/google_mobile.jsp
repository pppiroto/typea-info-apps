<%@ page import="java.io.BufferedReader,
                 java.io.InputStreamReader,
                 java.io.IOException,
                 java.io.UnsupportedEncodingException,
                 java.net.URL,
                 java.net.URLEncoder" %>
<%!

private static final String PAGEAD =
    "http://pagead2.googlesyndication.com/pagead/ads?";

private void googleAppendUrl(StringBuilder url, String param, String value)
    throws UnsupportedEncodingException {
  if (value != null) {
    String encodedValue = URLEncoder.encode(value, "UTF-8");
    url.append("&").append(param).append("=").append(encodedValue);
  }
}

private void googleAppendColor(StringBuilder url, String param,
    String value, long random) {
  String[] colorArray = value.split(",");
  url.append("&").append(param).append("=").append(
      colorArray[(int)(random % colorArray.length)]);
}

private void googleAppendScreenRes(StringBuilder url, String uaPixels,
    String xUpDevcapScreenpixels) {
  String screenRes = uaPixels;
  String delimiter = "x";
  if (uaPixels == null) {
    screenRes = xUpDevcapScreenpixels;
    delimiter = ",";
  }
  if (screenRes != null) {
    String[] resArray = screenRes.split(delimiter);
    if (resArray.length == 2) {
      url.append("&u_w=").append(resArray[0]);
      url.append("&u_h=").append(resArray[1]);
    }
  }
}

%>
<%

long googleDt = System.currentTimeMillis();
String googleHost = (request.isSecure() ? "https://" : "http://")
    + request.getHeader("Host");

StringBuilder googleAdUrlStr = new StringBuilder(PAGEAD);
googleAdUrlStr.append("ad_type=text");
googleAdUrlStr.append("&channel=");
googleAdUrlStr.append("&client=ca-mb-pub-4280062068315072");
googleAppendColor(googleAdUrlStr, "color_border", "FFFFCC", googleDt);
googleAppendColor(googleAdUrlStr, "color_bg", "FFFFCC", googleDt);
googleAppendColor(googleAdUrlStr, "color_link", "0000CC", googleDt);
googleAppendColor(googleAdUrlStr, "color_text", "000000", googleDt);
googleAppendColor(googleAdUrlStr, "color_url", "0066CC", googleDt);
googleAdUrlStr.append("&dt=").append(googleDt);
googleAdUrlStr.append("&format=mobile_double");
googleAppendUrl(googleAdUrlStr, "host", googleHost);
googleAppendUrl(googleAdUrlStr, "ip", request.getRemoteAddr());
googleAdUrlStr.append("&markup=chtml");
googleAdUrlStr.append("&oe=utf8");
googleAdUrlStr.append("&output=chtml");
googleAppendUrl(googleAdUrlStr, "ref", request.getHeader("Referer"));
String googleUrl = request.getRequestURL().toString();
if (request.getQueryString() != null) {
  googleUrl += "?" + request.getQueryString().toString();
}
googleAppendUrl(googleAdUrlStr, "url", googleUrl);
googleAppendUrl(googleAdUrlStr, "useragent", request.getHeader("User-Agent"));
googleAppendScreenRes(googleAdUrlStr, request.getHeader("UA-pixels"),
    request.getHeader("x-up-devcap-screenpixels"));

try {
  URL googleAdUrl = new URL(googleAdUrlStr.toString());
  BufferedReader reader = new BufferedReader(
      new InputStreamReader(googleAdUrl.openStream(), "UTF-8"));
  for (String line; (line = reader.readLine()) != null;) {
    out.println(line);
  }
} catch (IOException e) {}

%>