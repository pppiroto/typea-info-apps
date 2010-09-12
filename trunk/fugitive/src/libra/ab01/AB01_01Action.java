package libra.ab01;

import info.typea.fugitive.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import libra.ab01.AB01Logic.UPDATE_TYPE;
import libra.ab01.model.Book;
import libra.ab01.model.Comment;
import libra.ab01.model.NullBook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AB01_01Action extends BaseAction {
	public final int RECENTLY_CNT = 256;
	
	@Override
	public String getActionId() {
		return "AB01_01";
	}
	
	private void setRecentlyAccessBooks(HttpServletRequest req, AB01Logic logic){
		req.setAttribute("access_books", logic.findRecentlyAccessBooks(RECENTLY_CNT));
		return;
	}
    /**
     * ActionFormをこのアクションで使用するFormにキャストする
     * @param actionForm
     * @return このアクションで使用する Form
     */
    private AB01_01Form getForm(ActionForm actionForm) {
    	AB01_01Form form = (AB01_01Form)actionForm;
		if (form == null) {
			form = new AB01_01Form();
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
		//HttpSession session = req.getSession(true);
		//session.setAttribute("book", new Book());
		req.setAttribute("book", new Book());
		setRecentlyAccessBooks(req, new AB01Logic());
		
		return mapping.findForward("success");
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward info(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		AB01_01Form form = getForm(actionForm);
		form.setComment("");
		
		// まずローカルを検索
		AB01Logic logic = new AB01Logic();
		Book book = logic.findBook(form.getAsin());
		// 存在しなければAmazonWebServiceからデータ取得
		if (book == null) {
			book = logic.createBook(form.getAsin(), "anonymous", req.getRemoteAddr());
			if (book != null) {
				logic.insertOrUpdateBook(UPDATE_TYPE.INSERT, book);
			} else {
				book = new NullBook(form.getAsin());
			}
		}
		
		//HttpSession session = req.getSession(true);
		//session.setAttribute("book", book);
		req.setAttribute("book", book);
		
		setRecentlyAccessBooks(req, logic);
		
		return mapping.findForward("success");
	}
	
	/**
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward comment(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		AB01_01Form form = getForm(actionForm);
		final int COMMENT_MAX_BLEN = 300;
		AB01Logic logic = new AB01Logic();
		Integer pos_x = null;
		Integer pos_y = null;
		
		try {
			pos_x = new Integer(form.getPos_x());
		} catch (NumberFormatException nfe) {
			pos_x = new Integer(100);
		}
		
		try {
			pos_y = new Integer(form.getPos_y());
		} catch (NumberFormatException nfe) {
			pos_y = new Integer(100);
		}
		
		String com = form.getComment(); 
		
		if (com.getBytes("Shift_JIS").length > COMMENT_MAX_BLEN) {
			StringBuilder buf = new StringBuilder(COMMENT_MAX_BLEN);
			int size = 0;
			char[] chars = com.toCharArray();
			for (char c : chars) {
				size += String.valueOf(c).getBytes("Shift_JIS").length;
				if (size > COMMENT_MAX_BLEN) {
					break;
				}
				buf.append(c);
			}
			com = buf.toString();
		}
	
		Comment comment = logic.createComment(
							form.getSelected_asin()
						  , com
						  , pos_x
						  , pos_y
						  , "anonymous"
						  , req.getRemoteAddr());	
		
		logic.insertOrUpdateComment(UPDATE_TYPE.INSERT, comment);
		Book book = logic.findBook(form.getSelected_asin());
		
		//HttpSession session = req.getSession(true);
		//session.setAttribute("book", book);
		req.setAttribute("book", book);
		
		form.setComment("");
		
		setRecentlyAccessBooks(req, logic);
		
		return mapping.findForward("success");
	}
}



