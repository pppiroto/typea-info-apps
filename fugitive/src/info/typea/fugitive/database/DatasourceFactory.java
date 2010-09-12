package info.typea.fugitive.database;

import javax.sql.DataSource;

/**
 * Datasource �����N���X
 * <br/>
 * @author totec yagi
 */
public interface DatasourceFactory {
	/**
	 * Datasource �𐶐����ĕԂ��B
	 * @return Datasource
	 */
	DataSource getDatasource();
}
