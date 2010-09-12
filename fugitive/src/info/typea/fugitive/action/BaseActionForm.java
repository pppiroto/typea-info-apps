package info.typea.fugitive.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * �Ɩ��A�v���P�[�V�������ActionForm�N���X
 * <br/>
 * {@link BaseAction}�N���X(�̌p���N���X)�ƕ����Ďg�p����B
 * ���̃N���X�ɂ́AHTML��FORM�^�O�ɂ���ăT�[�o�[�ɑ����郊�N�G�X�g�p�����[�^���A��͂���i�[�����B
 * ���N�G�X�g�p�����[�^�̂����A"trxId"�Ƃ������O�������̂́A���ʂȈӖ��������AtrxId�ɐݒ肳�ꂽ�l�Ɠ�����
 * �Ɩ�Action�N���X�̃��\�b�h��Submit���ɋN������B
 * <br/>
 * �Ⴆ�΁A&lt;input type="hidden" name="trxId" value="search" /&gt; �Ƃ����p�����[�^�𔺂��āA
 * HTML�@FORM ��Submit���ꂽ�ꍇ�ABaseAction.search() ���\�b�h���Ăяo�����B
 * <br/>
 * �������A"trxId"�Ƃ������O�́A�ʂ�struts-config.xml �ɂĎw�肷��K�v������B
 * 
 * @see info.typea.fugitive.action.BaseAction
 * @author totec yagi
 */
public class BaseActionForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	/**
	 * ����ID
	 */
	private String trxId;
	
	/**
     * ����ID���擾����B
	 * @return�@����ID
	 */
	public String getTrxId() {
		return trxId;
	}
	/**
     * ����ID��ݒ肷��B
	 * @param trxId�@����ID
	 */
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setTrxId("");
	}
	
}
