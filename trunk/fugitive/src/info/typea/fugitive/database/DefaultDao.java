package info.typea.fugitive.database;

/**
 * JNDIからDatasourceを取得するDaoクラス
 * <br/>
 * サーブレットコンテナで管理されているDatasourceを、取得することを想定している。
 * コンテナ側の設定は {@link JNDIDatasourceFactory}を参照。
 * 
 * @see JNDIDatasourceFactory
 * @author totec yagi
 */
public class DefaultDao extends Dao {
	/**
	 * コンストラクタ
	 * @param datasourceFactory Ｄａｔａｓｏｕｒｃｅ
	 */
	private DefaultDao(DatasourceFactory datasourceFactory) {
		super(datasourceFactory);
	}
	/**
	 * デフォルトコンストラクタ
	 */
	public DefaultDao() {
		this(new JNDIDatasourceFactory());
	}
	
	/**
	 * コンストラクタ
	 * JNDIリソースキーマッピングファイルのキーを指定する
	 * @param dictionaryKey 
	 */
	public DefaultDao(String dictionaryKey) {
		this(new JNDIDatasourceFactory(dictionaryKey));
	}
}
