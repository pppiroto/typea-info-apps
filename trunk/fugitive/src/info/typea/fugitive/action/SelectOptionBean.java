package info.typea.fugitive.action;

import org.apache.struts.util.LabelValueBean;

/**
 * �Z���N�g�{�b�N�X�𓮓I�ɍ쐬����Ƃ��́A���e��ێ�����
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
