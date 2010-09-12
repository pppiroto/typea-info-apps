package libra.al01;

import info.typea.fugitive.action.BaseActionForm;

@SuppressWarnings("serial")
public class AL01_01Form extends BaseActionForm {
	private String src;
	
	/**
	 * Base Direcotry Id
	 */
	private String bid;
	
	/**
	 * Directory Name
	 */
	private String dn;
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String pid) {
		this.bid = pid;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}
	
}
