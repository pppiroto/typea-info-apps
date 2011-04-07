package com.a_yan_android.lib;

import java.io.IOException;

import org.apache.http.HttpResponse;
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

public class GoogleServiceUtil {
	
	private GoogleServiceUtil() {}
	
	public enum GOOGLE_AUTH_TOKEN {
		GAE 		{ public String toString() {return "ah"; 		} },
		CALENDAR 	{ public String toString() {return "cl";		} },
		GMAIL 		{ public String toString() {return "mail";		} },
		READER 		{ public String toString() {return "reader";	} },
		TALK 		{ public String toString() {return "talk";		} },
		YOUTUBE 	{ public String toString() {return "youtube";	} },
	}
	
	/**
	 * GAE での認証用URIを返す
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
		AccountManager accountManager;
	
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
		 * @return
		 */
		public Account[] getGoogleAccounts() {
			return getAccountManager().getAccountsByType(GOOGLE_ACCOUNT_TYPE);
		}
		
		/**
		 * @param account
		 * @param type
		 * @param callback
		 */
		public void execute(Account account, GOOGLE_AUTH_TOKEN type, AccountManagerCallback<Bundle> callback) {
			getAccountManager().getAuthToken(
					account, 
					type.toString(), 
					false, 
					callback, 
					null);
			return;
		}
	}
	
	/**
	 * Google サービスの認証部を実装したコールバック
	 * @author piroto
	 *
	 */
	public static abstract class AbstractGoogleServiceCallback implements AccountManagerCallback<Bundle> {
		private Context context;
		public AbstractGoogleServiceCallback(Context context) {
			this.context = context;
		}
		public Context getContext() {
			return this.context;
		}
		@Override
		public void run(AccountManagerFuture<Bundle> future) {
			Bundle bundle;
			
			try {
				bundle = future.getResult();
				Intent intent = (Intent)bundle.get(AccountManager.KEY_INTENT);
				if (intent != null) {
					this.context.startActivity(intent);
				} else {
					String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
					DefaultHttpClient httpClient = getHttpClient();
					httpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);

					String uri = getAuthenticateUri(authToken);
					HttpGet httpGet = new HttpGet(uri);
					httpClient.execute(httpGet);
					
					String acsid = null;
					for (Cookie cookie : httpClient.getCookieStore().getCookies()) {
						if ("SACSID".equals(cookie.getName()) ||
							"ACSID".equals(cookie.getName())) {
							acsid = cookie.getName() + "=" + cookie.getValue();
						}
					}					

					HttpPost httpPost = request();
					httpPost.setHeader("Cookie", acsid);
					
					callback(httpClient.execute(httpPost));
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
			}
		}

		public DefaultHttpClient getHttpClient() {
			return new DefaultHttpClient();
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