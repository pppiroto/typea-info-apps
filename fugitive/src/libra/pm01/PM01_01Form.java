package libra.pm01;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import info.typea.fugitive.action.BaseActionForm;

public class PM01_01Form extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private String kdsi;
	private String effort;
	private String calc_mode;
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setCalc_mode("kdsi");
	}
	public String getEffort() {
		return effort;
	}
	public void setEffort(String effort) {
		this.effort = effort;
	}
	public String getKdsi() {
		return kdsi;
	}
	public void setKdsi(String kdsi) {
		this.kdsi = kdsi;
	}
	public String getCalc_mode() {
		return calc_mode;
	}
	public void setCalc_mode(String calc_mode) {
		this.calc_mode = calc_mode;
	}

}
