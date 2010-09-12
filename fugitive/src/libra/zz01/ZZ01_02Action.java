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
 * Employee データ登録・変更を行う(サンプルプログラム)
 * @author totec yagi
 */
public class ZZ01_02Action extends BaseAction {
    @Override
    public String getActionId() {
        return "ZZ01_02";
    }
    
    /**
     * ActionFormをこのアクションで使用するFormにキャストする
     * 
     * @param actionForm
     * @return このアクションで使用する Form
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
        saveToken(req); // 二重押し防止
        
        ZZ01Logic logic = new ZZ01Logic();

        form.setBean(new Employee());
        form.setDepartments(logic.findDepartments());
        
        return mapping.findForward(SUCCESS);
    }
	/**
	 * Employee データの編集のための画面表示を行う
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
		saveToken(req); // 二重押し防止
		
		// 業務ロジッククラスを生成し、Employeeデータ検索処理を委譲
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
	 * Employee データの更新処理
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
	 * Employee データの新規登録処理
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
	 * Employee データの更新・新規登録処理
	 * 
	 * @param type 更新か新規登録かを指定する。
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward insertOrUpdate(ZZ01Logic.UPDATE_TYPE type, ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ZZ01_02Form form = getForm(actionForm);
		// 二重押し防止
        Messages err = new Messages();
		if (isTokenValid(req)) {
			
			// 業務ロジッククラスの生成
			ZZ01Logic logic = new ZZ01Logic();
			
			// 更新内容の取得
            Employee emp = (Employee)form.getBean();
            
            // 主キーの退避
            String employee_id = emp.getEmployee_id();
            
            // 入力チェック
            err = logic.validateEmployee(err, emp);
            if (err.isEmpty()) {
            	// エラーがない場合の処理
                // 新規登録、更新を実行
            	logic.insertOrUpdateEmployee(type, emp);
                
                // 再検索
                emp = logic.findEmployee(employee_id.toString());
            }
            form.setBean(emp);
            form.setDepartments(logic.findDepartments());
            
		} else {
            err.add(new Message("errors.token"));
		}
		// エラー処理
		handleErrors(req, err);
        saveToken(req);

        return mapping.findForward(SUCCESS);
	}	
	/**
	 * Employee データの削除処理
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
		// 二重押し防止
		if (isTokenValid(req)) {
			Employee emp = (Employee)form.getBean();
        
			// 業務ロジッククラスの生成、削除処理の委譲
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
     * Employee データの一括登録
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
        	// 添付ファイルの取得
            FormFile file = form.getUpfile();
            
            // 業務ロジッククラスを生成し、CSVファイル取込処理を委譲
            ZZ01Logic logic = new ZZ01Logic();
            logic.importEmployeesFromCsvData(file.getInputStream());
            
        } else {
        	error("INVALID TOKEN!!");
        }
        saveToken(req);
        return mapping.findForward(SUCCESS);
    }
}
