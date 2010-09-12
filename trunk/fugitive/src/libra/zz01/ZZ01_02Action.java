package libra.zz01;

import info.typea.fugitive.action.BaseAction;
import info.typea.fugitive.logic.Message;
import info.typea.fugitive.logic.Messages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import libra.zz01.model.Employee;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


/**
 * Employee �f�[�^�o�^�E�ύX���s��(�T���v���v���O����)
 * @author totec yagi
 */
public class ZZ01_02Action extends BaseAction {
    @Override
    public String getActionId() {
        return "ZZ01_02";
    }
    
    /**
     * ActionForm�����̃A�N�V�����Ŏg�p����Form�ɃL���X�g����
     * 
     * @param actionForm
     * @return ���̃A�N�V�����Ŏg�p���� Form
     */
	private ZZ01_02Form getForm(ActionForm actionForm) {
		ZZ01_02Form form = (ZZ01_02Form)actionForm;
		if (form == null) {
			form = new ZZ01_02Form();
		}
		return form;
	}
    
    /* (non-Javadoc)
     * @see fugitive.fw.action.BaseAction#unspecified(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward unspecified(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
        info("unspecified request.");
        
        ZZ01_02Form form = getForm(actionForm);
        saveToken(req); // ��d�����h�~
        
        ZZ01Logic logic = new ZZ01Logic();

        form.setBean(new Employee());
        form.setDepartments(logic.findDepartments());
        
        return mapping.findForward(SUCCESS);
    }
	/**
	 * Employee �f�[�^�̕ҏW�̂��߂̉�ʕ\�����s��
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ZZ01_02Form form = getForm(actionForm);
		saveToken(req); // ��d�����h�~
		
		// �Ɩ����W�b�N�N���X�𐶐����AEmployee�f�[�^�����������Ϗ�
        ZZ01Logic logic = new ZZ01Logic();
		Employee emp = logic.findEmployee(form.getEmployee_id());

		if (emp == null) {
			emp = new Employee();
		}
		
        form.setBean(emp);
		form.setDepartments(logic.findDepartments());
		
		return mapping.findForward(SUCCESS);
	}
	/**
	 * Employee �f�[�^�̍X�V����
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
        return insertOrUpdate(ZZ01Logic.UPDATE_TYPE.UPDATE, mapping, actionForm, req, res);
	}
	/**
	 * Employee �f�[�^�̐V�K�o�^����
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return insertOrUpdate(ZZ01Logic.UPDATE_TYPE.INSERT, mapping, actionForm, req, res);
	}	
	
	/**
	 * Employee �f�[�^�̍X�V�E�V�K�o�^����
	 * 
	 * @param type �X�V���V�K�o�^�����w�肷��B
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward insertOrUpdate(ZZ01Logic.UPDATE_TYPE type, ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ZZ01_02Form form = getForm(actionForm);
		// ��d�����h�~
        Messages err = new Messages();
		if (isTokenValid(req)) {
			
			// �Ɩ����W�b�N�N���X�̐���
			ZZ01Logic logic = new ZZ01Logic();
			
			// �X�V���e�̎擾
            Employee emp = (Employee)form.getBean();
            
            // ��L�[�̑ޔ�
            String employee_id = emp.getEmployee_id();
            
            // ���̓`�F�b�N
            err = logic.validateEmployee(err, emp);
            if (err.isEmpty()) {
            	// �G���[���Ȃ��ꍇ�̏���
                // �V�K�o�^�A�X�V�����s
            	logic.insertOrUpdateEmployee(type, emp);
                
                // �Č���
                emp = logic.findEmployee(employee_id.toString());
            }
            form.setBean(emp);
            form.setDepartments(logic.findDepartments());
            
		} else {
            err.add(new Message("errors.token"));
		}
		// �G���[����
		handleErrors(req, err);
        saveToken(req);

        return mapping.findForward(SUCCESS);
	}	
	/**
	 * Employee �f�[�^�̍폜����
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ZZ01_02Form form = getForm(actionForm);
		// ��d�����h�~
		if (isTokenValid(req)) {
			Employee emp = (Employee)form.getBean();
        
			// �Ɩ����W�b�N�N���X�̐����A�폜�����̈Ϗ�
            ZZ01Logic logic = new ZZ01Logic();
            logic.deleteEmployee(emp.getEmployee_id().toString());
            
            form.setBean(logic.createEmployee());
            form.setDepartments(logic.findDepartments());
            
        } else {
        	error("INVALID TOKEN!!");
		}
        saveToken(req);
		return mapping.findForward(SUCCESS);
	}	
    
    /**
     * Employee �f�[�^�̈ꊇ�o�^
     * 
     * @param mapping
     * @param actionForm
     * @param req
     * @param res
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward upload(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ZZ01_02Form form = getForm(actionForm);
        if (isTokenValid(req)) {
        	// �Y�t�t�@�C���̎擾
            FormFile file = form.getUpfile();
            
            // �Ɩ����W�b�N�N���X�𐶐����ACSV�t�@�C���捞�������Ϗ�
            ZZ01Logic logic = new ZZ01Logic();
            logic.importEmployeesFromCsvData(file.getInputStream());
            
        } else {
        	error("INVALID TOKEN!!");
        }
        saveToken(req);
        return mapping.findForward(SUCCESS);
    }
}
