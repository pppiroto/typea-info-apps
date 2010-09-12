package info.typea.fugitive.action;

import org.apache.struts.util.LabelValueBean;

/**
 * セレクトボックスを動的に作成するときの、内容を保持する
 * @author totec yagi
 */
public class SelectOptionBean extends LabelValueBean {
	private static final long serialVersionUID = 1L;
	public SelectOptionBean(){
		super();
	}

	public SelectOptionBean(String label, String value) {
		this.setLabel(label);
		this.setValue(value);
	}
	
}
