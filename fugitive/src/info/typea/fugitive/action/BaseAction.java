package info.typea.fugitive.action;

import info.typea.fugitive.csv.CsvRecordable;
import info.typea.fugitive.logging.LogUtil;
import info.typea.fugitive.logic.ApplicationLogic;
import info.typea.fugitive.logic.Message;
import info.typea.fugitive.logic.Messages;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


/**
 * 業務アプリケーション基底Actionクラス
 * <br/>
 * StrutsのActionクラス(DispatchAction)を拡張。
 * 業務アプリケーションで共通使用する機能を実装する。
 * <br/>
 * 業務アプリケーションは、このクラスを継承したクラスを使用して、画面遷移や処理のコントロールを行う。
 * ただし、業務ロジックは、このクラスに実装せずに、{@link ApplicationLogic}を継承したクラスを作成し、そこに記述する。
 * このクラスからは、{@link ApplicationLogic}に処理を委譲する。
 * <br/>
 * Struts の DispatchActionクラスを継承しているため、指定されたリクエストパラメータに一致するメソッドがSubmit時に自動で呼び出される。
 * このことにより、execute()メソッドで、処理を振り分けたり、処理ごとに別のActionクラスを作成する必要がない。
 * リクエストパラメータが指定されない場合(直接URLをブラウザに入力した場合等)、unspecified()メソッドが呼ばれる。
 * 継承クラスでは、unspecified()メソッドをオーバーライドし、初期表示用の処理を行う必要がある。
 * <br/>
 * リクエストパラメータ名には任意の値を設定できるが、<span style="color:red">"trxId" とすること</span>。 対になる{@link BaseActionForm} では、"trxId" を前提としている。
 * 設定は、struts-config.xml の action 要素で、paremeter="trxId" とする。 
 * <pre style="color:blue">
 *   &lt;action-mappings&gt;
 *     &lt;action path="/nz01_01" 
 *                name="NZ01_01Form" 
 *                type="nwf.nz01.ZZ01_01Action"
 *                parameter="trxId"&gt;
 *       &lt;forward name="success"  path="/pages/nz01/nz01_01.jsp" /&gt;
 *       &lt;forward name="editform" path="/pages/nz01/nz01_02.jsp" /&gt;
 *       &lt;forward name="fail"     path="welcome" /&gt;
 *     &lt;/action&gt;
 *          ：
 * </pre>
 * ただし、実際には、業務ごとに struts-config.xml を分割するため、WEB-INF/struts-config.xml にではなく、
 * 分割先の設定ファイルに記述することになる。
 * 分割の設定は、WEB-INF/web.xml の以下のセクションの、param-value に、カンマ区切で指定する。
 * <pre style="color:blue">
 *   &lt;servlet&gt;
 *    &lt;servlet-name&gt;action&lt;/servlet-name&gt;
 *    &lt;servlet-class&gt;org.apache.struts.action.ActionServlet&lt;/servlet-class&gt;
 *    &lt;init-param&gt;
 *      &lt;param-name&gt;config&lt;/param-name&gt;
 *      &lt;!-- 業務App毎の設定ファイルを指定 --&gt;
 *      &lt;param-value&gt;
 *          /WEB-INF/struts-config.xml,
 *          /WEB-INF/config/nz01-config.xml
 *      &lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *    &lt;load-on-startup&gt;2&lt;/load-on-startup&gt;
 *  &lt;/servlet&gt;
 * </pre>
 * この設定ファイルの分割は、論理的には分割されない(実行時にマージされる)為、名前の衝突などには注意する必要がある。
 * <br/>
 * @see DispatchAction
 * @author totec yagi
 */
public abstract class BaseAction extends DispatchAction {
	
	/**
	 * 定型 ActionForward キー 処理成功
	 */
	public static final String SUCCESS = "success";
	/**
	 * 定型 ActionForward キー 処理失敗
	 */
	public static final String FAILURE = "failure";
	/**
	 * 定型 ActionForward キー Welcomeページへ
	 */
	public static final String WELCOME = "weclome";
	
	/**
	 * 業務アプリケーションActionクラスでログを出力したい場合に使用する。
     * <ul>
     * <li>BaseActionクラスでは、ログ出力のために、debug()、info()、warn()、error()メソッドを提供する。</li>
     * <li>ログの設定は、log4j.properties ファイルで行う。</li>
     * </ul>
     * @see BaseAction#debug(Object)
     * @see BaseAction#info(Object)
     * @see BaseAction#warn(Object)
     * @see BaseAction#error(Object)
	 */
	protected Logger log = LogUtil.getLogger(BaseAction.class);
	
    /** 
     * 業務アプリケーションActionクラスのIDを返す。
     */
    public abstract String getActionId();
    
	/**
     * 処理(trxId)を指定されずに、業務アプリケーションＡｃｔｉｏｎクラスが呼び出された場合の処理。 
	 * SubmitされたFormデータに、リクエストパラメータ("trxId")が存在しない場合に、呼び出される。
	 */
    public abstract ActionForward unspecified(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception;
    
    /**
     * 業務アプリケーションクラスで発生した、業務レベルのエラーを取り扱う共通処理。
     * 以下のように使用する。
     * <pre style="color:blue;">
     *    Messages errors = new Messages();
     *    if (isErrorOccured) { // 業務レベルエラーが発生
     *       err.add(new Message("errors.keyname"));
     *    }
     *    handleErrors(request, errors);
     * </pre>
     * <ul>
     * <li>エラーのキー(上記では"errors.keyname")に対応するメッセージは、MessageResources.native ファイルに指定する。</li>
     * <li>MessageResources.native ファイルは、ユニコードエスケープ(native2ascii)を行い、MessageResources.properties とする。</li>
     * </ul>
     * <br/>
     * 保存されたエラー情報は、JSP側で、以下のように取得することができる
     * <pre style="color:blue;">
     * &lt;%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %&gt;
     *             :
     *     &lt;html:messages id="errors" message="true" 
     *                                header="errors.header" 
     *                                footer="errors.footer"&gt;
     *       &lt;li&gt;&lt;bean:write name="errors"/&gt;&lt;/li&gt;
     *     &lt;/html:messages&gt;
     * </pre>
     * @param request 
     * @param errors エラーメッセージ情報を保持した Messages クラス
     * @see Messages
     */
    protected void handleErrors(HttpServletRequest request, Messages errors) {
        if (errors == null || errors.isEmpty()) {
            return;
        }
        ActionMessages actionMsgs = new ActionMessages();
        for (Message err : errors) {
            String key = err.getKey();
            Object[] params = err.getParameters();
            actionMsgs.add(ActionMessages.GLOBAL_MESSAGE, 
                    new ActionMessage(key, params));
            
            String msg = "action error has occurred, key[" + key + "]," 
                       + "params" + ((params == null)?"":Arrays.toString(params));
            error(msg);
        }
        saveMessages(request, actionMsgs);
    }
    
    /**
     * CSVファイルのダウンロードを行う。
     * <ul>
     * <li>CsvRecordableインターフェースを実装したクラスのインスタンスを1行としてCSVデータを出力します</li>
     * <li>CsvRecordableインターフェースを実装したクラスのリストを引数として渡してください</li>
     * <li>Action#execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)と同じ引数を持つメソッドから呼び出してください。</li>
     * <li>このとき、メソッドの戻値(ActionForward)はnullとしてください。</li>
     * </ul>
     * @param filename ファイル名
     * @param extention 拡張子
     * @param csvRecList CsvRecordable を実装したクラスのリスト 
     * @param req 
     * @param res
     * @throws IOException
     * @see CsvRecordable
     * @see Action#execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)
     * @see DispatchAction
     */
    protected void downloadCsvFile(String filename, String extention,
    		                       List csvRecList,
    		                       HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.reset();
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition","attachment;filename=" + filename + "." + extention);

		OutputStream writer = res.getOutputStream();
		if (csvRecList != null) {
			for (int i=0; i<csvRecList.size(); i++) {
				CsvRecordable csvRec = (CsvRecordable)csvRecList.get(i);
				writer.write(csvRec.toCsvRecord().getBytes());
				writer.write("\r\n".getBytes());
			}
		}
		writer.flush();    	
		writer.close();
    }

    /**
     * ログ出力のプレフィックスを指定する。
     * オーバーライドしない場合、業務アプリケーションIDを返す。
     * @return ログ出力のプレフィックス
     */
    protected String getLogPrefix() {
        return "[" + getActionId() + "] ";
    }
    /**
     * デバッグログの出力
     * @param msg 出力メッセージ
     * @param exception 対応例外
     */
    protected void debug(Object msg, Throwable exception) {
        log.debug(getLogPrefix() + msg, exception);
    }
    /**
     * デバッグログの出力
     * @param msg 出力メッセージ
     */
    protected void debug(Object msg) {
        log.debug(getLogPrefix() + msg);
    }
    /**
     * 情報ログの出力
     * @param msg 出力メッセージ
     * @param exception 対応例外
     */
    protected void info(Object msg, Throwable exception) {
        log.info(getLogPrefix() + msg, exception);
    }
    /**
     * 情報ログの出力
     * @param msg 出力メッセージ
     */
    protected void info(Object msg) {
        log.info(getLogPrefix() + msg);
    }
    /**
     * 警告ログの出力
     * @param msg 出力メッセージ
     * @param exception 対応例外
     */
    protected void warn(Object msg, Throwable exception) {
        log.warn(getLogPrefix() + msg, exception);
    }
    /**
     * 警告ログの出力
     * @param msg 出力メッセージ
     */
    protected void warn(Object msg) {
        log.warn(getLogPrefix() + msg);
    }
    /**
     * エラーログの出力
     * @param msg 出力メッセージ
     * @param exception 対応例外
     */
    protected void error(Object msg, Throwable exception) {
        log.error(getLogPrefix() + msg, exception);
    }
    /**
     * エラーログの出力
     * @param msg 出力メッセージ
     */
    protected void error(Object msg) {
        log.error(getLogPrefix() + msg);
    }
}
