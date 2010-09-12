package info.typea.fugitive.database;

import javax.sql.DataSource;

/**
 * Datasource ¶¬ƒNƒ‰ƒX
 * <br/>
 * @author totec yagi
 */
public interface DatasourceFactory {
	/**
	 * Datasource ‚ğ¶¬‚µ‚Ä•Ô‚·B
	 * @return Datasource
	 */
	DataSource getDatasource();
}
