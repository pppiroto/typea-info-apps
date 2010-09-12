package info.typea.fugitive.database;

import info.typea.fugitive.logging.LogUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

/**
 * 業務アプリケーションからのデータアクセスをカプセル化する
 * <br/>
 * SQLをプロパティファイルから読込、インスタンスで保持することができる。
 * SQLは、PreparedStatement で使用する場合のSQLと同様に、SQL文中の"?"をプレースホルダーとして、
 * そこにパラメータを代入した結果としてのSQLが実行される。
 * <br/>
 * トランザクションを使用する場合、各データ取得、更新メソッドのうち、引数にjava.sql.Connection をとるものを使用する。
 * 引数として Connectionを渡す前に、Connectionの自動コミットモードをOFFにしておく。
 * beginTransaction()メソッドにより、Connectionの自動コミットモードをOFFとしたConnectionを取得することができる。
 * <br/>
 * 基本的な使用法は、
 * <ol>
 * <li>{@link ValueBean}を実装した、モデルクラス(RDBMSのレコードにあたる)を作成。</li>
 * <li>Daoクラス(このクラス)を継承した業務Daoクラスを作成</li>
 * <li>getBean(), getBeanList()に、上記で作成したモデルクラス、SQL、パラメータなどを与えることでSQLを実行する。</li>
 * </ol>
 * となる。
 * 
 * Datasource の取得方法は、コンストラクタに渡す、{@link DatasourceFactory}の実装クラスに委譲する。
 * 
 * @see DatasourceFactory
 * @author totec yagi
 */
public class Dao {
	/**
	 * SQL文ファイルのデフォルト名
	 */
	public static final String SQL_FILE_NAME = "sql.properties";
	/**
	 * Daoクラスでログを出力したい場合に使用する。
     * <ul>
     * <li>ログの設定は、log4j.properties ファイルで行う。</li>
     * </ul>
	 */
	protected Logger log = LogUtil.getLogger(Dao.class);
	/**
	 * Datasource 生成クラス
	 */
	private DatasourceFactory datasourceFactory;
	/**
	 * SQL文を保持する
	 */
	private Map sqlMap = null;
	/**
	 * コンストラクタ
	 */
	public Dao(DatasourceFactory datasourceFactory) {
		String sqlProp = getSqlPropertyPath();
		if (sqlProp != null) {
			loadSql(sqlProp);
		}
		this.setDatasourceFactory(datasourceFactory);
	}
	/**
	 * このクラスが保持する Datasource 生成クラスを返す。
	 * @return Datasource 生成クラス
	 */
	public DatasourceFactory getDatasourceFactory() {
		return datasourceFactory;
	}

	/**
	 * このクラスが保持する Datasource 生成クラスを設定する。
	 * @param datasourceFactory Datasource 生成クラス
	 */
	public void setDatasourceFactory(DatasourceFactory datasourceFactory) {
		this.datasourceFactory = datasourceFactory;
	}
	
	/**
     * SQL文が定義されたプロパティファイルのパスを返す。
	 * @return SQL文が定義されたプロパティファイルのパス
	 */
	public String getSqlPropertyPath() {
		return "/" + getClass().getPackage().getName().replace(".", "/") 
		      +"/" + SQL_FILE_NAME;
	}
	
	/**
     * DataSourceを取得する。
	 * @return DataSource
	 */
	public DataSource getDataSource() {
		return getDatasourceFactory().getDatasource();
	}

	/**
     * 指定されたパスのプロパティファイルをSQL定義として読みこむ。
	 * @param path
	 */
	public void loadSql(String path) {
		try {
			sqlMap = QueryLoader.instance().load(path);
		} catch (IOException e) {
			throw new DatabaseAccessException(e);
		}
	}
	
	/**
     * 読込まれているSQL文からキーに一致するものを取得する。
	 * @param key SQL文のキー
	 * @return SQL文
	 */
	public String getSql(String key) {
		if (key == null || sqlMap == null) {
			return null;
		}
		return (String)sqlMap.get(key);
	}
	
	/**
     * 指定されたSQL文を実行し、結果からBeanのListを作成する。
	 * @param beanClazz SQL文実行結果を格納するBeanクラス
	 * @param sql SQL文
	 * @return SQL文を実行した結果をBeanとしてListに格納したもの
	 */
	public List getBeanList(Class beanClazz, String sql) {
		return getBeanList(beanClazz, sql, null);
	}
    
	/**
     * パラメータを指定し、SQL文を実行し、結果からBeanのListを作成する。
     * SQL文中の "?"が、先頭から順に、引数paramsに置き換えられる。
     * @param beanClazz SQL文実行結果を格納するBeanクラス
     * @param sql SQL文
	 * @param params SQL文に差し込むパラメータ
     * @return SQL文を実行した結果をBeanとしてListに格納したもの
	 */
	public List getBeanList(Class beanClazz, String sql, Object[] params) {
		return getBeanList(null, beanClazz, sql, params);
	}

	/**
     * パラメータを指定し、SQL文を実行し、結果からBeanのListを作成する。
     * SQL文中の "?"が、先頭から順に、引数paramsに置き換えられる。
     * トランザクションを明示的に制御したい場合、自動コミットモードを無効にした、Connectionオブジェクトを引数として渡す。
     * トランザクションの終了は、Connectionオブジェクトに対して、commit、rollbackを呼び出すことで行う。
	 * @param conn Connection トランザクション管理用接続
     * @param beanClazz SQL文実行結果を格納するBeanクラス
     * @param sql SQL文
	 * @param params SQL文に差し込むパラメータ
     * @return SQL文を実行した結果をBeanとしてListに格納したもの
	 */
	public List getBeanList(Connection conn, Class beanClazz, String sql, Object[] params) {
		boolean isInTransaction = (conn != null);
		if (log.isDebugEnabled()) {
			log.debug(getLogPrefix() + "beanlist:sql[" + sql + "],params:" +  Arrays.toString(params));
		}
		
		List result = null; 
		try {
			ResultSetHandler rsh = new BeanListHandler(beanClazz);
			QueryRunner runner = new QueryRunner();
			if (conn == null) {
				conn = getDataSource().getConnection();
			}
			if (params == null) {
				result = (List)runner.query(conn, sql, rsh);
			} else {
				result = (List)runner.query(conn, sql, params, rsh);
			}
		} catch (Exception e) {
			throw new DatabaseAccessException(e);
		} finally {
			if (!isInTransaction) {
				DbUtils.closeQuietly(conn);
			}
		}
		if (log.isDebugEnabled()) {
			if (result == null) {
				log.debug(getLogPrefix() + "beanlist:result[null]");
			} else {
				log.debug(getLogPrefix() + "beanlist:match[" + result.size() + "]");
			}
		}
		return result;
	}

	/**
     * 指定されたSQL文を実行し、結果からBeanを作成する。
     * @param beanClazz SQL文実行結果を格納するBeanクラス
     * @param sql SQL文
	 * @return SQL文実行結果を格納したBean
	 */
	public Object getBean(Class beanClazz, String sql) {
		return getBean(beanClazz, sql, null);
	}
    
	/**
     * パラメータを指定し、SQL文を実行し、結果からBeanを作成する。
     * SQL文中の "?"が、先頭から順に、引数paramsに置き換えられる。
     * @param beanClazz SQL文実行結果を格納するBeanクラス
     * @param sql SQL文
     * @param params SQL文に差し込むパラメータ
     * @return SQL文実行結果を格納したBean
	 */
	public Object getBean(Class beanClazz, String sql, Object[] params) {
		return getBean(null, beanClazz, sql, params);
	}

	/**
     * パラメータを指定し、SQL文を実行し、結果からBeanを作成する。
     * SQL文中の "?"が、先頭から順に、引数paramsに置き換えられる。
     * トランザクションを明示的に制御したい場合、自動コミットモードを無効にした、Connectionオブジェクトを引数として渡す。
     * トランザクションの終了は、Connectionオブジェクトに対して、commit、rollbackを呼び出すことで行う。
	 * @param conn Connection トランザクション管理用接続
     * @param beanClazz SQL文実行結果を格納するBeanクラス
     * @param sql SQL文
     * @param params SQL文に差し込むパラメータ
     * @return SQL文実行結果を格納したBean
	 */
	public Object getBean(Connection conn, Class beanClazz, String sql, Object[] params) {
		boolean isInTransaction = (conn != null);
		
		if (log.isDebugEnabled()) {
			log.debug(getLogPrefix() + "bean:sql[" + sql + "],params:" +  Arrays.toString(params));
		}
		Object result = null; 
		try {
			ResultSetHandler rsh = new BeanHandler(beanClazz);
			QueryRunner runner = new QueryRunner();
			if (conn == null) {
				conn = getDataSource().getConnection();
			}
			if (params == null) {
				result = runner.query(conn, sql, rsh);
			} else {
				result = runner.query(conn, sql, params, rsh);
			}
			
		} catch (Exception e) {
			throw new DatabaseAccessException(e);
		} finally {
			if (!isInTransaction) {
				DbUtils.closeQuietly(conn);
			}
		}
		if (log.isDebugEnabled()) {
			if (result == null) {
				log.debug(getLogPrefix() + "bean:result[null]");
			} else {
				log.debug(getLogPrefix() + "bean:match[" + result.toString()+ "]");
			}
		}
		return result;
	}
	
	/**
     * 指定されたパラメータで、DML SQL文(update,insert,delete)を実行する。
     * SQL文中の "?"が、先頭から順に、引数paramsに置き換えられる。
	 * @param sql SQL文
	 * @param params パラメータ
	 * @return 対象件数
	 */
	public int update(String sql, Object[] params) {
        return update(null, sql, params);
    }

	/**
     * 指定されたパラメータで、DML SQL文(update,insert,delete)を実行する。
     * SQL文中の "?"が、先頭から順に、引数paramsに置き換えられる。
     * トランザクションを明示的に制御したい場合、自動コミットモードを無効にした、Connectionオブジェクトを引数として渡す。
     * トランザクションの終了は、Connectionオブジェクトに対して、commit、rollbackを呼び出すことで行う。
	 * conn Connection トランザクション管理用接続
	 * @param sql SQL文
	 * @param params パラメータ
	 * @return 対象件数
	 */
	public int update(Connection conn, String sql, Object[] params) throws DatabaseAccessException {
		boolean isInTransaction = (conn != null);
		
		if (log.isDebugEnabled()) {
			log.debug(getLogPrefix() + "update:sql[" + sql + "],params:" +  Arrays.toString(params));
		}
		int ret = -1;
        try {
        	QueryRunner runner = new QueryRunner();
        	if (conn == null) {
        		conn = getDataSource().getConnection();
        	}
        	ret = runner.update(conn, sql, params);
        } catch (Exception e) {
            throw new DatabaseAccessException(e);
        } finally {
			if (!isInTransaction) {
				DbUtils.closeQuietly(conn);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug(getLogPrefix() + "update:result[" + ret + "]");
		}
        return ret;
    }
	
	/**
	 * 自動コミットモードを無効にしたConnectionを取得する
	 * @return 自動コミットモードを無効にしたConnection
	 */
	public Connection beginTransaction() {
		return beginTransaction(null);
	}
	
	/**
	 * 自動コミットモードを無効にしたConnectionを取得する
	 * Connection の分離レベルの変更を試みる。
	 * 引数 isolationLevel として、以下のいずれかをラップしたIntegerオブジェクトを渡す。
	 * <br/>
	 * <ul>
	 * <li>java.sql.Connection.TRANSACTION_NONE</li>
	 * <li>java.sql.TRANSACTION_READ_COMMITTED</li>
	 * <li>java.sql.TRANSACTION_READ_UNCOMMITTED</li>
	 * <li>java.sql.TRANSACTION_REPEATABLE_READ</li>
	 * <li>java.sql.TRANSACTION_SERIALIZABLE</li>
	 * </ul>
	 * null の場合は設定変更を試みない。
	 * @param isolationLevel Connectionの分離レベルについての定数をIntegerでラップしたもの。
	 * @return 自動コミットモードを無効にしたConnection
	 * @see Connection
	 */
	public Connection beginTransaction(Integer isolationLevel) {
		log.debug(getLogPrefix() + "begin tranzaction.");
		Connection conn = getConnection(false);
		
		// 分離レベルの設定を試行する
		if (isolationLevel != null) {
			try {
				conn.setTransactionIsolation(isolationLevel.intValue());
			} catch (SQLException e) {
				throw new DatabaseAccessException(e);
			}
		}
		
		// 分離レベルのログ出力
		if (log.isDebugEnabled()) {
			try {
				int lvl = conn.getTransactionIsolation();
				String lbl = "";
				switch (lvl) {
					case Connection.TRANSACTION_NONE:			  lbl = "NONE";             break;             
					case Connection.TRANSACTION_READ_COMMITTED:   lbl = "READ COMMITTED";   break;
					case Connection.TRANSACTION_READ_UNCOMMITTED: lbl = "READ UNCOMMITTED"; break;
					case Connection.TRANSACTION_REPEATABLE_READ:  lbl = "REPEATABLE READ";  break;
					case Connection.TRANSACTION_SERIALIZABLE:	  lbl = "SERIALIZABLE";     break;
					default:
				}
				log.debug(getLogPrefix() + "isolation level is " + lbl);
			} catch (SQLException e) {
				log.debug(getLogPrefix() + "can't get isolation level.",e);
			}
		}
		return conn;
	}

	/**
     * ログ出力のプレフィックスを指定する。
     * @return ログ出力のプレフィックス
     */
    protected String getLogPrefix() {
        return "[" + this.getClass().getName() + "] ";
    }	

	/**
	 * DataSourceからConnectionを取得する
	 * @return Connection
	 */
	public Connection getConnection() {
		return getConnection(true);
	}
	
	/**
	 * DataSourceからConnectionを取得する
	 * @param autocommit 自動コミットモードの指定
	 * @return Connection
	 */
	public Connection getConnection(boolean autocommit) {
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			changeAutoCommitMode(conn, autocommit);
		} catch (SQLException e) {
			throw new DatabaseAccessException(e);
		}
		return conn;
	}

	/**
	 * トランザクションのコミットを行う。
	 * @param conn 対象Connection
	 */
	public void commitTranzaction(Connection conn) {
		log.debug(getLogPrefix() + "cmmit tranzaction.");
		changeAutoCommitMode(conn, true);
		DbUtils.commitAndCloseQuietly(conn);
	}
	
	/**
	 * トランザクションのロールバックを行う。
	 * @param conn 対象Connection
	 */
	public void rollbackTranzaction(Connection conn) {
		log.debug(getLogPrefix() + "rollback tranzaction.");
		changeAutoCommitMode(conn, true);
		DbUtils.rollbackAndCloseQuietly(conn);
	}
	
	/**
	 * Connection の自動コミットモードを変更する
	 * @param conn 対象Connection
	 * @param autocommit 自動コミットモード
	 */
	private void changeAutoCommitMode(Connection conn, boolean autocommit) {
		try {
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			log.error(getLogPrefix() + "change autocommit mode.",e);
		}
	}
}
