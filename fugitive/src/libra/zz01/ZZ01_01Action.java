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
 * Employee データ一覧を表示する(サンプルプログラム)
 * 
 * @author totec yagi
 */
public class ZZ01_01Action extends BaseAction {
    @Override
    public String getActionId() {
        return "ZZ01_01";
    }
	
    /**
     * ActionFormをこのアクションで使用するFormにキャストする
     * @param actionForm
     * @return このアクションで使用する Form
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
	 * Employeeの検索を行う。
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {

		// Actionフォームをダウンキャスト
		ZZ01_01Form form = getForm(actionForm);
		
		// 業務ロジッククラスを生成し、Employee 一覧検索処理を委譲する
        ZZ01Logic logic = new ZZ01Logic();
        List list = logic.findEmployees(
                form.getFirst_name(),
                form.getLast_name(),
                form.getEmail(),
                form.getSalary_from(),
                form.getSalary_to()
        );
        
        // 検索結果をセッションに格納
        HttpSession session = req.getSession(true);
        session.setAttribute("employees", list);
   
        return mapping.findForward(SUCCESS);
	}
	
	/**
	 * Employeeの検索結果をCSVで取得する。
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward download(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// セッションからEmployee一覧の検索結果を取り出す
		HttpSession session = req.getSession();
		List list = (List)session.getAttribute("employees");
		
		// CSVダウンロード用共通メソッドを呼び出す
		downloadCsvFile("employees","csv", list, req, res);
		
		// CSVダウンロード用共通メソッドを呼ぶ場合、null を返す
		return null;
	}
}
