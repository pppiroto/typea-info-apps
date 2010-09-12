package libra.pm01;

import info.typea.fugitive.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import libra.pm01.model.Cocomo;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PM01_01Action extends BaseAction {

	@Override
	public String getActionId() {
		return "PM01_01";
	}
    /**
     * ActionFormをこのアクションで使用するFormにキャストする
     * @param actionForm
     * @return このアクションで使用する Form
     */
    private PM01_01Form getForm(ActionForm actionForm) {
    	PM01_01Form form = (PM01_01Form)actionForm;
		if (form == null) {
			form = new PM01_01Form();
		}
		return form;
	}

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		return init(mapping, actionForm, req, res);
	}
	
	/**
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		return mapping.findForward(SUCCESS);
	}
	/**
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward calc(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		PM01_01Form form = getForm(actionForm);
		
		HttpSession session = req.getSession(true);
		
		boolean isKdsiBase = !"effort".equalsIgnoreCase(form.getCalc_mode());
		
		Cocomo cocomo = new Cocomo();
		
		double kdsi   = 0.0d;
		double effort = 0.0d;

		try {
			if (isKdsiBase) {
				kdsi   = Double.parseDouble(form.getKdsi());
				cocomo.setKdsi(kdsi);
			} else {
				effort = Double.parseDouble(form.getEffort());
				cocomo.setEffort(effort);
			}
		} catch (NumberFormatException nfe) {
			
		}
		cocomo.calc(isKdsiBase);
		cocomo.raund();
		
		form.setKdsi(String.valueOf(cocomo.getKdsi()));
		form.setEffort(String.valueOf(cocomo.getEffort()));
		
		session.setAttribute("cocomo", cocomo);
		
		
		return mapping.findForward(SUCCESS);
	}
}
