package com.a_yan_android.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.a_yan_android.lbmsg.LocationBasedMessageApplication;

/**
 * @author piroto
 * 
 * @see http://typea.info/blg/glob/2010/08/android-gae-windows7.html
 * @see http://typea.info/blg/glob/2010/08/android-google-gae.html
 * @see http://typea.info/blg/glob/2010/09/android-gae.html
 */
public class GoogleServiceUtil {
	
	private GoogleServiceUtil() {}
	
	public enum GOOGLE_ACCOUNT_TYPE {
		GAE 		{ public String toString() {return "ah"; 		} },
		CALENDAR 	{ public String toString() {return "cl";		} },
		GMAIL 		{ public String toString() {return "mail";		} },
		READER 		{ public String toString() {return "reader";	} },
		TALK 		{ public String toString() {return "talk";		} },
		YOUTUBE 	{ public String toString() {return "youtube";	} },
	}
	
	/**
	 * GAE での認証用URIを生成
	 * @param appPath
	 * @param authToken
	 * @return
	 */
	public static String defaultGAELoginUrl(String hostname, String appPath, String authToken) {
		return "http://" + hostname + "/_ah/login?continue=" + appPath + "&auth=" + authToken;
	}
	
	/**
	 * Google のサービスを実行するクラス
	 * 
	 * @author piroto
	 */
	public static class Executer {
		public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
		
		private Context context;
		private AccountManager accountManager;
	
		/**
		 * @param context
		 */
		public Executer(Context context) {
			this.context = context;
		}
		
		/**
		 * @return
		 */
		private AccountManager getAccountManager() {
			if (accountManager == null) {
				accountManager = AccountManager.get(this.context);
			}
			return accountManager;
		}
		
		/**
		 * Google アカウントを取得
		 * @return
		 */
		public Account[] getGoogleAccounts() {
			return getAccountManager().getAccountsByType(GOOGLE_ACCOUNT_TYPE);
		}
		
		/**
		 * 認証を行った後、Google サービスを実行させる
		 * @param account Google アカウント
		 * @param type アカウントタイプ
		 * @param callback 認証の要求が完了したときに呼び出されるコールバック
		 */
		public void execute(Account account, GOOGLE_ACCOUNT_TYPE type, AbstractGoogleServiceCallback callback) {
			
			getAccountManager().getAuthToken(
						account, 
						type.toString(), 
						false, 
						callback, 
						null		// nullの場合、callback はメインスレッド
						);
		}
		
		/**
		 * アカウントを削除
		 * @param account
		 * @param type
		 */
		public void removeAccount(Account account) {
			getAccountManager().removeAccount(account, null, null);
		}
	}
	
	/**
	 * Google サービスの認証部を実装したコールバック
	 * @author piroto
	 *
	 */
	public static abstract class AbstractGoogleServiceCallback implements AccountManagerCallback<Bundle> {
		private Context context;

		/**
		 * @param context
		 */
		public AbstractGoogleServiceCallback(Context context) {
			this.context = context;
		}
		
		/**
		 * @return
		 */
		public Context getContext() {
			return this.context;
		}
	
		/**
		 * @param bundle
		 * @param authToken
		 */
		public void removeAuthTokenCache(Bundle bundle, String authToken) {
			String accountType = bundle.getString(AccountManager.KEY_ACCOUNT_TYPE);
			AccountManager manager = AccountManager.get(getContext());
			manager.invalidateAuthToken(accountType, authToken);
		}
		
		/**
		 * ACSID を取得する
		 * @param bundle
		 * @return
		 */
		private String getACSID(Bundle bundle){
			final int RETRY_MAX = 3;
			boolean isValidToken = false;
			
			int    retry = 0;
			String acsid = null;
			try {
				DefaultHttpClient httpClient = null;
				while (!isValidToken) {

					String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
					httpClient = new DefaultHttpClient();
					httpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);

					String uri = getAuthenticateUri(authToken);
					HttpGet httpAuthGetRequest = new HttpGet(uri);
					HttpResponse httpAuthResponse = httpClient.execute(httpAuthGetRequest);
					
					int status = httpAuthResponse.getStatusLine().getStatusCode();
					if (status == HttpStatus.SC_INTERNAL_SERVER_ERROR) {

						// 認証エラーの場合、ログにレスポンスの内容を出力
						try {
							StringBuilder buf = new StringBuilder();
							buf.append(String.format("status:%d\n",status));
							InputStream in = httpAuthResponse.getEntity().getContent();
							BufferedReader reader = new BufferedReader(new InputStreamReader(in));
							String l = null;
							while((l = reader.readLine()) != null) {
								buf.append(l + "\n");
							}			
							Log.e(LocationBasedMessageApplication.TAG,buf.toString());
						} catch(Exception e) {
						}
						
						// 認証トークンキャッシュの削除
						//   期限切れ、もしくは、認証リクエストが無効になるような、認証トークンが見つかった場合、
						//   キャッシュのクリアを行う
						removeAuthTokenCache(bundle, authToken);

					} else {
						isValidToken = true;
					}
					retry++;
					if (retry > RETRY_MAX) {
						break;
					}
				}
				if (isValidToken) {
					// 認証エラーでなければ、Cookie から ACSIDを取得する
					for (Cookie cookie : httpClient.getCookieStore().getCookies()) {
						if ("SACSID".equals(cookie.getName()) ||
							"ACSID".equals(cookie.getName())) {
							acsid = cookie.getName() + "=" + cookie.getValue();
						}
					}					
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return acsid;
		}
		
		@Override
		public void run(AccountManagerFuture<Bundle> future) {

			HttpResponse httpBodyResponse = null;
			try {
				Bundle bundle = future.getResult();
				Intent intent = (Intent)bundle.get(AccountManager.KEY_INTENT);
				if (intent != null) {
					// 認証されていない場合、認証画面を起動
					this.context.startActivity(intent);
				} else {

					DefaultHttpClient httpClient = new DefaultHttpClient();
					String acsid = getACSID(bundle);
					// 認証が正常に行われ、ACSIDが取得できたら、サービスのリクエストをPOSTし、
					// レスポンスを取得
					if (acsid != null) {
						HttpPost httpPost = request();
						httpPost.setHeader("Cookie", acsid);
						httpBodyResponse = httpClient.execute(httpPost);
					} 
				}
					
			} catch (OperationCanceledException e) {
				// TODO
				e.printStackTrace();
			} catch (AuthenticatorException e) {
				// TODO
				e.printStackTrace();
			} catch (IOException e) {
				// TODO
				e.printStackTrace();
			} finally {
				// コールバックする。認証などでエラーが発生している場合、レスポンスは null
				callback(httpBodyResponse);
			}
		}

		/**
		 * 認証を行うURLを指定
		 * たとえばGAEであれば、以下のようなURLを返す
		 * <pre>"http://typea-msg-brd.appspot.com/_ah/login?continue=/&auth=" + authToken;</pre>
		 * @param authToken
		 * @return
		 */
		public abstract String getAuthenticateUri(String authToken);
		
		/**
		 * 認証に成功した後、実際に行いたいリクエストを実装する
		 * @return
		 */
		public abstract HttpPost request();
		
		/**
		 * request() レスポンスを受け取る
		 * @param response
		 */
		public abstract void callback(HttpResponse response);
	}
}