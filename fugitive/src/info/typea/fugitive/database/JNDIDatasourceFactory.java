package info.typea.fugitive.database;

import info.typea.fugitive.service.ServiceDictionary;
import info.typea.fugitive.service.ServiceLocator;

import javax.sql.DataSource;


/**
 * JNDIからDatasourceを取得して返すクラス
 * <br/>
 * クラスファイルのルートフォルダ直下に、service_dictionary.properties ファイルを作成し、
 * jdbc.datasource=java:comp/env/jdbc/fugitive という形式で、DataSourceを取得するJNDIリソースのキーを設定する。
 * <br/>
 * サーブレットコンテナにて、JNDIリソースを登録する。
 * 例えば、Tomcatで、Datasourceを管理するには、
 * %TOMCAT_HOME%/conf/server.xmlに、
 * 以下のようなセクションを追加する。
 * また、以下の例では、%TOMCAT_HOME%/common/lib 以下に、JDBCドライバ(oracle.jdbc.OracleDriver)を置いておく。
 *  
 *<pre style="color:blue;">
 * &lt;Host&gt;
 *       :
 *   &lt;Context docBase="fugitive" path="/fugitive" ･･･ 略 ･･･ &gt;
 *     &lt;Resource name="jdbc/fugitive" auth="Container"
 *                  type="javax.sql.DataSource" driverClassName="oracle.jdbc.OracleDriver"
 *                  url="jdbc:oracle:thin:@hostname:1521:schema"
 *                  username="username" password="password" maxActive="20" maxIdle="10"
 *                  maxWait="-1"/&gt; 
 *   &lt;/Context&gt;
 * &lt;/Host&gt;
 * </pre>
 * @author totec yagi
 */
public class JNDIDatasourceFactory implements DatasourceFactory {
	private String dictionaryKey = "jdbc.datasource";
	/**
	 * 
	 */
	public JNDIDatasourceFactory() {}

	/**
	 * @param resourceKeyName
	 */
	public JNDIDatasourceFactory(String dictionaryKey) {
		this.dictionaryKey =dictionaryKey;
	}
	
	/* (non-Javadoc)
	 * @see fugitive.fw.database.DatasourceFactory#getDatasource()
	 */
	public DataSource getDatasource() {
		String key = ServiceDictionary.getInstance().getResourceKeyName(dictionaryKey);
		return (DataSource)ServiceLocator.getInstance().getService(key);
	}
}
