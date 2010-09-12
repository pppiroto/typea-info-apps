package info.typea.fugitive.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 * JNDIリソースからのサービス取得をカプセル化するクラス
 * 
 * @author totec yagi
 */
public class ServiceLocator {
	/**
	 * Singleton用インスタンス
	 */
	private static ServiceLocator me = null;
	/**
	 * ネーミングコンテキスト
	 */
	private InitialContext context = null;
	
	/**
	 * コンストラクタ
	 */
	private ServiceLocator(){}
	
	/**
	 * Singletonとしてのインスタンスを取得
	 * @return
	 */
	public static ServiceLocator getInstance(){
		if (me == null) {
			me = new ServiceLocator();
		}
		return me;
	}

	/**
	 * ネーミングコンテキストを取得
	 * @return ネーミングコンテキス
	 */
	private InitialContext getContext() {
		if (context == null) {
			try {
				context = new InitialContext();
			} catch (NamingException e) {
				throw new ServiceException(e);
			}
		}
		return context;
	}
	
	/**
	 * JNDIリソースから指定されたキーに対応するサービスを取得する。
	 * @param key JNDIリソースキー 
	 * @return 指定されたキーに対応するサービス
	 */
	public Object getService(String key) {
		Object ret = null;
		try {
			ret = getContext().lookup(key);
		} catch (NamingException e) {
			throw new ServiceException(e);
		}
		return ret;
	}
}
