package info.typea.fugitive.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ユーザー認証用インターフェース
 * <br/>
 * サーブレットコンテナの機能を利用せず、BasicAuthorizationFilterクラスを利用してBasic認証を行う場合、
 * このインターフェースを実装したクラスを以下のようにweb.xmlで指定して、ユーザ認証を行う。
 * 以下の例では、CheckAuthImpl が実装クラス。
 * <br/>
 * WEB-INF/web.xml にて、以下例のようにフィルターの設定をおこなう。
 * <pre style="color:blue;">
 *   &lt;filter&gt;
 *     &lt;filter-name&gt/BasicAuthorizationFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt/fugitive.fw.filter.BasicAuthorizationFilter&lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt/authorizeClassName&lt;/param-name&gt;
 *       &lt;param-value&gt/nwf.nz00.CheckAuthImpl&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt/realmName&lt;/param-name&gt;
 *       &lt;param-value&gt/fugitive&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt/BasicAuthorizationFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre>
 * 
 * @see info.typea.fugitive.filter.BasicAuthenticationFilter
 * @author totec yagi
 */
public interface CheckAuth {
	/**
     * ユーザ認証を行う
     * @param request HttpServletRequest
     * @param response HttpServletResponse
	 * @param username ユーザID
	 * @param password パスワード
	 * @return 認証情報を保持したAuthInfoクラス
	 */
	AuthInfo isAuthorized(ServletRequest request, ServletResponse response, String username, String password);

	/**
	 * 認証状況を設定する
	 * @param authInfo 認証済みならtrue 
	 */
	public void setAuthInfo(AuthInfo authInfo);
	
	/**
	 * 認証状況を設定する
	 */
	public AuthInfo getAuthInfo();
}
