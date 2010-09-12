package libra.kb01;

import java.util.ArrayList;
import java.util.List;

import info.typea.fugitive.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import libra.kb01.model.DefaultKaimeSorter;
import libra.kb01.model.Kaime;
import libra.kb01.model.Senario;
import libra.kb01.model.UmarenEngine;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KB01_01Action extends BaseAction {

	@Override
	public String getActionId() {
		return "KB01_01";
	}
    /**
     * ActionFormをこのアクションで使用するFormにキャストする
     * @param actionForm
     * @return このアクションで使用する Form
     */
    private KB01_01Form getForm(ActionForm actionForm) {
    	KB01_01Form form = (KB01_01Form)actionForm;
		if (form == null) {
			form = new KB01_01Form();
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
		
		KB01_01Form form = getForm(actionForm);
		
		form.init();
		
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
		
		KB01_01Form form = getForm(actionForm);
		
		String[] strChoised = form.getChoise();
		List<Integer> tmpChoised = new ArrayList<Integer>();
		if (strChoised != null) {
			for (String c : strChoised) {
				if (c != null) {
					try {
						tmpChoised.add(Integer.parseInt(c));
					} catch (NumberFormatException nfe) {}
				}
			}
		}
		int[] choised = new int[tmpChoised.size()];
		for (int i=0; i<choised.length; i++) {
			choised[i] = tmpChoised.get(i);
		}
		
		String[] strOdds = form.getOdds();
		List<Double> tmpOdds = new ArrayList<Double>();
		if (strOdds != null) {
			for (String o : strOdds) {
				try {
					tmpOdds.add(Double.parseDouble(o));
				} catch (NumberFormatException nfe) {}	
			}
		}
		double[] odds = new double[tmpOdds.size()];
		for (int i=0; i<odds.length; i++) {
			odds[i] = tmpOdds.get(i);
		}
		
		int r = 0;
		try {
			r = Integer.parseInt(form.getTarget_race());
		} catch (NumberFormatException nfe) {}	

		int hit = 0;
		try {
			hit = Integer.parseInt(form.getHit_race());
		} catch (NumberFormatException nfe) {}	

		int yosan = 0;
		try {
			yosan = Integer.parseInt(form.getYosan());
		} catch (NumberFormatException nfe) {}	
		
		int rieki = 0;
		try {
			rieki = Integer.parseInt(form.getRieki());
		} catch (NumberFormatException nfe) {}	
		
		Senario senario = new Senario(r, hit, yosan, rieki, new DefaultKaimeSorter());
		
		KB01Logic logic = new KB01Logic();
		List<Kaime> list = logic.doCalc(senario, odds, choised);
		
		form.setSenarioMsg(senario.toString());
		form.setKaime(list);
		return mapping.findForward(SUCCESS);
	}
	
}
