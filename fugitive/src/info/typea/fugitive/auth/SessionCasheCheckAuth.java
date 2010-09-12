package info.typea.fugitive.auth;

import info.typea.fugitive.filter.BasicAuthenticationFilter;
import info.typea.fugitive.logging.LogUtil;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 * 認証結果をセッションにキャッシュする。
 * <br/>
 * 認証結果をセッションに格納することにより、認証にコストがかかる場合など同一セッションであれば認証済みの状態を維持することができるため
 * 認証にかかるコスト(例えばデータベースに認証情報が格納されているのであれば、取得コスト)を軽減できる。
 * <br/>
 * ただし、セッションに情報を格納するため、キャッシュされる期間は、セッションの有効期間に依存する。
 * WEB-INF/web.xml の以下のセクションで、セッションのタイムアウトの時間を設定することができる。
 * 無期限にするには、 0 を指定する。
 * <pre style="color:blue">
 *  &lt;session-config&gt;
 *    &lt;session-timeout&gt;15&lt;/session-timeout&gt;
 *  &lt;/session-config&gt;
 * </pre>
 * また、キャッシュの有効期間は、セッションの有効期間のみに依存するのではなく、独自に有効期間を管理している。
 * setKeepCasheMinutes() メソッドを利用し、認証時からの時間(分)で指定する。
 * <br/>
 * これは、セッションの有効期間とキャッシュの有効時間を同じとした場合に、元情報との不一致が生じることが想定されるための対策である。
 * 例えば、認証OKで情報がキャッシュされた後、元の認証情報(例えばデータベース)でアクセス不可に設定したとしても、
 * その情報が反映されるのには、セッションのタイムアウトを待たねばならない。
 * キャッシュの有効期間を適度に調整することにより、この不一致が起こる時間を調整することを可能とする。
 * <br/>
 * {@link BasicAuthenticationFilter}から利用される場合、キャッシュタイムアウト~最認証の場合であって、
 * HTTPヘッダに埋め込まれてくるBasic認証情報を、再度認証処理をするのであり、ユーザ透過である。
 * ブラウザ側で、再度認証ダイアログが表示されるわけではない。
 * 
 * @author totec yagi
 */
public abstract class SessionCasheCheckAuth implements CheckAuth {
	/**
	 * 認証可否
	 */
	private AuthInfo authInfo;
	/**
	 * 認証処理実行時間
	 */
	private long authrizedTime;
	/**
	 * 認証キャッシュ時間(分)
	 */
	private long keepCasheMinutes = 15;

	
	/**
	 * 認証クラスでログを出力したい場合に使用する。
     * <ul>
     * <li>ログの設定は、log4j.properties ファイルで行う。</li>
     * </ul>
	 */
	protected Logger log = LogUtil.getLogger(SessionCasheCheckAuth.class);	

	/**
	 * 認証結果を格納するキー
	 */
	private final String SESSION_KEY = SessionCasheCheckAuth.class.getName();
	
	/**
	 * サブクラスで実装し、認証処理を行う
	 * @param username ユーザー名
	 * @param password パスワード
	 * @return 認証情報を保持したAuthInfoクラス
	 */
	public abstract AuthInfo authorize(String username, String password);

	/**
	 * 認証結果をセッションに格納するためのキー
	 * 独自のキーを利用したい場合、サブクラスでオーバーライドする
	 * @return 認証結果をセッションに格納するためのキー
	 */
	public String getSessionKey() {
		return SESSION_KEY;
	}
	
	/* (non-Javadoc)
	 * @see fugitive.fw.auth.AuthorizedCheck#isAuthorized(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.String, java.lang.String)
	 */
	public AuthInfo isAuthorized(ServletRequest request, ServletResponse response, String userName, String password) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		AuthInfo authInfo = new AuthInfo();
		
		try {
			long now = System.currentTimeMillis();
			HttpSession session = httpReq.getSession();
			SessionCasheCheckAuth val = (SessionCasheCheckAuth)session.getAttribute(getSessionKey());
			if (val != null) {
				authInfo = val.getAuthInfo();
			}
			
			// 以下の場合、認証を行う
			if (val == null                                       // 認証キャッシュクラスがない場合
			    || !authInfo.isAuthorized()                       // 認証が失敗している場合
				|| !authInfo.isMatchIdentity(userName, password)  // ユーザ名、パスワードが一致していない場合
			    ||  val.isExpiredCache(now)                       // 認証キャッシュが失効している場合
				) {
				
				// 認証を行う
				authInfo = authorize(userName, password);
				
				// 認証情報を登録
				this.setAuthInfo(authInfo);
				this.setAuthrizedTime(now);
				
				// 認証情報をキャッシュ
				session.setAttribute(getSessionKey(), this);
				
				log.info("authorization check." + logInfo(authInfo,now,httpReq));
			} else {
				log.debug("authorization check by cashe." + logInfo(authInfo,val.getAuthrizedTime(), httpReq));
			}
		} catch (Exception e) {
			// 認証キャッシュで例外が発生しても、システムエラーとはせずに、認証処理を行う
			log.error("authorization cashe doesn't work. exception occurred.", e);
			authInfo = authorize(userName, password);
			log.info("authorization check. without cashe." + logInfo(authInfo, System.currentTimeMillis(), httpReq));
		}
		return authInfo;
	}
	
	/**
	 * ログ出力時の付加情報書式統一
	 * @param result 認証結果
	 * @param timeMillis 認証時間
	 * @param user 認証ユーザ
	 * @param httpReq HttpServletRequestの参照
	 * @return ログ文字列
	 */
	private String logInfo(AuthInfo info, long timeMillis, HttpServletRequest httpReq) {
		return  "[" + ((info == null)?"nothing auth info":info.toString()) 
		     + ", time:" + new Date(timeMillis) 
		     + ", request:" + httpReq.getRequestURI()
		     + "]";
	}
	
    /**
     * キャッシュが有効期間内か否か 
     * @param now 現在時
     * @return 有効ならtrue
     */
    public boolean isExpiredCache(long now) {
		return ((now - this.getAuthrizedTime()) >= this.getKeepCasheMilliSeconds());
	}

	/**
	 * 認証状況を取得
	 * @return 認証済みならtrue
	 */
	public boolean isAuthorized() {
		return getAuthInfo().isAuthorized();
	}

	/**
	 * 認証状況を設定する
	 * @param authInfo 認証済みならtrue 
	 */
	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}
	
	/**
	 * 認証状況を設定する
	 */
	public AuthInfo getAuthInfo() {
		if (this.authInfo == null) {
			this.authInfo = new AuthInfo();
		}
		return this.authInfo;
	}
	
	/**
	 * 認証処理時間を取得する
	 * @return 認証処理時間
	 */
	public long getAuthrizedTime() {
		return authrizedTime;
	}

	/**
	 * 認証処理時間を設定する
	 * @param authrizedTime 認証処理時間を取得する
	 */
	public void setAuthrizedTime(long authrizedTime) {
		this.authrizedTime = authrizedTime;
	}

	/**
	 * キャッシュ保持期間を取得する
	 * @return キャッシュ保持期間(分)
	 */
	public long getKeepCasheMinutes() {
		return keepCasheMinutes;
	}
	
	/**
	 * キャッシュ保持期間を取得する
	 * @return キャッシュ保持期間(分)
	 */
	public long getKeepCasheMilliSeconds() {
		return getKeepCasheMinutes() * 60000;
	}
	
	/**
	 * キャッシュ保持期間を設定する
	 * @param keepCasheMinutes キャッシュ保持期間(分)
	 */
	public void setKeepCasheMinutes(long keepCasheMinutes) {
		this.keepCasheMinutes = keepCasheMinutes;
	}
}
