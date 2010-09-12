package info.typea.fugitive.auth;

import info.typea.fugitive.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * ユーザ認証情報を保持する
 * 
 * @author totec yagi
 */
public class AuthInfo {
	/**
	 * セッション、リクエスト等の格納時のキー
	 */
	public static final String AUTH_INFO_KEY = AuthInfo.class.getName();

	/**
	 * 認証ユーザ名
	 */
	private String userName;

	/**
	 * 認証パスワード
	 */
	private String password;

	/**
	 * 認証成否
	 */
	private boolean authorized;

	/**
	 * ユーザーロールのリスト
	 */
	protected List<String> roleList;
	
	/**
	 * コンストラクタ
	 * @param isAuthorized 認証成否
	 * @param userName ユーザ名
	 * @param password パスワード
	 */
	public AuthInfo(boolean isAuthorized, String userName, String password) {
		this.authorized = isAuthorized;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * コンストラクタ
	 * @param isAuthorized 認証成否
	 */
	public AuthInfo(boolean isAuthorized) {
		this(isAuthorized, "", "");
	}
	
	/**
	 * コンストラクタ
	 */
	public AuthInfo() {
		this(false, "", "");
	}

	/**
	 * 認証時パスワードを取得する
	 * @return 認証時パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 認証時パスワードを設定する
	 * @param password 認証時パスワード
	 */
	public void setPassword(String password) {
		this.password = StringUtil.nvl(password);
	}

	/**
	 * 認証時ユーザ名を取得する
	 * @return 認証時ユーザ名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 認証時ユーザ名を設定する
	 * @param userName 認証時ユーザ名
	 */
	public void setUserName(String userName) {
		this.userName = StringUtil.nvl(userName);
	}
	
	/**
	 * キャッシュされた情報と、今回のユーザ名、パスワードが一致するか
	 * @param userName ユーザ名
	 * @param password パスワード
	 * @return 一致する場合true
	 */
	public boolean isMatchIdentity(String userName, String password) {
		return this.getUserName().equals(StringUtil.nvl(userName))
				&& this.getPassword().equals(StringUtil.nvl(password));
	}
	
	/**
	 * 認証成否の取得
	 * @return 認証成否
	 */
	public boolean isAuthorized() {
		return authorized;
	}

	/**
	 * 認証成否の設定
	 * @param authorized 認証成否
	 */
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	
	/**
	 * ユーザロールのリストを取得
	 * @return ユーザーロールのリスト
	 */
	public List<String> getRoleList() {
		if (this.roleList == null) {
			this.roleList = new ArrayList<String>();
		}
		return roleList;
	}
	
	/**
	 * ユーザーロールのリストをセット
	 * @param roleList ユーザーロールのリスト
	 */
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	
	/**
	 * ユーザーロールを追加する
	 * @param role ユーザーロール
	 */
	public void addRole(String role) {
		getRoleList().add(role);
	}
	
	/**
	 * 指定されたロールに属しているか
	 * @param role ユーザーロール
	 * @return 属している場合true
	 */
	public boolean isInRole(String role) {
		return getRoleList().contains(role);
	}

	@Override
	public String toString() {
		return   "user:" + this.userName
		       + ", authorized:" + ((this.authorized)?"yes":"no")
		       + ", role:" + Arrays.toString(getRoleList().toArray());
	}
}
