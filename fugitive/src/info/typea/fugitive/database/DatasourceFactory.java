package info.typea.fugitive.database;

import javax.sql.DataSource;

/**
 * Datasource 生成クラス
 * <br/>
 * @author totec yagi
 */
public interface DatasourceFactory {
	/**
	 * Datasource を生成して返す。
	 * @return Datasource
	 */
	DataSource getDatasource();
}
