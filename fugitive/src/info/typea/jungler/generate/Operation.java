package info.typea.jungler.generate;

//import info.typea.fugitive.logging.LogUtil;
import info.typea.jungler.type.AmazonEcsType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


//import org.apache.log4j.Logger;

public class Operation {
	//	private static final Logger log = LogUtil.getLogger(Operation.class);
	
	private static final String URL_ENCODE = "UTF-8";
	private String wsUrl   = "http://webservices.amazon.co.jp/onca/xml";
	private String service = "AWSECommerceService";
	private String version = "2006-11-14";
	
	private Map<String, String[]> paramMap;
	public Operation() {
		paramMap = new TreeMap<String, String[]>();
		
		this.setParameter("AWSAccessKeyId", "1498TGK1YPN1JATPXXG2");
		this.setParameter("AssociateTag",   "typea09-22");
	}
	public void setParameter(String key, String[] val) {
		paramMap.put(key, val);
	}
	public void setParameter(String key, String val) {
		setParameter(key, new String[]{val});
	}
	public void setParameter(String key, AmazonEcsType[] val) {
		String[] newVal = new String[val.length];
		for(int i=0; i<val.length; i++) {
			newVal[i] = val[i].toString();
		}
		paramMap.put(key, newVal);
	}
	public void setParameter(String key, AmazonEcsType val) {
		setParameter(key, new AmazonEcsType[]{val});
	}
	public String[] getParameter(String key) {
		return paramMap.get(key);
	}
	public void setResponseGroup(Object responseGroup) {
		setParameter("ResponseGroup", responseGroup.toString());
	}
	public String[] getResponseGroup() {
		return getParameter("ResponseGroup");
	}
	public String toRestRequest(boolean isValidateCheck) {
		if (isValidateCheck) {
			checkAllRequiredFields();
		}
		
		StringBuffer result = new StringBuffer(this.wsUrl);
		result.append("?Service=" + this.service);
		result.append("&Version=" + this.version);
		
		Set<Map.Entry<String, String[]>> mapSet = paramMap.entrySet();
		for (Map.Entry<String, String[]> param : mapSet) {
			String   key  = param.getKey();
			String[] vals = param.getValue();
			
			for (String val : vals) {
				result.append("&").append(key)
					.append("=").append(encodeUrlString(val));
			}
		}
		
		return result.toString();
	}
	
	public String toRestRequest() {
		return toRestRequest(false);
	}
	
	public String[] getRequiredFields() {
		return null;
	}

	public void checkAllRequiredFields() {
		String[] fields = getRequiredFields();
		if (fields != null) {
			for(String field : fields) {
				if (!this.paramMap.containsKey(field)) {
					throw new IllegalStateException(field + " is required parameter but is null.");
				}
			}
		}
	}
	
	public boolean hasAllRequiredFields() {
		boolean result = true;
		try {
			checkAllRequiredFields();
		} catch (IllegalStateException e) {
			result = false;
		}
		return result;
	}
	
	protected String encodeUrlString(String value) {
		try {
			return URLEncoder.encode(value, URL_ENCODE);
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}
	
	public String toString() {
		return toRestRequest();
	}
}
