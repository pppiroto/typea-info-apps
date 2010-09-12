package info.typea.fugitive.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 業務アプリケーション基底ActionFormクラス
 * <br/>
 * {@link BaseAction}クラス(の継承クラス)と併せて使用する。
 * このクラスには、HTMLのFORMタグによってサーバーに送られるリクエストパラメータが、解析され格納される。
 * リクエストパラメータのうち、"trxId"という名前を持つものは、特別な意味を持ち、trxIdに設定された値と同名の
 * 業務ActionクラスのメソッドをSubmit時に起動する。
 * <br/>
 * 例えば、&lt;input type="hidden" name="trxId" value="search" /&gt; というパラメータを伴って、
 * HTML　FORM がSubmitされた場合、BaseAction.search() メソッドが呼び出される。
 * <br/>
 * ただし、"trxId"という名前は、個別にstruts-config.xml にて指定する必要がある。
 * 
 * @see info.typea.fugitive.action.BaseAction
 * @author totec yagi
 */
public class BaseActionForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	/**
	 * 処理ID
	 */
	private String trxId;
	
	/**
     * 処理IDを取得する。
	 * @return　処理ID
	 */
	public String getTrxId() {
		return trxId;
	}
	/**
     * 処理IDを設定する。
	 * @param trxId　処理ID
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
