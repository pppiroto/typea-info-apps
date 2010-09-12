package info.typea.fugitive.service;

import info.typea.fugitive.logging.LogUtil;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * JNDIリソースを検索するキーについて、プログラムで指定する値とJNDIを検索する値とを
 * マッピングした設定ファイルの値を保持する。
 * 
 * @author totec yagi
 */
public final class ServiceDictionary {
	/**
	 * JNDIリソースKeyの値を定義したプロパティファイル名 
	 */
	public static final String PROP_FILE_NAME = "service_dictionary.properties";
	
	/**
	 * ログ出力用クラス
	 */
	protected Logger log = LogUtil.getLogger(ServiceDictionary.class);
	
	/**
	 * Singleton用インスタンス保持
	 */
	private static ServiceDictionary me;
	
	/**
	 * JNDIリソースKeyの値を定義したプロパティ 
	 */
	private Properties prop = null;
	
	/**
	 * コンストラクタ
	 */
	private ServiceDictionary(){
		loadProperties();
	}

	/**
	 * Singletonとしてインスタンスを取得
	 * @return
	 */
	public static ServiceDictionary getInstance() {
		if (me == null) {
			me = new ServiceDictionary();
		}
		return me;
	}

	/**
	 * JNDIリソースKeyの値を定義したプロパティファイルを読込む
	 */
	private synchronized void loadProperties() {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			if (loader == null) {
				getClass().getClassLoader();
			}
			InputStream in = loader.getResourceAsStream(PROP_FILE_NAME);
			prop = new Properties();
			prop.load(in);
			
			if (log.isDebugEnabled()) {
				Enumeration keys = prop.keys();
				log.debug("loading service dictionary.");
				int num = 1;
				while(keys.hasMoreElements()) {
					String key = (String)keys.nextElement();
					log.debug(String.valueOf(num++)+ ":" + key + ":" + prop.getProperty(key));
				}
			}
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * JNDIリソースKeyにプロパティファイルで対応付けたキーを指定して、JNDIリソースKeyを取得する。
	 * @param dictionaryKey JNDIリソースKeyに応付けたキー
	 * @return JNDIリソースKey
	 */
	public String getResourceKeyName(String dictionaryKey) {
		String keyName = null;
		if (prop != null) {
			keyName = prop.getProperty(dictionaryKey);
		}
		return keyName;
	}
}
