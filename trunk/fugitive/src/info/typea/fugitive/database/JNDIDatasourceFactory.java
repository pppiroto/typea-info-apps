package info.typea.fugitive.database;

import info.typea.fugitive.service.ServiceDictionary;
import info.typea.fugitive.service.ServiceLocator;

import javax.sql.DataSource;


/**
 * JNDI����Datasource���擾���ĕԂ��N���X
 * <br/>
 * �N���X�t�@�C���̃��[�g�t�H���_�����ɁAservice_dictionary.properties �t�@�C�����쐬���A
 * jdbc.datasource=java:comp/env/jdbc/fugitive �Ƃ����`���ŁADataSource���擾����JNDI���\�[�X�̃L�[��ݒ肷��B
 * <br/>
 * �T�[�u���b�g�R���e�i�ɂāAJNDI���\�[�X��o�^����B
 * �Ⴆ�΁ATomcat�ŁADatasource���Ǘ�����ɂ́A
 * %TOMCAT_HOME%/conf/server.xml�ɁA
 * �ȉ��̂悤�ȃZ�N�V������ǉ�����B
 * �܂��A�ȉ��̗�ł́A%TOMCAT_HOME%/common/lib �ȉ��ɁAJDBC�h���C�o(oracle.jdbc.OracleDriver)��u���Ă����B
 *  
 *<pre style="color:blue;">
 * &lt;Host&gt;
 *       :
 *   &lt;Context docBase="fugitive" path="/fugitive" ��� �� ��� &gt;
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
