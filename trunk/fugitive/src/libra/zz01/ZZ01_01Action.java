package libra.zz01;

import info.typea.fugitive.action.BaseAction;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Employee �f�[�^�ꗗ��\������(�T���v���v���O����)
 * 
 * @author totec yagi
 */
public class ZZ01_01Action extends BaseAction {
    @Override
    public String getActionId() {
        return "ZZ01_01";
    }
	
    /**
     * ActionForm�����̃A�N�V�����Ŏg�p����Form�ɃL���X�g����
     * @param actionForm
     * @return ���̃A�N�V�����Ŏg�p���� Form
     */
    private ZZ01_01Form getForm(ActionForm actionForm) {
		ZZ01_01Form form = (ZZ01_01Form)actionForm;
		if (form == null) {
			form = new ZZ01_01Form();
		}
		return form;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
        info("unspecified request.");
        
        HttpSession session = req.getSession(true);
        session.setAttribute("employees", Collections.EMPTY_LIST);
		return mapping.findForward(SUCCESS);
	}	
	
	/**
	 * Employee�̌������s���B
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {

		// Action�t�H�[�����_�E���L���X�g
		ZZ01_01Form form = getForm(actionForm);
		
		// �Ɩ����W�b�N�N���X�𐶐����AEmployee �ꗗ�����������Ϗ�����
        ZZ01Logic logic = new ZZ01Logic();
        List list = logic.findEmployees(
                form.getFirst_name(),
                form.getLast_name(),
                form.getEmail(),
                form.getSalary_from(),
                form.getSalary_to()
        );
        
        // �������ʂ��Z�b�V�����Ɋi�[
        HttpSession session = req.getSession(true);
        session.setAttribute("employees", list);
   
        return mapping.findForward(SUCCESS);
	}
	
	/**
	 * Employee�̌������ʂ�CSV�Ŏ擾����B
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward download(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// �Z�b�V��������Employee�ꗗ�̌������ʂ����o��
		HttpSession session = req.getSession();
		List list = (List)session.getAttribute("employees");
		
		// CSV�_�E�����[�h�p���ʃ��\�b�h���Ăяo��
		downloadCsvFile("employees","csv", list, req, res);
		
		// CSV�_�E�����[�h�p���ʃ��\�b�h���Ăԏꍇ�Anull ��Ԃ�
		return null;
	}
}
