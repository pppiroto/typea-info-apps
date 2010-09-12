package libra.ab01;

import info.typea.fugitive.action.BaseActionForm;
import info.typea.fugitive.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

public class AB01_01Form extends BaseActionForm {
	private static final long serialVersionUID = 1L;

	private String asin;
	private String selected_asin;
	private String comment;
	private String pos_x;
	private String pos_y;
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setAsin("");
		setSelected_asin("");
		setComment("");
	}

	public String getAsin() {
		return StringUtil.nvl(asin).trim();
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getSelected_asin() {
		return selected_asin;
	}

	public void setSelected_asin(String selected_asin) {
		this.selected_asin = selected_asin;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPos_x() {
		return pos_x;
	}

	public void setPos_x(String pos_x) {
		this.pos_x = pos_x;
	}

	public String getPos_y() {
		return pos_y;
	}

	public void setPos_y(String pos_y) {
		this.pos_y = pos_y;
	}
}
